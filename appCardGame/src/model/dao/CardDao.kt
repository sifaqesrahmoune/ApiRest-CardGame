package com.app.model.dao

import com.app.model.CardTab
import com.app.model.CardTable

interface CardDao {

    suspend fun insert(
        defausse:String,
        mescartes:String
    ):CardTab?

    suspend fun getAllCardTab():List<CardTab>?

    suspend fun getCardTabById(cardTabId:Int):CardTab?

    suspend fun deleteCardTabById(cardTabId:Int):Int

    suspend fun update(
        cardTabId: Int,
        defausse:String,
        mescartes:String
    ):Int

}