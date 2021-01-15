package main;

import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

import java.util.List;

public class PathDrawer3D{
    int image_width = 500;
    int image_height = 300;
    Box floor = new Box();
    List<Cylinder> points;
    Group group = new Group();
    PerspectiveCamera camera = new PerspectiveCamera();
    Group root = new Group();
    SubScene ss;
    public PathDrawer3D(int width, int height){

        Group root = new Group();

        floor.setWidth(width);
        floor.setHeight(height);
        floor.setDepth(0);
        PhongMaterial mat =  new PhongMaterial();
        mat.setDiffuseMap(new Image("assets/map.png"));
        floor.setMaterial(mat);
        root.getChildren().add(floor);
        ss = new SubScene(root,width,height,true, SceneAntialiasing.BALANCED);
        ss.setCamera(camera);
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
