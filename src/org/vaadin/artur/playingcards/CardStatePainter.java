package org.vaadin.artur.playingcards;

import org.vaadin.artur.playingcards.client.ui.VCardState;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;

public class CardStatePainter extends VCardState {
    public static void paint(Card card, PaintTarget target)
            throws PaintException {

        if (!card.isBacksideUp()) {
            target.addAttribute(ATTRIBUTES.SUITE.id(), card.getSuite()
                    .ordinal());
            target.addAttribute(ATTRIBUTES.RANK.id(), card.getRank());
            target.addAttribute(ATTRIBUTES.SELECTED.id(), card.isSelected());
        }

        if (card.isAcceptDrop()) {
            target.addAttribute(ATTRIBUTES.ACCEPT_DROP.id(), true);
        }
        if (card.isDraggable()) {
            target.addAttribute(ATTRIBUTES.DRAGGABLE.id(), true);
        }

    }
}
