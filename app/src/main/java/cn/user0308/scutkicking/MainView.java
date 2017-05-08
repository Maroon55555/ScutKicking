package cn.user0308.scutkicking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Component.Ball;
import cn.user0308.scutkicking.Component.Hero;
import cn.user0308.scutkicking.Component.Rect;
import cn.user0308.scutkicking.Component.Ruddy;
import cn.user0308.scutkicking.Component.Line;
import cn.user0308.scutkicking.Utils.RandomUtil;
import cn.user0308.scutkicking.activity.MainActivity;
import cn.user0308.scutkicking.building.Attackable;
import cn.user0308.scutkicking.building.Building;
import cn.user0308.scutkicking.building.Hole;

/**
 * Created by user0308 on 4/22/17.
 */

public class MainView extends SurfaceView implements Runnable, SurfaceHolder.Callback{

    //Component 相关
    //操控杆
    private Ruddy mRuddy = null;
    //小球
    private static List<Ball> mBallList ;
    //Building 相关
    private List<Building> mBuildingList;
    //Hero 主人公
    Hero mHero = null;

    //绘画起点
    private int x = 0;
    private int y = 0;
    private boolean IsCanvasMove = false;

    //SurfaceView 相关
    private SurfaceHolder mSurfaceHolder = null;
    private Thread mThread = null;
    private boolean mIsRunning = false;
    private Paint mPaint = null;
    private Canvas canvas = null;

    //地图中所有的线段
    private List<Line> mLineList;

    private Rect rect;

    public MainView(Context context){
        super(context);
        mRuddy = new Ruddy(context);
        mHero = new Hero(RandomUtil.createRandomPoint());
        mBallList = new ArrayList<>();
        mBuildingList = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        initBuilding();
        //initXY();

        List<Line> lines = new ArrayList<>();
        Line line1 = new Line(600,600,600,800);//left
        Line line2 = new Line(600,600,800,600);//top
        Line line3 = new Line(600,800,800,800);//bottom
        Line line4 = new Line(800,600,800,800);//right
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);
        rect = new Rect(lines);

        mLineList = new ArrayList<>();
        initWalls();
        Line line = new Line(100,100,500,500);
        mLineList.add(line);

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    private void initBuilding(){
        //Hold继承自Building,构造函数参数为:重心x,y坐标,在(起始角度,结束角度)范围内发射球
        //屏幕左上角为原点,水平向右为x轴正方向,竖直向下为y轴正方向,以x轴正方向顺时针旋转为正角度
        Building leftTop = new Hole(0, 0, 0 ,90);//左上角
        //Log.d("MainView", "WindowHeight" + MainActivity.sWindowHeightPix);
        Building leftButtom = new Hole(0, MainActivity.sWindowHeightPix, -90, 0);//左下角
        Building rightTop= new Hole(MainActivity.sWindowWidthPix, 0, 90, 180);//右上角
        Building rightButtom = new Hole(MainActivity.sWindowWidthPix,
                                        MainActivity.sWindowHeightPix, -180, -90);//右下角
        mBuildingList.add(leftTop);
        mBuildingList.add(leftButtom);
        mBuildingList.add(rightTop);
        mBuildingList.add(rightButtom);
    }
    private void initWalls(){//初始化四周的边界
        mLineList.add(new Line(0, 0, MainActivity.sWindowWidthPix, 0));
        mLineList.add(new Line(MainActivity.sWindowWidthPix, 0,
                MainActivity.sWindowWidthPix, MainActivity.sWindowHeightPix));
        mLineList.add(new Line(0, MainActivity.sWindowHeightPix,
                MainActivity.sWindowWidthPix, MainActivity.sWindowHeightPix));
        mLineList.add(new Line(0, MainActivity.sWindowHeightPix,
                0, 0));

    }
    /*
    private void initXY(){
        switch (mHero.getmArea()){
            case 11:
                x = 0;
                y = 0;
                break;
            case 12:
                x = MainActivity.sMapWidth - MainActivity.sWindowWidthPix;
                y=0;
                break;
            case 13:
                x = 0;
                y = MainActivity.sMapHeight - MainActivity.sWindowHeightPix;
                break;
            case 14:
                x = MainActivity.sMapWidth - MainActivity.sWindowWidthPix;
                y = MainActivity.sMapHeight - MainActivity.sWindowHeightPix;
                break;
            case 21:
            case 22:
                x = mHero.getPosX() - MainActivity.sWindowWidthPix/2;
                y = 0;
                break;
            case 23:
            case 24:
                x = mHero.getPosX() - MainActivity.sWindowWidthPix/2;
                y = MainActivity.sMapHeight - MainActivity.sWindowHeightPix;
                break;
            case 31:
            case 33:
                x = 0;
                y = mHero.getPosY() - MainActivity.sWindowHeightPix/2;
                break;
            case 32:
            case 34:
                x = MainActivity.sMapWidth - MainActivity.sWindowWidthPix;
                y = mHero.getPosY() - MainActivity.sWindowHeightPix/2;
                break;
            case 44:
                x = mHero.getPosX() - MainActivity.sWindowWidthPix/2;
                y = mHero.getPosY() - MainActivity.sWindowHeightPix/2;
                IsCanvasMove = true;
                break;
        }
    }
    */
    public static void addBall(Ball ball){
        if(ball!=null){
            mBallList.add(ball);
        }else {
            Log.d("MainView","adding ball but ball is null");
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //如果屏幕接触点不在摇杆挥动范围内,则不处理返回false
            //mHero.setmSpeed(20.0);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int speed = mRuddy.getmLength();
            mHero.setmSpeed(speed/5);
        }
        //如果手指离开屏幕，则摇杆返回初始位置
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mHero.setmSpeed(0.0);
        }

