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

import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Collideable;
import cn.user0308.scutkicking.Lineable;
import cn.user0308.scutkicking.activity.MainActivity;
import cn.user0308.scutkicking.R;
import cn.user0308.scutkicking.Utils.InWhichArea;
import cn.user0308.scutkicking.Utils.RandomUtil;

/**
 * Created by user0308 on 4/25/17.
 */

public class Hero extends Lineable{

    private Bitmap mBitmap = null;
    private Paint mPaint = null;
    private Point mPoint = null;//重心
    //private int posX;
    //private int posY;
    private int screenX;
    private int screenY;//左上角的店
    private int mHeroWidth;
    private int mHeroHeight;
    private int mArea;
    private double mSpeed;
    private double mAngle;

    //    public int getPosX(){
//        return posX;
//    public int getPosY() {
//        return posY;
//    public void setPosX(int posX) {
//        this.posX = posX;
//    }
//
//    public void setPosY(int posY) {
//        this.posY = posY;
    @Override
    public void initLines() {
        Line lineTop = new Line(screenX,screenY, screenX+mHeroWidth,screenY);//图片上方线段
        Line lineLeft = new Line(screenX,screenY,screenX,screenY+mHeroHeight);//左边
        Line lineBottom = new Line(screenX,screenY+mHeroHeight,screenX+mHeroWidth,screenY+mHeroHeight);//下方
        Line lineRight = new Line(screenX+mHeroWidth,screenY,screenX+mHeroWidth,screenY+mHeroHeight);//右方
        lines.add(lineTop);
        lines.add(lineLeft);
        lines.add(lineRight);
        lines.add(lineBottom);

    }
    public Hero(){
        //posX = 0;
        //posY = 0;
        screenX = 0;
        screenY = 0;
        mSpeed = 0;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        initHero();
        for(int i=0;i<lines.size();i++){
            lines.remove(i);
        }
        initLines();
    }
    public Hero(Point point){
        this(point.x,point.y);
    }

    public Hero(int x,int y){
        //posX = x;
        //posY = y;
        screenX=x;
        screenY=y;
        mSpeed = 0;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        initHero();
        for(int i=0;i<lines.size();i++){
            lines.remove(i);
        }
        initLines();
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
        mHeroHeight = 50;
        mHeroWidth = 40;
        // 计算缩放比例
        float scaleWidth = ((float) mHeroWidth) / width;
        float scaleHeight = ((float) mHeroHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix,true);
        //init2Hero();
    }

    @Override
    public boolean collide(Collideable object) {
        for (int i=0;i<lines.size();i++){
            if(lines.get(i).collide(object)){
                if (object instanceof Ball){//如果碰到球人物死亡
                    die();
                }else if(object instanceof Line){//如果碰到线段则移动受限
                    double angle = mAngle - 180;//方法是让人物反向运动知道刚好脱离墙壁
                    while(lines.get(i).collide(object)){//解决人物嵌入墙壁的bug
                            //updatePoint(mSpeed/5,mAngle);
                        updatePoint(mSpeed/5,angle);
                    }
                    float b = (float) Math.cos(Math.toRadians(mAngle - ((Line)object).getAngle()));
                    if (b>=0){
                        mAngle = ((Line)object).getAngle();
                        mSpeed = mSpeed * b;
                    }else {
                        mAngle = ((Line)object).getAngle()-180;
                        mSpeed = -mSpeed * b;
                    }
                }//如果碰到的非球非线那么就不操作，应该避免这种情况，因此应该一直保证用object.collide(hero)
                return true;
            }
        }
        return false;
    }

    private void die(){}
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(mBitmap,screenX,screenY,mPaint);
    }

    public Hero getHero(){
        return this;
    }

    public void updatePoint(double speed,double angle){
        double offsetX = (speed*Math.cos(Math.toRadians(angle)));
        double offsetY = (speed*Math.sin(Math.toRadians(angle)));
        screenX= (int) (screenX+offsetX);
        screenY= (int) (screenY+offsetY);
        lines = new ArrayList<>();
        initLines();
    }
    public void updatePoint(){
        int offsetX = (int)(mSpeed*Math.cos(Math.toRadians(mAngle)));
        int offsetY = (int)(mSpeed*Math.sin(Math.toRadians(mAngle)));
        if(screenX+offsetX<0)
            screenX=0;
        else if(screenX+offsetX>MainActivity.sWindowWidthPix-mHeroWidth){
            screenX=MainActivity.sWindowWidthPix-mHeroWidth;
        }else
            screenX=screenX+offsetX;
        if(screenY+offsetY<0)
            screenY=0;
        else if(screenY+offsetY>MainActivity.sWindowHeightPix-mHeroHeight){
            screenY=MainActivity.sWindowHeightPix-mHeroHeight;
        }else
            screenY=screenY+offsetY;
        lines = new ArrayList<>();
        initLines();
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

    public double getmAngle() {
        return mAngle;
    }

    public void setmPoint(Point point){
        mPoint = point;
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
