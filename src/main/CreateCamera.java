package main;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class CreateCamera {
    private static Stage CreateStage = new Stage();
    private static Scene createScene;
    private static String path = "src/cameras";
    private static TextField name = new TextField();
    private static TextField sensor_width = new TextField();
    private static TextField sensor_height = new TextField();
    private static TextField battery = new TextField();

    public CreateCamera(){
        CreateStage.setTitle("Camera Setup");
        CreateStage.initModality(Modality.APPLICATION_MODAL);
    }

    public static void newCamera(){

        GridPane gp = new GridPane();
        Text title =  new Text("Create Camera");
        Text name = new Text("Name");
        Text sensorWidthLabel = new Text("Sensor Width");
        Text sensorHeightLabel = new Text("Sensor Height");

        gp.add(title,0,0);
        gp.add(name, 0,1);
        gp.add(sensorWidthLabel,0,2);
        gp.add(sensorHeightLabel,0,3);

        createScene = new Scene(gp,500,700, Color.GRAY);
        CreateStage.setScene(createScene);
        CreateStage.show();

    }


    private static void createPopup(String name, String path){
        Stage owPopup = new Stage();
        Text message = new Text("The filename "+name+".uav already exists in "+path+".\n Would you like to overwrite?");
        Button yes = new Button("Yes");
        Button no = new Button("No");
        BorderPane bp = new BorderPane();
        bp.setCenter(message);
        HBox hb = new HBox(yes, no);
        bp.setBottom(hb);
        Scene owScene = new Scene(bp, 200,100, Color.web("rgb(42, 45, 48)"));

        owPopup.setTitle("Overwrite file");
        owPopup.initModality(Modality.APPLICATION_MODAL);
        owPopup.setScene(owScene);
        owPopup.show();
        yes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Set overwrite to true");
                writeFile();
                owPopup.close();
            }
        });
        no.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Change the name of the craft and re-save");
                owPopup.close();
            }
        });
    }
    private static void writeFile(){
        try {
            System.out.println("Writing to "+path+"/"+name.getText()+".uav");
            FileWriter writer = new FileWriter(path + "/" + name.getText() + ".uav");
            System.out.println("Writing file");
            writer.write("name:" + name.getText());
            //writer.write("\nweight:" + weight.getText());
            //writer.write("\nturnRadius:" + turnRadius.getText());
            writer.write("\nbattery:" + battery.getText());
            writer.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("Unable to write to file");
        }
    }
}
