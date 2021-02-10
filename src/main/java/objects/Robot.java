package objects;

public class Robot implements IObject{
    int lifeTokens;
    Flag lastFlag = null;
    int damageTokens;
    //TODO: direction

    int posX;
    int posY;

    public Robot(int x, int y){
        lifeTokens = 3;
        damageTokens = 0;

        setXPosition(x);
        setYPosition(y);
    }
    
    @Override
    public void setXPosition(int x) {
        posX = x;
    }

    @Override
    public void setYPosition(int y) {
        posY = y;
    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
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
