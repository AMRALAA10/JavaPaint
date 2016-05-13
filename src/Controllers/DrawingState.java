package Controllers;

public class DrawingState {

    /*
    * 0 for drawing -- default
    * 1 for selecting
    * */
    private int state = 0;

    private static DrawingState ourInstance = new DrawingState();

    public static DrawingState getInstance() {
        return ourInstance;
    }

    private DrawingState() {

    }

    public int getState() {
        return state;
    }

    public void switchState(){
        this.state = 1 - this.state;
    }

    @Override
    public String toString() {
        String state = "Drawing State";
        if (this.state == 1) state = "Selecting State";
        return state;
    }
}
