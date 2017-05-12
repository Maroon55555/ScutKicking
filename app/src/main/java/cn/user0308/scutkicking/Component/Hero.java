package cn.user0308.scutkicking.Component;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

<<<<<<< HEAD
import cn.user0308.scutkicking.MainView;
import cn.user0308.scutkicking.activity.MainActivity;
=======
>>>>>>> 8ee86514cdc00123da9ee654cccda1ebc99f4155
import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Collideable;
import cn.user0308.scutkicking.Component.Ball.Ball;
import cn.user0308.scutkicking.Component.Ball.BubbleBall;
import cn.user0308.scutkicking.Component.Ball.ThornBall;
import cn.user0308.scutkicking.Lineable;
import cn.user0308.scutkicking.Utils.ImageConvertUtil;
import cn.user0308.scutkicking.activity.MainActivity;
import cn.user0308.scutkicking.R;

/**
 * Created by user0308 on 4/25/17.
 */

public class Hero extends Lineable{

    private Bitmap mBitmap = null;
    private Point mPoint = null;//重心
    //private int posX;
    //private int posY;
    private int screenX;
    private int screenY;//左上角的店
    private int lastX;
    private int lastY;//上一帧的位置
    private List<Line> lastLines = new ArrayList<>();
    private int mHeroWidth;
    private int mHeroHeight;
    private int mArea;
    private double mSpeed;
    private double mAngle;
    private boolean isPause;//如果为真那么人物暂停，这是被泡泡球击中的效果

    private double mBallAngle;
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
        lines = new ArrayList<>();
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
        super();
        //posX = 0;
        //posY = 0;
        screenX = 0;
        screenY = 0;
        mSpeed = 0;
        initHero();
        initLines();
    }
    public Hero(Point point){
        this(point.x,point.y);
    }

    public Hero(int x,int y){
        //posX = x;
        //posY = y;
        super();
        screenX=x;
        screenY=y;
        mSpeed = 0;
        initHero();
        initLines();
    }
    public void initHero(){
        //mPoint = RandomUtil.createRandomPoint();
        //mPaint.setColor(Color.RED);
        mSpeed = 0.0;
        mAngle= Double.NaN;
        mHeroHeight = 100;
        mHeroWidth = 50;
        setImage(R.drawable.renqianmian);
//        int width = mBitmap.getWidth();
//        int height = mBitmap.getHeight();
//        // 设置想要的大小
    }

    @Override
    public boolean collide(Collideable object) {
        for (int i=0;i<lines.size();i++){
            if(lines.get(i).collide(object)){
                if (object instanceof BubbleBall){//如果碰到球人物死亡
                    //isPause = true;

                }else if (object instanceof ThornBall){
                    die();
                }
                else if(object instanceof Line){//如果碰到线段则移动受限
                    back();
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

    private void die(){
    }
    @Override
    public void onDraw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmap,screenX,screenY,paint);
    }

    public Hero getHero(){
        return this;
    }

    private void back(){
        screenX = lastX;
        screenY = lastY;
        lines = lastLines;
    }
    public void updatePoint(double speed,double angle){
        lastX = screenX;
        lastY = screenY;
        lastLines = lines;
        int offsetX = (int)(speed*Math.cos(Math.toRadians(angle)));
        int offsetY = (int)(speed*Math.sin(Math.toRadians(angle)));
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
    public void updatePoint(){
        int b = ((int)mAngle)%360;
        b = convert(b);
       if(range(b,22,67)){
            setImage(R.drawable.renyouxia);
        }else if(range(b,67,112)){
            setImage(R.drawable.renqianmian);
        }else if(range(b,112,157)){
            setImage(R.drawable.renzuoxia);
        }else if(range(b,157,202)){
            setImage(R.drawable.renxiangzuo);
        }else if(range(b,202,247)){
            setImage(R.drawable.renzuoshang);
        }else if(range(b,247,292)){
            setImage(R.drawable.renhoumian);
        }else if(range(b,292,337)){
            setImage(R.drawable.renyoushang);
        }else{
            setImage(R.drawable.renxiangyou);
       }
        lastX = screenX;
        lastY = screenY;
        lastLines = lines;
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
    private boolean range(int num, int small, int large){
        return num >= small && num<large;
    }
    private int convert(int a){//将输入的值映射在（0，360）之中
        if(a<0){
            return a+360;
        }else {
            return a;
        }
    }

    private void setImage(int resourceId){
        mBitmap = BitmapFactory.decodeResource(MainActivity.sContext.getResources(), resourceId);
        mBitmap = ImageConvertUtil.Zoom(mBitmap, mHeroWidth,mHeroHeight);
    }

    public void sendBall(){
        //发球

        int centerPx=screenX+mHeroWidth/2;
        int centerPy=screenY+mHeroHeight/2;
        double r = Math.sqrt((double)(mHeroHeight/2*mHeroHeight/2+mHeroWidth/2*mHeroWidth/2));


        Ball tmpBall = new Ball(centerPx +(float)((r+Ball.mRadius)*Math.cos(Math.toRadians(mBallAngle))),
                centerPy+(float)((r+Ball.mRadius)*Math.sin(Math.toRadians(mBallAngle))),
                                (float)mBallAngle,true);
        MainView.addBall(tmpBall);
    }

    public void setmBallAngle(double angle){
        this.mBallAngle = angle;
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

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
}
