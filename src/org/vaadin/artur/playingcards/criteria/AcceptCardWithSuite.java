package org.vaadin.artur.playingcards.criteria;

import org.vaadin.artur.playingcards.Card.CardTransferable;
import org.vaadin.artur.playingcards.client.ui.Suite;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;

public class AcceptCardWithSuite extends ClientSideCriterion {

    private Suite suite;

    public AcceptCardWithSuite(Suite suite) {
        this.suite = suite;
    }

    public boolean accept(DragAndDropEvent dragEvent) {
        Transferable t = dragEvent.getTransferable();
        if (!(t instanceof CardTransferable)) {
            return false;
        }

        return ((CardTransferable) t).getSuite() == suite;
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);
        target.addAttribute("suite", suite.ordinal());
    }
}
