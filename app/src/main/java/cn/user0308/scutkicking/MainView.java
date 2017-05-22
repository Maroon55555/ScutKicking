package cn.user0308.scutkicking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import cn.user0308.scutkicking.Component.Ball.Ball;
import cn.user0308.scutkicking.Component.Hero;
import cn.user0308.scutkicking.Component.Ruddy;
import cn.user0308.scutkicking.Component.Line;
import cn.user0308.scutkicking.Component.Shooter;
import cn.user0308.scutkicking.Utils.ImageConvertUtil;
import cn.user0308.scutkicking.Utils.RandomUtil;
import cn.user0308.scutkicking.Utils.RuddyMathUtils;
import cn.user0308.scutkicking.activity.MainActivity;
import cn.user0308.scutkicking.bluetooth.BluetoothMsg;
import cn.user0308.scutkicking.building.Attackable;
import cn.user0308.scutkicking.building.Building;
import cn.user0308.scutkicking.building.Hole;
import cn.user0308.scutkicking.building.Obstacle;

import static cn.user0308.scutkicking.activity.MainActivity.ViewCreated;
//处理屏幕适配
import static cn.user0308.scutkicking.activity.MainActivity.sWindowHeightPix;
import static cn.user0308.scutkicking.activity.MainActivity.sWindowWidthPix;
import static cn.user0308.scutkicking.activity.MainActivity.XMOD;
import static cn.user0308.scutkicking.activity.MainActivity.YMOD;
import static cn.user0308.scutkicking.activity.MainActivity.toUnit;
import static cn.user0308.scutkicking.activity.MainActivity.transferX;
import static cn.user0308.scutkicking.activity.MainActivity.transferY;
import static cn.user0308.scutkicking.bluetooth.TransportData.transfering;
/**
 * Created by user0308 on 4/22/17.
 */

public class MainView extends SurfaceView implements Runnable, SurfaceHolder.Callback{

    //Component 相关
    //操控杆
    private Ruddy mRuddy = null;
    //发射器
    private Shooter mShooter = null;
    //小球
    public static List<Ball> mBallList ;
    //Building 相关
    private List<Building> mBuildingList;
    //Hero 主人公
    public static Hero mHero = null;
    public static Hero oHero = null;
    //background
    Bitmap background;

    //倒计时
    int numberCount=30;
    Timer timer = null;
    TimerTask timerTask = null;

    //handler
    boolean shooterPressed=false;
    boolean ruddyPressed=false;
    int shooterEventID=-1,ruddyEventID=-1;
    int mode=-1;
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

    private List<Lineable> mLineableList;


    private int countForHero = 0;//计数器，用来记录进程跑了多少次用来设置人物暂停时间
    private int countForHole = 0;//用来设置洞发球间隔
    private int time = 25;//让进程睡眠的时间

    public MainView(Context context) {
        super(context);

        mRuddy = new Ruddy(context);
        mShooter = new Shooter(context);
        mHero = new Hero(RandomUtil.createRandomPoint());
        oHero = new Hero(RandomUtil.createRandomPoint());
        mBallList = new ArrayList<>();
        mBuildingList = new ArrayList<>();
        mLineableList = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        initBuilding();
        //initXY();

        //缩放背景图片开始
        Bitmap bg = BitmapFactory.decodeResource(context.getResources(),R.drawable.bg);
        background= ImageConvertUtil.Zoom(bg,(float)MainActivity.sWindowWidthPix,(float)MainActivity.sWindowHeightPix);

//        background = BitmapFactory.decodeResource(context.getResources(),R.drawable.bg);
        //缩放背景图片结束
//        Toast.makeText(context,"w,h is "
//                + background.getWidth() + " "
//                + background.getHeight(),Toast.LENGTH_SHORT ).show();
        Obstacle obstacle1 = new Obstacle(200,200, R.drawable.zhangaiwuheng,300,100);
        Obstacle obstacle2 = new Obstacle(600,200, R.drawable.zhangaiwushuzhe,100,300);
        mLineableList.add(obstacle1);
        mLineableList.add(obstacle2);

        mLineList = new ArrayList<>();
        initWalls();
        Line line = new Line(100, 100, 500, 500);
        mLineList.add(line);

        timer = new Timer(true);
        timerTask = new TimerTask() {
            @Override
            public void run() {

                numberCount--;
            }
        };
        timer.schedule(timerTask,1000,1000);

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
    }


