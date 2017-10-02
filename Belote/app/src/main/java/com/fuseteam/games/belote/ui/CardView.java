package com.fuseteam.games.belote.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by arielyust on 02/10/2017.
 */

public class CardView extends View {

    private boolean showen;

    /*....................................Constructor.Methods.....................................*/

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*.......................................Public.Methods.......................................*/

    public void show() {
        showen = true;
    }

    public void hide() {
        showen = false;
    }

    /*.......................................Private.Methods......................................*/

}
