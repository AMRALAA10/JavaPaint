package Moldels;

import java.awt.Color;
import java.awt.Graphics;


public abstract class BoundedShape extends Shape
{
    private boolean fill;

    public BoundedShape()
    {
        super();
        fill=false;
    }
    

    public BoundedShape(int x1, int y1, int x2, int y2, Color color, boolean fill)
    {
        super(x1, y1, x2, y2, color);
        this.fill=fill;
    }
    

    public void setFill(boolean fill)
    {
        this.fill=fill;
    }
    

    public int getUpperLeftX(){ return Math.min(getX1(),getX2()); }
    public int getUpperLeftY()
    {
        return Math.min(getY1(),getY2());
    }
    public int getWidth()
    {
        return Math.abs(getX1()-getX2());
    }
    public int getHeight()
    {
        return Math.abs(getY1()-getY2());
    }
    public boolean getFill()
    {
        return fill;
    }

    abstract public void draw( Graphics g );

    @Override
    public void move(Graphics g, int x, int y) {
        int width = getWidth();
        int height = getHeight();
        if(getUpperLeftX() == getX1()){
            setX1(x - width/2);
            setX2(getX1() + width);
        }
        else {
            setX2(x - width/2);
            setX1(getX2() + width);
        }

        if(getUpperLeftY() == getY1()){
            setY1(y - height/2);
            setY2(getY1() + height);
        }
        else {
            setY2(y - height/2);
            setY1(getY2() + height);
        }

    }
}