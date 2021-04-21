package objects;

import Cards.Deck;
import Cards.ICards;
import Cards.MovementCard;
import Cards.TurningCard;
import Game.Game;
import Multiplayer.Client;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.Application;

import java.lang.reflect.Array;
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
    boolean isServer = true;
    float startPosX = 0;
    float startPosY = 0;
    int rotation;
    Deck deck;

    public Robot(int x, int y){
        lifeTokens = 3;
        damageTokens = 0;
        rotation=0;
        setPosition(x,y);
        deck = new Deck();
    }


    public Robot(int x, int y, Game game){
        this.game = game;
        lifeTokens = 3;
        damageTokens = 0;
        rotation=0;
        setPosition(x,y);
        deck = new Deck();
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

    public void setPositionFromHost(float x, float y) {

        if (game != null) {
            game.getApplication().getPlayerLayer().setCell(Math.round(getX()), Math.round(getY()), null);
        }

        this.set(x,y);

        if (game.getPlayers().get(0).client != null) {
            game.getPlayers().get(0).client.UpdateClientPosition(new Vector2(x, y), getId());
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
     * @param playRound True if this is movement ordered by the host because of a round being played
     */
    public void moveBasedOnNextCard(boolean animate, boolean playRound){
        ICards card = drawAndDiscardFirstCardInList();
        if (card.getClass() == MovementCard.class) {
            MovementCard card_ = (MovementCard) card;
            System.out.println("Moving " + card_.getDistance() + " tiles in direction " + getDir());
            moveBasedOnCard((MovementCard) card, animate, playRound);
        } else {
            TurningCard card_ = (TurningCard) card;
            turnBasedOnCard((TurningCard) card, playRound);
            if (card_.isUturn()) { System.out.println("Uturned, new direction is " + getDir()); }
            else if (card_.getDirection()) { System.out.println("Turned to the right, new direction is " + getDir()); }
            else { System.out.println("Turned to the left, new direction is " + getDir()); }
        }
    }

    /**
     * moves x tiles in the direction the robot is facing
     * @param tiles number of tiles it moves
     */
    public void move(int tiles, String dir_, Boolean animate, Boolean playRound){
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
            if(!CheckIfOutOfBounds(newPosition) && robotCanMove()) {
                switch (checkIfPositionIsClear(newPosition)) {
                    case 0:
                        // Position was clear
                        if (playRound) {
                            setPositionFromHost(newPosition.x, newPosition.y);
                        } else {
                            setPosition(newPosition.x, newPosition.y);
                        }
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
                        if (playRound) {
                            setPositionFromHost(newPosition.x, newPosition.y);
                        } else {
                            setPosition(newPosition.x, newPosition.y);
                        }
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
    public void moveBasedOnCard(MovementCard movementCard, Boolean animate, Boolean playRound) {
        move(movementCard.getDistance(), getDir(), animate, playRound);
    }

    public void moveBasedOnCard(MovementCard movementCard, String dir, Boolean playRound) {
        move(movementCard.getDistance(), dir, true, playRound);
    }

    /**
     * Turns robot according to one of the given cards
     * @param turnCard Turns robot according to card
     */
    public void turnBasedOnCard(TurningCard turnCard, Boolean playRound) {
        if (turnCard.isUturn()) {
            turnRight(playRound);
            turnRight(playRound);
        }
        else {
            if (turnCard.getDirection()) {
                turnRight(playRound);
            } else {
                turnLeft(playRound);
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
    public void turnLeft(boolean calledFromServer){

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
        setRotation(1);
        System.out.println(rotation);
        game.getApplication().getPlayerLayer().getCell(Math.round(getX()),Math.round(getY())).setRotation(getRotation());

        if (calledFromServer) {
            game.getPlayers().get(0).getClient().turnLeft(getId());
        }

        try {
            Thread.sleep(800);
        } catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

    }

    /**
     * Turns robot right
     */
    public void turnRight(boolean calledFromServer){
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
        setRotation(-1);
        System.out.println(rotation);
        game.getApplication().getPlayerLayer().getCell(Math.round(getX()),Math.round(getY())).setRotation(getRotation());

        if (calledFromServer) {
            game.getPlayers().get(0).getClient().turnRight(getId());
        }

        try {
            Thread.sleep(800);
        } catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    /**
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

    public void drawHand(){
        for (int i = 0; i<9; i++) {
            hand.add(getDeck().draw());
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<ICards> getHand(){
        return hand;
    }

    public void chooseCardFromHand(ICards card){
        chosenCardsFromHand.add(card);
    }

    public void setChosenCardFromHand(ArrayList<ICards> card){
        chosenCardsFromHand = new ArrayList<>();
        chosenCardsFromHand = card;
    }


    public void discardHand(){
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
        chosenCardsFromHand = new ArrayList<>();
        while (chosenCardsFromHand.size()<5) {
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

        String cards = "";

        cards += getId();
        cards += ",";
        for(ICards card : chosenCardsFromHand){
            cards += card.getSimpleCardName();
            cards += ",";
        }
        cards = cards.substring(0, cards.length() - 1);

        getClient().emitChosenCards(cards);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        try {
            /*System.out.println("clientID: " + client.getId());
            System.out.println("type: " + client.getId().getClass().getName());
            System.out.println("1: " + client.getId());
            System.out.println("type: " + "1".getClass().getName());*/
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
     * @param xPos
     * @param yPos
     * @return A wall if the tile contains a wall
     * Returns null if it does not contain a wall
     */

    public ArrayList<Wall> getWallsOnTile(float xPos, float yPos){
        ArrayList<Wall> walls;
        walls = game.getWallList();

        ArrayList<Wall> newWallsList = new ArrayList<>();

        for(Wall wall : walls){
            //wall har samme pos som robot
            if((wall.getX() == xPos) && (wall.getY() == yPos)){
                newWallsList.add(wall);
            }
        }
        return newWallsList;
    }

    /**
     * Checks if robot can move
     * Made to prevent robot moving trough walls
     * Should prohibit robots from moving through walls even if the wall is on the same tile as the robot
     * @return
     */
    public Boolean robotCanMove(){
        if(!game.isPlaying()) return true;
        ArrayList<Wall> tileWithWall = getWallsOnTile(this.x,this.y);

        if(!tileWithWall.isEmpty()){
            for(Wall wall : tileWithWall){
                if(dir.equals(wall.getDir())){
                    return false;
                }
            }
        }

        Vector2 newTile = new Vector2(this.x, this.y);
        switch (dir) {
            case "N":
                newTile.y += 1;
                break;
            case "E":
                newTile.x += 1;
                break;
            case "S":
                newTile.y -= 1;
                break;
            case "W":
                newTile.x -= 1;
                break;
        }

        ArrayList<Wall> newTileWithWall = getWallsOnTile(newTile.x,newTile.y);

        if(!newTileWithWall.isEmpty()){
            for(Wall wall : newTileWithWall){
                switch (dir){
                    case "N":
                        if(wall.getDir().equals("S")){return false;}
                        break;
                    case "E":
                        if(wall.getDir().equals("W")){return false;}
                        break;
                    case "S":
                        if(wall.getDir().equals("N")){return false;}
                        break;
                    case "W":
                        if(wall.getDir().equals("E")){return false;}
                        break;
                }
            }
        }

        return true;
    }

    public void robotOnBelt(){ //Må kanskje kalles på i game, på slutten av runden
        float xPos = this.x;
        float yPos = this.y;

        ArrayList<Belt> belts = game.getBelts();
        System.out.println(belts);

        for(Belt belt : belts){
            if((xPos == belt.x) && (yPos == belt.y)){
                move(1,belt.dir,false);
            }
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

    public void loseLife(){
        lifeTokens--;
    }

    public int getLifeTokens(){ return lifeTokens; }

    public boolean isServer(){
        return isServer;
    }

    public Client getClient(){
        return client;
    }

    public void setClient(Client client_){
        client = client_;
    }

    public float getStartPositionX() {
        return startPosX;
    }

    public float getStartPositionY() { return startPosY; }

    public void setStartPosX(float x){
        startPosX = x;
    }
    public void setStartPosY(float y){
        startPosY = y;
    }

    public void setHand(ArrayList<ICards> hand_) {
        hand = hand_;
    }
    public int getRotation(){
        return rotation;
    }
    public void setRotation(int i){
        rotation +=i;
        if(rotation>3){
            rotation=0;
        }
        if(rotation<0){
            rotation=3;
        }
    }



}
