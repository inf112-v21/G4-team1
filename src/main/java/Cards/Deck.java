package Cards;

import java.util.ArrayList;

public class Deck {
    ArrayList<ICards> cardDeck;

    public Deck(){
        //Move 1 cards
        for (int i = 1; i < 19; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(1, prio));
        }

        //Move 2 cards
        for (int i = 1; i < 13; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(2, prio));
        }

        //Move 3 cards
        for (int i = 1; i < 7; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(3, prio));
        }

        //Move 1 back cards
        for (int i = 1; i < 7; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new MovementCard(-1, prio));
        }

        //Turn right cards
        for (int i = 1; i < 19; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new TurningCard(true, false, prio));
        }

        //Turn left cards
        for (int i = 1; i < 19; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new TurningCard(false, false, prio));
        }

        //Uturn cards
        for (int i = 1; i < 7; i++){
            int prio = (int)Math.round(100*Math.random());
            cardDeck.add(new TurningCard(true, true, prio));
        }
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

    }
}
