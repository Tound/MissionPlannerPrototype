package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MissionPlanner extends Application {
    private double width = 1280;
    private double height = 720;
    private double maxWidth = 1920;
    private double maxHeight = 1080;
    private double scaling = 0;
    public Canvas canvas = new Canvas();
    public Button done;
    public Button resetPath;
    public Scene mainScene;
    public Stage primaryStage;

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

        BorderPane bp = new BorderPane();
        StackPane sp = new StackPane();

        Image map = new Image("assets/map.png");
        ImageView iv = new ImageView(map);
        GridPane gp = new GridPane();
        gp.setHgap(50);
        //gp.setPadding(new Insets(10,50,10,50));
        gp.add(done,0,0);
        gp.add(resetPath, 1,0);

        sp.getChildren().add(iv);
        sp.getChildren().add(canvas);
        //sp.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
        //sp.setMaxHeight(680);
        sp.setPrefSize(1280, 650);
        sp.requestFocus();

        SubScene ss =  new SubScene(gp,1280,20);
        bp.setTop(title);
        bp.setCenter(sp);
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
                sp.getChildren().add(pathDrawer.joinPath());
            }
        });
        resetPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pathDrawer.resetPath();
            }
        });
    }

}
