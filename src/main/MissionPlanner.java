package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.List;
import java.util.Observable;

public class MissionPlanner extends Application {
    private double width = 1280;
    private double height = 720;
    private double maxWidth = 1920;
    private double maxHeight = 1080;
    private double scaling = 0;
    public Canvas canvas = new Canvas();
    public Button done;
    public Button resetPath;
    public Button createUAV;
    public Button createCam;
    public ChoiceBox uavChooser;
    public Scene mainScene;
    public Stage primaryStage;
    public Group flightPoly;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Mission Planner V1.0");
        primaryStage.setMaxWidth(maxWidth);
        primaryStage.setMaxHeight(maxHeight);
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.isResizable();

        //Set scene and show stage
        Text title = new Text("Mission Planner V1.0");
        title.setFont(Font.font("Bebas",FontPosture.ITALIC, 36));
        title.setFill(Color.WHITE);

        //Label label = new Label("Done");
        //label.setFont(Font.font("Arial"));
        done =  new Button("Done");
        done.setFont(Font.font("Arial", FontPosture.ITALIC, 16));
        done.setTextFill(Color.WHITE);
        resetPath =  new Button("Reset Path");
        resetPath.setFont(Font.font("Arial", FontPosture.ITALIC, 16));
        resetPath.setTextFill(Color.WHITE);
        createUAV = new Button("Create UAV");
        createCam = new Button("Create Camera");
        uavChooser = new ChoiceBox();
        uavChooser.getItems().add(populateUAVs());
        uavChooser.getSelectionModel().selectFirst();

        ScrollPane flightTab = FlightSettings.createFlightSetup();
        TabPane tp = new TabPane();
        Tab flightSettings = new Tab("Flight settings", flightTab);
        Tab cameraSettings = new Tab("Camera settings");

        CreateUAV uavCreator =  new CreateUAV();
        CreateCamera camCreator = new CreateCamera();
        tp.getTabs().addAll(flightSettings, cameraSettings);
        BorderPane bp = new BorderPane();
        StackPane sp = new StackPane();

        Image map = new Image("assets/map.png");
        ImageView iv = new ImageView(map);

        //JAVA MAP
        JXMapViewer mapViewer = new JXMapViewer();
        JXMapKit mapKit = new JXMapKit();
        JToolTip toolTip = new JToolTip();
        toolTip.setComponent(mapKit.getMainMap());


        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        tileFactory.setThreadPoolSize(8);

        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        GeoPosition whitby = new GeoPosition(54.48860179430841, -0.6231669702867165);
        mapViewer.setZoom(8);
        mapViewer.setAddressLocation(whitby);

        mapViewer.addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(java.awt.event.MouseEvent e) {
               if(e.getButton() == java.awt.event.MouseEvent.BUTTON3){
                   Point p = e.getPoint();
                   GeoPosition geo = mapViewer.convertPointToGeoPosition(p);
                   System.out.println("x:" + geo.getLatitude()+"Y:"+geo.getLongitude());
               }
           }

           @Override
           public void mousePressed(java.awt.event.MouseEvent e) {

           }

           @Override
           public void mouseReleased(java.awt.event.MouseEvent e) {

           }

           @Override
           public void mouseEntered(java.awt.event.MouseEvent e) {

           }

           @Override
           public void mouseExited(java.awt.event.MouseEvent e) {

           }
       });
        mapKit.setTileFactory(tileFactory);
        mapKit.getMainMap().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {

                JXMapViewer map = mapKit.getMainMap();

                // convert to world bitmap
                Point2D worldPos = map.getTileFactory().geoToPixel(whitby, map.getZoom());

                // convert to screen
                Rectangle rect = map.getViewportBounds();
                int sx = (int) worldPos.getX() - rect.x;
                int sy = (int) worldPos.getY() - rect.y;
                Point screenPos = new Point(sx, sy);

                // check if near the mouse
                if (screenPos.distance(e.getPoint()) < 20)
                {
                    screenPos.x -= toolTip.getWidth() / 2;

                    toolTip.setLocation(screenPos);
                    toolTip.setVisible(true);
                }
                else
                {
                    toolTip.setVisible(false);
                }
            }
        });



        SwingNode sn = new SwingNode();
        //sn.setScaleX(500);
        //sn.setScaleY(500);
        sn.setContent(mapViewer);

        iv.setScaleX(1.8);
        iv.setScaleY(1.8);
        GridPane gp = new GridPane();
        gp.setHgap(50);
        //gp.setPadding(new Insets(10,50,10,50));
        gp.add(done,0,0);
        gp.add(resetPath, 1,0);
        gp.add(createUAV,2,0);
        gp.add(uavChooser,3,0);
        sp.getChildren().add(sn);
        //sp.getChildren().add(canvas);
        //PathDrawer3D pd3d = new PathDrawer3D((int)map.getWidth(),(int)map.getHeight());
        //sp.getChildren().add(pd3d.ss);
        //sp.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
        //sp.setMaxHeight(680);
        sp.setPrefSize(1280, 650);
        sp.requestFocus();

        SubScene ss =  new SubScene(gp,1280,20);
        bp.setTop(title);
        bp.setLeft(tp);
        bp.setRight(sp);
        bp.setBottom(gp);

        mainScene = new Scene(bp, primaryStage.getWidth(), primaryStage.getHeight(), Color.web("rgb(42, 45, 48)"));
        canvas.setWidth(bp.getWidth());
        canvas.setHeight(bp.getHeight()-20-60);
        System.out.println(canvas.getWidth() + ", " + canvas.getHeight());

        PathDrawer pathDrawer = new PathDrawer(canvas);
        mainScene.setUserAgentStylesheet("style/main.css");
        primaryStage.setScene(mainScene);
        primaryStage.sizeToScene();
        primaryStage.show();

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pathDrawer.placePoint(event.getX(), event.getY());
            }
        });
        done.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                flightPoly = pathDrawer.joinPath();
                flightPoly.setLayoutX(0);
                flightPoly.setLayoutY(0);
                pathDrawer.plotPath(20,5);

                //sp.getChildren().add(flightPoly);
                //sp.setAlignment(flightPoly, Pos.TOP_LEFT);

            }
        });
        resetPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pathDrawer.resetPath();
                sp.getChildren().remove(flightPoly);
            }
        });
        createUAV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {uavCreator.newUAV(); }
        });
        createCam.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { camCreator.newCamera(); }
        });

    }
    public ObservableList populateUAVs(){
        String path = "src/UAVs";
        ObservableList uavs = FXCollections.observableArrayList("Create a UAV!");
        int uavsNo;
        try{
            System.out.println(new File(path).isDirectory());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot access directory! - Ignoring");
            uavs.clear();
            uavs.add("Cannot find 'UAVs' directory!");
            return uavs;
        }
        if (new File(path).list() == null){
            System.out.println("Returning as list is null");
            return uavs;
        }else{
            uavsNo = new File(path).list().length;
            uavs.clear();
            for(int i=0; i<uavsNo; i++){
                String filename = new File(path).list()[i];
                System.out.println(filename);
                uavs.add(filename);
                return uavs;
            }
        }
        return uavs;
    }
}