        //如果有Touch事件,把事件传递给操控杆处理
        //若操控杆返回true表示操控杆已经处理过此事件,通知上层不再处理
        boolean isDealBymRuddy = mRuddy.onTouchEvent(event);
        if(isDealBymRuddy){
            //Log.d("MainView","操控杆已处理");
            mHero.setAngle(mRuddy.getAngle());
            return true;
        }else {
            //操控杆没有处理,交由其他控件处理,若其他控件已经处理则把下面的返回值false改为true
            return false;
        }

    }

    @Override
    public void run() {
        while (mIsRunning) {
            long start = System.currentTimeMillis();
            logic();
            myDraw();
            long end = System.currentTimeMillis();
            //切换线程
            try {
                if(end - start < 20);
                Thread.sleep(20 - (end - start));
                Log.d("MainView", "time:" + (end -start));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public  void logic(){
        for (int i = 0; i <mBallList.size() ; i++) {
            mBallList.get(i).calculatePoint();
            if(mBallList.get(i).collide(rect))
                continue;
            for(int j = i + 1; j < mBallList.size(); j ++){
                if(mBallList.get(i). collide(mBallList.get(j)))
                    break;
            }
            for(Line line:mLineList){
                if(mBallList.get(i).collide(line))
                    break;
            }
        }
        //每隔50*50ms=2500ms建筑发球伤人
        if(mBallList.size()<20) {
            for (Building building : mBuildingList) {
                if (building instanceof Attackable) {
                    ((Attackable) building).attack();
                }
            }
        }
        //move();
        mHero.updatePoint();
    }
    //绘画对象的先后顺序不同,后画的会覆盖前画的
    public void myDraw(){
        try {
            canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);//清屏
            //canvas.drawBitmap(MainActivity.bg, 0, 0, mPaint);//(0,0)代表canvas的起始点而不是bg的起始点
            canvas.drawLine(100,100,500,500, mPaint);
            //画操纵杆
            mRuddy.onDraw(canvas);
            //画出所有建筑
            for (Building building :
                    mBuildingList) {
                building.onDraw(canvas, mPaint);
            }
            rect.onDraw(canvas, mPaint);
            //画出所有球
            for(Ball ball:
                    mBallList){
                ball.onDraw(canvas, mPaint);
            }
            mHero.onDraw(canvas);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(canvas != null){
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }


//    public void move(){
//        int screenCenterX = MainActivity.sWindowWidthPix/2;
//        int screenCenterY = MainActivity.sWindowHeightPix/2;
//
//        int tmpangle = (int)mRuddy.getAngle();
//        int mSpeed = (int)mHero.getmSpeed();
//        //在此速度方向和大小的情况下,每一帧的偏移量
//        int offsetX = (int)(mSpeed*Math.cos(Math.toRadians(tmpangle)));
//        int offsetY = (int)(mSpeed*Math.sin(Math.toRadians(tmpangle)));
//
//
//        Point pointA = new Point(screenCenterX,screenCenterY);
//        //Point pointB = new Point(MainActivity.sMapWidth-screenCenterX,screenCenterY);
//        //Point pointC = new Point(screenCenterX,MainActivity.sMapHeight-screenCenterY);
//        Point pointD = new Point(MainActivity.sMapWidth-screenCenterX,MainActivity.sMapHeight-screenCenterY);
//
//        //如果屏幕在画布中间区域,则画布动,小球在屏幕上不动
//        if(new android.graphics.Rect(pointA.x,pointA.y,pointD.x,pointD.y).contains(mHero.getPosX(),mHero.getPosY())){
//            //预测下一帧屏幕位置,防止屏幕超出画布外
//            //x轴
//            if(x+offsetX<0) x=0;//屏幕左边超出画布,设置为0
//            else if(x+offsetX>MainActivity.sMapWidth-MainActivity.sWindowWidthPix)
//                x=MainActivity.sMapWidth-MainActivity.sWindowWidthPix;//屏幕右边超出画布,设置画布宽-屏幕宽
//            else x = x + offsetX;//没有超出,画布偏移
//            //y轴
//            if(y+offsetY<0) y=0;//屏幕上边超出画布,设置为0
//            else if(y+offsetY>MainActivity.sMapHeight-MainActivity.sWindowHeightPix)
//                y=MainActivity.sMapHeight-MainActivity.sWindowHeightPix;//屏幕下边超出画布,设置画布高-屏幕高
//            else  y = y+ offsetY;//没有超出,画布偏移
//            //小球在画布上动
//            if(mHero.getPosX()+offsetX<0)
//                mHero.setPosX(0);
//            else if(mHero.getPosX()+offsetX>MainActivity.sMapWidth){
//                mHero.setPosX(MainActivity.sMapWidth);
//            }else
//                mHero.setPosX(mHero.getPosX()+offsetX*2);
//            if(mHero.getPosY()+offsetY<0)
//                mHero.setPosY(0);
//            else if(mHero.getPosY()+offsetY>MainActivity.sMapHeight){
//                mHero.setPosY(MainActivity.sMapHeight);
//            }else
//                mHero.setPosY(mHero.getPosY()+offsetY*2);
//
//            //屏幕在画布角落位置
//        }else if(new android.graphics.Rect(0,0,pointA.x,pointA.y).contains(mHero.getPosX(),mHero.getPosY())
//                ||new android.graphics.Rect(MainActivity.sMapWidth-screenCenterX,0,MainActivity.sMapWidth,screenCenterY).contains(mHero.getPosX(),mHero.getPosY())
//                ||new android.graphics.Rect(0,MainActivity.sMapHeight-screenCenterY,screenCenterX,MainActivity.sMapHeight).contains(mHero.getPosX(),mHero.getPosY())
//                ||new android.graphics.Rect(MainActivity.sMapWidth-screenCenterX,MainActivity.sMapHeight-screenCenterY,MainActivity.sMapWidth,MainActivity.sMapHeight).contains(mHero.getPosX(),mHero.getPosY())){
//            //人物在画布上动
//            if(mHero.getPosX()+offsetX<0)
//                mHero.setPosX(0);
//            else if(mHero.getPosX()+offsetX>MainActivity.sMapWidth){
//                mHero.setPosX(MainActivity.sMapWidth);
//            }else
//                mHero.setPosX(mHero.getPosX()+offsetX*2);
//            if(mHero.getPosY()+offsetY<0)
//                mHero.setPosY(0);
//            else if(mHero.getPosY()+offsetY>MainActivity.sMapHeight){
//                mHero.setPosY(MainActivity.sMapHeight);
//            }else
//                mHero.setPosY(mHero.getPosY()+offsetY*2);
//            //人物在屏幕上动
//            if(mHero.getScreenX()+offsetX<0)
//                mHero.setScreenX(0);
//            else if(mHero.getScreenX()+offsetX>MainActivity.sWindowWidthPix){
//                mHero.setPosX(MainActivity.sWindowWidthPix);
//            }else
//                mHero.setScreenX(mHero.getScreenX()+offsetX);
//            if(mHero.getScreenY()+offsetY<0)
//                mHero.setScreenY(0);
//            else if(mHero.getScreenY()+offsetY>MainActivity.sWindowHeightPix){
//                mHero.setScreenY(MainActivity.sWindowHeightPix);
//            }else
//                mHero.setScreenY(mHero.getScreenY()+offsetY);
//        //如果屏幕在画布边缘位置
//        }else {
//            //人物在画布上动
//            if(mHero.getPosX()+offsetX<0)
//                mHero.setPosX(0);
//            else if(mHero.getPosX()+offsetX>MainActivity.sMapWidth){
//                mHero.setPosX(MainActivity.sMapWidth);
//            }else
//                mHero.setPosX(mHero.getPosX()+offsetX*2);
//            if(mHero.getPosY()+offsetY<0)
//                mHero.setPosY(0);
//            else if(mHero.getPosY()+offsetY>MainActivity.sMapHeight){
//                mHero.setPosY(MainActivity.sMapHeight);
//            }else
//                mHero.setPosY(mHero.getPosY()+offsetY*2);
//            //屏幕在画布上动
//            if(x+offsetX<0) x=0;
//            else if(x+offsetX>MainActivity.sMapWidth-MainActivity.sWindowWidthPix)
//                x=MainActivity.sMapWidth-MainActivity.sWindowWidthPix;
//            else   x = x + offsetX;
//            if(y+offsetY<0) y=0;
//            else if(y+offsetY>MainActivity.sMapHeight-MainActivity.sWindowHeightPix)
//                y=MainActivity.sMapHeight-MainActivity.sWindowHeightPix;
//            else  y = y + offsetY;
//            //人物在屏幕上动
//            if(mHero.getScreenX()+offsetX<0)
//                mHero.setScreenX(0);
//            else if(mHero.getScreenX()+offsetX>MainActivity.sWindowWidthPix){
//                mHero.setPosX(MainActivity.sWindowWidthPix);
//            }else mHero.setScreenX(mHero.getScreenX()+offsetX);
//            if(mHero.getScreenY()+offsetY<0)
//                mHero.setScreenY(0);
//            else if(mHero.getScreenY()+offsetY>MainActivity.sWindowHeightPix){
//                mHero.setScreenY(MainActivity.sWindowHeightPix);
//            }else  mHero.setScreenY(mHero.getScreenY()+offsetY);
//
//
//
//        }
//
//    }
//    private Bitmap getPartBitmap(int x, int y){
//        //Log.d("MainView","get x ,y is " + x + " " + y);
//        return Bitmap.createBitmap(MainActivity.bg,x,y,MainActivity.sWindowWidthPix,MainActivity.sWindowHeightPix);
//    }
//*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsRunning = true;
        Log.d("MainView","" + (null==mThread));
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsRunning = false;
        //mThread.interrupt();
    }
}
