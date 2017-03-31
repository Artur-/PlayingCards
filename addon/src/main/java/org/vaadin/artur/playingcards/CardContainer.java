package org.vaadin.artur.playingcards;

public interface CardContainer {

    /**
     * Removes the card from the card container. If the card cannot be removed
     * this method returns false.
     * 
     * @param card
     * @return true if the card was removed
     */
    boolean removeCard(Card card);

    /**
     * Checks if the card container is empty
     * 
     * @return true if the container is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Adds the card to the card container.
     * 
     * @param card
     */
    void addCard(Card card);

}
