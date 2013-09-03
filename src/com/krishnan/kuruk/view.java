package com.krishnan.kuruk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class view extends View {

  private Paint paint;
  private float position = 0;

  public view(Context context) {
    super(context);
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setStrokeWidth(2);
    paint.setTextSize(25);
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(Color.WHITE);
  }

  @SuppressLint("DrawAllocation") protected void onDraw(Canvas canvas) {
    int xPoint = (int) ((getMeasuredWidth() / 2)*0.9);
    int yPoint = (int) ((getMeasuredHeight() / 2)*0.9);
    
    float radius = (float) (Math.max(xPoint, yPoint) * 0.08);
    Paint myPaint = new Paint();
    myPaint.setColor(Color.BLACK);
    myPaint.setStrokeWidth(4);
   canvas.drawCircle(xPoint,yPoint, radius, myPaint);

    myPaint.setColor(Color.RED);
    myPaint.setStrokeWidth(3);
    // 3.143 is a good approximation for the circle
    canvas.drawLine(xPoint,
        yPoint,
        (float) (xPoint + radius
            * Math.sin((double) (-position) / 180 * 3.143)),
        (float) (yPoint - radius
            * Math.cos((double) (-position) / 180 * 3.143)), myPaint);
    myPaint.setColor(Color.DKGRAY);
    canvas.drawText(String.valueOf(position), xPoint, yPoint, myPaint);
  }

  public void updateData(float position) {
    this.position = position;
    postInvalidate();
    invalidate();
  }

} 