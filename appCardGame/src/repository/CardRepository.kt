package com.app.repository

import com.app.model.CardTab
import com.app.model.CardTable
import com.app.model.dao.CardDao
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement


class CardRepository : CardDao {

    override suspend fun insert(defausse: String, mescartes: String): CardTab? {
        var statement:InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = CardTable.insert { cardTab ->
                cardTab[CardTable.defausse]=defausse
                cardTab[CardTable.mescartes]=mescartes
            }
        }
        return rowToCard(statement?.resultedValues?.get(0))
    }

    override suspend fun getAllCardTab(): List<CardTab>? =
        DatabaseFactory.dbQuery {
            CardTable.selectAll().mapNotNull {
                rowToCard(it)
            }
        }


    override suspend fun getCardTabById(cardTabId: Int): CardTab? =
        DatabaseFactory.dbQuery {
            CardTable.select { CardTable.cardTabId.eq(cardTabId) }
                .map {
                    rowToCard(it)
                }.singleOrNull()
        }

    override suspend fun deleteCardTabById(cardTabId: Int): Int =
        DatabaseFactory.dbQuery {
            CardTable.deleteWhere { CardTable.cardTabId.eq(cardTabId) }
        }


    override suspend fun update(cardTabId: Int, defausse: String, mescartes: String): Int =
        DatabaseFactory.dbQuery {
            CardTable.update({CardTable.cardTabId.eq(cardTabId)}){cardTab->
                cardTab[CardTable.defausse]=defausse
                cardTab[CardTable.mescartes]=mescartes
            }
        }


    private fun rowToCard(row:ResultRow?) : CardTab? {
        if(row == null)
            return null
        return CardTab(
            cardTabId = row[CardTable.cardTabId],
            defausse = row[CardTable.defausse],
            mescartes = row[CardTable.mescartes]
        )
    }

}