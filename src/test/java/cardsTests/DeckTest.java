package cardsTests;

import Cards.Deck;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeckTest {

    @Test
    public void testIfAddMovementCardsWorks() {
        Deck deck = new Deck();

        // Checks if correct amount of cards was added
        int amountOfCardsBeforeAddition = deck.getCardDeck().size();
        deck.AddMovementCardsToDeck(18, 1);
        assertEquals(amountOfCardsBeforeAddition + 18, deck.getCardDeck().size());
    }

    @Test
    public void testIfAddTurnCardsWorks() {
        Deck deck = new Deck();

        // Checks if correct amount of cards was added
        int amountOfCardsBeforeAddition = deck.getCardDeck().size();
        deck.AddTurnCardsToDeck(18, true, false);
        assertEquals(amountOfCardsBeforeAddition + 18, deck.getCardDeck().size());
    }

}
