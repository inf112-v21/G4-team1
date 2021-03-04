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

    @Override
    public String getDisplayText() {
        return String.format("%-2s %-10d %s %d","Move",getDistance(),"Priority: ",getPriority());
    }
}
