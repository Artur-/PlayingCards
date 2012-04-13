package org.vaadin.artur.playingcards.client.ui;

import org.vaadin.artur.playingcards.client.ui.VCard.MyClickEventHandler;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HumanInputEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.BrowserInfo;
import com.vaadin.terminal.gwt.client.ComponentConnector;
import com.vaadin.terminal.gwt.client.ConnectorMap;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.ClickEventHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragAndDropManager;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VTransferable;

/**
 * VCardPile represents a pile of front-up cards on top of each other.
 */
public class VCardPile extends AbsolutePanel implements Paintable,
        VHasDropHandler {

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-cardpile";

    public static final String CLICK_EVENT_IDENTIFIER = "click";

    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    protected ApplicationConnection client;

    private Image emptyPile;
    private CardImage topCard;

    private boolean acceptDrop = false;
    private boolean draggable = false;

    private ClickEventHandler ceh;

    private VAbstractDropHandler dropHandler;

    private ComponentConnector connector;

    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VCardPile() {
        emptyPile = new Image(CardImageLookup.getEmptyPile());
        add(emptyPile);
        topCard = new CardImage();
        add(topCard, 0, 0);

        new VDragStarter(this) {

            @Override
            protected void startDrag(HumanInputEvent<?> event) {
                VCardPile.this.dragDropMouseDown(event.getNativeEvent());
            }
        };

        // This method call of the Paintable interface sets the component
        // style name in DOM tree
        setStyleName(CLASSNAME);
    }

    protected void dragDropMouseDown(NativeEvent event) {
        if (!draggable || isEmpty()) {
            return;
        }

        // Transferable data
        VTransferable transferable = new VCardTransferable(connector,
                topCard.getSuite(), topCard.getRank());
        transferable.setDragSource(connector);

        // Start drag event and let DragAndDropManager handle the rest
        VDragEvent drag = VDragAndDropManager.get().startDrag(transferable,
                event, true);

        // FIXME: This should hide the card where it is and restore it to
        // visible if the drag was cancelled

        // Set the drag image
        drag.createDragImage(getElement(), true);

        // Workaround for #4475
        drag.getDragImage().getStyle().setPosition(Position.ABSOLUTE);

        event.preventDefault();
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

        acceptDrop = uidl.getBooleanAttribute("acceptDrop");
        draggable = uidl.getBooleanAttribute("draggable");

        // Process attributes/variables from the server
        if (uidl.hasAttribute("empty")) {
            topCard.setVisible(false);
        }

        for (int i = 0; i < uidl.getChildCount(); i++) {
            UIDL childUIDL = uidl.getChildUIDL(i);
            if (childUIDL.getTag().equals("-ac")) {
                getDropHandler().updateAcceptRules(childUIDL);
            } else {
                VCardState newState = VCardState.deserialize(childUIDL);
                topCard.setCard(newState.getSuite(), newState.getRank(),
                        newState.isSelected());
                topCard.setVisible(true);
            }

        }

    }

    public boolean isEmpty() {
        return !topCard.isVisible();
    }

    public VAbstractDropHandler getDropHandler() {
        if (dropHandler == null) {
            dropHandler = new VAbstractDropHandler() {

                @Override
                protected void validate(VAcceptCallback cb, VDragEvent event) {
                    if (!acceptDrop) {
                        return;
                    }

                    super.validate(cb, event);
                }

                public ApplicationConnection getApplicationConnection() {
                    return client;
                }

                @Override
                public boolean drop(VDragEvent drag) {
                    deEmphasis();

                    return acceptDrop;
                }

                @Override
                public void dragLeave(VDragEvent drag) {
                    super.dragLeave(drag);
                    deEmphasis();
                }

                @Override
                protected void dragAccepted(VDragEvent drag) {
                    emphasis();

                }

                @Override
                public ComponentConnector getConnector() {
                    return connector;
                }

            };
        }
        return dropHandler;
    }

    DivElement empahsisElem = Document.get().createDivElement();

    private void emphasis() {
        if (empahsisElem.getParentElement() == null) {
            // Create an emphasis element of the same size as the VCardPile
            Style style = empahsisElem.getStyle();
            style.setWidth(getOffsetWidth(), Unit.PX);
            style.setHeight(getOffsetHeight(), Unit.PX);
            style.setBackgroundColor("#333333");
            style.setOpacity(0.3);
            if (BrowserInfo.get().isIE()) {
                style.setProperty("filter", "alpha(opacity=30)");
            }
            style.setPosition(Position.ABSOLUTE);
            style.setLeft(0, Unit.PX);
            style.setTop(0, Unit.PX);
            getElement().appendChild(empahsisElem);
        }

    }

    private void deEmphasis() {
        if (empahsisElem.getParentElement() != null) {
            empahsisElem.getParentElement().removeChild(empahsisElem);
        }
    }
}
