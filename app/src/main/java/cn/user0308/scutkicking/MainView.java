package cn.user0308.scutkicking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Component.Ball;
import cn.user0308.scutkicking.Component.Ruddy;
import cn.user0308.scutkicking.building.Attackable;
import cn.user0308.scutkicking.building.Building;
import cn.user0308.scutkicking.building.Hole;

/**
 * Created by user0308 on 4/22/17.
 */

public class MainView extends SurfaceView implements Runnable, SurfaceHolder.Callback{

    //Component 相关
    private Ruddy mRuddy = null;
    private static List<Ball> mBallList ;

    //building 相关
    private List<Building> mBuilding;

    //SurfaceView 相关
    private SurfaceHolder mSurfaceHolder = null;
    private Thread mThread = null;

    private boolean mIsRunning = false;
    private Paint mPaint = null;
    private Canvas canvas = null;



    public MainView(Context context){
        super(context);
        mRuddy = new Ruddy(context);
        mBallList = new ArrayList<>();
        mBuilding = new ArrayList<>();
        mPaint = new Paint();
        initBuilding();

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    private void initBuilding(){
        Building leftTop = new Hole(0, 0, 0 ,90);//左上角
        Log.d("MainView", "WindowHeight" + MainActivity.sWindowHeightPix);
        Building leftButtom = new Hole(0, MainActivity.sWindowHeightPix, -90, 0);
        Building rightTop= new Hole(MainActivity.sWindowWidthPix, 0, 90, 180);
        Building rightButtom = new Hole(MainActivity.sWindowWidthPix, MainActivity.sWindowHeightPix, -180, -90);
        mBuilding.add(leftTop);
        mBuilding.add(leftButtom);
        mBuilding.add(rightTop);
        mBuilding.add(rightButtom);
    }

    public static void addBall(Ball ball){
        mBallList.add(ball);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mRuddy.onTouchEvent(event);
        //return super.onTouchEvent(event);
    }

    @Override
    public void run() {
        int count = 0;
        while (mIsRunning) {
            for (Ball ball : mBallList) {
                ball.run();
            }
            if(count % 50 == 0) {
                for (Building building : mBuilding) {
                    if (building instanceof Attackable) {
                        ((Attackable) building).attack();
                    }
                }
            }
            myDraw();
            try {
                Thread.sleep(50);
                count ++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void myDraw(){
        try {
            canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);//清屏
            //画操纵杆
            mRuddy.onDraw(canvas);
            //画出所有建筑
            for (Building building :
                    mBuilding) {
                building.onDraw(canvas, mPaint);
            }
            //画出所有球
            for(Ball ball:
                    mBallList){
                ball.onDraw(canvas, mPaint);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(canvas != null){
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
