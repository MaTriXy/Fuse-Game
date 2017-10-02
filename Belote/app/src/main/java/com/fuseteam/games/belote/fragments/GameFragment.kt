package com.fuseteam.games.belote.fragments

import android.animation.Animator
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuseteam.games.belote.R
import com.fuseteam.games.belote.logic.CardSet
import com.fuseteam.games.belote.model.CardData
import com.fuseteam.games.belote.ui.CardView
import android.animation.ValueAnimator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v4.view.ViewCompat.setTranslationY
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.ViewTreeObserver


/**
 * A placeholder fragment containing a simple view.
 */
class GameFragment : Fragment() {

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    private val playerCardViewList: ArrayList<CardView> = arrayListOf()
    private val opponentLeftCardViewList: ArrayList<CardView> = arrayListOf()
    private val opponentRightCardViewList: ArrayList<CardView> = arrayListOf()
    private val opponentTopCardViewList: ArrayList<CardView> = arrayListOf()

    private val cardSet: CardSet = CardSet()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v: ViewGroup = inflater.inflate(
                R.layout.fragment_main, container, false
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
                }
            })
        }

        return v
    }

    private fun firstDivide() {
        var cardView: CardView
        var cardData: CardData

        /* Player */
        var playerList: List<CardData> = cardSet.pop3()
        for (i in 0 until playerList.size) {
            cardData = playerList.get(i)

            cardView = playerCardViewList.get(i)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, true)
        }

        /* Opponent Left */
        var opponentLeft: List<CardData> = cardSet.pop3()
        for (i in 0 until opponentLeft.size) {
            cardData = opponentLeft.get(i)

            cardView = opponentLeftCardViewList.get(i)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, false)
        }

        /* Opponent Top */
        var opponentTop: List<CardData> = cardSet.pop3()
        for (i in 0 until opponentTop.size) {
            cardData = opponentTop.get(i)

            cardView = opponentTopCardViewList.get(i)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, false)
        }

        /* Opponent Right */
        var opponentRight: List<CardData> = cardSet.pop3()
        for (i in 0 until opponentRight.size) {
            cardData = opponentRight.get(i)

            cardView = opponentRightCardViewList.get(i)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, false)
        }
    }

    private fun secondDivide() {
        var cardView: CardView
        var cardData: CardData
        val cardDelt = 3;

        /* Player */
        var playerList: List<CardData> = cardSet.pop2()
        for (i in 0 until playerList.size) {
            cardData = playerList.get(i)

            cardView = playerCardViewList.get(i + cardDelt)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, true)
        }

        /* Opponent Left */
        var opponentLeft: List<CardData> = cardSet.pop2()
        for (i in 0 until opponentLeft.size) {
            cardData = opponentLeft.get(i)

            cardView = opponentLeftCardViewList.get(i + cardDelt)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, false)
        }

        /* Opponent Top */
        var opponentTop: List<CardData> = cardSet.pop2()
        for (i in 0 until opponentTop.size) {
            cardData = opponentTop.get(i)

            cardView = opponentTopCardViewList.get(i + cardDelt)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, false)
        }

        /* Opponent Right */
        var opponentRight: List<CardData> = cardSet.pop2()
        for (i in 0 until opponentRight.size) {
            cardData = opponentRight.get(i)

            cardView = opponentRightCardViewList.get(i + cardDelt)
            cardView.setCardData(null)
            cardView.visibility = View.VISIBLE

            animateCardDivide(cardView, cardData, false)
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