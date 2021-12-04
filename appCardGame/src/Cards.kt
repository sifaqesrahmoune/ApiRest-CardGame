package com.app

import com.app.model.CardTab

class Cards {
    var mescartes: List<String>
    var defausse: List<String>
    var cardTabId: Int

    constructor(cards: CardTab) {
        this.cardTabId = cards.cardTabId;
        if (cards.defausse.length==0 ) {
            this.defausse = emptyList()
        } else {
            this.defausse = cards.defausse.split('-');
        }
        if (cards.mescartes.length==0 ) {
            this.mescartes = emptyList()
        } else {
            this.mescartes = cards.mescartes.split('-');
        }
    }
    fun shuffle() {

        this.mescartes = this.mescartes.shuffled()
    }

    fun discardFirst(){

        val firstCard = this.mescartes.elementAt(0)

        this.defausse += firstCard
        this.mescartes -= firstCard
    }

    fun putDiscardCard(){
        val cardDiscarted = this.defausse.elementAt(0)
        this.defausse -= cardDiscarted
        this.mescartes += cardDiscarted
    }
}