package org.vaadin.artur.playingcards;

import java.util.ArrayList;
import java.util.Collections;

import org.vaadin.artur.playingcards.shared.Suite;
import org.vaadin.elements.ElementIntegration;
import org.vaadin.elements.Root;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.JavaScript;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 * Representation of a deck of cards or an empty placeholder where the deck
 * would be.
 *
 * Optionally shows the top card and the number of cards in the deck.
 */
@JavaScript("frontend:///webcomponentsjs/webcomponents-lite.js")
@JavaScript("deck.js")
@HtmlImport("frontend://game-card/game-card-deck.html")
public class Deck extends AbstractJavaScriptComponent {

    private ArrayList<CardInfo> cards = new ArrayList<>();
    private Root element = ElementIntegration.getRoot(this);

    public Deck() {
        element.addEventListener("click", e -> {
            fireClick();
        });

        reset();

    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public void shuffle() {
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(cards);
        }
    }

    private void fireClick() {
        fireEvent(new ClickEvent(this, null));
    }

    public Registration addClickListener(ClickListener listener) {
        return super.addListener(Card.CLICK_EVENT_IDENTIFIER, ClickEvent.class,
                listener, ClickListener.clickMethod);
    }

    public CardInfo removeTopCard() {
        if (cards.size() == 0) {
            return null;
        }

        CardInfo c = cards.remove(0);
        updateNumberOfCards();
        return c;
    }

    public void addCard(CardInfo c) {
        cards.add(c);
        updateNumberOfCards();
    }

    public void reset() {
        removeAllCards();

        // Initialize deck with 52 cards, no jokers
        for (Suite suite : Suite.values()) {
            for (int i = 1; i <= 13; i++) {
                CardInfo c = new CardInfo(suite, i);
                cards.add(c);
            }
        }
        updateNumberOfCards();
    }

    private void updateNumberOfCards() {
        element.setAttribute("card-count", cards.size() + "");
    }

    private void removeAllCards() {
        cards.clear();
        updateNumberOfCards();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

}
