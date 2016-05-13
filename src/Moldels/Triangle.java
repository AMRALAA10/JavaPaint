package Moldels;

import java.awt.*;

public class Triangle extends BoundedShape {

    private int x3;
    private int y3;

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color, boolean fill)
    {
        super(x1, y1, x2, y2, color, fill);
        this.x3 = x3 + 200;
        this.y3 = y3;
    }


    @Override
    public void draw(Graphics g)
    {
        g.setColor( getColor() );
        if (getFill()){
            g.fillPolygon(new int[] {getX1(), getX2(), this.x3},
                    new int[] {getY1(), getY2(), this.y3}, 3);
            //g.fillRect( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() );
        }
        else{
            g.drawPolygon(new int[] {getX1(), getX2(), this.x3},
                    new int[] {getY1(), getY2(), this.y3}, 3);
        }

    }
}
