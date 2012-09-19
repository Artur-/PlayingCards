package org.vaadin.artur.playingcards.criteria;

import org.vaadin.artur.playingcards.Card.CardTransferable;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;

public class AcceptCardWithRank extends ClientSideCriterion {

    private int rank;

    public AcceptCardWithRank(int rank) {
        this.rank = rank;
    }

    public boolean accept(DragAndDropEvent dragEvent) {
        Transferable t = dragEvent.getTransferable();
        if (!(t instanceof CardTransferable)) {
            return false;
        }

        return ((CardTransferable) t).getRank() == rank;
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);
        target.addAttribute("rank", rank);
    }
}
