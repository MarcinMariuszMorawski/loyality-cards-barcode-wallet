package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Card;

public final class CardButton extends android.support.v7.widget.AppCompatButton {

    private Card card;

    public CardButton(Context context) {
        super(context);
    }

    public CardButton(Context context,Card card,int width, int height) {
        super(context);
        this.card = card;
        setWidth(width);
        setHeight(height);
        setText(card.getBrand().getName());
        setTextSize(24);
        getBackground().setColorFilter(Color.parseColor(card.getBrand().getColor()), PorterDuff.Mode.MULTIPLY);
    }

    public Card getCard() {
        return card;
    }
}