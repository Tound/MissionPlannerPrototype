package main;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class FlightSettings {
    public static ScrollPane createFlightSetup(){
        //Titles
        Text flightSettingsTitle = new Text("Flight Setup");
        Text uavSettings = new Text("UAV Setup");
        Text cameraSettings = new Text("Camera Setup");
        Text flightSettings = new Text("Flight Settings");

        Button run = new Button("Run");
        Button defaultSettings = new Button("Default");
        Button save = new Button("Save Flight Settings");

        Button createUAV = new Button("Create UAV");
        Button editUAV = new Button("Edit UAV");

        Button createCam = new Button("Create Camera");
        Button editCam = new Button("Edit Camera");

        Text chosenUav = new Text("UAV: ");
        Text chosenCamera = new Text("Camera:");
        //UAV Settings
        Text uavWeight = new Text("Weight:");
        Text uavMinRad = new Text("Turning Radius:");
        Text uavBattery = new Text("Battery Type");
        Text uavBatterySettings = new Text("Battery Settings:");

        Text weight = new Text();
        Text minRad = new Text();
        Text batteryType = new Text();
        Text batterySettings = new Text();

        //Camera Settings
        Text camReso = new Text("Resolution:");
        Text camAp = new Text("Aperture:");
        Text camShutterSpeed = new Text("Shutter Speed");
        Text camWeight = new Text("Camera Weight");

        //Flight Settings
        Text uavSpeed = new Text("Speed:");
        Text uavAltitude = new Text("Altitude:");
        Text uavPasses =  new Text("Passes");

        ComboBox uav = new ComboBox();
        ComboBox camera = new ComboBox();
        GridPane gp = new GridPane();
        //Title
        gp.add(flightSettingsTitle, 0,0);
        //UAV settings
        gp.add(uavSettings, 0,1);

        gp.add(chosenUav, 0,2);
        gp.add(uav, 1,2);
        gp.add(uavWeight,0,3);
        gp.add(weight,1,3);
        gp.add(uavMinRad,0,4);
        gp.add(minRad,1,4);
        gp.add(batteryType,0,5);
        gp.add(batterySettings,1,5);
        gp.add(createUAV,0,6);
        gp.add(editUAV,1,6);
        //Camera settings
        gp.add(cameraSettings,0,7);
        gp.add(chosenCamera, 0,8);
        gp.add(camera, 1,9);
        gp.add(camReso,0,10);
        gp.add(camAp,0,11);
        gp.add(camShutterSpeed,0,12);
        gp.add(camWeight,0,13);
        gp.add(createCam,0,14);
        gp.add(editCam,1,14);
        //Flight settings
        gp.add(flightSettings,0,15);
        gp.add(uavSpeed, 0,16);
        gp.add(uavAltitude, 0,17);
        gp.add(uavPasses,0,18);
        gp.add(save,0,19);
        gp.add(defaultSettings,1,19);
        gp.add(run,0,20);
        ScrollPane sp = new ScrollPane(gp);
        return sp;
    }
}
