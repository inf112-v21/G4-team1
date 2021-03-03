package objects;

import Cards.Deck;
import Cards.ICards;
import Cards.MovementCard;
import Cards.TurningCard;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Robot extends Vector2 implements IObject{
    int lifeTokens;
    ArrayList<Flag> visitedFlags = new ArrayList<>();
    Flag lastFlag = null;
    int damageTokens;
    ArrayList<ICards> hand = new ArrayList<ICards>(); //Containing all 9 cards in
    ArrayList<ICards> chosenCards = new ArrayList<ICards>(); //The cards the player has chosen, same card can be in both lists
    String dir = "E";

    /** TODO
     * Finish checkIfRobotIsAtPosition()
     * Finish pushRobot()
     * Integrate with server
     */

    public Robot(int x, int y){
        lifeTokens = 3;
        damageTokens = 0;

       setPosition(x,y);
    }

    @Override
    public void setPosition(float x, float y) {
        this.set(x,y);
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    /**
     * This method moves the robot based on the next movement card, which is the first card in the currentCards list.
     * It will discard the used card from the current cards list.
     */
    public void moveBasedOnNextCard(){
        ICards card = drawAndDiscardFirstCardInList();
        if (card.getClass() == MovementCard.class) {
            moveBasedOnCard((MovementCard) card);
        } else {
            turnBasedOnCard((TurningCard) card);
        }
    }

    /**
     * moves x tiles in the direction the robot is facing
     * @param tiles number of tiles it moves
     */
    public void move(int tiles){
        Vector2 moveDirection = new Vector2(0,0);

        switch (dir) {
            case "N":
                moveDirection.y = 1;
                break;
            case "E":
                moveDirection.x = 1;
                break;
            case "S":
                moveDirection.y = -1;
                break;
            case "W":
                moveDirection.x = -1;
                break;
        }

        // The position the robot moves to each loop
        Vector2 newPosition = new Vector2(0,0);
        // The position the robot will push another robot to each loop. It's the position two steps ahead, instead of 1 step ahead.
        Vector2 pushPositon = new Vector2(0, 0);

        for (int i = 0; i < tiles; i++) {
            newPosition = new Vector2(getX() + moveDirection.x, getY() + moveDirection.y);
            pushPositon = new Vector2(getX() + (moveDirection.x * 2), getY() + (moveDirection.y * 2));
            if (checkIfPositionIsClear(newPosition)) {
                setPosition(newPosition.x, newPosition.y);
            } else {
                // Position is not clear, figure out what to do
            }
        }
    }

    // TODO
    /**
     * Check if position is clear
     * @param position
     */
    public boolean checkIfPositionIsClear(Vector2 position) {
        return true;
    }

    // TODO
    /**
     * Push robot at position
     * @param position
     */
    public void pushRobot(Vector2 position, Vector2 newPosition) {

    }

    /**
     * Moves robot according to one of the given cards
     * @param movementCard Moves robot x tiles according to card
     */
    public void moveBasedOnCard(MovementCard movementCard) {
        move(movementCard.getDistance());
    }

    /**
     * Turns robot according to one of the given cards
     * @param turnCard Turns robot according to card
     */
    public void turnBasedOnCard(TurningCard turnCard) {
        if (turnCard.getDirection())
            turnRight();
        else {
            if (turnCard.isUturn()) {
                turnRight();
                turnRight();
            } else {
                turnLeft();
            }
        }
    }

    /**
     *
     * @param direction
     */
    public void setDirection(String direction) {
        if ((direction == "N") || (direction == "E") || (direction == "S") || (direction == "W")) {
            dir = direction;
        }

    }

    /**
     * Turns robot in left
     */
    public void turnLeft(){
        if (dir == "N"){
            dir = "W";
        }
        else if (dir == "E"){
            dir = "N";
        }
        else if (dir == "S"){
            dir = "E";
        }
        else if (dir == "W"){
            dir = "S";
        }
    }

    /**
     * Turns robot right
     */
    public void turnRight(){
        if (dir == "N"){
            dir = "E";
        }
        else if (dir == "E"){
            dir = "S";
        }
        else if (dir == "S"){
            dir = "W";
        }
        else if (dir == "W"){
            dir = "N";
        }
    }

    /**
     *
     * @return the direction the robot is facing
     */
    public String getDir(){
        return dir;
    }

    public void registerFlag(Flag flag){
        if(flag.equals(getLastFlag())){
            return;
        }
        lastFlag = flag;
        visitedFlags.add(flag);
    }

    public Flag getLastFlag(){
        return lastFlag;
    }

    public ArrayList<Flag> getVisitedFlags() {
        return visitedFlags;
    }

    public ICards getFirstCard(){
        return chosenCards.get(0);
    }

    public ICards drawAndDiscardFirstCardInList(){
        ICards card = chosenCards.get(0);
        chosenCards.remove(0);
        return card;
    }

    public void drawHand(Deck deck){
        for (int i = 0; i<9; i++) {
            hand.add(deck.draw());
        }
    }

    public void chooseCard(ICards card){
        chosenCards.add(card);
    }


    public void discardHand(Deck deck){
        for (ICards i: hand){
            deck.discardCard(i);
        }
        hand.clear();
    }
}
