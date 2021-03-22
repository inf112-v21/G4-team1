package Game;

import inf112.skeleton.app.Application;
import objects.Flag;
import objects.Robot;

import java.util.ArrayList;
import java.util.Scanner;

import Cards.*;

public class Game {
    Boolean playing = false;
    ArrayList<Robot> players;
    int numberOfFlags;
    ArrayList<Flag> flags;
    Deck deck;
    Application application;
    boolean isCurrentlyPlayingARound = false;
    String currentHands;

    public Game(ArrayList<Robot> playerList, ArrayList<Flag> flagList, Application application) {
        this.application = application;

        players = playerList;
        flags = flagList;
        numberOfFlags = flags.size();
        deck = new Deck();

    }

    /**
     * Resets all players position and starts the game
     */
    public void startGame() {
        playing = true;
        //players.get(0).setPosition(0,0);
        application.render();
        playGame();
    }


    /**
     * the games turn order
     */
    public void playGame() {

        while (playing) {
            drawStep();
            //printCardsToTerminal();
            //playTurn();
            //checkIfWinner();
            //discardStep();
        }
    }

    public void drawStep(){
        String hands = "";
        for (Robot rob : players) {
            hands += rob.getId();
            hands += ",";
            rob.drawHand(deck);
        }


    }

    public void discardStep(){
        for (Robot rob : players) {
            rob.discardHand(deck);
        }
    }

    public void playTurn(){
        ArrayList<ICards> cards = new ArrayList();
        for (int i = 0; i < 5; i++){
            for(Robot j : players){
                cards. add(j.getFirstCard());
            }
            //TODO
        }

        for (Robot i : players) {
            int amountOfCards = i.getChosenCardsFromHand().size();
            for (int j = 0; j < amountOfCards; j++) {
                i.moveBasedOnNextCard(true);
                application.render();
            try
            {
                Thread.sleep(1500);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            }
        }
    }

    /**
     * returns true if any players have visited all the flags,
     * false otherwise
     */
    public boolean checkIfWinner(){
        for(Robot player : players){
            if(player.getVisitedFlags().size() == flags.size()){
                System.out.println("you win!");
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


    public boolean isPlaying() {
        return playing;
    }
}
