package cn.user0308.scutkicking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

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
    public static List<Ball> mBallList ;
    //Building 相关
    private List<Building> mBuildingList;
    //Hero 主人公
    public static Hero mHero = null;
    public static Hero oHero = null;
    //background
    Bitmap background;

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

    private Rect rect;

    public MainView(Context context){
        super(context);

        mRuddy = new Ruddy(context);
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
        int bgWidth = bg.getWidth();
        int bgHeight = bg.getHeight();
        Matrix matrix = new Matrix();
        float sx = (float)MainActivity.sWindowWidthPix / bgWidth;
        float sy = (float)MainActivity.sWindowHeightPix/bgHeight;
        matrix.postScale(sx,sy);
        background = Bitmap.createBitmap(bg,0,0,bgWidth,
                bgHeight,matrix,false);
        //缩放背景图片结束
        Toast.makeText(context,"w,h is "
                + background.getWidth() + " "
                + background.getHeight(),Toast.LENGTH_SHORT ).show();

        List<Line> lines = new ArrayList<>();
        Line line1 = new Line(600,300,600,500);//left
        Line line2 = new Line(600,300,700,300);//top
        Line line3 = new Line(600,500,700,500);//bottom
        Line line4 = new Line(700,300,700,500);//right
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);
        rect = new Rect(lines);

        mLineableList.add(rect);

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
            Log.d("MainView", "ACTION_DOWN");
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int speed = mRuddy.getmLength();
            mHero.setmSpeed(speed/7);
            Log.d("MainView", "ACTION_MOVE");
        }
        //如果手指离开屏幕，则摇杆返回初始位置
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mHero.setmSpeed(0.0);
            Log.d("MainView", "ACTION_UP");
        }

        //如果有Touch事件,把事件传递给操控杆处理
        //若操控杆返回true表示操控杆已经处理过此事件,通知上层不再处理
        boolean isDealBymRuddy = mRuddy.onTouchEvent(event);
        Log.d("MainView", "isDealByRuddy"+isDealBymRuddy);
        if(isDealBymRuddy){
            //Log.d("MainView","操控杆已处理");
            Log.d("MainView", "mRuddy.getAngle"+mRuddy.getAngle());
            Log.d("MainView", "mHero.setAngle"+mHero.getmAngle());
            mHero.setAngle(mRuddy.getAngle());
            Log.d("MainView", "mHero.setAngle"+mHero.getmAngle());
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
                if(end - start < 30);
                Thread.sleep(30 - (end - start));
                Log.d("MainView", "time:" + (end -start));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public  void logic(){
        mHero.updatePoint();
        for(Lineable object:mLineableList){
            if(object.collide(mHero))
                continue;
        }

        for (int i = 0; i <mBallList.size() ; i++) {
            mBallList.get(i).calculatePoint();
            mBallList.get(i).collide(mHero);
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
        if(mBallList.size()<12 ) {
            for (Building building : mBuildingList) {
                if (building instanceof Attackable) {
                    ((Attackable) building).attack();
                }
            }
        }
        //move();

    }
    //绘画对象的先后顺序不同,后画的会覆盖前画的
    public void myDraw(){
        try {
            canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);//清屏
            //canvas.drawBitmap(MainActivity.bg, 0, 0, mPaint);//(0,0)代表canvas的起始点而不是bg的起始点
            canvas.drawBitmap(background,0,0,mPaint);

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
