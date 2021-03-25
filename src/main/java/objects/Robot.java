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
import java.util.Scanner;

public class Robot extends Vector2 implements IObject{
    int lifeTokens;
    ArrayList<Flag> visitedFlags = new ArrayList<>();
    int damageTokens;
    ArrayList<ICards> hand = new ArrayList<>(); //Containing all 9 cards in
    ArrayList<ICards> chosenCardsFromHand = new ArrayList<>(); //The cards the player has chosen, same card can be in both lists
    String dir = "E";
    Client client;
    String id;
    Game game;
    int respawnPositionX;
    int respawnPositionY;

    boolean isServer = true;
    float startPosX = 0;
    float startPosY = 0;

    /** TODO
     * Finish checkIfRobotIsAtPosition()
     * Finish pushRobot()
     * Integrate with server
     */



    public Robot(int x, int y){
        lifeTokens = 3;
        damageTokens = 0;

       setPosition(x,y);
       respawnPositionX = x;
       respawnPositionY = y;
    }


    public Robot(int x, int y, Game game){
        this.game = game;
        lifeTokens = 3;
        damageTokens = 0;

        setPosition(x,y);
    }

    public void InitializeClient(Game game, Application application) {
        this.game = game;
        client = new Client(game, this, application);
    }

    @Override
    public void setPosition(float x, float y) {

        if (game != null) {
            game.getApplication().getPlayerLayer().setCell(Math.round(getX()), Math.round(getY()), null);
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

    public void setStartPosition(float x, float y){
        startPosX = x;
        startPosY = y;
    }

    /**
     * This method moves the robot based on the next movement card, which is the first card in the currentCards list.
     * It will discard the used card from the current cards list.
     * @param animate
     */
    public void moveBasedOnNextCard(boolean animate){
        ICards card = drawAndDiscardFirstCardInList();
        if (card.getClass() == MovementCard.class) {
            MovementCard card_ = (MovementCard) card;
            System.out.println("Moving " + card_.getDistance() + " tiles in direction " + getDir());
            moveBasedOnCard((MovementCard) card, animate);
        } else {
            TurningCard card_ = (TurningCard) card;
            turnBasedOnCard((TurningCard) card);
            if (card_.isUturn()) { System.out.println("Uturned, new direction is " + getDir()); }
            else if (card_.getDirection()) { System.out.println("Turned to the right, new direction is " + getDir()); }
            else { System.out.println("Turned to the left, new direction is " + getDir()); }
        }
    }

    /**
     * moves x tiles in the direction the robot is facing
     * @param tiles number of tiles it moves
     */
    public void move(int tiles, String dir_, Boolean animate){
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

        // Switches move direction for backwards cards.
        if (tiles == -1) {
            moveDirection.x *= -1;
            moveDirection.y *= -1;
            tiles = 1;
        }

        // The position the robot moves to each loop
        Vector2 newPosition;
        // The position the robot will push another robot to each loop. It's the position two steps ahead, instead of 1 step ahead.
        Vector2 pushPosition;

        for (int i = 0; i < tiles; i++) {
            newPosition = new Vector2(getX() + moveDirection.x, getY() + moveDirection.y);
            pushPosition = new Vector2(getX() + (moveDirection.x * 2), getY() + (moveDirection.y * 2));
            if(!CheckIfOutOfBounds(newPosition)) {
                switch (checkIfPositionIsClear(newPosition)) {
                    case 0:
                        // Position was clear
                        setPosition(newPosition.x, newPosition.y);
                        break;
                    case 1:
                        // Position blocked by a robot
                        Vector2 tempPosition = new Vector2(getX() + moveDirection.x, getY() + moveDirection.y);
                        for (int j = 0; j < 100; j++) {
                            tempPosition = new Vector2(newPosition.x + (moveDirection.x * j), newPosition.y + (moveDirection.y * j));
                            if (CheckIfOutOfBounds(tempPosition)) {
                                return;
                            }
                            if (checkIfPositionIsClear(tempPosition) != 1) {
                                break;
                            }
                        }
                        for (Robot robot : game.getPlayers()) {
                            if (robot.getX() == newPosition.x && robot.getY() == newPosition.y) {
                                pushRobot(robot.getId(), pushPosition, dir_);
                            }
                        }
                        setPosition(newPosition.x, newPosition.y);
                        break;
            }
            }
            if (animate) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public boolean CheckIfOutOfBounds(Vector2 position) {
        return position.x < 0 || position.x > 15 || position.y < 0 || position.y > 11;
    }
    /**
     * Check if position is clear
     * @param position
     * @return 0 means position is clear, 1 means position has a robot
     */
    public int checkIfPositionIsClear(Vector2 position) {
        for (Robot robot : game.getPlayers()) {
            if (robot.getX() == position.x && robot.getY() == position.y) {
                return 1;
            }
        }
        return 0;
    }


    /**
     * Push robot at position
     */
    public void pushRobot(String id, Vector2 newPosition, String dir) {
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
    public void moveBasedOnCard(MovementCard movementCard, Boolean animate) {
        move(movementCard.getDistance(), getDir(), animate);
    }

    public void moveBasedOnCard(MovementCard movementCard, String dir) {
        move(movementCard.getDistance(), dir, true);
    }

    /**
     * Turns robot according to one of the given cards
     * @param turnCard Turns robot according to card
     */
    public void turnBasedOnCard(TurningCard turnCard) {
        if (turnCard.isUturn()) {
            turnRight();
            turnRight();
        }
        else {
            if (turnCard.getDirection()) {
                turnRight();
            } else {
                turnLeft();
            }
        }
    }

    /**
     *
     * @param direction the direction it should face
     */
    public void setDirection(String direction) {
        if ((direction.equals("N")) || (direction.equals("E")) || (direction.equals("S")) || (direction.equals("W"))) {
            dir = direction;
        }

    }

    /**
     * Turns robot in left
     */
    public void turnLeft(){

        switch (dir) {
            case "N":
                dir = "W";
                break;
            case "E":
                dir = "N";
                break;
            case "S":
                dir = "E";
                break;
            case "W":
                dir = "S";
                break;
        }
    }

    /**
     * Turns robot right
     */
    public void turnRight(){
        switch (dir) {
            case "N":
                dir = "E";
                break;
            case "E":
                dir = "S";
                break;
            case "S":
                dir = "W";
                break;
            case "W":
                dir = "N";
                break;
        }
    }

    /**
     *
     * @return the direction the robot is facing
     */
    public String getDir(){
        return dir;
    }

    /**
     *
     * @param flag
     * adds flag to visitedFlags if it is not already visited.
     */
    public void registerFlag(Flag flag){
        if(visitedFlags.contains(flag)){
            return;
        }
        visitedFlags.add(flag);
    }

    public ArrayList<Flag> getVisitedFlags() {
        return visitedFlags;
    }

    /**
     * To be used in next iteration of the project
     * @return
     */
    public ICards getFirstCard(){
        return chosenCardsFromHand.get(0);
    }

    public ICards drawAndDiscardFirstCardInList(){
        return chosenCardsFromHand.remove(0);
    }

    public void drawHand(Deck deck){
        for (int i = 0; i<9; i++) {
            hand.add(deck.draw());
        }
    }


    public ArrayList<ICards> getHand(){
        return hand;
    }

    public void chooseCardFromHand(ICards card){
        chosenCardsFromHand.add(card);
    }


    public void discardHand(Deck deck){
        for (ICards i: hand){
            deck.discardCard(i);
        }
        hand.clear();
    }

    /**
     * Gets 9 random cards from the deck and prints cards to the terminal
     * Also runs chooseCards
     */
    public void printCardsToTerminal() {
        System.out.flush();
        ArrayList<ICards> cardsToPrint = getHand();

        int counter = 1;

        for (ICards cards : cardsToPrint) {
            System.out.println(counter + ": " + cards.getDisplayText());
            counter++;
        }
        System.out.println("\n" +"Choose five of these cards using 1-9 on your keyboard");

        // Separate thread to take input
        Thread one = new Thread(() -> {
            try {
                chooseCards(cardsToPrint);
            } catch(Exception v) {
                System.out.println(v);
            }
        });

        one.start();
    }

    /**
     * User picks 5 out of 9 cards for their hand
     * Cannot choose the same card more than once
     * Sends a card that is chosen to the robot class
     * @param cardsToPrint 9 cards to choose from
     */
    public void chooseCards(ArrayList<ICards> cardsToPrint){
        while (chosenCardsFromHand.size()<5){
            System.out.println("Enter a number between 1-9");
            Scanner scanner = new Scanner(System.in);
            if(!scanner.hasNextInt()){
                continue;
            }
            int number = scanner.nextInt();
            if(!(number > 0 && number < 10)){
                continue;
            }
            ICards chosenCard = cardsToPrint.get(number-1);
            if(!chosenCardsFromHand.contains(chosenCard)){
                chooseCardFromHand(chosenCard);
            }
            else{
                System.out.println("This card is already chosen, chose a new");
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        try {
            System.out.println("clientID: " + client.getId());
            System.out.println("type: " + client.getId().getClass().getName());
            System.out.println("1: " + client.getId());
            System.out.println("type: " + "1".getClass().getName());
            if (client.getId() == "1") {
                isServer = true;
            } else {
                isServer = true;
            }
        }
        catch (Exception e) {

        }
    }

    /**
     * To be used in next iteration of the project
     * @return
     */
    public Game getGame() {
        return game;
    }

    public ArrayList<ICards> getChosenCardsFromHand() {
        return chosenCardsFromHand;
    }

    public void loseLife(){ lifeTokens--; }

    public int getRespawnPositionX() { return respawnPositionX; }

    public int getRespawnPositionY() { return respawnPositionY; }

    public int getLifeTokens(){ return lifeTokens; }

    public boolean isServer(){
        return isServer;
    }

    public Client getClient(){
        return client;
    }

    public float getStartPositionX() {
        return startPosX;
    }

    public float getStartPositionY() { return startPosY; }

    public void setRespawnPositionX(int x){
        respawnPositionY = x;
    }
    public void setRespawnPositionY(int y){
        respawnPositionY = y;
    }

    public void setHand(ArrayList<ICards> hand_) {
        hand = hand_;
    }
}
