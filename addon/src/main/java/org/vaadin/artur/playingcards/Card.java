package org.vaadin.artur.playingcards;

import org.vaadin.artur.playingcards.shared.Suite;
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
 * A game card with a given suite and a given rank; either face up or down.
 */
@JavaScript("frontend:///webcomponentsjs/webcomponents-lite.js")
@JavaScript("card.js")
@HtmlImport("frontend://game-card/game-card.html")
public class Card extends AbstractJavaScriptComponent {
    public static final String CLICK_EVENT_IDENTIFIER = "click";

    public static int WIDTH = 186;
    public static int HEIGHT = 264;
    private CardInfo cardInfo;

    private Root element;

    private boolean backsideUp;

    public Card(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
        element = ElementIntegration.getRoot(this);
        updateSymbolAndRank();

        element.addEventListener("click", e -> {
            fireClick(null);
        });
    }

    private void updateSymbolAndRank() {
        if (backsideUp) {
            // This could be improved to remove symbol/rank delayed to avoid
            // clearing the card before flippin
            element.setAttribute("symbol", "");
            element.setAttribute("rank", "");
            element.setAttribute("unrevealed", true);
        } else {
            element.setAttribute("symbol", cardInfo.getSymbol());
            element.setAttribute("rank", cardInfo.getRankSymbol());
            element.removeAttribute("unrevealed");
        }
    }

    public Card(Suite suite, int rank) {
        this(new CardInfo(suite, rank));
    }

    private void fireClick(MouseEventDetails mouseDetails) {
        fireEvent(new ClickEvent(this, mouseDetails));
    }

    public Registration addClickListener(ClickListener listener) {
        return super.addListener(Card.CLICK_EVENT_IDENTIFIER, ClickEvent.class,
                listener, ClickListener.clickMethod);
    }

    public boolean isBacksideUp() {
        return element.hasAttribute("unrevealed");
    }

    public void setBacksideUp(boolean backsideUp) {
        this.backsideUp = backsideUp;
        updateSymbolAndRank();
    }

    public boolean isSelected() {
        return element.getAttribute("highlighted") != null;
    }

    public void setSelected(boolean selected) {
        element.setAttribute("highlighted", selected);
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + ": " + getCardInfo() + "]";
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public int getRank() {
        return getCardInfo().getRank();
    }

    public Suite getSuite() {
        return getCardInfo().getSuite();
    }

}
