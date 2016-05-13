package Controllers;

import Moldels.BoundedShape;
import Moldels.Shape;
import interfaces.ModifyState;

import java.awt.*;


public class ColoringState implements ModifyState {


    @Override
    public void doAction(Shape shape, Graphics g, int x, int y, Color color, boolean fill) {
        /*
        *TODO: continue the function
        * */
        shape.setColor(color);
        try {
            ((BoundedShape) shape).setFill(fill);
        } catch (Exception ex){
            System.out.println("Can't convert to bounded shape");
        }
    }
}
