package org.vaadin.artur.playingcards.client.ui;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;

public class CardImage extends AbsolutePanel {

    private Image image;
    private Image selectOverlayImage;
    private Suite suite;
    private int rank;

    public Suite getSuite() {
        return suite;
    }

    public int getRank() {
        return rank;
    }

    public CardImage() {
        image = new Image();
        add(image);
    }

    public void setCard(Suite suite, int rank, boolean selected) {
        this.rank = rank;
        this.suite = suite;
        image.setResource(CardImageLookup.getCard(suite, rank));
        if (selected) {
            showOverlayImage();
        } else {
            hideOverlayImage();
        }
    }

    private void hideOverlayImage() {
        if (selectOverlayImage == null)
            return;

        selectOverlayImage.setVisible(false);
    }

    private void showOverlayImage() {
        if (selectOverlayImage == null) {
            selectOverlayImage = new Image(CardImageLookup.getSelectedOverlay());
            add(selectOverlayImage, 0, 0);
        }
        selectOverlayImage.setVisible(true);
    }

}
