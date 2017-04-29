package cn.user0308.scutkicking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.user0308.scutkicking.Component.Ball;
import cn.user0308.scutkicking.Component.Hero;
import cn.user0308.scutkicking.Component.Ruddy;
import cn.user0308.scutkicking.Utils.LineSegmentUtil;
import cn.user0308.scutkicking.Utils.RuddyMathUtils;
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

    //SurfaceView 相关
    private SurfaceHolder mSurfaceHolder = null;
    private Thread mThread = null;
    private boolean mIsRunning = false;
    private Paint mPaint = null;
    private Canvas canvas = null;

    //地图中所有的线段
    private List<LineSegmentUtil> mLineList;


    public MainView(Context context){
        super(context);
        mRuddy = new Ruddy(context);
        mHero = new Hero(context);
        mBallList = new ArrayList<>();
        mBuildingList = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        initBuilding();

        mLineList = new ArrayList<>();
        initWalls();
        LineSegmentUtil line = new LineSegmentUtil(100,100,500,500);
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
        mLineList.add(new LineSegmentUtil(0, 0, MainActivity.sWindowWidthPix, 0));
        mLineList.add(new LineSegmentUtil(MainActivity.sWindowWidthPix, 0,
                MainActivity.sWindowWidthPix, MainActivity.sWindowHeightPix));
        mLineList.add(new LineSegmentUtil(0, MainActivity.sWindowHeightPix,
                MainActivity.sWindowWidthPix, MainActivity.sWindowHeightPix));
        mLineList.add(new LineSegmentUtil(0, MainActivity.sWindowHeightPix,
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
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mHero.setmSpeed(25.0);
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
        int count = 0;
        while (mIsRunning) {
            //计算小球实时位置
            for (int i = 0; i <mBallList.size() -1; i++) {
                //如果未碰撞就移动，如果碰撞了它的位置在checkLineCollide方法中设置
                if(!checkLineCollide(mBallList.get(i))){
                    mBallList.get(i).calculatePoint();
                }
//                for(int j = i + 1; j < mBallList.size(); j ++){
//                    checkBallCollide(mBallList.get(i), mBallList.get(j));
//                }
            }
            //每隔50*50ms=2500ms建筑发球伤人
            if(count % 2000 == 0) {
                count = 0;
                for (Building building : mBuildingList) {
                    if (building instanceof Attackable) {
                        ((Attackable) building).attack();
                    }
                }
            }
            //绘制小球,建筑,操控杆
            mHero.updatePoint();
            myDraw();
            //切换线程
            try {
                Thread.sleep(50);
                count ++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //绘画对象的先后顺序不同,后画的会覆盖前画的
    public void myDraw(){
        try {
            canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);//清屏
            canvas.drawLine(100,100,500,500, mPaint);
            //画操纵杆
            mRuddy.onDraw(canvas);
            //画出所有建筑
            for (Building building :
                    mBuildingList) {
                building.onDraw(canvas, mPaint);
            }
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
    /** 
     *  检测球与线段的碰撞
     *  @author Yuan Qiang
     *  created at 2017/4/27 18:02
     *  @param 
     */
    
    public boolean checkLineCollide(Ball ball){
        //用小球当前位置与下一要运动到的位置构成线段
        LineSegmentUtil ballLine = new LineSegmentUtil(ball.getX(), ball.getY(), 
                ball.getX() +ball.offsetX(ball.getSpeed()+ ball.getRadius()),
                ball.getY() +ball.offsetY(ball.getSpeed()+ ball.getRadius()));
        for(LineSegmentUtil line:mLineList){
            //如果线段相交说明小球将碰到建筑物
            if(line.checkIntersect(ballLine)){
                ball.rebound(line);
                return true;
            }
        }
        return false;
    }

    /**
     *  检测球与球的碰撞
     *  @author Yuan Qiang
     *  created at 2017/4/27 20:21
     *  @param
     */

    public void checkBallCollide(Ball ball1, Ball ball2){
        //两球的距离
        float distance = (ball1.getY() - ball2.getY()) * (ball1.getY() - ball2.getY())
                + (ball1.getX() - ball2.getX()) * (ball1.getX() - ball2.getX());
        //两球距离小于半径则碰撞
        if(distance <= (ball1.getRadius() + ball2.getRadius()) * (ball1.getRadius() + ball2.getRadius())){
            float s1 = ball1.getSpeed();
            float s2 = ball2.getSpeed();
            float a3 = new LineSegmentUtil(ball1.getX(), ball1.getY(), ball2.getX(), ball2.getY())
                    .getAngle();//两圆心构成的线段的角度
            //接下来是玄学向量操作
            float x1 = ball1.offsetX(s1);//将球1的速度转化为向量形式称为向量a
            float y1 = ball1.offsetY(s1);
            float x2 = ball2.offsetX(s2);//同理球2称为向量b
            float y2 = ball2.offsetY(s2);
            float p = (float) Math.cos(Math.toRadians(a3));//将线段转化为单位向量，称该向量为R
            float q = (float) Math.sin(Math.toRadians(a3));
            float dx1 = (p * x1 + q * y1)/(p * p + q * q);//将a投影到向量R
            float dy1 = (p * y1 - q * x1)/(p * p + q * q);
            float px1 = p * dx1;//这是投影沿R方向的向量   p便是ping行，c表示chui直  XD
            float py1 = q * dx1;
            float cx1 =-q * dy1;//这是投影垂直R方向的向量
            float cy1 = p * dy1;
            //同理
            float dx2 = (p * x2 + q * y2)/(p * p + q * q);//将a投影到向量R
            float dy2 = (p * y2 - q * x2)/(p * p + q * q);
            float px2 = p * dx2;//这是投影沿R方向的向量   p便是ping行，c表示chui直  XD
            float py2 = q * dx2;
            float cx2 =-q * dy2;//这是投影垂直R方向的向量
            float cy2 = p * dy2;

            /**
             * 注意：根据计算，两质量相同的小球碰撞，其沿球心方向的速度交换，而垂直球心的方向不变
             */
            float temp;//交换速度
            temp = px1; px1 = px2;  px2 = temp;
            temp = py1; py1 = py2;  py2 = temp;

            //接下来根据向量转化为速度方向，先将两速度分量组合，n表示now
            float nx1 = px1 + cx1;
            float ny1 = py1 + cy1;
            ball1.setAngle((float) Math.toDegrees(Math.atan(ny1/nx1)));
            //同理
            float nx2 = px2 + cx2;
            float ny2 = py2 + cy2;
            ball2.setAngle((float) Math.toDegrees(Math.atan(ny2/nx2)));

        }
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
