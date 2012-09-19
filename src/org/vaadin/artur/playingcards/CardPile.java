package org.vaadin.artur.playingcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.vaadin.artur.playingcards.Card.CardTransferable;
import org.vaadin.artur.playingcards.client.ui.VCard;
import org.vaadin.artur.playingcards.client.ui.VDeck;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragSource;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.server.LegacyComponent;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

/**
 * Server side component for the VDeck widget.
 */
public class CardPile extends AbstractComponent implements CardContainer,
        DropTarget, DragSource, LegacyComponent {

    private ArrayList<Card> cards = new ArrayList<Card>();

    private boolean showTopCard = false;

    private DropHandler dropHandler;

    private boolean draggable;

    public CardPile() {
        // Initialize an empty CardPile
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {

        Card topCard = getTopCard();
        if (topCard != null) {
            target.startTag("topCard");
            CardStatePainter.paint(getTopCard(), target);
            target.endTag("topCard");
        } else {
            target.addAttribute("empty", true);
        }
        target.addAttribute("acceptDrop", isAcceptDrop());
        target.addAttribute("draggable", isDraggable());

        if (dropHandler != null && dropHandler.getAcceptCriterion() != null) {
            dropHandler.getAcceptCriterion().paint(target);
        }
    }

    public boolean isEmpty() {
        return cards.isEmpty();
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

    /**
     * Remove the card at the top of the pile
     * 
     * @return The card that was on top of the pile.
     */
    public Card removeTopCard() {
        if (cards.size() == 0) {
            return null;
        }

        requestRepaint();
        Card topCard = cards.remove(cards.size() - 1);
        topCard.setCardContainer(null);
        return topCard;
    }

    /**
     * Adds a card at the top of the pile
     * 
     * @param c
     *            The card to add
     */
    public void addCard(Card c) {
        cards.add(c);
        c.setCardContainer(this);
        requestRepaint();
    }

    /**
     * Returns the top card without removing it from the pile.
     * 
     * @return
     */
    public Card getTopCard() {
        if (cards.size() == 0) {
            return null;
        }

        return cards.get(cards.size() - 1);
    }

    public List<Card> removeAllCards() {
        ArrayList<Card> oldCards = cards;
        cards = new ArrayList<Card>();
        requestRepaint();

        for (Card c : oldCards) {
            c.setCardContainer(null);
        }
        return oldCards;
    }

    public void setTopCardSelected(boolean selected) {
        if (isEmpty()) {
            return;
        }

        getTopCard().setSelected(selected);
        requestRepaint();
    }

    public boolean isTopCardSelected() {
        if (isEmpty()) {
            return false;
        }

        return getTopCard().isSelected();
    }

    public boolean removeCard(Card card) {

        // Can only remove top card
        if (card == getTopCard()) {
            removeTopCard();
            return true;
        }

        return false;
    }

    public void deselectAll() {
        setTopCardSelected(false);
    }

    public DropHandler getDropHandler() {
        return dropHandler;
    }

    public void setDropHandler(DropHandler dropHandler) {
        this.dropHandler = dropHandler;
    }

    public boolean isAcceptDrop() {
        return dropHandler != null;
    }

    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return null;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public Transferable getTransferable(Map<String, Object> rawVariables) {
        // Top card is the only that can be dragged

        // TODO: Use rawVariables instead
        CardTransferable trans = new CardTransferable(this, getTopCard()
                .getSuite(), getTopCard().getRank());
        return trans;
    }
}
