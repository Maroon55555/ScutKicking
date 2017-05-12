package cn.user0308.scutkicking.Component;

/**
 * Created by Wu XianZhe on 2017/5/8.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;

import cn.user0308.scutkicking.activity.MainActivity;
import cn.user0308.scutkicking.Utils.RuddyMathUtils;


public class Shooter {

    //private GestureDetector mGestureDetector;//触屏动作处理器
    //发射器组成
    //绿色瞬发按钮 代号：mShooterGreen
    //蓝色蓄力方向决定杆 代号：mShooterBlue
    //灰色决定杆活动区域 代号：mShooterGray
    private Paint mPaint;

    private int pressdTime=0;
    private boolean isCounting=false;

    public static final int GREEN_RADIUS = (int)(0.05*MainActivity.sWindowWidthPix);//需要手动设置之处2：三部分（都是圆）的半径
    private static final int BLUE_RADIUS = (int)(0.05*MainActivity.sWindowWidthPix);
    private static final int GRAY_RADIUS = (int)(0.15*MainActivity.sWindowWidthPix);

    int longPressed = -1;

    //发射器中心点坐标
    public static final Point mShooterInitPoint=new Point(GRAY_RADIUS+BLUE_RADIUS*2,
                                                MainActivity.sWindowHeightPix-GRAY_RADIUS-BLUE_RADIUS);//初始green和gray共同圆心位置
    private Point mShooterCurrPoint;//当前blue圆心位置

    //要返回的唯一一个值，能够代表球发射方向的angle
    private double mAngle=0;


    public Shooter(Context context){

        int windowHeightPix = MainActivity.sWindowHeightPix;
        mShooterCurrPoint=new Point(mShooterInitPoint);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);

    }

    //绘制函数,绘制大小圆,设置大圆颜色为浅灰,透明度100,小圆红色,透明度200
    public void onDraw(Canvas canvas){
        if(longPressed==0){//瞬发
            mPaint.setColor(Color.GRAY);
            canvas.drawCircle(mShooterInitPoint.x,mShooterInitPoint.y,GRAY_RADIUS,mPaint);
            mPaint.setColor(Color.RED);
            canvas.drawCircle(mShooterInitPoint.x,mShooterInitPoint.y,GREEN_RADIUS,mPaint);
            mPaint.setColor(Color.GRAY);
        }else if(longPressed==1){//蓄力
            mPaint.setColor(Color.GRAY);
            canvas.drawCircle(mShooterInitPoint.x,mShooterInitPoint.y,GRAY_RADIUS,mPaint);
            mPaint.setColor(Color.BLUE);
            canvas.drawCircle(mShooterCurrPoint.x,mShooterCurrPoint.y,BLUE_RADIUS,mPaint);
        }else {
//            mPaint.setColor(Color.GRAY);
//            canvas.drawCircle(mShooterInitPoint.x,mShooterInitPoint.y,GRAY_RADIUS,mPaint);
            mPaint.setColor(Color.BLACK);//平时
            canvas.drawCircle(mShooterInitPoint.x,mShooterInitPoint.y,GREEN_RADIUS,mPaint);
        }
    }
/*
    public boolean onTouchEvent(MotionEvent event){
        Log.d("Shooter","in Shooter touch");
        //获取触摸点与同圆圆心之间的长度
        int length = RuddyMathUtils.getLength(mShooterInitPoint.x, mShooterInitPoint.y, event.getX(), event.getY());
        if (event.getAction() == MotionEvent.ACTION_DOWN||event.getAction()==MotionEvent.ACTION_MOVE) {
            Log.d("Shooter","in Shooter touch");
            //如果屏幕接触点不在Green范围内,则不处理返回false
            if (length > GREEN_RADIUS) {
                return false;
            }else {
                mShooterGreen=1;
                //启动线程计时
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        isCounting=true;
                        while (isCounting){
                            if(pressdTime>1){
                                mShooterGreen=0;
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                                pressdTime++;
                            }catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

            }
        }

        //mGestureDetector.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_UP) {
            isCounting=false;
            pressdTime=0;
            Log.d("Shooter","in Shooter up");
            mShooterGreen=-1;
            mShooterBlueCurrPoint.x=mShooterInitPoint.x;
            mShooterBlueCurrPoint.y=mShooterInitPoint.y;
            mAngle=getAngle();
        }

        if (mShooterGreen==1){
            //瞬发

        }

        if(mShooterGreen==0) {//在蓄力状态下，实现blue部分的活动
            if(length>GRAY_RADIUS){//如果距离比灰色范围大，就让蓝色小球沿着边缘活动
                mShooterBlueCurrPoint = RuddyMathUtils.getBorderPoint(mShooterInitPoint,
                        new Point((int) event.getX(), (int) event.getY()),
                        GRAY_RADIUS);}
            else if(length<GRAY_RADIUS){//如果距离比灰色范围小，就在手指触屏点活动
                mShooterBlueCurrPoint.x=(int)event.getX();
                mShooterBlueCurrPoint.y=(int)event.getY();

            }
        }

        return true;
    }
*/
    public void actionUp(){
        mAngle=getAngle();
        isCounting=false;
        longPressed=-1;
        pressdTime=0;
        Log.d("Shooter","in Shooter up");
        //mShooterGreen=-1;
        mShooterCurrPoint.x=mShooterInitPoint.x;
        mShooterCurrPoint.y=mShooterInitPoint.y;

    }

    public void actionDown(){
        //mShooterGreen=1;
        longPressed=0;
        //启动线程计时
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                isCounting=true;
//                while (isCounting){
//                    if(pressdTime>1){
//                        //mShooterGreen=0;
//                        longPressed=1;
//                        break;
//                    }
//                    try {
//                        Thread.sleep(1000);
//                        pressdTime++;
//                    }catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();

    }

    public void actionMove(int length,float x,float y){
        //mShooterGreen=1;
        //length = RuddyMathUtils.getLength(mShooterInitPoint.x, mShooterInitPoint.y, x, y);
        longPressed=1;

        if (length <= GRAY_RADIUS) {
            //如果手指在摇杆活动范围内，则摇杆处于手指触摸位置
            mShooterCurrPoint.x=(int)x;
            mShooterCurrPoint.y=(int)y;
        } else {
            //设置摇杆位置，使其处于手指触摸方向的 摇杆活动范围边缘
            mShooterCurrPoint = RuddyMathUtils.getBorderPoint(mShooterInitPoint,
                    new Point((int) x,(int) y), GRAY_RADIUS);
        }
        mAngle = RuddyMathUtils.getAngle(mShooterCurrPoint,new Point((int)x,(int)y));

        //if(even.X,y>r)
//        mLength = RuddyMathUtils.getLength((int)x,(int)y,mShooterInitPoint.x,mShooterInitPoint.y);
//        if(mLength>GREEN_RADIUS) mLength = GREEN_RADIUS;

        //启动线程计时
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                isCounting=true;
//                while (isCounting){
//                    if(pressdTime>1){
//                        mShooterGreen=0;
//                        break;
//                    }
//                    try {
//                        Thread.sleep(1000);
//                        pressdTime++;
//                    }catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
    }


    public Point getmShooterInitPoint(){
        return mShooterInitPoint;
    }

    public double getAngle(){
        if(longPressed==0){
            //返回hero面朝的方向
            return 1.0;
        }
        else if (longPressed==1){
            mAngle = RuddyMathUtils.getAngle(mShooterInitPoint, mShooterCurrPoint);
            //有可能出现 NAN
//        if(Double.isNaN(mAngle)){
//            //angle is NAN
//            mAngle = 0.0;
//        }
            return mAngle;
        }else {
            return 1.0;
        }
    }

