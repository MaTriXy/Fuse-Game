package com.fuseteam.games.belote.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuseteam.games.belote.R
import com.fuseteam.games.belote.logic.CardSet
import com.fuseteam.games.belote.model.CardData

/**
 * A placeholder fragment containing a simple view.
 */
class GameFragment : Fragment() {

    var cardSet: CardSet = CardSet()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v: View = inflater.inflate(R.layout.fragment_main, container, false)

        cardSet.shuffle()

        while (true) {
            val card: CardData? = cardSet.suggestTrump()
            Log.e("GameFragment", card?.toString() ?: break)
        }

        return v
    }
}