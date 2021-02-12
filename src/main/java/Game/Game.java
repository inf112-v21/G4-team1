package Game;

import objects.Flag;
import objects.Robot;

import java.util.ArrayList;

public class Game {
    Boolean playing = false;
    ArrayList<Robot> players = new ArrayList<>();
    int numberOfFlags;
    ArrayList<Flag> flags = new ArrayList<>();
    //Map Gamemap;


    public Game() {
        playing = true;
        players.add(new Robot(0,0));
        flags.add(new Flag(4,4));

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

    /**
     * Checks if robot position equals flag position
     * If flag equals the final flag and robot has visited all previous flags,
     * player wins and the game is done
     */
    public void RegistrerFlag() {
        for(Robot rob : players)
            for(Flag flag : flags){
                if (rob.getX() == (flag.getX()) && rob.getY() == (flag.getY()))
                    if(rob.getLastFlag().equals(flags.get(flags.indexOf(flag)-1)) || flags.indexOf(flag) == 0){
                        rob.registerFlag(flag);
                        if(flag.equals(finalflag())) {
                            //System.out.println(rob.getName + "is the winner!");
                            playing = false;
                        }
                    }
                }
            }



    public Flag finalflag(){
       return flags.get(flags.size()-1);
    }

}
