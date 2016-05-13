package Controllers;

import Moldels.Shape;
import interfaces.ModifyState;

import java.awt.*;


public class MoveState implements ModifyState {
    @Override
    public void doAction(Shape shape, Graphics g, int x, int y, Color color, boolean fill) {
        shape.move(g, x, y);
    }
}
