package com.ranholmes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextId extends EditText {  
  
    public EditTextId(Context context) {  
        super(context);  
    }  
      
    public EditTextId(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    public EditTextId(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas) {  
        Paint paint = new Paint();  
        paint.setTextSize(45);  
        
        
        if(this.isFocused() == true)

            paint.setColor(Color.parseColor("#ffffff"));

      else

           paint.setColor(Color.parseColor("#ffffff"));
        
        canvas.drawRoundRect(new RectF(2+this.getScrollX(), 2+this.getScrollY(), this.getWidth()-3+this.getScrollX(), this.getHeight()+ this.getScrollY()-1), 3,3, paint);
        
        super.onDraw(canvas);  
    }  
}
