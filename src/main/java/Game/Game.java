package Game;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.Application;
import objects.Belt;
import objects.Flag;
import objects.Robot;

import java.lang.reflect.Array;
import java.util.*;

import Cards.*;
import objects.Wall;

public class Game {
    Boolean playing = false;
    ArrayList<Robot> players;
    int numberOfFlags;
    ArrayList<Flag> flags;
    Application application;
    String currentHands;
    ArrayList<Vector2> startPositions;
    ArrayList<Wall> wallList;
    ArrayList<Belt> beltList;

    public Game(ArrayList<Robot> playerList, Application application) {
        this.application = application;

        players = playerList;
    }

    /**
     * Resets all players position and starts the game
     */
    public void startGame() {
        startPositions = application.getEntities(application.getStartPositionLayer());
        playing = true;

        ArrayList<Vector2> walls = application.getEntities(application.getWallsLayer());
        wallList = getWalls(walls);

        ArrayList<Vector2> belts = application.getEntities(application.getConveyorBeltLayer());
        beltList = getBelts(belts);

        ArrayList<Vector2> entitiesList = application.getEntities(application.getFlagLayer());
        flags = sortFlags(entitiesList);


        int count = 0;
        for (Robot rob: players){
            rob.setPosition(startPositions.get(count).x, startPositions.get(count).y);
            rob.setStartPosX(startPositions.get(count).x);
            rob.setStartPosY(startPositions.get(count).y);
            count++;

        }
        application.render();


        //playGame();
        System.out.println("Server: " + players.get(0).isServer());
        System.out.println("ROBOT LENGTH: " + players.size());
        if (players.get(0).isServer()) {
            playGame();
        }
    }


    /**
     * the games turn order
     */
    public void playGame() {

        while (playing) {
            drawStep();
            for (int i = 0; i<5; i++){
                //playTurn();
            }
            //discardStep();
            return;
        }
    }

    /**
     * Gives each player a hand
     */
    public void drawStep(){
        String hands = "";
        for (Robot rob : players) {
            hands += rob.getId();
            hands += ",";
            rob.drawHand();
            for(ICards card : rob.getHand()){
                hands += card.getSimpleCardName();
                hands += ",";
            }
        }
        currentHands = hands;
        for (Robot rob : players){
            if(rob.isServer()){
                try {
                    // This removes the last character from the string
                    hands = hands.substring(0, hands.length() - 1);
                    rob.getClient().emitCards(hands);
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * Discards each players hand
     */
    public void discardStep(){
        for (Robot rob : players) {
            rob.discardHand();
        }
    }

    /**
     * Plays the next card for each robot in order of their priority
     * Then checks if anyone has won
     */
    public void playTurn(){
        ArrayList<ICards> cards = new ArrayList();
        for (int i = 0; i < 5; i++){
            for(Robot rob : players){
                cards.add(rob.getFirstCard());
            }
            //Sorts every players card so the one with highest priority goes first
            Collections.sort(cards, (c1, c2) -> {
                if (c1.getPrio() > c2.getPrio()) return -1;
                if (c1.getPrio() < c2.getPrio()) return 1;
                return 0;
            });

            for (ICards c: cards) {
                ArrayList<Robot> playersCopy = players;
                for (Robot rob : playersCopy) {
                    if (rob.getFirstCard().equals(c)) {
                        rob.moveBasedOnNextCard(true);
                        playersCopy.remove(rob);
                        break;
                    }
                }
            }
            checkIfWinner();
        }
    }

    /**
     * returns true if any players have visited all the flags,
     * false otherwise
     */
    public boolean checkIfWinner(){
        for(Robot player : players){
            if(player.getVisitedFlags().size() == flags.size()){
                String WinString = "";
                WinString += "Player ";
                WinString += player.getId();
                WinString += " Wins!";
                System.out.println(WinString);
                playing = false;
                return true;
            }
            application.render();
        }
        return false;
    }

    public Flag finalFlag(){
       return flags.get(flags.size()-1);
    }

    public ArrayList<Robot> getPlayers() {
        return players;
    }

    public void AddPlayer(Robot robot) {
        players.add(robot);
    }

    public Application getApplication() {
        return application;
    }

    public ArrayList<Flag> sortFlags(ArrayList<Vector2> flagList){
        ArrayList<Flag> sortedFlags = new ArrayList<>();

        for(Vector2 v : flagList){
            //legge til i flagsliste etter størrelse, minst verdi først, representerer første flag
            sortedFlags.add(new Flag(Math.round(v.x), Math.round(v.y)));
        }
        //TODO: flaggene skal sorteres ordentlig, det under kompilerer ikke

        Collections.sort(sortedFlags, (c1, c2) -> {

            //Sorts the flags according to their tile value

            int id1 = application.getFlagLayer().getCell(Math.round(c1.x), Math.round(c1.y)).getTile().getId();
            int id2 = application.getFlagLayer().getCell(Math.round(c2.x), Math.round(c2.y)).getTile().getId();
            if (id1 > id2) return 1;
            if (id1 < id2) return -1;
            return 0;
        }
         );

        numberOfFlags = sortedFlags.size();
        return sortedFlags;
    }

    public boolean isPlaying() {
        return playing;
    }

    public ArrayList<Wall> getWalls(ArrayList<Vector2> walls){
        ArrayList<Wall> wallList = new ArrayList<>();
        for(Vector2 wall : walls){
            int wallID = application.getWallsLayer().getCell(Math.round(wall.x), Math.round(wall.y)).getTile().getId();
            switch (wallID){
                case 29:
                    wallList.add(new Wall(Math.round(wall.x), Math.round(wall.y), "S"));
                    break;
                case 31:
                    wallList.add(new Wall(Math.round(wall.x), Math.round(wall.y), "N"));
                    break;
                case 30:
                    wallList.add(new Wall(Math.round(wall.x), Math.round(wall.y), "W"));
                    break;
                case 32:
                    wallList.add(new Wall(Math.round(wall.x), Math.round(wall.y), "W"));
                    wallList.add(new Wall(Math.round(wall.x), Math.round(wall.y), "S"));
                    break;
                case 8:
                    wallList.add(new Wall(Math.round(wall.x), Math.round(wall.y), "E"));
                    wallList.add(new Wall(Math.round(wall.x), Math.round(wall.y), "S"));
                    break;
                }
        }
        return wallList;
    }

    public ArrayList<Belt> getBelts(ArrayList<Vector2> belts){
        ArrayList<Belt> beltList = new ArrayList<>();

        for(Vector2 belt : belts){
            int beltId = application.getConveyorBeltLayer().getCell(Math.round(belt.x), Math.round(belt.y)).getTile().getId();

            switch (beltId){
                case 50:
                    beltList.add(new Belt(Math.round(belt.x), Math.round(belt.y), "S",1));
                    break;
                case 52:
                    beltList.add(new Belt(Math.round(belt.x), Math.round(belt.y), "E",1));
                    break;
                case 49:
                    beltList.add(new Belt(Math.round(belt.x), Math.round(belt.y), "N",1));
                    break;
            }
        }

        return beltList;
    }

    public ArrayList<Belt> getBelts() {return beltList;}

    public ArrayList<Flag> getFlags() {return flags;}

    public ArrayList<Wall> getWallList() {return wallList;}


}
