package cn.user0308.scutkicking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cn.user0308.scutkicking.Component.Ball;
import cn.user0308.scutkicking.Component.Ruddy;

/**
 * Created by user0308 on 4/22/17.
 */

public class MainView extends SurfaceView implements Runnable,SurfaceHolder.Callback{

    //Component 相关
    private Ruddy mRuddy = null;

    //SurfaceView 相关
    private SurfaceHolder mSurfaceHolder = null;
    private Thread mThread = null;
    private boolean mIsRunning = false;

    //球相关
    private Ball mBall = null;
    public MainView(Context context){
        super(context);
        mRuddy = new Ruddy(context);

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        mBall = new Ball();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mRuddy.onTouchEvent(event);
        //return super.onTouchEvent(event);
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while (mIsRunning){
            try {
                canvas = mSurfaceHolder.lockCanvas();
                if(canvas == null){
                    Log.d("MyView","canvas is null in run");
                }else if(mSurfaceHolder == null){
                    Log.d("MyView","holder is null in run");
                }
                canvas.drawColor(Color.BLACK);//清屏
                mBall.onDraw(canvas);
                mRuddy.onDraw(canvas);
            }catch (NullPointerException e){
                e.printStackTrace();
            }finally {
                if(null != canvas){
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
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
