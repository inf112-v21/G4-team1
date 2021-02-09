package Game;

import objects.robot;

import java.util.ArrayList;

public class Game {
    Boolean playing = false;
    ArrayList<robot> players = new ArrayList<>();

    public Game() {
        playing = true;
    }

    public void Playgame(){
        while(playing){
            for(robot rob : players){
                //rob.pickcard()

            }
            //rob.move();
            Checkwinner();
        }
    }

    public void Checkwinner(){
/*        for(robot rob : players)
            if (rob.position.equals(flag.position)) {
                System.out.println(rob.getName + "is the winner!");
                playing = false;
            }*/

    }

}
