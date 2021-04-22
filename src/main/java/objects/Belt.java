package objects;

import com.badlogic.gdx.math.Vector2;

public class Belt extends Vector2 implements IObject {

    String beltDir;
    int beltPower;

    public Belt (int x, int y, String dir, int power){
        setPosition(x,y);
        beltDir = dir;
        beltPower = power;
    }
    public void setPosition(float x, float y) {
        this.set(x,y);
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public String getDir(){
        return beltDir;
    }

    public int getPower() {
        return beltPower;
    }
}
