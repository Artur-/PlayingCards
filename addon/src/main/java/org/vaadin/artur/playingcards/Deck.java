package org.vaadin.artur.playingcards;

import java.util.Map;

import org.vaadin.artur.playingcards.client.ui.Suite;
import org.vaadin.artur.playingcards.client.ui.VCard;
import org.vaadin.artur.playingcards.client.ui.VDeck;
import org.vaadin.artur.playingcards.collection.ShufflableArrayList;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.LegacyComponent;

/**
 * Server side component for the VDeck widget.
 */
public class Deck extends AbstractComponent implements CardContainer,
        LegacyComponent {

    private ShufflableArrayList<Card> cards = new ShufflableArrayList<Card>();

    private boolean showTopCard = false;

    public Deck() {
        reset();
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {

        target.addAttribute("cardsInDeck", size());
        target.addAttribute("showTopCard", isShowTopCard());
    }

    public int size() {
        return cards.size();
    }

    public void shuffle() {
        for (int i = 0; i < 10; i++) {
            cards.shuffle();
        }
    }

    /**
     * Receive and handle events and other variable changes from the client.
     * 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        // Variables set by the widget are returned in the "variables" map.

        if (variables.containsKey(VDeck.CLICK_EVENT_IDENTIFIER)) {
            fireClick((Map<String, Object>) variables
                    .get(VDeck.CLICK_EVENT_IDENTIFIER));
        }

    }

    private void fireClick(Map<String, Object> parameters) {
        MouseEventDetails mouseDetails = MouseEventDetails
                .deSerialize((String) parameters.get("mouseDetails"));
        Component childComponent = (Component) parameters.get("component");

        fireEvent(new LayoutClickEvent(this, mouseDetails, childComponent,
                childComponent));
    }

    public void addListener(ClickListener listener) {
        super.addListener(VCard.CLICK_EVENT_IDENTIFIER, ClickEvent.class,
                listener, ClickListener.clickMethod);
    }

    public void removeListener(ClickListener listener) {
        super.removeListener(VCard.CLICK_EVENT_IDENTIFIER, ClickEvent.class,
                listener);
    }

    public boolean isShowTopCard() {
        return showTopCard;
    }

    public void setShowTopCard(boolean showTopCard) {
        this.showTopCard = showTopCard;
        requestRepaint();
    }

    public Card removeTopCard() {
        if (cards.size() == 0) {
            return null;
        }

        requestRepaint();

        Card c = cards.remove(0);
        c.setCardContainer(null);
        return c;
    }

    public void addCard(Card c) {
        cards.add(c);
        requestRepaint();
    }

    public void reset() {
        removeAllCards();

        // Initialize deck with 52 cards, no jokers
        for (Suite suite : Suite.values()) {
            for (int i = 1; i <= 13; i++) {
                Card c = new Card(suite, i);
                c.setCardContainer(this);
                cards.add(c);
            }
        }
        requestRepaint();
    }

    private void removeAllCards() {
        cards.clear();
        requestRepaint();
    }

    public boolean removeCard(Card card) {
        return false;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

}
