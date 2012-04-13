package org.vaadin.artur.playingcards;

import com.vaadin.ui.Component;

public class AbsoluteLayout extends com.vaadin.ui.AbsoluteLayout {

    public void addComponent(Component c, int top, int left) {
        if (getMargin().hasLeft()) {
            left += 10;
        }
        if (getMargin().hasTop()) {
            top += 10;
        }
        super.addComponent(c, "top: " + top + "px; left: " + left + "px");
    }

}
