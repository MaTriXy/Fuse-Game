package com.fuseteam.games.belote.fragments

import android.animation.Animator
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuseteam.games.belote.R
import com.fuseteam.games.belote.logic.CardSet
import com.fuseteam.games.belote.model.CardData
import com.fuseteam.games.belote.ui.CardView
import android.animation.ValueAnimator
import android.animation.AnimatorSet
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.fuseteam.games.belote.model.Player
import com.google.android.gms.tasks.Tasks
import java.util.*
import android.view.View.DragShadowBuilder
import android.content.ClipData
import android.view.MotionEvent
import android.view.View.OnTouchListener


/**
 * A placeholder fragment containing a simple view.
 */
class GameFragment : Fragment() {

    private var showAllCards: Boolean = true

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    private val playerCardViewList: ArrayList<CardView> = arrayListOf()
    private val opponentLeftCardViewList: ArrayList<CardView> = arrayListOf()
    private val opponentRightCardViewList: ArrayList<CardView> = arrayListOf()
    private val opponentTopCardViewList: ArrayList<CardView> = arrayListOf()
    private lateinit var trumpCardView: CardView
    private lateinit var trumpCardData: CardData

    private val cardSet: CardSet = CardSet()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v: ViewGroup = inflater.inflate(
                R.layout.fragment_game, container, false
        ) as ViewGroup

        playerCardViewList.clear()
        playerCardViewList.add(findView(v, R.id.playerCard1))
        playerCardViewList.add(findView(v, R.id.playerCard2))
        playerCardViewList.add(findView(v, R.id.playerCard3))
        playerCardViewList.add(findView(v, R.id.playerCard4))
        playerCardViewList.add(findView(v, R.id.playerCard5))
        playerCardViewList.add(findView(v, R.id.playerCard6))
        playerCardViewList.add(findView(v, R.id.playerCard7))
        playerCardViewList.add(findView(v, R.id.playerCard8))
        hideCards(playerCardViewList)

        opponentLeftCardViewList.clear()
        opponentLeftCardViewList.add(findView(v, R.id.opponentLeftCard1))
        opponentLeftCardViewList.add(findView(v, R.id.opponentLeftCard2))
        opponentLeftCardViewList.add(findView(v, R.id.opponentLeftCard3))
        opponentLeftCardViewList.add(findView(v, R.id.opponentLeftCard4))
        opponentLeftCardViewList.add(findView(v, R.id.opponentLeftCard5))
        opponentLeftCardViewList.add(findView(v, R.id.opponentLeftCard6))
        opponentLeftCardViewList.add(findView(v, R.id.opponentLeftCard7))
        opponentLeftCardViewList.add(findView(v, R.id.opponentLeftCard8))
        hideCards(opponentLeftCardViewList)

        opponentRightCardViewList.clear()
        opponentRightCardViewList.add(findView(v, R.id.opponentRightCard1))
        opponentRightCardViewList.add(findView(v, R.id.opponentRightCard2))
        opponentRightCardViewList.add(findView(v, R.id.opponentRightCard3))
        opponentRightCardViewList.add(findView(v, R.id.opponentRightCard4))
        opponentRightCardViewList.add(findView(v, R.id.opponentRightCard5))
        opponentRightCardViewList.add(findView(v, R.id.opponentRightCard6))
        opponentRightCardViewList.add(findView(v, R.id.opponentRightCard7))
        opponentRightCardViewList.add(findView(v, R.id.opponentRightCard8))
        hideCards(opponentRightCardViewList)

        opponentTopCardViewList.clear()
        opponentTopCardViewList.add(findView(v, R.id.opponentTopCard1))
        opponentTopCardViewList.add(findView(v, R.id.opponentTopCard2))
        opponentTopCardViewList.add(findView(v, R.id.opponentTopCard3))
        opponentTopCardViewList.add(findView(v, R.id.opponentTopCard4))
        opponentTopCardViewList.add(findView(v, R.id.opponentTopCard5))
        opponentTopCardViewList.add(findView(v, R.id.opponentTopCard6))
        opponentTopCardViewList.add(findView(v, R.id.opponentTopCard7))
        opponentTopCardViewList.add(findView(v, R.id.opponentTopCard8))
        hideCards(opponentTopCardViewList)

        trumpCardView = findView(v, R.id.trampCard)
        trumpCardView.visibility = View.GONE

        cardSet.shuffle()

        /* Fragment was initialized and has a size, start game */
        val viewTreeObserver = v.getViewTreeObserver()
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                    screenWidth = v.width
                    screenHeight = v.height

