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
        //players.get(0).setPosition(0,0);
        application.render();
        playGame();
    }

    /**
     * the games turn order
     */
    public void playGame() {
        if (!isCurrentlyPlayingARound) {
            drawStep();
            printCardsToTerminal();
        }
        //playTurn();
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

    /**
     *
     * Gets 9 random cards from the deck and prints cards to the terminal
     * Also runs chooseCards
     */
    public void printCardsToTerminal() {
        isCurrentlyPlayingARound = true;
        System.out.flush();
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
        System.out.println("\n" +"Choose five of these cards using 1-9 on your keyboard");

        // Separate thread to take input
        Thread one = new Thread() {
            public void run() {
                try {
                    chooseCards(cardsToPrint);
                } catch(Exception v) {
                    System.out.println(v);
                }
            }
        };

        one.start();
    }

    /**
     * User picks 5 out of 9 cards for their hand
     * Cannot choose the same card more than once
     * Sends a card that is chosen to the robot class
     * @param cardsToPrint 9 cards to choose from
     */
    public void chooseCards(ArrayList<ICards> cardsToPrint){
        ArrayList<ICards> chosenCardsFromNineDeck = new ArrayList<>();

        while (chosenCardsFromNineDeck.size()<5){
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
            if(!chosenCardsFromNineDeck.contains(chosenCard)){
                chosenCardsFromNineDeck.add(chosenCard);
                players.get(0).addCardToHand(chosenCard);
            }
            else{
                System.out.println("This card is already chosen, chose a new");
            }
        }

        playTurn();
    }

    public void playTurn(){
        int amountOfCards = players.get(0).getChosenCardsFromHand().size();
        for (int i = 0; i < amountOfCards; i++) {
            players.get(0).moveBasedOnNextCard();
            try
            {
                Thread.sleep(500);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
        isCurrentlyPlayingARound = false;
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
