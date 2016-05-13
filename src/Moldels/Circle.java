package Moldels;

import java.awt.*;


public class Circle extends Oval {

    private int r;

    public Circle(int x1, int y1, int x2, int y2, Color color, boolean fill) {
        super(x1, y1, x2, y2, color, fill);
        this.r = Math.max( Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor( getColor() );
        setX2(getUpperLeftX() + getWidth());
        setY2(getUpperLeftY() + getWidth());
        if (getFill())
            g.fillOval( getUpperLeftX(), getUpperLeftY(), getWidth(), getWidth() );
        else
            g.drawOval( getUpperLeftX(), getUpperLeftY(), getWidth(), getWidth() );


    }

}
