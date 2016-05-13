package interfaces;


import java.awt.*;

public interface DrawCommand {
    /*
    *instead of execute
    *we use draw
    */

    void draw(Graphics g);
    void clear(Graphics g);

}
