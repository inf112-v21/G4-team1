package objects;

import com.badlogic.gdx.math.Vector2;

public class Robot extends Vector2 implements IObject{
    int lifeTokens;
    Flag lastFlag = null;
    int damageTokens;
    //TODO: direction


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
    public int getX() {
        return Math.round(this.x);
    }

    @Override
    public int getY() {
        return Math.round(this.y);
    }

    //TODO

    /**
     * moves x tiles in the direction the robot is facing
     * @param tiles number of tiles it moves
     */
    private void move(int tiles){

    }

    //TODO

    /**
     * Turns robot in given direction
     */
    private void turn(){

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
