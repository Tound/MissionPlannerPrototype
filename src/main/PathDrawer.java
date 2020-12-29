package main;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PathDrawer{
    private Canvas canvas;
    private GraphicsContext gc;
    private double width = 0;
    private double height = 0;
    private ArrayList<Coordinates> points  =  new ArrayList<Coordinates>();
    private int ovalRadius = 20;
    private Boolean complete = false;
    private Group flightGroup;
    public PathDrawer(Canvas canvas){
        this.canvas = canvas;
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.gc = canvas.getGraphicsContext2D();
    }
    public void placePoint(double x, double y){
        if (complete != true) {
            gc.strokeOval(x - ovalRadius / 2, y - ovalRadius / 2, ovalRadius, ovalRadius);
            points.add(new Coordinates(x, y));
            gc.fillText(Integer.toString(points.size()), x, y);

            System.out.println("X: " + x + " Y: " + y);
        }
        else{
            System.out.println("Press the reset button as the path has been finalised!");
        }
    }
    public Group joinPath(){
        for(int i = 0; i<points.size()-1;i++){
            gc.strokeLine(points.get(i).getX(), points.get(i).getY(),points.get(i+1).getX(), points.get(i+1).getY());
        }
        gc.strokeLine(points.get(0).getX(), points.get(0).getY(), points.get(points.size()-1).getX(),points.get(points.size()-1).getY());
        complete = true;

        Polygon flightArea = new Polygon();
        double flightPointsX[] = new double[points.size()];
        double flightPointsY[] = new double[points.size()];
        for(int i = 0;i<points.size();i++) {
            flightArea.getPoints().addAll(points.get(i).getX(), points.get(i).getY());
            flightPointsX[i] = points.get(i).getX();
            flightPointsY[i] = points.get(i).getY();
        }
        flightArea.setFill(Color.ALICEBLUE);
        System.out.println(flightPointsX);
        flightGroup =  new Group();
        flightGroup.getChildren().add(flightArea);
        Image stripes = new Image("assets/stripes.jpg");
        gc.setFill(new ImagePattern(stripes, 0,0,canvas.getWidth(), canvas.getHeight(), true));
        gc.setGlobalAlpha(0.2);
        gc.fillPolygon(flightPointsX, flightPointsY, points.size());
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha(1);
        return flightGroup;
    }

    public void resetPath(){
        points.clear();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        complete = false;
        flightGroup.getChildren().removeAll();
        gc.restore();
    }

}
