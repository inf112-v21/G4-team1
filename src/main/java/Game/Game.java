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

    public Game(ArrayList<Robot> playerlist, ArrayList<Flag> flaglist, Application application) {
        this.application = application;

        players = playerlist;
        flags = flaglist;
        numberOfFlags = flags.size();
        deck = new Deck();
    }

    /**
     * Resets all players position and starts the game
     */
    public void startGame() {
        playing = true;
        players.get(0).setPosition(0,0);
        application.render();
        playGame();
    }

    /**
     * the games turn order
     */
    public void playGame() {

        drawStep();
        printCardsToTerminal();
        playTurn();
        //checkIfWinner();
        //discardStep();
        /*while (playing) {
            drawStep();
            //printCardsToTerminal();
            //playTurn();
            checkIfWinner();
            discardStep();
            System.out.println("TEST");
        }*/
    }

    public void drawStep(){
        for (Robot rob : players) {
            rob.drawHand(deck);
        }


    }

    public void discardStep(){
        for (Robot rob : players) {
            rob.discardHand(deck);
        }
    }

    public void printCardsToTerminal() {
        ArrayList<ICards> cardsToPrint = new ArrayList<>();
        ArrayList<ICards> cardDeck = deck.getCardDeck();

        for(int i = 0; i<9; i++){
            cardsToPrint.add(cardDeck.get(i));
        }

        int counter = 1;

        for (ICards cards : cardsToPrint) {
            System.out.println(counter + ": " + cards.getDisplayText());
            counter++;
        }
        System.out.println("Choose five of these cards using 1-9 on your keyboard");
        chooseCards(cardsToPrint);
    }

    public void chooseCards(ArrayList<ICards> cardsToPrint){
        ArrayList<ICards> chosenCardsFromNineDeck = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (chosenCardsFromNineDeck.size()<5){
            System.out.println("Enter a number between 1-9");
            ICards chosenCard = cardsToPrint.get(scanner.nextInt()-1);

            if(!chosenCardsFromNineDeck.contains(chosenCard)){
                chosenCardsFromNineDeck.add(chosenCard);
                players.get(0).chooseCard(chosenCard);
            }
            else{
                System.out.println("This card is already chosen, chose a new");
            }
        }
    }

    public void playTurn(){
        for (int i = 0; i < players.get(0).getChosenCards().size(); i++) {
            players.get(0).moveBasedOnNextCard();
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