    private void initBuilding() {
        //Hold继承自Building,构造函数参数为:重心x,y坐标,在(起始角度,结束角度)范围内发射球
        //屏幕左上角为原点,水平向右为x轴正方向,竖直向下为y轴正方向,以x轴正方向顺时针旋转为正角度
        Building leftTop = new Hole(0, 0, 0, 90);//左上角
        //Log.d("MainView", "WindowHeight" + MainActivity.sWindowHeightPix);
        Building leftButtom = new Hole(0, MainActivity.sWindowHeightPix, -90, 0);//左下角
        Building rightTop = new Hole(MainActivity.sWindowWidthPix, 0, 90, 180);//右上角
        Building rightButtom = new Hole(MainActivity.sWindowWidthPix,
                MainActivity.sWindowHeightPix, -180, -90);//右下角
        mBuildingList.add(leftTop);
        mBuildingList.add(leftButtom);
        mBuildingList.add(rightTop);
        mBuildingList.add(rightButtom);
    }

    private void initWalls() {//初始化四周的边界
        mLineList.add(new Line(0, 0, MainActivity.sWindowWidthPix, 0));
        mLineList.add(new Line(MainActivity.sWindowWidthPix, 0,
                MainActivity.sWindowWidthPix, MainActivity.sWindowHeightPix));
        mLineList.add(new Line(0, MainActivity.sWindowHeightPix,
                MainActivity.sWindowWidthPix, MainActivity.sWindowHeightPix));
        mLineList.add(new Line(0, MainActivity.sWindowHeightPix,
                0, 0));

    }

    public static void addBall(Ball ball) {
        if (ball != null) {
            mBallList.add(ball);
        } else {
            Log.d("MainView", "adding ball but ball is null");
        }

    }

