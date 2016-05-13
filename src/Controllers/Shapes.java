package Controllers;

import Moldels.Shape;
import java.util.ArrayList;

public class Shapes {

    private static Shapes ourInstance = new Shapes();

    public static Shapes getInstance() {
        return ourInstance;
    }

    private ArrayList<Shape> myShapes;
    private ArrayList<Shape> clearedShapes;


    private Shapes() {
        myShapes = new ArrayList<>();
        clearedShapes = new ArrayList<>();

    }

    public ArrayList<Shape> getShapes(){
        return this.myShapes;
    }

    public ArrayList<Shape> getClearedShapes(){
        return this.clearedShapes;
    }

    public Shape getShape(int x, int y){
        for (Shape shape : myShapes) {
            if (Math.min(shape.getX1(),shape.getX2()) < x
                    && Math.max(shape.getX1(),shape.getX2()) > x
                    && Math.min(shape.getY1(),shape.getY2()) < y
                    && Math.max(shape.getY1(),shape.getY2()) > y)
            {
                System.out.println("gets a shape");
                return shape;
            }
        }
        return null;
    }


}
