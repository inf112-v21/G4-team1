package Game;

import objects.flag;
import objects.robot;

import java.util.ArrayList;

public class Game {
    Boolean playing = false;
    ArrayList<robot> players = new ArrayList<>();
    int numberOfFlags;
    ArrayList<flag> flags = new ArrayList<>();
    //Map Gamemap;


    public Game() {
        playing = true;
    }

    public void Playgame() {
        while (playing) {
            for (robot rob : players) {
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
        for(robot rob : players)
            for(flag flag : flags){
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

    }

    public flag finalflag(){
       return flags.get(flags.size()-1);
    }

}
