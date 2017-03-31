package org.vaadin.artur.playingcards.collection;

import java.util.ArrayList;
import java.util.Random;

public class ShufflableArrayList<E> extends ArrayList<E> {

    public void shuffle() {
        Random rng = new Random(System.currentTimeMillis());
        // n is the number of items left to shuffle
        int len = size();
        for (int n = len; n > 1; n--) {
            // Pick a random element to move to the end
            int k = rng.nextInt(n); // 0 <= k <= n - 1.
            // Simple swap of variables
            E temp = get(k);
            set(k, get(n - 1));
            set(n - 1, temp);
        }

    }
}
