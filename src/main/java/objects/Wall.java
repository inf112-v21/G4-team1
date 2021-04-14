package objects;

public class Wall {
    int xPos;
    int yPos;
    String direction;

    public Wall(int x, int y,  String dir){
        int xPos = x;
        int yPos = y;

        direction = dir;
    }

    public int getXPos(){
        return xPos;
    }

    public int getYPos(){
        return yPos;
    }

    public String getDir(){
        return  direction;
    }
}
