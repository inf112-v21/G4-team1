package Cards;

public class TurningCard implements ICards{
    boolean turnRight;
    int priority;
    boolean isUturn;

    public TurningCard(boolean right, boolean uturn, int prio){
        turnRight = right; //True if it is a turn right card
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

    @Override
    public String getDisplayText() {
        String returnMessage = "";
        if (isUturn){
            returnMessage += "Uturn";
        }
        else if (turnRight){
            returnMessage += "Turn right";
        }
        else{
            returnMessage += "Turn left";
        }
        returnMessage += "\nPriority: " + getPriority();
        return returnMessage;
    }
}
