org_vaadin_artur_playingcards_Deck = function() {
	var connector = this;
	var element = this.getElement();
	
	this.onStateChange = function() {
		var state = this.getState();
		//element.cardCount = state.numberOfCards;
	}

}
org_vaadin_artur_playingcards_Deck.tag = "game-card-deck";
