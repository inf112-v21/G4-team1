package Cards;

public class TurningCard implements ICards{
    String direction;
    int priority;

    public TurningCard(String dir, int prio){
        direction = dir;
        priority = prio;
    }

    public int getPriority() {
        return priority;
    }

    public String getDirection() {
        return direction;
    }
}
