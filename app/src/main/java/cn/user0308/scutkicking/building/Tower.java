package cn.user0308.scutkicking.building;

import android.graphics.Canvas;
import android.graphics.Paint;

/**中央高塔
 * Created by Yuan Qiang on 2017/4/23.
 */

public class Tower extends Building implements Attackable {

    public Tower(float positionX, float positionY) {
        super( positionX, positionY);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {

    }

    @Override
    public void attack() {
        //发射激光
    }
}
