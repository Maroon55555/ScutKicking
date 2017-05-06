package cn.user0308.scutkicking.Component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import cn.user0308.scutkicking.MainActivity;
import cn.user0308.scutkicking.R;
import cn.user0308.scutkicking.Utils.InWhichArea;
import cn.user0308.scutkicking.Utils.RandomUtil;

/**
 * Created by user0308 on 4/25/17.
 */

public class Hero {

    private Bitmap mBitmap = null;
    private Paint mPaint = null;
    private Point mPoint = null;//重心
    private int posX;
    private int posY;
    private int screenX;
    private int screenY;
    private int mArea;
    private double mSpeed;
    private double mAngle;

    public Hero(){
        posX = 0;
        posY = 0;
        screenX = 0;
        screenY = 0;
        mSpeed = 0;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        initHero();
    }
    public Hero(Point point){
        this(point.x,point.y);
    }
    public Hero(int x,int y){
        posX = x;
        posY = y;
        mSpeed = 0;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        initHero();
    }

    public void initHero(){
        //mPoint = RandomUtil.createRandomPoint();
        mPaint = new Paint();
        //mPaint.setColor(Color.RED);
        mSpeed = 0.0;
        mAngle= Double.NaN;
        mBitmap = BitmapFactory.decodeResource(MainActivity.sContext.getResources(), R.drawable.hero);
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        // 设置想要的大小
        int newWidth = 50;
        int newHeight = 40;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix,true);
        init2Hero();
    }

    public void init2Hero(){

        int mScreenWidth = MainActivity.sWindowWidthPix;
        int mScreenHeight = MainActivity.sWindowHeightPix;
        int mMapWidth = MainActivity.sMapWidth;
        int mMapHeight = MainActivity.sMapHeight;

        if(InWhichArea.isInA1(posX,posY,mScreenWidth,mScreenHeight)){
            //在A1
            Log.d("InWhichArea ","A1");

            mArea = 11;
            screenX=posX;
            screenY=posY;
        } else if(InWhichArea.isInA2(posX,posY,mScreenWidth,mScreenHeight,mMapWidth,mMapHeight)){
            //in A2
            Log.d("InWhichArea ","A2");

            //mHero.setScreenX(mScreenWidth-mMapWidth+hero_x);
            mArea = 12;
            screenX=mScreenWidth-mMapWidth+posX;
            screenY=posY;
        }else if(InWhichArea.isInA3(posX,posY,mScreenWidth,mScreenHeight,mMapWidth,mMapHeight)){
            Log.d("InWhichArea ","A3");

            mArea = 13;
            screenX=posX;
            screenY=mScreenHeight-mMapHeight+posY;
        }else if(InWhichArea.isInA4(posX,posY,mScreenWidth,mScreenHeight,mMapWidth,mMapHeight)){
            Log.d("InWhichArea ","A4");

            mArea = 14;
            screenX=mScreenWidth-mMapWidth+posX;
            screenY=mScreenHeight-mMapHeight+posY;
        }else if(InWhichArea.isInB1(posX,posY,mScreenWidth,mScreenHeight)){//B1 & B2 is the same
            Log.d("InWhichArea ","B1");

            mArea = 21;
            screenX=mScreenWidth/2;
            screenY=posY;
        }else if(InWhichArea.isInB2(posX,posY,mScreenWidth,mScreenHeight,mMapWidth,mMapHeight)){
            Log.d("InWhichArea ","B2");

            mArea = 22;
            screenX=mScreenWidth/2;
            screenY=posY;
        }else if(InWhichArea.isInB3(posX,posY,mScreenWidth,mScreenHeight,mMapWidth,mMapHeight)){//B3 & B4 is the same
            Log.d("InWhichArea ","B3");

            mArea = 23;
            screenX=mScreenWidth/2;
            screenY=mScreenHeight-mMapHeight+posY;
        }else if(InWhichArea.isInB4(posX,posY,mScreenWidth,mScreenHeight,mMapWidth,mMapHeight)){
            Log.d("InWhichArea ","B4");

            mArea = 24;
            screenX=mScreenWidth/2;
            screenY=mScreenHeight-mMapHeight+posY;
        }else if(InWhichArea.isInC1(posX,posY,mScreenWidth,mScreenHeight)){//C1 &C3is the same
            Log.d("InWhichArea ","C1");

            mArea = 31;
            screenX=posX;
            screenY=mScreenHeight/2;
        }else if(InWhichArea.isInC2(posX,posY,mScreenWidth,mScreenHeight,mMapWidth,mMapHeight)){//C2 & C4is the same
            Log.d("InWhichArea ","C2");

            mArea = 32;
            screenX=mScreenWidth-mMapWidth+posX;
            screenY=mScreenHeight/2;
        }else if(InWhichArea.isInC3(posX,posY,mScreenWidth,mScreenHeight,mMapWidth,mMapHeight)){
            Log.d("InWhichArea ","C3");

            mArea = 33;
            screenX=posX;
            screenY=mScreenHeight/2;
        }else if(InWhichArea.isInC4(posX,posY,mScreenWidth,mScreenHeight,mMapWidth,mMapHeight)){
            Log.d("InWhichArea ","C4");

            mArea = 34;
            screenX=mScreenWidth-mMapWidth+posX;
            screenY=mScreenHeight/2;
        }else {
            //在中间区域
            Log.d("InWhichArea ","D");

            mArea = 44;
            screenX=mScreenWidth/2;
            screenY=mScreenHeight/2;
        }
    }

    public void onDraw(Canvas canvas){
        canvas.drawBitmap(mBitmap,screenX,screenY,mPaint);
    }
    public Hero getHero(){
        return this;
    }

    public void setmSpeed(double speed){
        mSpeed = speed;
    }

    public double getmSpeed() {
        return mSpeed;
    }

    public void setAngle(double angle){
        Log.d("Hero","setting angle " + angle);
        // maybe NAN
        this.mAngle = angle;
    }

    public void setmPoint(Point point){
        mPoint = point;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public void setmSpeed(int mSpeed) {
        this.mSpeed = mSpeed;
    }

    public void setmAngle(int mAngle) {
        this.mAngle = mAngle;
    }

    public int getmArea() {
        return mArea;
    }

    public void printXY(){
        Log.d("Hero","screenXY is " + screenX + " " + screenY);
        //Log.d("Hero","pos XY is " + posX + " " + posY);
    }

}
