package Game;

import objects.Flag;
import objects.Robot;

import java.util.ArrayList;
import Cards.*;

public class Game {
    Boolean playing = false;
    ArrayList<Robot> players = new ArrayList<>();
    int numberOfFlags;
    ArrayList<Flag> flags = new ArrayList<>();


    public Game(Robot player, Flag flag) {
        playing = true;
        players.add(player);
        flags.add(flag);
    }

    public void Playgame() {
        while (playing) {
            for (Robot rob : players) {
                //rob.pickcard()
            }
            //rob.move();
            //Checkwinner();
        }
    }

    public void Playturn(){
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

    public void CheckifWinner(){
        for(Robot player : players){
            if(player.getLastFlag().equals(finalflag())){
                System.out.println("you win!");
                playing = false;
            }
        }
    }

    /**
     * Checks if robot position equals flag position
     * If flag equals the final flag and robot has visited all previous flags,
     * player wins and the game is done
     */
    public void RegisterFlag(Flag flag) {




//        for(Robot rob : players)
//            for(Flag flag : flags){
//                if (rob.getX() == (flag.getX()) && rob.getY() == (flag.getY()))
//                    if(rob.getLastFlag().equals(flags.get(flags.indexOf(flag)-1)) || flags.indexOf(flag) == 0){
//                        rob.registerFlag(flag);
//                        if(flag.equals(finalflag())) {
                            //System.out.println(rob.getName + "is the winner!");
//                            playing = false;
//                        }
//                    }
//                }
            }



    public Flag finalflag(){
       return flags.get(flags.size()-1);
    }

}
