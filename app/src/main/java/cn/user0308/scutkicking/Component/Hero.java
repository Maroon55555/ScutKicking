package cn.user0308.scutkicking.Component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import cn.user0308.scutkicking.R;

/**
 * Created by user0308 on 4/25/17.
 */

public class Hero {

    private Bitmap mBitmap = null;
    private Paint mPaint = null;
    private Point mPoint = null;
    private double mSpeed;
    private double mAngle;

    public Hero(Context context){
        mPoint = new Point(100,200);
        mPaint = new Paint();
        //mPaint.setColor(Color.RED);
        mSpeed = 0.0;
        mAngle= Double.NaN;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero);
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        // 设置想要的大小
        int newWidth = 150;
        int newHeight = 140;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix,true);
    }

    public void onDraw(Canvas canvas){
        canvas.drawBitmap(mBitmap,mPoint.x,mPoint.y,mPaint);
    }
    public Hero getHero(){
        return this;
    }

    public void setmSpeed(double speed){
        mSpeed = speed;
    }
    public void setAngle(double angle){
        Log.d("Hero","setting angle " + angle);
        // maybe NAN
        this.mAngle = angle;
    }

    public void setmPoint(Point point){
        mPoint = point;
    }

    public void updatePoint(){
        if(Double.isNaN(mAngle)){
            return;
        }
        mPoint.x = mPoint.x + (int)(mSpeed*Math.cos(Math.toRadians(mAngle)));
        mPoint.y = mPoint.y + (int)(mSpeed*Math.sin(Math.toRadians(mAngle)));
    }

}
