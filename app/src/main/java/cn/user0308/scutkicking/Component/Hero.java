package cn.user0308.scutkicking.Component;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import cn.user0308.scutkicking.MainView;
import cn.user0308.scutkicking.activity.GameOverActivity;
import cn.user0308.scutkicking.activity.MainActivity;
import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Collideable;
import cn.user0308.scutkicking.Component.Ball.Ball;
import cn.user0308.scutkicking.Component.Ball.BubbleBall;
import cn.user0308.scutkicking.Component.Ball.ThornBall;
import cn.user0308.scutkicking.Lineable;
import cn.user0308.scutkicking.Utils.ImageConvertUtil;
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
    //人上次发球的时间
    private long lastSendBallTime;
    private long cd = 1000;

    private double mSpeed;
    private double mAngle;
    private boolean isPause;//如果为真那么人物暂停，这是被泡泡球击中的效果
    private boolean isDie;

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
        screenX= (int) (x*MainActivity.widthScale);
        screenY= (int) (y*MainActivity.heightScale);
        mSpeed = 0;
        initHero();
        initLines();
    }
    public void initHero(){
        //mPoint = RandomUtil.createRandomPoint();
        //mPaint.setColor(Color.RED);
        mSpeed = 0.0;
        mAngle= Double.NaN;
        mHeroHeight = (int) (100*MainActivity.heightScale);
        mHeroWidth = (int) (50*MainActivity.widthScale);
        setImage(R.drawable.renqianmian);
//        int width = mBitmap.getWidth();
//        int height = mBitmap.getHeight();
//        // 设置想要的大小
    }

    @Override
    public boolean collide(Collideable object) {
        for (int i=0;i<lines.size();i++){
            if(lines.get(i).collide(object)){
                if (object instanceof BubbleBall){
                    isPause = true;
                    setImage(R.drawable.renwupaoqi);
                }else if (object instanceof ThornBall){//如果碰到球人物死亡
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
        //isDie = true;
        setImage(R.drawable.renwusile);
        GameOverActivity.startGameOverActivity(MainActivity.sContext);
        int tempX = screenX;//让人物死亡后不再被碰撞，方法是改变lines的位置
        int tempY = screenY;
        screenX = -50;
        screenY = -50;
        initLines();
        screenX = tempX;
        screenY = tempY;
    }
    @Override
    public void onDraw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmap,screenX,screenY,paint);
    }

    private void back(){
        screenX = lastX;
        screenY = lastY;
        lines = lastLines;
    }

    public void updatePoint(){
        if(mSpeed == 0){
            return;
        }
        setImageByAngle(mAngle);
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
    private void setImageByAngle(double angle){//根据人物的朝向设置图片
        int b = ((int)angle)%360;
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

    public void setImage(int resourceId){
        mBitmap = BitmapFactory.decodeResource(MainActivity.sContext.getResources(), resourceId);
        mBitmap = ImageConvertUtil.Zoom(mBitmap, mHeroWidth,mHeroHeight);
    }

    public void sendBall(){
        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastSendBallTime;//两次发球间隔，即cd时间
        if (time < cd || isPause || isDie  ){
            return;
        }
        //发球
        setImageByAngle(mBallAngle);
        int centerPx=screenX+mHeroWidth/2;
        int centerPy=screenY+mHeroHeight/2;
        ThornBall thornBall = new ThornBall(centerPx,centerPy, (float) mBallAngle);
        thornBall.calculatePoint((float) (Math.max(mHeroHeight, mHeroWidth)/2+mSpeed+thornBall.getRadius()));
//        double r = Math.sqrt((double)(mHeroHeight/2*mHeroHeight/2+mHeroWidth/2*mHeroWidth/2));

//
//        Ball tmpBall = new Ball(centerPx +(float)((r+Ball.mRadius)*Math.cos(Math.toRadians(mBallAngle))),
//                centerPy+(float)((r+Ball.mRadius)*Math.sin(Math.toRadians(mBallAngle))),
//                                (float)mBallAngle);
//        ThornBall thornBall =  new ThornBall(centerPx +(float)((r+Ball.mRadius)*Math.cos(Math.toRadians(mBallAngle))),
//                centerPy+(float)((r+Ball.mRadius)*Math.sin(Math.toRadians(mBallAngle))),
//                (float)mBallAngle);
        MainView.addBall(thornBall);
        lastSendBallTime = currentTime;
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

    public boolean isDie() {
        return isDie;
    }

    public void setDie(boolean die) {
        isDie = die;
    }
}
