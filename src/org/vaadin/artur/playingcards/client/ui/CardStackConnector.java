package org.vaadin.artur.playingcards.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.customcomponent.CustomComponentConnector;
import com.vaadin.shared.ui.Connect;

@Connect(org.vaadin.artur.playingcards.CardStack.class)
public class CardStackConnector extends CustomComponentConnector implements
        com.vaadin.client.Paintable {

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