//    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
//
//
//
//        @Override//短暂按下抬起
//        public boolean onSingleTapUp(MotionEvent e) {
//            Log.i(getClass().getName(), "onSingleTapUp-----" + getActionName(e.getAction()));
//            setGreen(1);//抬起了，所以将green设置为1
//            mAngle=getAngle();//抬起了，一次发射活动结束，更新下mAngle
//            return true;
//        }
//        @Override//长按不滑动
//        public void onLongPress(MotionEvent e) {
//            Log.i(getClass().getName(), "onLongPress-----" + getActionName(e.getAction()));
//            setGreen(0);//进入蓄力状态，所以将green设置为0
//        }
//
//        @Override//先短暂按下，然后滑动
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            Log.i(getClass().getName(),
//                    "onScroll-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
//                            + e2.getX() + "," + e2.getY() + ")");
//            if(mShooterGreen==1){
//                try{
//                    Thread.sleep(500);
//                }catch(InterruptedException e){
//                    e.printStackTrace();
//                }
//                setGreen(0);
//            }
//
//            return false;
//        }
//
//        @Override//短暂按下，然后滑动，最后抬起
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            Log.i(getClass().getName(),
//                    "onFling-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
//                            + e2.getX() + "," + e2.getY() + ")");
//            return false;
//        }
//
//        @Override//先短暂按下，短按不滑动
//        public void onShowPress(MotionEvent e) {
//            Log.i(getClass().getName(), "onShowPress-----" + getActionName(e.getAction()));
//
//        }
//
//        @Override//按下
//        public boolean onDown(MotionEvent e) {
//            Log.i(getClass().getName(), "onDown-----" + getActionName(e.getAction()));
//            return false;
//        }
//
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            Log.i(getClass().getName(), "onDoubleTap-----" + getActionName(e.getAction()));
//            return false;
//        }
//
//        @Override
//        public boolean onDoubleTapEvent(MotionEvent e) {
//            Log.i(getClass().getName(), "onDoubleTapEvent-----" + getActionName(e.getAction()));
//            return false;
//        }
//
//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent e) {
//            Log.i(getClass().getName(), "onSingleTapConfirmed-----" + getActionName(e.getAction()));
//            return false;
//        }
//    }
//
//    private String getActionName(int action) {
//        String name = "";
//        switch (action) {
//            case MotionEvent.ACTION_DOWN: {
//                name = "ACTION_DOWN";
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                name = "ACTION_MOVE";
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//                name = "ACTION_UP";
//                break;
//            }
//            default:
//                break;
//        }
//        return name;
//    }
//
//    public void setGreen(int Green){
//        mShooterGreen=Green;
//    }
//
//    //用于在手指离开时将bluecurr坐标刷新一下
//    public void refreshBlueCurrPoint(){
//        mShooterBlueCurrPoint.x=mCenterX;
//        mShooterBlueCurrPoint.y=mCenterY;
//    }
//
//    public double getAngle(){
//        if(mShooterGreen==1){
//            //返回hero面朝的方向
//            return 1.0;
//        }
//        else {
//            mAngle = RuddyMathUtils.getAngle(mShooterInitPoint, mShooterBlueCurrPoint);
//            //有可能出现 NAN
////        if(Double.isNaN(mAngle)){
////            //angle is NAN
////            mAngle = 0.0;
////        }
//            return mAngle;
//        }
//    }
//
//    public int getmCenterX(){
//        return mCenterX;
//    }
//
//    public int getmCenterY(){
//        return mCenterY;
//    }
//
//    public int getGrayRadius(){
//        return GRAY_RADIUS;
//    }
}