package cn.user0308.scutkicking.Utils;

import java.util.Random;

/**用于产生指定范围内随机数
 * Created by Yuan Qiang on 2017/4/23.
 */

public class RandomUtil {
    public static float randomNum(float begin, float end){
        return (float) (Math.random() * (end - begin ) + begin);
    }
}
