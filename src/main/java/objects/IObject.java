package objects;

public interface IObject {

    /**
     * Set x position of object
     * @param x
     */
    public void setXPosition(int x);

    /**
     * Set y position of object
     * @param y
     */
    public void setYPosition(int y);

    /**
     *
     * @return objects x position
     */
    public int getX();

    /**
     *
     * @return objects y position
     */
    public int getY();
}
