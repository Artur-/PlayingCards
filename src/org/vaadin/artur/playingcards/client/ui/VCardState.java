package org.vaadin.artur.playingcards.client.ui;

import com.vaadin.terminal.gwt.client.UIDL;

public class VCardState {

    private Suite suite = null;

    private int rank = -1;

    private boolean selected = false;

    private boolean acceptDrop = false;

    private boolean draggable = false;

    protected enum ATTRIBUTES {
        BACKSIDE, SUITE, RANK, SELECTED, ACCEPT_DROP, DRAGGABLE;

        public String id() {
            return String.valueOf(ordinal());
        }
    }

    public static VCardState deserialize(UIDL uidl) {

        VCardState state = new VCardState();
        if (uidl.hasAttribute(ATTRIBUTES.SUITE.id())) {
            int ordinal = uidl.getIntAttribute(ATTRIBUTES.SUITE.id());

            state.setSuite(Suite.getByOrdinal(ordinal));
            state.setRank(uidl.getIntAttribute(ATTRIBUTES.RANK.id()));
            state.setSelected(uidl
                    .getBooleanAttribute(ATTRIBUTES.SELECTED.id()));
        } else {
            state.setSuite(null);
        }

        state.setAcceptDrop(uidl.hasAttribute(ATTRIBUTES.ACCEPT_DROP.id()));
        state.setDraggable(uidl.hasAttribute(ATTRIBUTES.DRAGGABLE.id()));
        return state;

    }

    private void setAcceptDrop(boolean acceptDrop) {
        this.acceptDrop = acceptDrop;
    }

    public boolean isAcceptDrop() {
        return acceptDrop;
    }

    private void setSuite(Suite suite) {
        this.suite = suite;
    }

    private void setRank(int rank) {
        this.rank = rank;
    }

    public Suite getSuite() {
        return suite;
    }

    public int getRank() {
        return rank;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

}
