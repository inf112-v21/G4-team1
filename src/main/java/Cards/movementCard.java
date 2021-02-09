package Cards;

public class movementCard implements ICards{
    int distance;
    int priority;

    public movementCard(int move, int prio){
        distance = move;
        priority = prio;
    }

}
