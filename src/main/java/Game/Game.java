package Game;

import objects.Flag;
import objects.Robot;

import java.util.ArrayList;
import Cards.*;

public class Game {
    Boolean playing = false;
    ArrayList<Robot> players;
    int numberOfFlags;
    ArrayList<Flag> flags;
    Deck deck;


    public Game(ArrayList<Robot> playerlist, ArrayList<Flag> flaglist) {
        playing = true;
        players = playerlist;
        flags = flaglist;
        numberOfFlags = flags.size();
        deck = new Deck();
    }

    /**
     * the games turn order
     */
    public void playGame() {
        while (playing) {
            drawStep();
            playTurn();
            checkIfWinner();
            discardStep();
        }
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

    public void playTurn(){
        for(Robot Rob : players){
            ICards card = Rob.getFirstCard();
            if(card.getClass() == MovementCard.class){
                Rob.move(((MovementCard) card).getDistance());
            }
            if(card.getClass() == TurningCard.class){
                if(!((TurningCard) card).getDirection()) Rob.turnLeft();
                if(((TurningCard) card).getDirection()) Rob.turnRight();
            }
        }

    }

    public boolean checkIfWinner(){
        for(Robot player : players){
            if(player.getVisitedFlags().size() == flags.size()){
                System.out.println("you win!");
                playing = false;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if robot position equals flag position
     * If flag equals the final flag and robot has visited all previous flags,
     * player wins and the game is done
     */








    public Flag finalFlag(){
       return flags.get(flags.size()-1);
    }

}