    public void btinit(){
        if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.SERVICE&&mBallList.size() < 10) {
            for (Building building : mBuildingList) {
                if (building instanceof Attackable) {
                    ((Attackable) building).attack();
                }
            }
            ViewCreated=true;

        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Point ruddyPoint = mRuddy.getmRuddyInitPoint();
        Point shooterPoint = mShooter.getmShooterInitPoint();


//        int lengthRuddy = RuddyMathUtils.getLength(ruddyPoint.x, ruddyPoint.y, event.getX(), event.getY());
//        int lengthShooter = RuddyMathUtils.getLength(shooterPoint.x, shooterPoint.y, event.getX(), event.getY());
        int lengthRuddy=0,lengthShooter=0;

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                    int tmpRuddylength = RuddyMathUtils.getLength(ruddyPoint.x, ruddyPoint.y, event.getX(), event.getY());
                    int tmpShooterlength = RuddyMathUtils.getLength(shooterPoint.x, shooterPoint.y, event.getX(), event.getY());
                    if(tmpRuddylength<Ruddy.RUDDY_RADIUS){
                        ruddyPressed=true;
                        mode=1;
                        ruddyEventID=event.getPointerId(event.getActionIndex());
                        lengthRuddy=tmpRuddylength;
                        mRuddy.actionDown(lengthRuddy, event.getX(), event.getY());
                        mHero.setAngle(mRuddy.getAngle());
                    }else if(tmpShooterlength<Shooter.GREEN_RADIUS){//在shooter内
                        shooterPressed=true;
                        mode=0;
                        shooterEventID=event.getPointerId(event.getActionIndex());
                        lengthShooter=tmpShooterlength;
                        mShooter.actionDown();
                    }

//                if(lengthRuddy<Ruddy.RUDDY_RADIUS) {//在ruddy内
//                    ruddyPressed=true;
//                }else if(lengthShooter<Shooter.GREEN_RADIUS){//在shooter内
//                    shooterPressed=true;
//                }
                break;

            case  MotionEvent.ACTION_POINTER_DOWN:
                //Toast.makeText(MainActivity.sContext,"double click" + lengthShooter,Toast.LENGTH_SHORT).show();
                int eventIndex = event.getActionIndex();
                    int tmpRuddylength1 = RuddyMathUtils.getLength(ruddyPoint.x, ruddyPoint.y, event.getX(eventIndex), event.getY(eventIndex));
                    int tmpShooterlength1 = RuddyMathUtils.getLength(shooterPoint.x, shooterPoint.y, event.getX(eventIndex), event.getY(eventIndex));
                    if(tmpRuddylength1<Ruddy.RUDDY_RADIUS){//先按下shooter后按下ruddy
                        ruddyPressed=true;
                        mRuddy.actionDown(lengthRuddy, event.getX(eventIndex), event.getY(eventIndex));
                        mHero.setAngle(mRuddy.getAngle());
                        if(mode==0){//
                            mode=2;
                        }else if(mode==-1){//如果原来shooter没按下,现在ruddy按下
                            mode=1;
                        }
                        ruddyEventID=event.getPointerId(eventIndex);
                        lengthRuddy=tmpRuddylength1;
                    }else if(tmpShooterlength1<Shooter.GREEN_RADIUS){//在shooter内,先按下ruddy,后按下shooter
                        shooterPressed=true;
                        mShooter.actionDown();
                        if(mode==1){
                            mode=2;
                        }else if(mode==-1){
                            mode=0;
                        }
                        shooterEventID=event.getPointerId(eventIndex);
                        lengthShooter=tmpShooterlength1;
                    }
//                if(lengthRuddy<Ruddy.RUDDY_RADIUS) {//在ruddy内
//                    ruddyPressed=true;
//                }else if(lengthShooter<Shooter.GREEN_RADIUS){//在shooter内
//                    shooterPressed=true;
//                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //Toast.makeText(MainActivity.sContext,"" + event.getPointerCount(),Toast.LENGTH_SHORT).show();
//                int counters = event.getPointerCount();
//                for(int i=0;i<counters;i++){
//                    int tmpRuddylength1 = RuddyMathUtils.getLength(ruddyPoint.x, ruddyPoint.y, event.getX(i), event.getY(i));
//                    int tmpShooterlength1 = RuddyMathUtils.getLength(shooterPoint.x, shooterPoint.y, event.getX(i), event.getY(i));
//                    if(tmpRuddylength1<Ruddy.RUDDY_RADIUS && mode==0){//先按下shooter后按下ruddy
//                        ruddyPressed=true;
//                        mode=2;
////                        ruddyEventID=event.getPointerId(i);
////                        event.getPointerId(i);
//                        lengthRuddy=tmpRuddylength1;
//                    }else if(tmpShooterlength1<Shooter.GREEN_RADIUS && mode==1){//在shooter内,先按下ruddy,后按下shooter
//                        shooterPressed=true;
//                        mode=2;
////                        shooterEventID=event.getPointerId(i);
//                        lengthShooter=tmpShooterlength1;
//                    }
//                }
//
////                if(lengthRuddy<Ruddy.RUDDY_RADIUS) {//在ruddy内
////                    ruddyPressed=true;
////                }else if(lengthShooter<Shooter.GREEN_RADIUS){//在shooter内
////                    shooterPressed=true;
////                }
//
//                if(shooterPressed==true){
//                    mShooter.actionDown();
//                }else if(ruddyPressed==true){
//                    mRuddy.actionDown(lengthRuddy, event.getX(), event.getY());
//                    mHero.setAngle(mRuddy.getAngle());
//                }
                if(event.getPointerId(event.getActionIndex())==ruddyEventID){//ruddy up
                    ruddyPressed=false;
                    mRuddy.actionUP();
                    ruddyEventID=-1;
                    if(mode==2){
                        mode=0;
                    }else if(mode==1){
                        mode=-1;
                    }
                }else if(event.getPointerId(event.getActionIndex())==shooterEventID){//shooter up
                    shooterPressed=false;
                    mShooter.actionUp();
                    shooterEventID=-1;
                    mHero.sendBall();
                    if(mode==2){
                        mode=1;
                    }else if(mode==0){
                        mode=-1;
                    }
                }
                break;
            case  MotionEvent.ACTION_MOVE:

                //int indexx = event.getActionIndex();
                if(ruddyPressed){
                    mRuddy.actionMove(lengthRuddy, event.getX(event.findPointerIndex(ruddyEventID)), event.getY(event.findPointerIndex(ruddyEventID)));//不能直接设置为1,因为只有ruddy点时,不存在ID=1,直接结束activity
                    mHero.setAngle(mRuddy.getAngle());
                    int speed = mRuddy.getmLength();
                    mHero.setmSpeed(speed/7);
                }
                if(shooterPressed){
                    lengthShooter=RuddyMathUtils.getLength(shooterPoint.x, shooterPoint.y, event.getX(event.findPointerIndex(shooterEventID)), event.getY(event.findPointerIndex(shooterEventID)));
                    mShooter.actionMove(lengthShooter,event.getX(event.findPointerIndex(shooterEventID)),event.getY(event.findPointerIndex(shooterEventID)));
                    double tmpAng = mShooter.getAngle();
                    //Toast.makeText(MainActivity.sContext,""+tmpAng,Toast.LENGTH_SHORT).show();
                    mHero.setmBallAngle(tmpAng);

                }
                break;

            case MotionEvent.ACTION_UP:
                int index = event.getActionIndex();
                if(event.getPointerId(index)==ruddyEventID){//ruddy up
                    mRuddy.actionUP();
                    ruddyPressed=false;
                    mode=-1;
                    ruddyEventID=-1;
                }else if(event.getPointerId(index)==shooterEventID){//shooter up
                    mShooter.actionUp();
                    shooterPressed=false;
                    mode=-1;
                    shooterEventID=-1;
                    mHero.sendBall();
                }

//                if(shooterPressed==true){
//                    //hero发球
//                    mShooter.actionUp();
//                    shooterPressed=false;
//                    mode=-1;
//                }else if(ruddyPressed==true){
//                    mRuddy.actionUP();
//                    ruddyPressed=false;
//                    mode=-1;
//                }
                mHero.setmSpeed(0.0);
                break;

        }

