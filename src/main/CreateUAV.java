package main;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateUAV {
    private static Stage CreateStage = new Stage();
    private static Scene createScene;
    private static String path = "src/UAVs";
    private static TextField name = new TextField();
    private static TextField weight = new TextField();
    private static TextField turnRadius = new TextField();
    private static TextField battery = new TextField();

    public static void newUAV(){
        CreateStage.setTitle("UAV Setup");
        CreateStage.initModality(Modality.APPLICATION_MODAL);
        GridPane gp = new GridPane();
        Text title =  new Text("UAV Settings");
        Text nameLabel =  new Text("Name:");
        Text weightLabel =  new Text("Weight (g):");
        Text turnRadiusLabel =  new Text("Turn Radius (m):");
        Text batteryLabel =  new Text("Battery Type:");

        name.setText("Name of craft");
        weight.setText("Weight of craft");
        turnRadius.setText("Min turn radius of craft");
        battery.setText("Battery used in craft (Lipo, NiMH, Li-ion...)");

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        Button def = new Button("Set to default");

        gp.add(title,1,0);
        title.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
        gp.add(name,1,1);
        gp.add(weight,1,2);
        gp.add(turnRadius,1,3);
        gp.add(battery,1,4);

        gp.add(nameLabel,0,1);
        gp.add(weightLabel,0,2);
        gp.add(turnRadiusLabel,0,3);
        gp.add(batteryLabel,0,4);

        gp.add(save,0,5);
        gp.add(cancel,2,5);
        gp.add(def,1,5);

        gp.setHgap(10);
        gp.setVgap(20);
        gp.setAlignment(Pos.CENTER);
        gp.setStyle("-fx-background-color: rgb(42, 45, 48)");
        createScene = new Scene(gp,500,700, Color.GRAY);
        CreateStage.setScene(createScene);
        CreateStage.show();

        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    File uavFile = new File("src/UAVs/" + name.getText() + ".uav");
                    if(!uavFile.createNewFile()){
                        createPopup(name.getText(), path);
                        CreateStage.close();
                    }else{
                        writeFile();
                        CreateStage.close();
                    }
                }catch (IOException ioe){
                    ioe.printStackTrace();
                    System.out.println("Unable to create UAV file");
                }
            }
        });
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CreateStage.close();
            }
        });
        def.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Setting back to defaults");
            }
        });
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
            writer.write("\nweight:" + weight.getText());
            writer.write("\nturnRadius:" + turnRadius.getText());
            writer.write("\nbattery:" + battery.getText());
            writer.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("Unable to write to file");
        }
    }
}
