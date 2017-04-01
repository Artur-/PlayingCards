package org.vaadin.artur.playingcards.client.ui.dnd;

import org.vaadin.artur.playingcards.client.ui.VCardTransferable;
import org.vaadin.artur.playingcards.criteria.AcceptCardWithColor;
import org.vaadin.artur.playingcards.shared.Suite.Color;

import com.vaadin.client.UIDL;
import com.vaadin.client.ui.dd.VAcceptCriterion;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.shared.ui.dd.AcceptCriterion;

@AcceptCriterion(AcceptCardWithColor.class)
public class VAcceptCardWithColor extends VAcceptCriterion {

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
