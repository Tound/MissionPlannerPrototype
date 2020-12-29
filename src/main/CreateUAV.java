package main;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class CreateUAV {
    public static Stage CreateStage = new Stage();
    public static Scene createScene;
    public static void newUAV(){
        CreateStage.setTitle("UAV Setup");
        CreateStage.initModality(Modality.APPLICATION_MODAL);

        GridPane gp = new GridPane();
        Text title =  new Text("UAV Settings");
        Text nameLabel =  new Text("Name:");
        Text weightLabel =  new Text("Weight (g):");
        Text turnRadiusLabel =  new Text("Turn Radius (m):");
        Text batteryLabel =  new Text("Battery Type:");

        TextField name = new TextField();
        TextField weight = new TextField();
        TextField turnRadius = new TextField();
        TextField battery = new TextField();

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
                CreateStage.close();
                File uavFile = new File();

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
}
