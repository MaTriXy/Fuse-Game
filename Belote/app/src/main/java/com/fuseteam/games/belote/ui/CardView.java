package com.fuseteam.games.belote.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.fuseteam.games.belote.R;
import com.fuseteam.games.belote.model.CardData;

/**
 * Created by arielyust on 02/10/2017.
 */

public class CardView extends AppCompatImageView {

    private CardData data;
    private boolean showen;
    private int cardRes;
    private boolean topDown;

    /*....................................Constructor.Methods.....................................*/

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context,
                    AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*.......................................Public.Methods.......................................*/

    public void show() {
        showen = true;
        setImageDrawable(getResources().getDrawable(cardRes));
    }

    public void hide() {
        showen = false;
        setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    public void setCardData(CardData data) {
        this.data = data;

        if (data == null) {
            cardRes = 0;
            hide();
        } else {
            cardRes = getImageRes(data);
        }
    }

    /*.......................................Private.Methods......................................*/

    private int getImageRes(CardData data) {
        String name = data.component1().getPrefix() + "_" + data.component2().getSofix();

        Log.e("CardView", name);

        Context context = getContext();
        Resources res = context.getResources();
        return res.getIdentifier(name, "drawable", context.getPackageName());
    }
}
