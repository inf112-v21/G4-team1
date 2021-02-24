package Cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<ICards> cardDeck;

    public Deck(){
        cardDeck = new ArrayList<ICards>();

        //18 move 1 cards
        AddMovementCardsToDeck(18, 1);

        //Move 2 cards
        AddMovementCardsToDeck(12, 2);

        //Move 3 cards
        AddMovementCardsToDeck(6, 3);

        //Move 1 back cards
        AddMovementCardsToDeck(6, -1);

        //Turn right cards
        AddTurnCardsToDeck(18, true, false);

        //Turn left cards
        AddTurnCardsToDeck(18, false, false);

        //Uturn cards
        AddTurnCardsToDeck(6, true, true);
    }

    /**
     * Adds movement cards
     * @param amountOfCards Amount of cards added to the deck
     * @param moveDistance The amount of tiles the card moves the robot
     */
    public void AddMovementCardsToDeck(int amountOfCards, int moveDistance) {
        for (int i = 0; i < amountOfCards; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(moveDistance, prio));
        }
    }

    /**
     * Adds turn cards to deck
     * @param amountOfCards Amount of cards added to the deck
     * @param turnRight Robot turns right if true, left if not.
     * @param uTurn Whether the card is a uTurn card or not.
     */
    public void AddTurnCardsToDeck(int amountOfCards, boolean turnRight, boolean uTurn) {
        for (int i = 0; i < amountOfCards; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new TurningCard(turnRight, uTurn, prio));
        }
    }

    /**
     * Returns entire deck of cards
     */
    public ArrayList<ICards> getCardDeck() {
        return cardDeck;
    }

    /**
     *
     * @return the top card of the deck
     */
    private Object draw(){
        return null;
    }

    /**
     * shuffle the deck
     */
    private void shuffle(){
        Collections.shuffle(cardDeck);
    }
}
