package org.vaadin.artur.playingcards.criteria;

import org.vaadin.artur.playingcards.dnd.CardTransferable;
import org.vaadin.artur.playingcards.shared.Suite.Color;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;

public class AcceptCardWithColor extends ClientSideCriterion {

    private Color color;

    public AcceptCardWithColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean accept(DragAndDropEvent dragEvent) {
        Transferable t = dragEvent.getTransferable();
        if (!(t instanceof CardTransferable)) {
            return false;
        }

        return ((CardTransferable) t).getSuite().getColor() == color;
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);
        target.addAttribute("color", color.ordinal());
    }
}
