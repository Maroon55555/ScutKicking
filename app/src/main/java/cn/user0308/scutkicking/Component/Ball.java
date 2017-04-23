package cn.user0308.scutkicking.Component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by user0308 on 4/23/17.
 */

public class Ball implements Runnable{

    //球半径
    private static final float RADIUS = 30f;
    //球速度
    private int mSpeed = 50;
    //球角度
    private double angleInDegree = -45;

    private Paint mPaint = null;
    //球心当前位置
    private Point mBallCurrPoint = null;

    private boolean mIsRunning = false;
    public Ball(){

        mBallCurrPoint = new Point(10,0);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mIsRunning = true;
        new Thread(this).start();
        //mSpeed = 10;
        //angle = 0;
    }

    public Ball(Point ballPoint,int speed,int angleInDegree){
        mBallCurrPoint = new Point(ballPoint);
        mSpeed = speed;
        this.angleInDegree = angleInDegree;
    }

    public void onDraw(Canvas canvas){
        //canvas.drawColor(Color.BLACK);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mBallCurrPoint.x,mBallCurrPoint.y,RADIUS,mPaint);
    }

    public void setmBallCurrPoint(Point currPoint){
        mBallCurrPoint = currPoint;
    }

    public void setmBallCurrPoint(int x,int y){
        mBallCurrPoint.x = x;
        mBallCurrPoint.y = y;
    }

    @Override
    public void run() {
        while (mIsRunning){
            mBallCurrPoint.x += (int)(mSpeed*Math.cos(Math.toRadians(angleInDegree)));
            mBallCurrPoint.y -= (int)(mSpeed*Math.sin(Math.toRadians(angleInDegree)));
            if(mBallCurrPoint.x>=1920) {
                mBallCurrPoint.x = 1920;
                angleInDegree = 90 + angleInDegree;
                if(angleInDegree>180) angleInDegree -= 360;
            }
            if(mBallCurrPoint.y>=1080) {
                mBallCurrPoint.y = 1080;
                angleInDegree = 90 + angleInDegree;
                if(angleInDegree>180) angleInDegree -= 360;
            }
            if(mBallCurrPoint.x<=0) {
                mBallCurrPoint.x = 0;
                angleInDegree = 90 + angleInDegree;
                if(angleInDegree>180) angleInDegree -= 360;
            }
            if(mBallCurrPoint.y<=0) {
                mBallCurrPoint.y = 0;
                angleInDegree = 90 + angleInDegree;
                if(angleInDegree>180) angleInDegree -= 360;
            }
            if(y>=1080) y=1080,angleInDegree=angleInDegree-90,
            if(x>=1920)  x=1920,angleInDegree=180-angleInDegree,
            if(y<=0) y=0,angleInDegree=90-angleInDegree,
            if(x<=0) x=0,angleInDegree=180-angleInDegree,

            //if(mBallCurrPoint.x<0) mBallCurrPoint.x *= -1;
            //if(mBallCurrPoint.y<0) mBallCurrPoint.y *= -1;
            Log.d("Ball","x is " + mBallCurrPoint.x);
            Log.d("Ball","y is " + mBallCurrPoint.y);
            Log.d("Ball","Angle is " + angleInDegree);
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //angle1球角度,angle2墙角度
    public int f(int angle1,int angle2){
        if(angle1>angle2){
            return 180-angle1+2*angle2;
        }
        if(angle2>angle1){
            return 180-2*angle2+angle1;
        }
    }

}
