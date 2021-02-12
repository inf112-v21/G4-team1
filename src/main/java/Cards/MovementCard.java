package Cards;

public class MovementCard implements ICards{
    int distance;
    int priority;

    public MovementCard(int move, int prio){
        distance = move;
        priority = prio;
    }

}
