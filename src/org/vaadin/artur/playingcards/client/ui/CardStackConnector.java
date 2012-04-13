package org.vaadin.artur.playingcards.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.ComponentConnector;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.Connect;
import com.vaadin.terminal.gwt.client.ui.customcomponent.CustomComponentConnector;

@Connect(org.vaadin.artur.playingcards.CardStack.class)
public class CardStackConnector extends CustomComponentConnector implements
        Paintable {

    @Override
    protected Widget createWidget() {
        return GWT.create(VCardStack.class);
    }

    @Override
    public void updateCaption(ComponentConnector connector) {

    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        ((Paintable) getWidget()).updateFromUIDL(uidl, client);

    }

}
