package objects;

import com.badlogic.gdx.math.Vector2;

public class Wall extends Vector2 implements IObject {
    String direction;

    public Wall (int x, int y,  String dir){
        setPosition(x,y);
        direction = dir;
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
        return  direction;
    }
}
