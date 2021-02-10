package Game;

import objects.flag;
import objects.robot;

import java.util.ArrayList;

public class Game {
    Boolean playing = false;
    ArrayList<robot> players = new ArrayList<>();
    int numberofflags;
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
            Checkwinner();
        }
    }

    public void RegistrerFlag() {
        for(robot rob : players)
            for(flag flag : flags){
                if (rob.getX().equals(flags.get(0).getX()) && rob.getY().equals(flags.get(0).getY()))
                {
                    rob.registerFlag(flag);
                    if(flag.equals(finalflag())) {
                        System.out.println(rob.getName + "is the winner!");
                        playing = false;
                    }
                }


            }

    }

    public flag finalflag(){
       return flags.get(flags.size()-1);
    }

}
