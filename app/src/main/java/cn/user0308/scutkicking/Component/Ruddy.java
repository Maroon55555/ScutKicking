package cn.user0308.scutkicking.Component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import cn.user0308.scutkicking.activity.MainActivity;
import cn.user0308.scutkicking.Utils.RuddyMathUtils;

/**
 * Created by user0308 on 4/22/17.
 */
//虚拟摇杆类,由两个同心圆构成,外大内小,大圆不动,小圆圆心随手指触摸位置改变,但小圆圆心始终在大圆内
public class Ruddy {

    //中间滑轮(小圆)的半径,具体数值到时修改
    public static final int RUDDY_WHEEL_RADIUS = (int)(0.05*MainActivity.sWindowHeightPix);
    //外面摇杆(大圆)的半径,具体数值到时修改
    public static final int RUDDY_RADIUS = (int)(0.15*MainActivity.sWindowHeightPix);
    //
    private boolean flag=false;
    private double mAngle;//小圆圆心与大圆圆心连线与x轴正方向形成的夹角
    private int mLength;//触摸点与大圆圆心之间距离,距离越长,设置Hero运动速度越大
    private Point mRuddyWheelCurrPoint;//当前小圆圆心位置
    private static Point mRuddyInitPoint;//初始大圆和小圆共同圆心位置

    private Paint mPaint = null;

    public Ruddy(Context context){

        int windowHeightPix = MainActivity.sWindowHeightPix;
        int windowWidthPix = MainActivity.sWindowWidthPix;

        //初始化初始大圆小圆共同圆心位置
        mRuddyInitPoint = new Point(windowWidthPix-(RUDDY_RADIUS+RUDDY_WHEEL_RADIUS*2),
                                    windowHeightPix-RUDDY_RADIUS-RUDDY_WHEEL_RADIUS);

        //初始化画笔,设置抗锯齿属性
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        //初始化当前小球圆心位置为初始圆心位置
        mRuddyWheelCurrPoint = new Point(mRuddyInitPoint);
    }

    //绘制函数,绘制大小圆,设置大圆颜色为浅灰,透明度100,小圆红色,透明度200
    public void onDraw(Canvas canvas){
        //大圆浅灰色,不透明度100,越大越不透明,范围为1~255
        mPaint.setColor(Color.LTGRAY);
        mPaint.setAlpha(100);
        //在初始圆心位置绘制一个大圆半径的圆
        canvas.drawCircle(mRuddyInitPoint.x, mRuddyInitPoint.y, RUDDY_RADIUS, mPaint);
        //小圆红色,不透明度200,
        mPaint.setColor(Color.RED);
        mPaint.setAlpha(150);
        canvas.drawCircle(mRuddyWheelCurrPoint.x, mRuddyWheelCurrPoint.y, RUDDY_WHEEL_RADIUS, mPaint);//绘制摇杆
        //mPaint.setAlpha(255);
    }

    public void onTouchEvent(MotionEvent event){
        //Log.d("Ruddy","in Ruddy touch");

        //获取触摸点与同圆圆心之间的长度
//        int length = RuddyMathUtils.getLength(mRuddyInitPoint.x, mRuddyInitPoint.y, event.getX(), event.getY());
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            //如果屏幕接触点不在摇杆挥动范围内,则不处理返回false
//            if (length > RUDDY_RADIUS) {
//                return false;
//            }
//            else flag=true;
//        }
//        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            if (length <= RUDDY_RADIUS) {
//                //如果手指在摇杆活动范围内，则摇杆处于手指触摸位置
//                mRuddyWheelCurrPoint.set((int) event.getX(), (int) event.getY());
//            } else {
//                if(flag)
//                //设置摇杆位置，使其处于手指触摸方向的 摇杆活动范围边缘
//                mRuddyWheelCurrPoint = RuddyMathUtils.getBorderPoint(mRuddyInitPoint, new Point((int) event.getX(),
//                                                                    (int) event.getY()), RUDDY_RADIUS);
//            }
            //Log.d("Ruddy","point is" + mRuddyWheelCurrPoint.x + " " + mRuddyWheelCurrPoint.y);
//            if(flag){
//                mAngle = RuddyMathUtils.getAngle(mRuddyWheelCurrPoint,new Point((int)event.getX(),(int)event.getY()));
//
//                //if(even.X,y>r)
//                mLength = RuddyMathUtils.getLength((int)event.getX(),(int)event.getY(),mRuddyInitPoint.x,mRuddyInitPoint.y);
//                if(mLength>RUDDY_RADIUS) mLength = RUDDY_RADIUS;
//            }
//        }
//        //如果手指离开屏幕，则摇杆返回初始位置
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            mRuddyWheelCurrPoint = new Point(mRuddyInitPoint);
//            flag = false;
//        }
//        return true;
    }

    public void actionUP(){
        mRuddyWheelCurrPoint = new Point(mRuddyInitPoint);
    }

    public void actionDown(int length,float x,float y){
        if (length <= RUDDY_RADIUS) {
            //如果手指在摇杆活动范围内，则摇杆处于手指触摸位置
            mRuddyWheelCurrPoint.set((int) x, (int) y);
        } else {
            //设置摇杆位置，使其处于手指触摸方向的 摇杆活动范围边缘
            mRuddyWheelCurrPoint = RuddyMathUtils.getBorderPoint(mRuddyInitPoint,
                    new Point((int) x,(int) y), RUDDY_RADIUS);
        }
        mAngle = RuddyMathUtils.getAngle(mRuddyWheelCurrPoint,new Point((int)x,(int)y));

        //if(even.X,y>r)
        mLength = RuddyMathUtils.getLength((int) x,(int)y,mRuddyInitPoint.x,mRuddyInitPoint.y);
        if(mLength>RUDDY_RADIUS) mLength = RUDDY_RADIUS;
    }

    public void actionMove(int length,float x,float y){
        length = RuddyMathUtils.getLength(mRuddyInitPoint.x, mRuddyInitPoint.y, x, y);
        if (length <= RUDDY_RADIUS) {
            //如果手指在摇杆活动范围内，则摇杆处于手指触摸位置
            mRuddyWheelCurrPoint.set((int) x, (int) y);
        } else {
            //设置摇杆位置，使其处于手指触摸方向的 摇杆活动范围边缘
            mRuddyWheelCurrPoint = RuddyMathUtils.getBorderPoint(mRuddyInitPoint,
                    new Point((int) x,(int) y), RUDDY_RADIUS);
        }
        mAngle = RuddyMathUtils.getAngle(mRuddyWheelCurrPoint,new Point((int)x,(int)y));

        //if(even.X,y>r)
        mLength = RuddyMathUtils.getLength((int)x,(int)y,mRuddyInitPoint.x,mRuddyInitPoint.y);
        if(mLength>RUDDY_RADIUS) mLength = RUDDY_RADIUS;
    }

    public int getmLength(){
        return mLength;
    }

    public double getAngle(){
        mAngle = RuddyMathUtils.getAngle(mRuddyInitPoint,mRuddyWheelCurrPoint);
        //有可能出现 NAN
//        if(Double.isNaN(mAngle)){
//            //angle is NAN
//            mAngle = 0.0;
//        }
        return mAngle;
    }

    public Point getmRuddyInitPoint(){
        return mRuddyInitPoint;
    }
}
