package objects;

import com.badlogic.gdx.math.Vector2;

public class Flag extends Vector2 implements IObject{

    public Flag(int x, int y){
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
}
