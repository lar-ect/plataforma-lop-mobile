package br.ufrn.ect.lop.lopalpha;

/**
 * Created by marcus on 07/03/18.
 */

import android.graphics.Rect;
import android.widget.EditText;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Author S Mahbub Uz Zaman on 5/9/15.
 * Lisence Under GPL2
 */
public class MyEditText extends android.support.v7.widget.AppCompatEditText {

    private Rect rect;
    private Paint paint;

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int baseline = getBaseline();
        for (int i = 0; i < getLineCount()+5; i++) {
            canvas.drawText("" + (i+1), rect.left, baseline, paint);
            baseline += getLineHeight();
        }
        super.onDraw(canvas);
    }
}
