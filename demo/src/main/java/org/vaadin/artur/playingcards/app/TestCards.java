package org.vaadin.artur.playingcards.app;

import javax.servlet.annotation.WebServlet;

import org.vaadin.artur.playingcards.Card;
import org.vaadin.artur.playingcards.CardStack;
import org.vaadin.artur.playingcards.Deck;
import org.vaadin.artur.playingcards.shared.Suite;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.dnd.DragSourceExtension;
import com.vaadin.event.dnd.DropEvent;
import com.vaadin.event.dnd.DropTargetExtension;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TestCards extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = TestCards.class)
    public static class Servlet extends VaadinServlet {
    }

    private Deck deck;
    private Deck deck2;
    private CardStack nextStack;
    private HorizontalLayout stackLayout;

    @Override
    protected void init(VaadinRequest request) {
        getPage().getStyles()
                .add(".testcards { background-color: cadetblue !important; }");
        CssLayout layout = new CssLayout();
        layout.setWidth("100%");
        setContent(new VerticalLayout(layout));

        deck = new Deck();
        deck.shuffle();
        deck.addClickListener(event -> {
            if (!deck.isEmpty()) {
                Card card = new Card(deck.removeTopCard());
                card.addClickListener(e -> {
                    card.setSelected(!card.isSelected());
                });
                getNextStack().addCard(card);
            }
        });
        layout.addComponent(deck);
        DragSourceExtension deckDrag = new DragSourceExtension<>(deck);
        deckDrag.setEffectAllowed(EffectAllowed.MOVE);
        deckDrag.addDragEndListener(e -> {
            System.out.println(e.getDropEffect());
            System.out.println("drag end for deck");
        });
        new DropTargetExtension<>(layout)
                .addDropListener((DropEvent<CssLayout> e) -> {
                    System.out.println("drop for layout");
                });

        Card c1 = new Card(Suite.SPADES, 12);
        c1.addClickListener(e -> c1.setBacksideUp(!c1.isBacksideUp()));
        layout.addComponent(c1);
        Card c2 = new Card(Suite.DIAMONDS, 1);
        c2.addClickListener(e -> c2.setSelected(true));
        layout.addComponent(c2);

        stackLayout = new HorizontalLayout();

        CardStack stack = new CardStack();
        for (int i = 13; i >= 1; i--) {
            stack.addCard(new Card(Suite.HEARTS, i));
        }
        stack.addLayoutClickListener(e -> {
            Card c = (Card) e.getChildComponent();
            if (!e.isDoubleClick()) {
                c.setBacksideUp(!c.isBacksideUp());
            }
        });
        stackLayout.addComponent(stack);
        nextStack = new CardStack();
        stackLayout.addComponent(nextStack);

        layout.addComponent(stackLayout);
    }

    private CardStack getNextStack() {
        if (nextStack.getNumberOfCards() == 13) {
            nextStack = new CardStack();
            stackLayout.addComponent(nextStack);
        }
        return nextStack;
    }
}
