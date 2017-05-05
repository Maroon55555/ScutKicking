package cn.user0308.scutkicking.Component;

import android.provider.Settings;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import cn.user0308.scutkicking.MainView;

import static org.junit.Assert.*;

/**
 * Created by Yuan Qiang on 2017/4/30.
 */
public class LineTest {
    private final String TAG = "LineTest";
    private MainView mainView;

    @Test
    public void checkIntersect() throws Exception {
        Line line = new Line(0,0,100,100);
        Line line1 = new Line(0,100,100,0);
        line.checkIntersect(line1);
        line1.checkIntersect(line);
    }



}