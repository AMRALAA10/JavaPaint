package Views;

import Controllers.ColoringState;
import Controllers.MoveState;
import Controllers.ResizeState;
import interfaces.ModifyState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;


public class DrawFrame extends JFrame
{
    private JLabel stausLabel;
    private DrawPanel panel;
    
    private JButton undo;
    private JButton redo;
    private JButton clear;
    private JButton save;


    /*Modify shape buttons*/
    private JComboBox modifications;
    private String ModifyOptions[] =
            {"move", "resize", "reColor"};

    private JButton copyShape;
    private JButton deleteShape;

    private JComboBox colors;
    
    private String colorOptions[]=
    {"White","Blue","Cyan","Dark Gray","Gray","Green","Light Gray",
        "Magenta","Orange","Pink","Red","White","Yellow"};
    

    private Color colorArray[]=
    {Color.white , Color.BLUE , Color.CYAN , Color.darkGray , Color.GRAY ,
        Color.GREEN, Color.lightGray , Color.MAGENTA , Color.ORANGE , 
    Color.PINK , Color.RED , Color.WHITE , Color.YELLOW};
    
    private JComboBox shapes;
    

    private String shapeOptions[]=
    {"Shapes", "Free drawing", "Line","Rectangle", "Circle", "Oval", "Triangle"};
    
    private JCheckBox filled;
        
    private JPanel widgetJPanel;
    private JPanel widgetPadder;

    private JPanel modifingPanel;
    private JPanel modifingPadder;


    private JLabel names;
    private JButton deliever;


    private static DrawFrame ourInstance = new DrawFrame();

    public static DrawFrame getInstance() {
        return ourInstance;
    }



    private DrawFrame()
    {
        super("Paint App");


        //JLabel statusLabel = new JLabel( "" );
        names = new JLabel("AMR ALAA: 3688 - MOAZ AZZ: 3691");
        names.setForeground(Color.YELLOW);
        names.setVisible(false);
        Font labelFont = names.getFont();
        names.setFont(new Font(labelFont.getName(), Font.PLAIN, 50));




        panel = new DrawPanel();
        panel.add(names, BorderLayout.EAST);

        undo = new JButton( "Undo" );
        redo = new JButton( "Redo" );
        clear = new JButton( "Clear" );
        save = new JButton( "Save" );
        deliever = new JButton( "Deliever" );

        /**/
        modifications = new JComboBox( ModifyOptions );
        copyShape = new JButton( "copy shape" );
        deleteShape = new JButton( "delete shape" );

        colors = new JComboBox( colorOptions );
        shapes = new JComboBox( shapeOptions );
        
        filled = new JCheckBox( "Filled" );
        
        widgetJPanel = new JPanel();
        widgetJPanel.setLayout( new GridLayout( 8, 1, 10, 10 ) );

        modifingPanel = new JPanel();
        modifingPanel.setLayout( new GridLayout(1,3,10,10));

        widgetPadder = new JPanel();
        widgetPadder.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));

        modifingPadder = new JPanel();
        modifingPadder.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

        widgetJPanel.add( undo );
        widgetJPanel.add( redo );
        widgetJPanel.add( clear );
        widgetJPanel.add( colors );
        widgetJPanel.add( shapes );                 
        widgetJPanel.add( filled );
        widgetJPanel.add( save );
        widgetJPanel.add( deliever );


        modifingPanel.add( modifications );
        modifingPanel.add( copyShape );
        modifingPanel.add( deleteShape );


        widgetJPanel.setBackground(Color.darkGray);
        widgetPadder.add( widgetJPanel );
        widgetPadder.setBackground(Color.DARK_GRAY);

        modifingPanel.setBackground(Color.GRAY);
        modifingPadder.add(modifingPanel);
        modifingPadder.setBackground(Color.GRAY);
        modifingPadder.setVisible(false);


        add( widgetPadder, BorderLayout.WEST);
        add(modifingPadder, BorderLayout.SOUTH);



        add( panel, BorderLayout.CENTER);
        

        ButtonHandler buttonHandler = new ButtonHandler();
        undo.addActionListener( buttonHandler );
        redo.addActionListener( buttonHandler );
        clear.addActionListener( buttonHandler );
        save.addActionListener( buttonHandler );
        deliever.addActionListener( buttonHandler );

        copyShape.addActionListener( buttonHandler );
        deleteShape.addActionListener( buttonHandler );

        ItemListenerHandler handler = new ItemListenerHandler();
        colors.addItemListener( handler );
        shapes.addItemListener( handler );
        filled.addItemListener( handler );
        modifications.addItemListener( handler );

        panel.setBackground(Color.BLACK);
        //getContentPane().setBackground(Color.yellow);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 1200, 600 );

        setVisible( true );
        
    }

    public void showModifingPanel(){ modifingPadder.setVisible(true); }
    public void hideModifingPanel(){ modifingPadder.setVisible(false); }


    public DrawPanel getPanel() {
        return panel;
    }


    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed( ActionEvent event )
        {
            if (event.getActionCommand().equals("Undo")){
                panel.clearLastShape();
            }
            else if (event.getActionCommand().equals("Redo")){
                panel.redoLastShape();
            }
            else if (event.getActionCommand().equals("Clear")){
                panel.clearDrawing();
            }
            else if (event.getActionCommand().equals("Save")){
                try {
                    panel.save();
                } catch (IOException e) {
                    System.out.println("IO exception occured");
                }
            }
            else if (event.getActionCommand().equals("Deliever")){
                names.setVisible(true);
            }

            else if (event.getActionCommand().equals("copy shape")){
              panel.copyShapeToModify();
            }
            else if (event.getActionCommand().equals("delete shape")){
                panel.deleteShapeToModify();
            }

        }
    }

    private class ItemListenerHandler implements ItemListener
    {
        public void itemStateChanged( ItemEvent event )
        {
            if ( event.getSource() == filled )
            {
                boolean checkFill=filled.isSelected() ? true : false;
                panel.setCurrentShapeFilled(checkFill);
            }

            if ( event.getStateChange() == ItemEvent.SELECTED )
            {
                if ( event.getSource() == colors)
                {
                    panel.setCurrentShapeColor
                        (colorArray[colors.getSelectedIndex()]);
                }
                else if ( event.getSource() == shapes)
                {
                    panel.setCurrentShapeType(shapes.getSelectedIndex());
                }
                else if ( event.getSource() == modifications )
                {
                    int selectedIndex = modifications.getSelectedIndex();
                    ModifyState currentState = new MoveState();
                    switch (selectedIndex){
                        case 0:
                            currentState = new MoveState();
                            break;
                        case 1:
                            currentState = new ResizeState();
                            break;
                        case 2:
                            currentState = new ColoringState();
                            break;
                    }
                    panel.setCurrentModifyState(currentState);
                }
            }
            
        }
    }
    
}