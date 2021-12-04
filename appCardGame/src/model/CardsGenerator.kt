package com.app.model

import com.app.model.dao.MyCard


 class CardsGenerator {

    private var myColors : ArrayList<String> = arrayListOf<String>()
    private var myValues : ArrayList    <String> = arrayListOf<String>()
    private var cards :List<MyCard> = arrayListOf<MyCard>()
    init {
        myColors = arrayListOf<String>("carreau","trefle","coeur","pique")
        myValues = arrayListOf<String>("As","2","3","4","5","6","7","8","9","10","Valle","Dame","Roi")
    }


    fun createPackage(nb:Int) : List<MyCard>{
        var list = listOf<String>("2","3","4","5","6")
        for (color in myColors)
            for(value in myValues)
                cards+=(MyCard(value,color))
        if (nb == 32){
            cards = cards.filterNot { v -> list.contains(v.name) }.toList()
        }
        return cards
    }



}