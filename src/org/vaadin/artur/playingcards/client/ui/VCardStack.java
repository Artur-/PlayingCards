package org.vaadin.artur.playingcards.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.absolutelayout.VAbsoluteLayout;
import com.vaadin.client.ui.absolutelayout.VAbsoluteLayout.AbsoluteWrapper;
import com.vaadin.client.ui.customcomponent.VCustomComponent;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.client.ui.dd.VDropHandler;
import com.vaadin.client.ui.dd.VHasDropHandler;

public class VCardStack extends VCustomComponent implements VHasDropHandler,
        Paintable {

    private VDropHandler dropHandler;
    private ApplicationConnection client;
    private boolean acceptDrop;
    private ComponentConnector connector;

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        connector = ConnectorMap.get(client).getConnector(this);
        this.client = client;
        acceptDrop = uidl.getBooleanAttribute("acceptDrop");
    };

    Element getDragImage(VCard child) {
        Element e = Document.get().createDivElement().cast();
        e.getStyle().setPosition(Position.ABSOLUTE);
        VAbsoluteLayout layout = (VAbsoluteLayout) getWidget();
        Widget wrapper = child.getParent();
        int topOffset = getTop(wrapper);

        int childIndex = layout.getWidgetIndex(wrapper);
        int height = 0;

        for (int i = childIndex; i < layout.getWidgetCount(); i++) {
            AbsoluteWrapper widget = (AbsoluteWrapper) layout.getWidget(i);
            int cloneTop = getTop(widget);

            Element clone = widget.getElement().cloneNode(true).cast();
            clone.getStyle().setTop(cloneTop - topOffset, Unit.PX);
            height = cloneTop - topOffset + widget.getOffsetHeight();
            e.appendChild(clone);
        }
        // IE dies without these, don't know why
        Style style = e.getStyle();
        style.setWidth(getOffsetWidth(), Unit.PX);
        style.setHeight(height, Unit.PX);
        style.setPadding(1, Unit.PX);

        return e;

    }

    private int getTop(Widget wrapper) {
        String top = wrapper.getElement().getStyle().getTop();
        if (top != null && top.endsWith("px")) {
            int t = Integer.parseInt(top.substring(0, top.length() - 2));
            return t;
        }

        return 0;
    }

    public VDropHandler getDropHandler() {
        if (dropHandler == null) {
            dropHandler = new VDropHandler() {

                public ApplicationConnection getApplicationConnection() {
                    return client;
                }

                public boolean drop(VDragEvent drag) {
                    if (!acceptDrop) {
                        return false;
                    }

                    return true;
                }

                public void dragOver(VDragEvent currentDrag) {

                }

                public void dragLeave(VDragEvent drag) {

                }

                public void dragEnter(VDragEvent drag) {

                }

                @Override
                public ComponentConnector getConnector() {
                    return connector;
                }
            };
        }

        return dropHandler;
    }
}
