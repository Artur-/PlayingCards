package org.vaadin.artur.playingcards.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ui.Connect;
import com.vaadin.terminal.gwt.client.ui.Vaadin6Connector;

@Connect(org.vaadin.artur.playingcards.CardPile.class)
public class CardPileConnector extends Vaadin6Connector {

    @Override
    protected Widget createWidget() {
        return GWT.create(VCardPile.class);
    }
}
