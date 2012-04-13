package org.vaadin.artur.playingcards.criteria;

import org.vaadin.artur.playingcards.Card.CardTransferable;
import org.vaadin.artur.playingcards.client.ui.Suite.Color;
import org.vaadin.artur.playingcards.client.ui.VCard.VAcceptCardWithColor;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.acceptcriteria.ClientCriterion;
import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;

@ClientCriterion(VAcceptCardWithColor.class)
public class AcceptCardWithColor extends ClientSideCriterion {

    private Color color;

    public AcceptCardWithColor(Color color) {
        this.color = color;
    }

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
