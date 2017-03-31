package org.vaadin.artur.playingcards.client.ui;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.dd.VTransferable;

public class VCardTransferable extends VTransferable {

    private Suite suite;
    private int rank;

    public VCardTransferable(ComponentConnector sourcePaintable, Suite suite,
            int rank) {
        setDragSource(sourcePaintable);
        this.suite = suite;
        this.rank = rank;
    }

    public Suite getSuite() {
        return suite;
    }

    public int getRank() {
        return rank;
    }

}
