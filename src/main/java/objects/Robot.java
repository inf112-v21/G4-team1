package objects;

import Cards.Deck;
import Cards.ICards;
import Cards.MovementCard;
import Cards.TurningCard;
import Game.Game;
import Multiplayer.Client;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.Application;

import java.util.ArrayList;

public class Robot extends Vector2 implements IObject{
    int lifeTokens;
    ArrayList<Flag> visitedFlags = new ArrayList<>();
    Flag lastFlag = null;
    int damageTokens;
    ArrayList<ICards> hand = new ArrayList<ICards>(); //Containing all 9 cards in
    ArrayList<ICards> chosenCards = new ArrayList<ICards>(); //The cards the player has chosen, same card can be in both lists
    String dir = "E";
    Client client;
    String id;
    Game game;

    /** TODO
     * Finish checkIfRobotIsAtPosition()
     * Finish pushRobot()
     * Integrate with server
     */

    /** TODO SERVER INTEGRATION
     *
     *
     */

    public Robot(int x, int y){
        lifeTokens = 3;
        damageTokens = 0;

       setPosition(x,y);
    }

    public Robot(int x, int y, Game game){
        this.game = game;
        lifeTokens = 3;
        damageTokens = 0;

        setPosition(x,y);
    }

    public void IntializeClient(Game game, Application application) {
        this.game = game;
        client = new Client(game, this, application);
    }

    @Override
    public void setPosition(float x, float y) {

        if (game != null) {
            game.getApplication().getPlayerLayer().setCell(Math.round(getX()), Math.round(getY()), null);
        } else {

        }

        this.set(x,y);

        // If client has been initialized
        if (client != null) {
            client.UpdateClientPosition(new Vector2(x, y), getId());
        }
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
    public void move(int tiles, String dir_, Boolean broadcast){
        Vector2 moveDirection = new Vector2(0,0);

        switch (dir_) {
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
            if(CheckIfOutOfBounds(newPosition)) {
                return;
            }
            pushPositon = new Vector2(getX() + (moveDirection.x * 2), getY() + (moveDirection.y * 2));
            switch (checkIfPositionIsClear(newPosition)) {
                case 0:
                    // Position was clear
                    setPosition(newPosition.x, newPosition.y);
                    break;
                case 1:
                    // Position blocked by a robot
                    for (Robot robot : game.getPlayers()) {
                        if (robot.getX() == newPosition.x && robot.getY() == newPosition.y) {
                            pushRobot(robot.getId(), new Vector2(0,0), pushPositon, dir_);
                        }
                    }
                    setPosition(newPosition.x, newPosition.y);
                    break;
            }
        }
    }

    public boolean CheckIfOutOfBounds(Vector2 position) {
        if (position.x < 0 || position.x > 10 || position.y < 0 || position.y > 10) {
            return true;
        }
        return false;
    }

    // TODO
    /**
     * Check if position is clear
     * @param position
     * @return 0 means position is clear, 1 means position has a robot
     */
    public int checkIfPositionIsClear(Vector2 position) {
        // Check if a robot is in the way
        for (Robot robot : game.getPlayers()) {
            if (robot.getX() == position.x && robot.getY() == position.y) {
                return 1;
            }
        }
        return 0;
    }

    // TODO
    /**
     * Push robot at position
     * @param position
     */
    public void pushRobot(String id, Vector2 position, Vector2 newPosition, String dir) {
        // VIKTIG!!!!!!!
        // Eg vett denna gir error i ny og ne, men ignorer da for no. Funke ikkje med try catch plaster ein gong, må gi error for at da skal funka...
        client.MoveClientBasedOnCard(id, 1, dir);

        for (Robot robot : game.getPlayers()) {
            if (robot.getId().equals(id)) {
                robot.setPosition(newPosition.x, newPosition.y);
            }
        }
    }

    /**
     * Moves robot according to one of the given cards
     * @param movementCard Moves robot x tiles according to card
     */
    public void moveBasedOnCard(MovementCard movementCard) {
        move(movementCard.getDistance(), getDir(), true);
    }

    public void moveBasedOnCard(MovementCard movementCard, String dir) {
        move(movementCard.getDistance(), dir, false);
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

    public ArrayList<ICards> getHand(){
        return hand;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<ICards> getChosenCards() {
        return chosenCards;
    }
}
