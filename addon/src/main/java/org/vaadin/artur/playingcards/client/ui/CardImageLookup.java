package org.vaadin.artur.playingcards.client.ui;

import com.google.gwt.resources.client.ImageResource;

public class CardImageLookup {
    private static VCardResources res = VCard150Resources.INSTANCE;

    public static ImageResource getCard(Suite suite, int rank) {
        if (suite == null) {
            return res.back();
        }
        if (suite == Suite.SPADES) {
            switch (rank) {
            case 1:
                return res.spades_1();
            case 2:
                return res.spades_2();
            case 3:
                return res.spades_3();
            case 4:
                return res.spades_4();
            case 5:
                return res.spades_5();
            case 6:
                return res.spades_6();
            case 7:
                return res.spades_7();
            case 8:
                return res.spades_8();
            case 9:
                return res.spades_9();
            case 10:
                return res.spades_10();
            case 11:
                return res.spades_11();
            case 12:
                return res.spades_12();
            case 13:
                return res.spades_13();
            }
        } else if (suite == Suite.HEARTS) {
            switch (rank) {
            case 1:
                return res.hearts_1();
            case 2:
                return res.hearts_2();
            case 3:
                return res.hearts_3();
            case 4:
                return res.hearts_4();
            case 5:
                return res.hearts_5();
            case 6:
                return res.hearts_6();
            case 7:
                return res.hearts_7();
            case 8:
                return res.hearts_8();
            case 9:
                return res.hearts_9();
            case 10:
                return res.hearts_10();
            case 11:
                return res.hearts_11();
            case 12:
                return res.hearts_12();
            case 13:
                return res.hearts_13();
            }
        } else if (suite == Suite.CLUBS) {
            switch (rank) {
            case 1:
                return res.clubs_1();
            case 2:
                return res.clubs_2();
            case 3:
                return res.clubs_3();
            case 4:
                return res.clubs_4();
            case 5:
                return res.clubs_5();
            case 6:
                return res.clubs_6();
            case 7:
                return res.clubs_7();
            case 8:
                return res.clubs_8();
            case 9:
                return res.clubs_9();
            case 10:
                return res.clubs_10();
            case 11:
                return res.clubs_11();
            case 12:
                return res.clubs_12();
            case 13:
                return res.clubs_13();
            }
        } else if (suite == Suite.DIAMONDS) {
            switch (rank) {
            case 1:
                return res.diamonds_1();
            case 2:
                return res.diamonds_2();
            case 3:
                return res.diamonds_3();
            case 4:
                return res.diamonds_4();
            case 5:
                return res.diamonds_5();
            case 6:
                return res.diamonds_6();
            case 7:
                return res.diamonds_7();
            case 8:
                return res.diamonds_8();
            case 9:
                return res.diamonds_9();
            case 10:
                return res.diamonds_10();
            case 11:
                return res.diamonds_11();
            case 12:
                return res.diamonds_12();
            case 13:
                return res.diamonds_13();
            }
        }

        return null;

    }

    public static ImageResource getCardBack() {
        return res.back();
    }

    public static ImageResource getEmptyPile() {
        return res.emptyPile();
    }

    public static ImageResource getCardBack2() {
        return res.back2();
    }

    public static ImageResource getCardBack3() {
        return res.back3();
    }

    public static ImageResource getSelectedOverlay() {
        return res.selectedOverlay();
    }
}
