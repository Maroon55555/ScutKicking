package cn.user0308.scutkicking.Component;

import android.util.Log;

import cn.user0308.scutkicking.Collideable;
import cn.user0308.scutkicking.Lineable;

/**
 * Created by Yuan Qiang on 2017/4/27.
 * 我爱数学 XD
 */

public class Line implements Collideable{
    private float mAngle;  //线段的角度，应该在-90度到90度之间，可以用角度来简单的改变碰撞小球的方向

    private float x1, y1;  //线段左边点的坐标
    private float x2, y2;

    private float A;//直线方程 Ax + By + C = 0，该直线方程用来检查两线段是否相交
    private float B;
    private float C;

    private float intersectionX;//两线段的交点
    private float intersectionY;

    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        A = y2 - y1;
        B = x1 - x2;
        C = y1 * x2 - x1 *y2;

        if( B != 0){
            mAngle = (float) Math.toDegrees(Math.atan(A / (-B)));
        }else {
            mAngle = 90;
        }
    }

    public float getAngle() {
        return mAngle;
    }

    @Override
    public boolean collide(Collideable object) {
        if (object instanceof Ball){
            return checkBallCollide((Ball)object);
        }else if(object instanceof Line){
            return checkIntersect((Line)object);
        }else if(object instanceof Lineable){
            return object.collide(this);
        }else {
            return false;
        }
    }
    /**
     *  用来确定两直线是否相交
     *  @author Yuan Qiang
     *  created at 2017/4/27 16:49
     *  @param
     */

    public boolean checkBallCollide(Ball ball){
//        return ball.checkLineCollide(this);
        float x = ball.getX();//球心坐标
        float y = ball.getY();
        float endpoint1 = (float) Math.sqrt((x1 - x)*(x1 - x) + (y1 - y)*(y1 - y));
        float endpoint2 = (float) Math.sqrt((x2 - x)*(x2 - x) + (y2 - y)*(y2 - y));
        if (endpoint1 <= ball.getRadius()){
                Line line = new Line(x1,y1,x,y);
                ball.setAngle(2*line.getAngle() - ball.getAngle());
//            float dx = x - x1;
//            float dy = y - y1;
//            float angle = (float) Math.toDegrees(Math.acos(dx/Math.sqrt(dx*dx+dy*dy)));
//            ball.setAngle(angle);
                ball.back();
            return true;
        }else if (endpoint2 <= ball.getRadius()){
                Line line = new Line(x2,y2,x,y);
                ball.setAngle(2*line.getAngle() - ball.getAngle());
                ball.back();
//            float dx = x - x2;
//            float dy = y - y2;
//            float angle = (float) Math.toDegrees(Math.acos(dx/Math.sqrt(dx*dx+dy*dy)));
//            ball.setAngle(angle);
//            ball.back();
            return true;
        }

        float vx1 = x -x1;//若点A为x1,y1,点B为x,y，点C为x2,y2,那么以下向量AB
        float vy1 = y - y1;
        float length = (float) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
        float vx2 = (x2 - x1)/length;//AC的单位向量
        float vy2 = (y2 - y1)/length;
        float dotProduct = vx1 * vx2 + vy1 * vy2;//计算点积，其意义是向量AB在AC上的投影
        if(dotProduct < 0 || dotProduct > length){
            return false;
        }else {
            float distance = calDistance(x,y);//计算球心到线段的距离
             if(distance <= ball.getRadius()){
                ball.setAngle(2 * mAngle - ball.getAngle());//碰撞小球改变角度
                ball.back();
                return true;
            }else {
                Log.d("Line", "return false");
                return false;
            }
        }
    }
    public boolean checkIntersect(Line line){//判断两线段是否相交
        if( this.checkOnLine(line.getX1(), line.getY1()) //该判断条件指line的两端点在本线两端
                * this.checkOnLine(line.getX2(), line.getY2()) < 0)
        {//但是由于这是两根线段因此需要判断这根线段的两端点也应在line的两端
            if( line.checkOnLine(x1, y1) * line.checkOnLine(x2, y2) < 0){//两线段相交
                float A1 = line.getA();
                float B1= line.getB();
                float C1 = line.getC();
                //计算两线段的交点，用于设置球的位置
                intersectionX = (C1 * B - C * B1)/(A * B1 - A1 * B);
                intersectionY = (C1 * A - C * A1)/(A1 * B - A * B1);
                return true;
            }
        }
        return false;
    }

    /**
     *  用于检测点是否在线段上，返回0即为是
     *  @author Yuan Qiang
     *  created at 2017/4/27 19:57
     *  @param
     */

    public float checkOnLine(float x, float y){//用来检查点是否在线上，返回0代表是
        return A * x + B * y + C ;
    }

    public float calDistance(float x, float y){
        //点到两线段端点的距离
        float a = (float) Math.sqrt( (y - y1) * (y - y1) + (x - x1) * (x - x1));
        float b = (float) Math.sqrt( (y - y2) * (y - y2) + (x - x2) * (x - x2));
        //点到直线最短距离
        float c = (float) (Math.abs(A * x+ B * y + C) / Math.sqrt(A * A + B * B));
        //点到线段最短距离是以上三者最短的一个
        return Math.min(a, Math.min(b, c));
    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public float getA() {
        return A;
    }

    public float getB() {
        return B;
    }

    public float getC() {
        return C;
    }

    public float getIntersectionX() {
        return intersectionX;
    }

    public float getIntersectionY() {
        return intersectionY;
    }

}