        return true;


//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            //如果屏幕接触点不在摇杆挥动范围内,则不处理返回false
//            //mHero.setmSpeed(20.0);
//
//            float x = event.getX();
//            float y = event.getY();
//            //如果在操控杆范围内
//
//            //如果在发射器范围内
//
//            Log.d("MainView", "ACTION_DOWN");
//        }
//        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            int speed = mRuddy.getmLength();
//            mHero.setmSpeed(speed/7);
//            Log.d("MainView", "ACTION_MOVE");
//        }
//        //如果手指离开屏幕，则摇杆返回初始位置
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            mHero.setmSpeed(0.0);
//            Log.d("MainView", "ACTION_UP");
//        }
//
//        //如果有Touch事件,把事件传递给操控杆处理
//        //若操控杆返回true表示操控杆已经处理过此事件,通知上层不再处理
//        boolean isDealBymRuddy = mRuddy.onTouchEvent(event);
//        boolean isDealBymShooter = mShooter.onTouchEvent(event);
//        Log.d("MainView", "isDealByRuddy"+isDealBymRuddy);
//        if(isDealBymRuddy){
//            //Log.d("MainView","操控杆已处理");
//            Log.d("MainView", "mRuddy.getAngle"+mRuddy.getAngle());
//            Log.d("MainView", "mHero.setAngle"+mHero.getmAngle());
//            mHero.setAngle(mRuddy.getAngle());
//            Log.d("MainView", "mHero.setAngle"+mHero.getmAngle());
//            return true;
//        }
//            //操控杆没有处理,交由其他控件处理,若其他控件已经处理则把下面的返回值false改为true
//
//            if(isDealBymShooter) return true;
//            else return false;


    }

    @Override
    public void run() {
        while (mIsRunning) {
            ViewCreated=true;
            long start = System.currentTimeMillis();
            logic();
            myDraw();
            long end = System.currentTimeMillis();
            //切换线程
            try {
                if (end - start < time)
                Thread.sleep(time - (end - start));
                Log.d("MainView", "time:" + (end - start));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void logic() {
        if(!mHero.isPause()){//如果人物不暂停就可以顺利移动
            mHero.updatePoint();
        }else {
            countForHero++;
            if(countForHero * time >= 1000){//看暂停时间是否满一秒
                mHero.setPause(false);
                countForHero = 0;
            }
        }
        for (Lineable object : mLineableList) {
            if (object.collide(mHero))
                continue;
        }

        for (int i = 0; i < mBallList.size(); i++) {
            mBallList.get(i).calculatePoint();
            mBallList.get(i).collide(mHero);
            for (Lineable object:mLineableList){
                mBallList.get(i).collide(object);
            }
            for (int j = i + 1; j < mBallList.size(); j++) {
                if (mBallList.get(i).collide(mBallList.get(j)))
                    break;
            }
            for (Line line : mLineList) {
                if (mBallList.get(i).collide(line))
                    break;
            }
        }
        //每隔50*50ms=2500ms建筑发球伤人
        if (mBallList.size() < 12) {
            for (Building building : mBuildingList) {
                if (building instanceof Attackable) {
                    ((Attackable) building).attack();
                }
            }ViewCreated=true;
        }else if (BluetoothMsg.serviceOrCilent == BluetoothMsg.ServerOrCilent.CILENT)
            ViewCreated=true;
        //move();

    }

    //绘画对象的先后顺序不同,后画的会覆盖前画的
    public void myDraw() {
        try {
            canvas = mSurfaceHolder.lockCanvas();
            //canvas.drawColor(Color.BLACK);//清屏
            //canvas.drawBitmap(MainActivity.bg, 0, 0, mPaint);//(0,0)代表canvas的起始点而不是bg的起始点
            canvas.drawBitmap(background, 0, 0, mPaint);

            //add newly
            mPaint.setTextSize(60);
            canvas.drawText(""+numberCount,MainActivity.sWindowWidthPix/2,MainActivity.sWindowHeightPix/20,mPaint);
            //add newly


            canvas.drawLine(100, 100, 500, 500, mPaint);
            //画操纵杆
            mRuddy.onDraw(canvas);
            mShooter.onDraw(canvas);
            //画出所有建筑
            for (Lineable object :
                    mLineableList) {
                object.onDraw(canvas, mPaint);
            }
            //画出所有球
            for (Ball ball :
                    mBallList) {
                ball.onDraw(canvas, mPaint);
            }
            mHero.onDraw(canvas,mPaint);
            oHero.onDraw(canvas,mPaint);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (canvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void transportData(){
        //需要传输的数据:角色,球   待定:建筑物,机关

    }

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