                    firstDivide()
                    secondDivide()
                    pickTrump()
                    thirdDivide(Player.BOTTOM)
                    trumpCardView.visibility = View.GONE
                }
            })
        }

        return v
    }

    /**
     * This defines your touch listener
     */
    private inner class MyTouchListener : OnTouchListener {
        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(
                        view)
                view.startDrag(data, shadowBuilder, view, 0)
                view.visibility = View.INVISIBLE
                return true
            } else {
                return false
            }
        }
    }

    private fun firstDivide() {
        val cardDelt = 0;

        /* Player */
        insert3CardData(cardDelt, true, playerCardViewList)

        /* Opponent Left */
        insert3CardData(cardDelt, showAllCards, opponentLeftCardViewList)

        /* Opponent Top */
        insert3CardData(cardDelt, showAllCards, opponentTopCardViewList)

        /* Opponent Right */
        insert3CardData(cardDelt, showAllCards, opponentRightCardViewList)
    }

    private fun secondDivide() {
        val cardDelt = 3;

        /* Player */
        insert2CardData(cardDelt, true, playerCardViewList)

        /* Opponent Left */
        insert2CardData(cardDelt, showAllCards, opponentLeftCardViewList)

        /* Opponent Top */
        insert2CardData(cardDelt, showAllCards, opponentTopCardViewList)

        /* Opponent Right */
        insert2CardData(cardDelt, showAllCards, opponentRightCardViewList)
    }

    private fun pickTrump() {
        trumpCardData = cardSet.suggestTrump() ?: return
        trumpCardView.visibility = View.VISIBLE
        trumpCardView.setCardData(trumpCardData)
        trumpCardView.show()
    }

    private fun thirdDivide(trumpChooser: Player) {
        val cardDelt = 5

        /* Player */
        insertCardData(trumpChooser, Player.BOTTOM, cardDelt, true, playerCardViewList)

        /* Opponent Left */
        insertCardData(trumpChooser, Player.LEFT, cardDelt, showAllCards, opponentLeftCardViewList)

        /* Opponent Top */
        insertCardData(trumpChooser, Player.TOP, cardDelt, showAllCards, opponentTopCardViewList)

        /* Opponent Right */
        insertCardData(trumpChooser, Player.RIGHT, cardDelt, showAllCards, opponentRightCardViewList)
    }

    private fun insert2CardData(cardDelt: Int, show: Boolean, cardViews: ArrayList<CardView>) {
        var list: List<CardData> = cardSet.pop2()

        var cardView: CardView
        var cardData: CardData

        for (i in 0 until list.size) {
            cardData = list[i]

            cardView = cardViews.get(i + cardDelt)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, show)
        }
    }

    private fun insert3CardData(cardDelt: Int, show: Boolean, cardViews: ArrayList<CardView>) {
        var list: List<CardData> = cardSet.pop3()

        var cardView: CardView
        var cardData: CardData

        for (i in 0 until list.size) {
            cardData = list[i]

            cardView = cardViews.get(i + cardDelt)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, show)
        }
    }

    private fun insertCardData(trumpChooser: Player, player: Player, cardDelt: Int, show: Boolean, cardViews: ArrayList<CardView>) {
        var list: ArrayList<CardData>
        if (trumpChooser == player) {
            list = cardSet.pop2()
            list.add(trumpCardData)
        } else {
            list = cardSet.pop3()
        }

        var cardView: CardView
        var cardData: CardData

        for (i in 0 until list.size) {
            cardData = list[i]

            cardView = cardViews.get(i + cardDelt)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, show)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun animateCardDivide(cardView: CardView, cardData: CardData, show: Boolean) {
        val viewTreeObserver = cardView.getViewTreeObserver()
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this)

                    val xAnimator = ValueAnimator.ofFloat(screenWidth / 2f - cardView.x, 0f)
                    xAnimator.addUpdateListener { animation ->
                        val value = animation.animatedValue as Float
                        cardView.translationX = value
                    }

                    val yAnimator = ValueAnimator.ofFloat(screenHeight / 2f - cardView.y, 0f)
                    yAnimator.addUpdateListener { animation ->
                        val value = animation.animatedValue as Float
                        cardView.translationY = value
                    }

                    val animatorSet = AnimatorSet()
                    animatorSet.play(xAnimator).with(yAnimator)
                    animatorSet.duration = 1000
                    animatorSet.start()

                    animatorSet.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(p0: Animator?) {
                        }

                        override fun onAnimationEnd(p0: Animator?) {
                            cardView.setCardData(cardData)
                            if (show) cardView.show()
                        }

                        override fun onAnimationCancel(p0: Animator?) {
                        }

                        override fun onAnimationStart(p0: Animator?) {
                        }

                    })
                }
            })
        }
    }

    private fun hideCards(list: List<CardView>) {
        list.forEach { cardView: CardView -> cardView.visibility = View.GONE }
    }

    private fun findView(view: ViewGroup, @IdRes res: Int): CardView {
        return view.findViewById<CardView>(res)
    }
}