package Cards;

public class TurningCard implements ICards{
    int direction;
    int priority;

    public TurningCard(int move, int prio){
        direction = move;
        priority = prio;
    }

    public int getPriority() {
        return priority;
    }

    public int getDirection() {
        return direction;
    }
}
