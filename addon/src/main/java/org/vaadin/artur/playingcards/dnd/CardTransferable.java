package org.vaadin.artur.playingcards.dnd;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.artur.playingcards.CardInfo;
import org.vaadin.artur.playingcards.shared.Suite;

import com.vaadin.event.Transferable;
import com.vaadin.ui.Component;

public class CardTransferable implements Transferable {

    private Component sourceComponent;
    private CardInfo cardInfo;
    private Map<String, Object> data = new HashMap<>();

    public CardTransferable(Component sourceComponent, CardInfo cardInfo) {
        this.sourceComponent = sourceComponent;
        this.cardInfo = cardInfo;
    }

    @Override
    public Component getSourceComponent() {
        return sourceComponent;
    }

    public Suite getSuite() {
        return cardInfo.getSuite();
    }

    public int getRank() {
        return cardInfo.getRank();
    }

    @Override
    public Object getData(String dataFlavor) {
        return data.get(dataFlavor);
    }

    @Override
    public Collection<String> getDataFlavors() {
        return data.keySet();
    }

    @Override
    public void setData(String dataFlavor, Object value) {
        data.put(dataFlavor, value);
    }

}
