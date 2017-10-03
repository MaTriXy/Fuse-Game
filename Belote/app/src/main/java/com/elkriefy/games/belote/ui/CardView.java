package com.elkriefy.games.belote.ui;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.elkriefy.games.belote.R;
import com.elkriefy.games.belote.model.CardData;

/**
 *
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

        Context context = getContext();
        Resources res = context.getResources();
        return res.getIdentifier(name, "drawable", context.getPackageName());
    }
}
