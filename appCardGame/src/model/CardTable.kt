package com.app.model

import com.app.model.dao.MyCard
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CardTable :Table() {

    val cardTabId: Column<Int> = integer("cardTabId").autoIncrement()
    val defausse: Column<String> = varchar("defausse",1000)
    val mescartes : Column<String> = varchar("mescartes",1000)

    override val primaryKey: PrimaryKey = PrimaryKey(cardTabId)

}