package org.vaadin.artur.playingcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.LegacyComponent;

public class CardStack extends CustomComponent
        implements CardContainer, DropTarget, LegacyComponent {

    private int spacing = 24;
    private List<Card> cards = new ArrayList<>();
    private AbsoluteLayout layout;
    private DropHandler dropHandler;

    public CardStack() {
        layout = new AbsoluteLayout();
        setCompositionRoot(layout);
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        target.addAttribute("acceptDrop", isAcceptDrop());
    }

    @Override
    public void addCard(Card c) {
        cards.add(c);
        c.setCardContainer(this);
        layout.addComponent(c, getCardPosition(cards.size() - 1));
        updateLayoutSize();
    }

    private void updateLayoutSize() {
        int h = Card.HEIGHT + (cards.size() - 1) * spacing;
        setHeight(h + "px");
        setWidth(Card.WIDTH + "px");

    }

    private String getCardPosition(int cardPosition) {
        int y = (cardPosition) * spacing;
        return "top: " + y + "px";
    }

    /**
     * Removes the card from the CardStack. This method fails if the card is not
     * present in the stack.
     */
    @Override
    public boolean removeCard(Card c) {
        int index = cards.indexOf(c);
        if (index < 0) {
            return false;
        }

        c.setCardContainer(null);
        cards.remove(index);
        layout.removeComponent(c);
        for (int i = index; i < cards.size(); i++) {
            layout.getPosition(cards.get(i)).setCSSString(getCardPosition(i));
        }
        updateLayoutSize();
        requestRepaint();

        return true;
    }

    public void removeAllCards() {
        layout.removeAllComponents();
        cards.clear();
        updateLayoutSize();
        requestRepaint();

    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    public void addListener(LayoutClickListener listener) {
        layout.addLayoutClickListener(listener);
    }

    public void removeListener(LayoutClickListener listener) {
        layout.removeLayoutClickListener(listener);
    }

    public void deselectAll() {
        for (Card c : cards) {
            c.setSelected(false);
        }
    }

    public Card getTopCard() {
        if (isEmpty()) {
            return null;
        }

        return cards.get(cards.size() - 1);

    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int getCardPosition(Card card) {
        return cards.indexOf(card);
    }

    public int size() {
        return cards.size();
    }

    public List<Card> getCardsAbove(Card card) {
        List<Card> cardsAbove = new ArrayList<>();
        int cardIndex = getCardPosition(card);
        for (int aboveIndex = cardIndex
                + 1; aboveIndex < size(); aboveIndex++) {
            cardsAbove.add(getCard(aboveIndex));
        }
        return cardsAbove;
    }

    @Override
    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return null;
    }

    @Override
    public DropHandler getDropHandler() {
        return dropHandler;
    }

    public void setDropHandler(DropHandler dropHandler) {
        this.dropHandler = dropHandler;
    }

    private boolean isAcceptDrop() {
        return getDropHandler() != null;
    }

    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        // TODO Auto-generated method stub

    }
}
