package com.fuseteam.games.belote.logic

import com.fuseteam.games.belote.model.CardData
import com.fuseteam.games.belote.model.Suit
import com.fuseteam.games.belote.model.Value

import java.util.ArrayList
import java.util.Collections

/**
 * Created by arielyust on 02/10/2017.
 */

class CardSet {

    private var list: MutableList<CardData>? = null
    private var index: Int = 0

    /*.......................................Public.Methods.......................................*/

    /**
     * Creates a new randomly shuffled set, must be called before any other call
     */
    @Synchronized
    fun shuffle() {
        if (list == null) {
            list = ArrayList()
            for (suit in Suit.values()) {
                list!!.add(CardData(suit, Value.SEVEN))
                list!!.add(CardData(suit, Value.EIGHT))
                list!!.add(CardData(suit, Value.NINE))
                list!!.add(CardData(suit, Value.TEN))
                list!!.add(CardData(suit, Value.JACK))
                list!!.add(CardData(suit, Value.QUEEN))
                list!!.add(CardData(suit, Value.KING))
                list!!.add(CardData(suit, Value.ACE))
            }
        }
        index = 0
        Collections.shuffle(list!!)
    }

    /**
     * Pops three cards
     *
     * @return
     */
    @Synchronized
    fun pop3(): List<CardData> {
        val list = pop2()
        addIfPossible(pop(), list)
        return list
    }

    /**
     * Pops two cards
     *
     * @return
     */
    @Synchronized
    fun pop2(): ArrayList<CardData> {
        val list = ArrayList<CardData>()
        addIfPossible(pop(), list)
        addIfPossible(pop(), list)
        return list
    }

    /**
     * Pops one card, by Belote rules must be called after [.pop2]
     *
     * @return
     */
    @Synchronized
    fun suggestTrump(): CardData? {
        return pop()
    }

    /*.......................................Private.Methods......................................*/

    /**
     * Pops the next card in the set
     *
     * @return
     */
    private fun pop(): CardData? {
        var result: CardData? = null

        if (list == null) {
            throw IllegalStateException("CardSet wasn't initialized, call shuffle()!")
        }
        if (index < list!!.size) {
            result = list!![index]
            index++
        }
        return result
    }

    private fun addIfPossible(cardData: CardData?, list: ArrayList<CardData>) {
        if (cardData != null)
            list.add(cardData)
    }
}
