package org.vaadin.artur.playingcards.client.ui;

import com.google.gwt.event.dom.client.HumanInputEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.Widget;

public abstract class VDragStarter implements MouseDownHandler, MouseUpHandler,
		MouseOverHandler, MouseOutHandler, MouseMoveHandler, TouchStartHandler,
		TouchEndHandler, TouchMoveHandler {

	boolean mouseDown = false;
	boolean touchStarted = false;

	public VDragStarter(Widget widget) {
		widget.addDomHandler(this, MouseDownEvent.getType());
		widget.addDomHandler(this, MouseUpEvent.getType());
		widget.addDomHandler(this, MouseOutEvent.getType());
		widget.addDomHandler(this, MouseMoveEvent.getType());
		widget.addDomHandler(this, TouchStartEvent.getType());
		widget.addDomHandler(this, TouchMoveEvent.getType());
		widget.addDomHandler(this, TouchEndEvent.getType());

	}

	public void onTouchEnd(TouchEndEvent event) {
		touchStarted = false;
	}

	public void onTouchStart(TouchStartEvent event) {
		touchStarted = true;
	}

	public void onMouseUp(MouseUpEvent event) {
		mouseDown = false;
	}

	public void onMouseOut(MouseOutEvent event) {
		if (mouseDown) {
			startDrag(event);
			mouseDown = false;
			event.stopPropagation();
		}
	}

	public void onMouseDown(MouseDownEvent event) {
		mouseDown = true;
		// Prevent a HTML5 drag'n'drop from starting
		event.preventDefault();
	}

	public void onTouchMove(TouchMoveEvent event) {
		if (touchStarted) {
			startDrag(event);
			touchStarted = false;
			event.stopPropagation();
		}
	}

	public void onMouseOver(MouseOverEvent event) {
		mouseDown = false;
	}

	public void onMouseMove(MouseMoveEvent event) {
		if (mouseDown) {
			startDrag(event);
			event.stopPropagation();
			mouseDown = false;
		}
	}

	protected abstract void startDrag(HumanInputEvent<?> event);

}
