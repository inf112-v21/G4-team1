package Cards;

public class MovementCard implements ICards{
    int distance;
    int priority;

    public MovementCard(int move, int prio){
        distance = move;
        priority = prio;
    }

    public int getPriority() {
        return priority;
    }

    public int getDistance() {
        return distance;
    }
}
