package org.vaadin.artur.playingcards.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.LegacyConnector;
import com.vaadin.shared.ui.Connect;

@Connect(org.vaadin.artur.playingcards.Deck.class)
public class DeckConnector extends LegacyConnector {

    @Override
    protected Widget createWidget() {
        return GWT.create(VDeck.class);
    }

}
