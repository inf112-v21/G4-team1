package Cards;

public class TurningCard implements ICards{
    boolean turnRight;
    int priority;
    boolean isUturn;

    public TurningCard(boolean right, boolean uturn, int prio){
        turnRight = right;
        priority = prio;
        isUturn = uturn;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getDirection() {
        return turnRight;
    }

    public boolean isUturn(){
        return isUturn;
    }
}
