package interfaces;


import Moldels.Shape;

import java.awt.*;

public interface ModifyState {

    void doAction(Shape shape, Graphics g, int x, int y, Color color, boolean fill);

}
