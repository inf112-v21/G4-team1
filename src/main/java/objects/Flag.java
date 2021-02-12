package objects;

public class Flag implements IObject{
    int posX;
    int posY;

    public Flag(int x, int y){
        setPosition(x,y);
    }



    @Override
    public void setPosition(float x, float y) {

    }

    @Override
    public float getX() {
        return posX;
    }

    @Override
    public float getY() {
        return posY;
    }
}
