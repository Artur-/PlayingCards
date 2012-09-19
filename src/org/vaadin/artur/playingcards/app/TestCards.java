package org.vaadin.artur.playingcards.app;

import org.vaadin.artur.playingcards.Card;
import org.vaadin.artur.playingcards.CardPile;
import org.vaadin.artur.playingcards.CardStack;
import org.vaadin.artur.playingcards.Deck;
import org.vaadin.artur.playingcards.client.ui.Suite;

import com.vaadin.LegacyApplication;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI.LegacyWindow;

public class TestCards extends LegacyApplication {

    private Deck deck;
    private CardStack stack;

    @Override
    public void init() {
        setMainWindow(new LegacyWindow("Cards test", new HorizontalLayout()));

        deck = new Deck();
        deck.addListener(new ClickListener() {

            public void click(ClickEvent event) {
                if (!deck.isEmpty()) {
                    stack.addCard(deck.removeTopCard());
                }

            }
        });
        getMainWindow().addComponent(deck);

        Card c1 = new Card(Suite.SPADES, 12);
        c1.setDropHandler(new DropHandler() {

            public void drop(DragAndDropEvent dropEvent) {
                Transferable t = dropEvent.getTransferable();
                Component source = t.getSourceComponent();
                if (source instanceof Card) {
                    // Card sourceCard = (Card) source;
                }
            }

            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }

        });

        getMainWindow().addComponent(c1);
        Card c2 = new Card(Suite.DIAMONDS, 1);
        c2.addListener(new ClickListener() {

            @Override
            public void click(ClickEvent event) {
                System.out.println("Foo");
            }
        });
        getMainWindow().addComponent(c2);

        stack = new CardStack();
        stack.addListener(new LayoutClickListener() {

            public void layoutClick(LayoutClickEvent event) {
                Card c = (Card) event.getChildComponent();
                if (c != null) {
                    c.setSelected(!c.isSelected());
                }

            }
        });
        stack.addCard(deck.removeTopCard());
        getMainWindow().addComponent(stack);

        CardPile pile = new CardPile();
        pile.addCard(deck.removeTopCard());

        getMainWindow().addComponent(pile);
    }
}
