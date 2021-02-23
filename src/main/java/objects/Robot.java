package objects;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Robot extends Vector2 implements IObject{
    int lifeTokens;
    Flag lastFlag = null;
    int damageTokens;
    String dir = "E";

    public Robot(int x, int y){
        lifeTokens = 3;
        damageTokens = 0;

       setPosition(x,y);
    }

    @Override
    public void setPosition(float x, float y) {
        this.set(x,y);
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    //TODO

    /**
     * moves x tiles in the direction the robot is facing
     * @param tiles number of tiles it moves
     */
    private void move(int tiles){

    }

    /**
     * Turns robot in left
     */
    private void turnLeft(){
        if (dir == "N"){
            dir = "W";
        }
        else if (dir == "E"){
            dir = "N";
        }
        else if (dir == "S"){
            dir = "E";
        }
        else if (dir == "W"){
            dir = "S";
        }
    }

    /**
     * Turns robot right
     */
    private void turnRight(){
        if (dir == "N"){
            dir = "E";
        }
        else if (dir == "E"){
            dir = "S";
        }
        else if (dir == "S"){
            dir = "W";
        }
        else if (dir == "W"){
            dir = "N";
        }
    }

    /**
     *
     * @return the direction the robot is facing
     */
    public String getDir(){
        return dir;
    }

    //TODO
    private void repair(){

    }

    //TODO: Visited flag should register, update last flag
    public void registerFlag(Flag flag){
        lastFlag = flag;
    }

    public Flag getLastFlag(){
        return lastFlag;
    }
}
