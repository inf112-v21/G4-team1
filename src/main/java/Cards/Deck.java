package Cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<ICards> cardDeck;

    public Deck(){
        cardDeck = new ArrayList<ICards>();

        //18 move 1 cards
        AddMovementCards(18, 1);
        /*for (int i = 1; i < 19; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(1, prio));
        }*/

        //Move 2 cards
        AddMovementCards(12, 2);
        /*for (int i = 1; i < 13; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(2, prio));
        }*/

        //Move 3 cards
        AddMovementCards(6, 3);
        /*for (int i = 1; i < 7; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(3, prio));
        }*/

        //Move 1 back cards
        AddMovementCards(6, -1);
        /*for (int i = 1; i < 7; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(-1, prio));
        }*/

        //Turn right cards
        AddTurnCards(18, true, false);
        /*for (int i = 1; i < 19; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new TurningCard(true, false, prio));
        }*/

        //Turn left cards
        AddTurnCards(18, false, false);
        /*for (int i = 1; i < 19; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new TurningCard(false, false, prio));
        }*/

        //Uturn cards
        AddTurnCards(6, true, true);
        /*for (int i = 1; i < 7; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new TurningCard(true, true, prio));
        }*/
    }

    /**
     * Adds amountOfCards amounts of movement cards with distance moveDistance
     */
    public void AddMovementCards(int amountOfCards, int moveDistance) {
        for (int i = 0; i < amountOfCards; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(moveDistance, prio));
        }
    }

    /**
     * Adds amountOfCards amounts of turn cards
     */
    public void AddTurnCards(int amountOfCards, boolean turnRight, boolean uTurn) {
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
