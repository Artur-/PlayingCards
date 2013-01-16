package org.vaadin.artur.playingcards;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.artur.playingcards.client.ui.Suite;
import org.vaadin.artur.playingcards.client.ui.VCard;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragSource;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.LegacyComponent;

/**
 * Server side component for the VCard widget.
 */
public class Card extends AbstractComponent implements DropTarget, DragSource,
        LegacyComponent {

    public static int WIDTH = 104;
    public static int HEIGHT = 150;
    private Suite suite;
    private int rank;
    private boolean backsideUp = false;
    private boolean selected = false;

    private CardContainer cardContainer;
    private DropHandler dropHandler;
    private boolean draggable = true;

    public Card(Suite suite, int rank) {
        setSuite(suite);
        setRank(rank);
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        if (dropHandler != null && dropHandler.getAcceptCriterion() != null) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        CardStatePainter.paint(this, target);
    }

    /**
     * Receive and handle events and other variable changes from the client.
     * 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {

        // Variables set by the widget are returned in the "variables" map.

        if (variables.containsKey(VCard.CLICK_EVENT_IDENTIFIER)) {
            fireClick((Map<String, Object>) variables
                    .get(VCard.CLICK_EVENT_IDENTIFIER));
        }

    }

    private void fireClick(Map<String, Object> parameters) {
        MouseEventDetails mouseDetails = MouseEventDetails
                .deSerialize((String) parameters.get("mouseDetails"));
        Component childComponent = (Component) parameters.get("component");

        fireEvent(new ClickEvent(this, mouseDetails));
    }

    public void addListener(ClickListener listener) {
        super.addListener(VCard.CLICK_EVENT_IDENTIFIER, ClickEvent.class,
                listener, ClickListener.clickMethod);
    }

    public void removeListener(ClickListener listener) {
        super.removeListener(VCard.CLICK_EVENT_IDENTIFIER, ClickEvent.class,
                listener);
    }

    public boolean isBacksideUp() {
        return backsideUp;
    }

    public void setBacksideUp(boolean backsideUp) {
        this.backsideUp = backsideUp;
        requestRepaint();
    }

    public Suite getSuite() {
        return suite;
    }

    public void setSuite(Suite suite) {
        this.suite = suite;
        requestRepaint();
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;

        if (this.rank > 13) {
            throw new RuntimeException("Rank cannot be larger than 13");
        }

        requestRepaint();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if (this.selected != selected) {
            this.selected = selected;
            requestRepaint();
        }
    }

    @Override
    public String toString() {
        return "[Card: " + getSuite() + " " + getRank() + "]";
    }

    public CardContainer getCardContainer() {
        return cardContainer;
    }

    public void setCardContainer(CardContainer cardContainer) {
        this.cardContainer = cardContainer;
    }

    public boolean isAcceptDrop() {
        return dropHandler != null;
    }

    public DropHandler getDropHandler() {
        return dropHandler;
    }

    public void setDropHandler(DropHandler dropHandler) {
        this.dropHandler = dropHandler;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return null;
    }

    public static class CardTransferable implements Transferable {

        private Component sourceComponent;
        private Suite suite;
        private int rank;
        private Map<String, Object> data = new HashMap<String, Object>();

        public CardTransferable(Component sourceComponent, Suite suite, int rank) {
            this.sourceComponent = sourceComponent;
            this.suite = suite;
            this.rank = rank;
        }

        public Component getSourceComponent() {
            return sourceComponent;
        }

        public Suite getSuite() {
            return suite;
        }

        public int getRank() {
            return rank;
        }

        public Object getData(String dataFlawor) {
            return data.get(dataFlawor);
        }

        public Collection<String> getDataFlavors() {
            return data.keySet();
        }

        public void setData(String dataFlawor, Object value) {
            data.put(dataFlawor, value);
        }

    }

    public Transferable getTransferable(Map<String, Object> rawVariables) {
        CardTransferable trans = new CardTransferable(this, getSuite(),
                getRank());
        return trans;
    }

}
