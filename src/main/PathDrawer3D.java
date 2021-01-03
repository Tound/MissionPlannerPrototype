package main;

import javafx.scene.*;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

import java.util.List;

public class PathDrawer3D extends SubScene{
    Box floor = new Box();
    List<Cylinder> points;
    Group group = new Group();
    PerspectiveCamera camera = new PerspectiveCamera();
    public PathDrawer3D(Parent root, int width, int height){
        super(root,width,height,true, SceneAntialiasing.BALANCED);
        this.setCamera(camera);
    }

    public void addPoints(List<Coordinates> coords){
        for(int i = 0; i< coords.size();i++){
            //Height, radius
            Cylinder point = new Cylinder(coords.get(i).getX(),coords.get(i).getY());
            points.add(point);
            group.getChildren().add(point);
        }
    }

}
