package org.vaadin.artur.playingcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;

/**
 * A card stack shows 0-N cards on top of each other but offset so that you can
 * see the rank and suite of all cards which are face up.
 */
public class CardStack extends CustomComponent {
    private int OFFSET = 24 + 18;

    private AbsoluteLayout layout;

    private List<Card> cards = new ArrayList<>();

    public CardStack() {
        layout = new AbsoluteLayout();
        setWidth(Card.WIDTH + "px");
        setCompositionRoot(layout);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card getTopCard() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(cards.size() - 1);
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public int getCardPosition(Card card) {
        return cards.indexOf(card);
    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    public List<Card> getCardsOnTopOf(Card cardInfo) {
        List<Card> cardsAbove = new ArrayList<>();
        int cardIndex = getCardPosition(cardInfo);
        for (int aboveIndex = cardIndex
                + 1; aboveIndex < getNumberOfCards(); aboveIndex++) {
            cardsAbove.add(getCard(aboveIndex));
        }
        return cardsAbove;
    }

    public void addCard(Card card) {
        cards.add(card);
        layout.addComponent(card, getCardPosition(cards.size() - 1));
        updateLayoutSize();
    }

    private void updateLayoutSize() {
        int h = Card.HEIGHT + (cards.size() - 1) * OFFSET;
        setHeight(h + "px");
    }

    private String getCardPosition(int cardPosition) {
        int y = (cardPosition) * OFFSET;
        return "top: " + y + "px";
    }

    public boolean removeCard(Card card) {
        int cardIndex = getCardPosition(card);
        if (cardIndex == -1) {
            return false;
        }

        cards.remove(card);
        layout.removeComponent(card);
        for (int i = cardIndex; i < getNumberOfCards(); i++) {
            layout.getPosition(cards.get(i)).setCSSString(getCardPosition(i));
        }

        updateLayoutSize();
        return true;
    }

    public boolean removeCard(CardInfo card) {
        for (Card c : cards) {
            if (c.getCardInfo().equals(card)) {
                return removeCard(c);
            }
        }

        return false;
    }

    public void removeAllCards() {
        layout.removeAllComponents();
        cards.clear();
        updateLayoutSize();
    }

    public void deselectAll() {
        for (Card c : cards) {
            c.setSelected(false);
        }
    }

    public Optional<Card> getFirstSelected() {
        List<Card> selected = getSelected();
        if (selected.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(selected.get(0));
        }

    }

    public List<Card> getSelected() {
        List<Card> selected = new ArrayList<>();

        for (Card c : cards) {
            if (c.isSelected()) {
                selected.add(c);
            }
        }

        return selected;
    }

    public Registration addLayoutClickListener(LayoutClickListener listener) {
        // layout.addLayoutClickListener(e -> {
        // fireEvent(e);
        // });
        return layout.addLayoutClickListener(listener);
    }

    public boolean containsCard(CardInfo cardToFind) {
        for (Card card : cards) {
            if (cardToFind.equals(card.getCardInfo())) {
                return true;
            }
        }
        return false;
    }

}
