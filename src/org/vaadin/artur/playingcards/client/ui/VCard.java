package org.vaadin.artur.playingcards.client.ui;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.artur.playingcards.client.ui.Suite.Color;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HumanInputEvent;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.BrowserInfo;
import com.vaadin.terminal.gwt.client.ComponentConnector;
import com.vaadin.terminal.gwt.client.ConnectorMap;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.ClickEventHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCriterion;
import com.vaadin.terminal.gwt.client.ui.dd.VDragAndDropManager;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VCard extends AbsolutePanel implements Paintable, VHasDropHandler {

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-card";

    public static final String CLICK_EVENT_IDENTIFIER = "click";

    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    protected ApplicationConnection client;

    // private CardState state;

    private CardImage image;

    private ClickEventHandler ceh = null;

    private VAbstractDropHandler dropHandler;

    private boolean draggable;

    private VCardState state;

    private ComponentConnector connector;

    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VCard() {
        image = new CardImage();
        add(image);
        // DndEventHandler dndHandler =
        new VDragStarter(this) {

            @Override
            protected void startDrag(HumanInputEvent<?> event) {
                VCard.this.startDrag(event.getNativeEvent());
            }
        };

        // This method call of the Paintable interface sets the component
        // style name in DOM tree
        setStyleName(CLASSNAME);
    }

    protected void startDrag(NativeEvent event) {
        if (!draggable) {
            return;
        }

        // Transferable data
        VCardTransferable transferable = new VCardTransferable(connector,
                state.getSuite(), state.getRank());

        // Start drag event and let DragAndDropManager handle the rest
        VDragEvent drag = VDragAndDropManager.get().startDrag(transferable,
                event, true);

        // Set the drag image

        Element dragElement;

        // Hackish support for CardStack
        if (getParent() != null && getParent().getParent() != null
                && getParent().getParent().getParent() != null
                && getParent().getParent().getParent() instanceof VCardStack) {
            dragElement = ((VCardStack) getParent().getParent().getParent())
                    .getDragImage(this);
        } else {
            dragElement = (Element) getElement().cloneNode(true);
        }

        int x = Util.getTouchOrMouseClientX(event) - getAbsoluteLeft();
        int y = Util.getTouchOrMouseClientY(event) - getAbsoluteTop();

        drag.setDragImage(dragElement, -x, -y);

        event.preventDefault();
    }

    public static class MyClickEventHandler extends ClickEventHandler {

        private String clickEventIdentifier;

        public MyClickEventHandler(ComponentConnector connector,
                String clickEventIdentifier) {
            super(connector);
            this.clickEventIdentifier = clickEventIdentifier;
        }

        @Override
        protected void fireClick(NativeEvent event,
                MouseEventDetails mouseDetails) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("mouseDetails", mouseDetails.serialize());
            connector.getConnection().updateVariable(
                    connector.getConnectorId(), clickEventIdentifier,
                    parameters, true);

        }
    };

    /**
     * Called whenever an update is received from the server
     */
    public void updateFromUIDL(UIDL uidl, final ApplicationConnection client) {
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

        // Process attributes/variables from the server
        // The attribute names are the same as we used in
        // paintContent on the server-side
        state = VCardState.deserialize(uidl);
        image.setCard(state.getSuite(), state.getRank(), state.isSelected());

        draggable = state.isDraggable();

        if (uidl.getChildCount() == 1) {
            getDropHandler().updateAcceptRules(uidl.getChildUIDL(0));
        }
    }

    public VAbstractDropHandler getDropHandler() {
        if (dropHandler == null) {
            dropHandler = new VCardDropHandler();
        }

        return dropHandler;
    }

    DivElement empahsisElem = Document.get().createDivElement();

    private void emphasis() {
        if (empahsisElem.getParentElement() == null) {
            Style style = empahsisElem.getStyle();
            style.setWidth(getOffsetWidth(), Unit.PX);
            style.setHeight(getOffsetHeight(), Unit.PX);
            style.setBackgroundColor("#333333");
            style.setOpacity(0.5);
            if (BrowserInfo.get().isIE()) {
                style.setProperty("filter", "alpha(opacity=50)");
            }
            style.setPosition(Position.ABSOLUTE);
            // style.setZIndex(1);
            style.setLeft(0, Unit.PX);
            style.setTop(0, Unit.PX);
            getElement().appendChild(empahsisElem);
            com.google.gwt.dom.client.Element parentElement = empahsisElem
                    .getParentElement();
            int childCount = parentElement.getChildCount();
            childCount++;
        }

    }

    private void deEmphasis() {
        if (empahsisElem.getParentElement() != null) {
            empahsisElem.getParentElement().removeChild(empahsisElem);
        }
    }

    public class VCardDropHandler extends VAbstractDropHandler {

        public ApplicationConnection getApplicationConnection() {
            return client;
        }

        @Override
        public void dragLeave(VDragEvent drag) {
            deEmphasis();
        }

        @Override
        public boolean drop(VDragEvent drag) {
            if (super.drop(drag)) {
                deEmphasis();
                return true;
            }

            return false;
        }

        @Override
        protected void dragAccepted(VDragEvent drag) {
            emphasis();
        }

        @Override
        public ComponentConnector getConnector() {
            return connector;
        }

    }

    public static class VAcceptCardWithSuite extends VAcceptCriterion {

        @Override
        public boolean needsServerSideCheck(VDragEvent drag, UIDL criterioUIDL) {
            return false;
        }

        @Override
        protected boolean accept(VDragEvent drag, UIDL configuration) {
            if (!(drag.getTransferable() instanceof VCardTransferable)) {
                return false;
            }

            VCardTransferable t = (VCardTransferable) drag.getTransferable();

            int s = configuration.getIntAttribute("suite");
            Suite suite = Suite.getByOrdinal(s);

            return (suite == t.getSuite());
        }

    }

    public static class VAcceptCardWithColor extends VAcceptCriterion {

        @Override
        public boolean needsServerSideCheck(VDragEvent drag, UIDL criterioUIDL) {
            return false;
        }

        @Override
        protected boolean accept(VDragEvent drag, UIDL configuration) {
            if (!(drag.getTransferable() instanceof VCardTransferable)) {
                return false;
            }

            VCardTransferable t = (VCardTransferable) drag.getTransferable();

            int c = configuration.getIntAttribute("color");
            Color color = Color.getByOrdinal(c);

            return (color == t.getSuite().getColor());
        }

    }

    public static class VAcceptCardWithRank extends VAcceptCriterion {

        @Override
        public boolean needsServerSideCheck(VDragEvent drag, UIDL criterioUIDL) {
            return false;
        }

        @Override
        protected boolean accept(VDragEvent drag, UIDL configuration) {
            if (!(drag.getTransferable() instanceof VCardTransferable)) {
                return false;
            }

            VCardTransferable t = (VCardTransferable) drag.getTransferable();

            int rank = configuration.getIntAttribute("rank");

            return (rank == t.getRank());
        }

    }

}
