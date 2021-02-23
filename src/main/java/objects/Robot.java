package objects;

import Cards.ICards;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Robot extends Vector2 implements IObject{
    int lifeTokens;
    Flag lastFlag = null;
    int damageTokens;
    ArrayList<ICards> currentcards = new ArrayList<ICards>();
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
        System.out.println("you won!");
    }

    public Flag getLastFlag(){
        return lastFlag;
    }

    public ICards getFirstCard(){
        return currentcards.get(0);
    }

    public void addCard(ICards card){
        currentcards.add(card);
    }
}
