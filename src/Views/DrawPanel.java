package Views;

import Controllers.MoveState;
import Controllers.Shapes;
import Moldels.*;
import Moldels.Point;
import Moldels.Rectangle;
import Moldels.Shape;
import interfaces.ModifyState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class DrawPanel extends JPanel
{

    private BufferedImage paintImage ;

    private int currentShapeType;
    private Shape currentShapeObject;
    private Color currentShapeColor;
    private boolean currentShapeFilled;
    private Shape shapeToModify;

    private ModifyState modifyState = new MoveState();

    public DrawPanel(){

        currentShapeType=0;
        currentShapeObject=null;
        currentShapeColor=Color.WHITE;
        currentShapeFilled=false;
        shapeToModify = null;

        setLayout(new BorderLayout());
        setBackground( Color.WHITE );

        MouseHandler handler = new MouseHandler();                                    
        addMouseListener( handler );
        addMouseMotionListener( handler );
        paintImage = new BufferedImage(1200, 600, BufferedImage.TYPE_3BYTE_BGR);
    }

    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        
        ArrayList<Shape> shapeArray = Shapes.getInstance().getShapes();
        for ( int counter=shapeArray.size()-1; counter>=0; counter-- )
           shapeArray.get(counter).draw(g);


        if (currentShapeObject!=null){
            currentShapeObject.draw(g);
        }

    }

    public void setCurrentShapeType(int type)
    {
        currentShapeType=type;
    }
    
    public void setCurrentShapeColor(Color color)
    {
        currentShapeColor=color;
    }
    
    public void setCurrentShapeFilled(boolean filled)
    {
        currentShapeFilled = filled;
    }

    public void setCurrentModifyState(ModifyState state)
    {
        System.out.println("Current state changed" + state);
        modifyState = state;
    }

    public void setCurrentShapeObject(Shape currentShapeObject) {
        this.currentShapeObject = currentShapeObject;
    }

    public void clearLastShape()
    {
        if (! Shapes.getInstance().getShapes().isEmpty())
        {
            //Shapes.getInstance().getClearedShapes()
              //      .add(0, Shapes.getInstance().getShapes().remove(0));
            ArrayList<Shape> shapes = Shapes.getInstance().getShapes();
            Shape shape = shapes.get(shapes.size() -1);
            shape.clear(getGraphics());
            repaint();
        }
    }
    
    public void redoLastShape()
    {
        if (! Shapes.getInstance().getClearedShapes().isEmpty())
        {
            Shapes.getInstance().getShapes()
                    .add(0, Shapes.getInstance().getClearedShapes().remove(0));
            repaint();
        }
    }
    
    public void clearDrawing()
    {
        Shapes.getInstance().getShapes().clear();
        Shapes.getInstance().getClearedShapes().clear();
        repaint();
    }

    public void save() throws IOException {
        print(paintImage.getGraphics());
        ImageIO.write(paintImage, "PNG", new File("E:\\collegestaff\\4th term\\programming 2\\PaintImages\\paintImage.png"));
    }

    public void copyShapeToModify() {
        if (shapeToModify != null){
            try {
                Shape newShape = shapeToModify.copyShape();
                Shapes.getInstance().getShapes().add(0, newShape);
                shapeToModify = newShape;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteShapeToModify() {
        if (shapeToModify != null){
            Shapes.getInstance().getShapes().remove(shapeToModify);
            repaint();
        }
    }


    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed( MouseEvent event )
        {
            switch (currentShapeType)
            {
                case 0:
                    currentShapeObject = null;
                    break;
                case 1:
                    currentShapeObject= new Point(event.getX(), event.getY(),
                                                            event.getX(), event.getY(), currentShapeColor);
                    DrawFrame.getInstance().hideModifingPanel();
                    break;
                case 2:
                    currentShapeObject= new Line( event.getX(), event.getY(),
                                                   event.getX(), event.getY(), currentShapeColor);
                    DrawFrame.getInstance().hideModifingPanel();
                    break;
                case 3:
                    currentShapeObject= new Rectangle( event.getX(), event.getY(),
                                                        event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                    DrawFrame.getInstance().hideModifingPanel();
                    break;
                case 4:
                    currentShapeObject = new Circle( event.getX(), event.getY(),
                                                   event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                    DrawFrame.getInstance().hideModifingPanel();
                    break;
                case 5:
                    currentShapeObject= new Oval( event.getX(), event.getY(),
                                                   event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                    DrawFrame.getInstance().hideModifingPanel();
                    break;
                case 6:
                    currentShapeObject= new Triangle(event.getX(), event.getY(),
                            event.getX(), event.getY(), event.getX(), event.getY(),currentShapeColor, currentShapeFilled);
                    DrawFrame.getInstance().hideModifingPanel();
                    break;
            }
            if(currentShapeObject == null){
                shapeToModify = Shapes.getInstance().getShape(event.getX(), event.getY());
                if(shapeToModify != null) {
                    DrawFrame.getInstance().showModifingPanel();

                }
                else {
                    DrawFrame.getInstance().hideModifingPanel();
                }
            }
        }

        public void mouseReleased( MouseEvent event )
        {
            if (currentShapeObject != null) {
                currentShapeObject.setX2(event.getX());
                currentShapeObject.setY2(event.getY());

                Shapes.getInstance().getShapes().add(0, currentShapeObject);

                currentShapeObject=null;
                Shapes.getInstance().getClearedShapes().clear();
                repaint();

            } else {
                /*selecting mode*/
            }

        }


        public void mouseDragged( MouseEvent event )
        {
            if (currentShapeObject != null) {
                if(currentShapeObject instanceof Point){
                    currentShapeObject.setX2(event.getX());
                    currentShapeObject.setY2(event.getY());
                    currentShapeObject.draw(getGraphics());
                    repaint();
                    Shapes.getInstance().getShapes().add(0, currentShapeObject);
                    currentShapeObject= new Point(event.getX(), event.getY(),
                            event.getX(), event.getY(), currentShapeColor);
                }
                else {
                    currentShapeObject.setX2(event.getX());
                    currentShapeObject.setY2(event.getY());
                }

                repaint();
            } else {
                if (shapeToModify != null) {
                    modifyState.doAction(shapeToModify, getGraphics(), event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                    repaint();
                }
            }

        }

    }
    
}