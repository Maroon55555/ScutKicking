package cn.user0308.scutkicking.Component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by user0308 on 4/23/17.
 */

public class Ball {

    //球半径
    private static final float RADIUS = 100f;
    //球速度
    private int mSpeed;

    private Paint mPaint = null;
    //球心当前位置
    private Point mBallPoint = null;

    public Ball(){

        mBallPoint = new Point(0,0);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mBallPoint.x,mBallPoint.y,RADIUS,mPaint);
    }

    public void setmBallPoint(Point currPoint){
        mBallPoint = currPoint;
    }
    
    public void setmBallPoint(int x,int y){
        mBallPoint.x = x;
        mBallPoint.y = y;
    }


}
