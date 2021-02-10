package objects;

public interface IObject {

    /**
     * Set x position of object
     * @param x
     */
    public void setPosition(float x, float y);

    /**
     *
     * @return objects x position
     */
    public float getX();

    /**
     *
     * @return objects y position
     */
    public float getY();
}
