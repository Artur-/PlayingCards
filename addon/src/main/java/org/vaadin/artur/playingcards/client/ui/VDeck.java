package org.vaadin.artur.playingcards.client.ui;

import org.vaadin.artur.playingcards.client.ui.VCard.MyClickEventHandler;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.ClickEventHandler;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VDeck extends AbsolutePanel implements Paintable {

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-deck";

    public static final String CLICK_EVENT_IDENTIFIER = "click";

    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    protected ApplicationConnection client;

    private ClickEventHandler ceh = null;
    private int cardsInDeck = 0;
    // private Label cardsInDeckLabel = new Label("");

    private Image deckPlaceholder = new Image(CardImageLookup.getEmptyPile());
    private Image topCard = new Image();

    private Image deckBackImage = new Image(CardImageLookup.getCardBack());

    private boolean showTopCard;

    private ComponentConnector connector;

    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VDeck() {
        add(deckPlaceholder);
        add(deckBackImage);
        add(topCard);
        // add(cardsInDeckLabel, 20, 20);

        // Enforce update
        showTopCard = true;
        setShowTopCard(false);

        // This method call of the Paintable interface sets the component
        // style name in DOM tree
        setStyleName(CLASSNAME);

        // Tell GWT we are interested in receiving click events
        // sinkEvents(Event.ONCLICK);

        // Add a handler for the click events (this is similar to
        // FocusWidget.addClickHandler())
        // addDomHandler(this, ClickEvent.getType());
    }

    private void setShowTopCard(boolean showTopCard) {
        this.showTopCard = showTopCard;
    }

    /**
     * Called whenever an update is received from the server
     */
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        connector = ConnectorMap.get(client).getConnector(this);
        if (ceh == null) {
            ceh = new MyClickEventHandler(connector, CLICK_EVENT_IDENTIFIER);
        }
        // This call should be made first.
        // It handles sizes, captions, tooltips, etc. automatically.
        if (client.updateComponent(this, uidl, true)) {
            // If client.updateComponent returns true there has been no changes
            // and we
            // do not need to update anything.
            return;
        }
        ceh.handleEventHandlerRegistration();

        // Save reference to server connection object to be able to send
        // user interaction later
        this.client = client;

        // Save the client side identifier (paintable id) for the widget
        paintableId = uidl.getId();

        setShowTopCard(uidl.getBooleanAttribute("showTopCard"));
        if (isShowTopCard()) {
            VCardState cs = VCardState.deserialize(uidl.getChildUIDL(0));
            setTopCard(cs);
        }
        setCardsInDeck(uidl.getIntAttribute("cardsInDeck"));

        updateImageVisibility();
    }

    private void setTopCard(VCardState state) {
        topCard.setResource(CardImageLookup.getCard(state.getSuite(),
                state.getRank()));
    }

    private void updateImageVisibility() {
        deckBackImage.setVisible(!showTopCard && cardsInDeck > 0);
        deckPlaceholder.setVisible(!showTopCard && cardsInDeck == 0);
        topCard.setVisible(showTopCard);
    }

    private void setCardsInDeck(int cardsInDeck) {
        if (cardsInDeck == this.cardsInDeck) {
            return;
        }

        if (this.cardsInDeck >= 3 && cardsInDeck >= 3) {
            this.cardsInDeck = cardsInDeck;
            return;
        }

        this.cardsInDeck = cardsInDeck;
        // cardsInDeckLabel.setText(String.valueOf(cardsInDeck));

        if (cardsInDeck >= 3) {
            deckBackImage.setResource(CardImageLookup.getCardBack3());
        } else if (cardsInDeck >= 2) {
            deckBackImage.setResource(CardImageLookup.getCardBack2());
        } else if (cardsInDeck >= 1) {
            deckBackImage.setResource(CardImageLookup.getCardBack());
        }

    }

    public boolean isShowTopCard() {
        return showTopCard;
    }
}
