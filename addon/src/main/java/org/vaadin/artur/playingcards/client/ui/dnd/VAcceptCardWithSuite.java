package org.vaadin.artur.playingcards.client.ui.dnd;

import org.vaadin.artur.playingcards.client.ui.VCardTransferable;
import org.vaadin.artur.playingcards.criteria.AcceptCardWithSuite;
import org.vaadin.artur.playingcards.shared.Suite;

import com.vaadin.client.UIDL;
import com.vaadin.client.ui.dd.VAcceptCriterion;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.shared.ui.dd.AcceptCriterion;

@AcceptCriterion(AcceptCardWithSuite.class)
public class VAcceptCardWithSuite extends VAcceptCriterion {

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
