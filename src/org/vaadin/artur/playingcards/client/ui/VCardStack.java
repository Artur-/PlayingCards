package org.vaadin.artur.playingcards.client.ui;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.VAbsoluteLayout;
import com.vaadin.client.ui.VCustomComponent;
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
		VAbsoluteLayout layout = (VAbsoluteLayout) getWidget();
		Widget wrapper = child.getParent();

		Element childWrapperElement = wrapper.getElement();
		Element clonedContainer = childWrapperElement.getParentElement().cloneNode(true)
				.cast();
		int topOffsetChild = getTop(wrapper);

		// Remove cards before the selected one as these are not being dragged
		while (clonedContainer.getFirstChildElement().equals(childWrapperElement)) {
			com.google.gwt.dom.client.Element childE = clonedContainer.getFirstChildElement();
			childE.removeFromParent();
		}

		int cardHeight = child.getOffsetHeight();
		int cardWidth = child.getOffsetWidth();
		int height = 0;
		for (int i = 0; i < clonedContainer.getChildCount(); i++) {
			// Adjust top with the offset of the first child so the first card
			// is at 0
			Node n = clonedContainer.getChild(i);
			if (!Element.is(n))
				continue;
			Element elem = (Element) n;
			int thisTop = getTop(elem);
			elem.getStyle().setTop(thisTop - topOffsetChild, Unit.PX);
			height = thisTop - topOffsetChild + cardHeight;
		}

		// IE dies without these, don't know why
		Style style = clonedContainer.getStyle();
		style.setWidth(cardWidth, Unit.PX);
		style.setHeight(height, Unit.PX);
		style.setPadding(1, Unit.PX);

		return clonedContainer;

	}

	private int getTop(Widget wrapper) {
		return getTop(wrapper.getElement());
	}

	private int getTop(Element element) {
		String top = element.getStyle().getTop();
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
