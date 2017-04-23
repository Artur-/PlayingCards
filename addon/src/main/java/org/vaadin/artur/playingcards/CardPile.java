package org.vaadin.artur.playingcards;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.elements.ElementIntegration;
import org.vaadin.elements.Root;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.JavaScript;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 * A card pile is either empty or has 1-N cards on top of each other, with the
 * topmost card face up.
 */
@JavaScript("frontend:///webcomponentsjs/webcomponents-lite.js")
@JavaScript("cardpile.js")
@HtmlImport("frontend://game-card/game-card-pile.html")
public class CardPile extends AbstractJavaScriptComponent {
    private static final String ATTR_TOP_CARD_SELECTED = "top-card-highlighted";
    private List<CardInfo> cards = new ArrayList<>();
    private Root element;

    public CardPile() {
        element = ElementIntegration.getRoot(this);
        element.addEventListener("click", e -> {
            fireClick(null);
        });
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public CardInfo getTopCard() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(cards.size() - 1);
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public void addCard(CardInfo cardInfo) {
        cards.add(cardInfo);
        updateTopCard();
    }

    public boolean removeTopCard() {
        if (isEmpty()) {
            return false;
        }

        int cardIndex = cards.size() - 1;
        cards.remove(cardIndex);
        updateTopCard();
        return true;
    }

    public void removeAllCards() {
        cards.clear();
        updateTopCard();
    }

    private void updateTopCard() {
        if (isEmpty()) {
            element.setAttribute("top-card-symbol", "");
            element.setAttribute("top-card-rank", "");
            element.removeAttribute(ATTR_TOP_CARD_SELECTED);
        } else {
            element.setAttribute("top-card-symbol", getTopCard().getSymbol());
            element.setAttribute("top-card-rank", getTopCard().getRankSymbol());
        }

    }

    private void fireClick(MouseEventDetails mouseDetails) {
        fireEvent(new ClickEvent(this, mouseDetails));
    }

    public Registration addClickListener(ClickListener listener) {
        return super.addListener(Card.CLICK_EVENT_IDENTIFIER, ClickEvent.class,
                listener, ClickListener.clickMethod);
    }

    public List<CardInfo> getCards() {
        return cards;
    }

    public void setTopCardSelected(boolean topCardSelected) {
        if (topCardSelected) {
            element.setAttribute(ATTR_TOP_CARD_SELECTED, topCardSelected);
        } else {
            element.removeAttribute(ATTR_TOP_CARD_SELECTED);
        }
    }

    public boolean isTopCardSelected() {
        return element.hasAttribute(ATTR_TOP_CARD_SELECTED);
    }

}
