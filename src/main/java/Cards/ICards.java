package Cards;

//Currently no methods in this interface, but it is useful to have a common class for all card types
public interface ICards {
    /**
     *
     * @return a describing string of the card
     */
    public String getDisplayText();

    /**
     *
     * @return a simple card name that the server can easily read
     */
    public String getSimpleCardName();

}
