package cn.user0308.scutkicking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    public static Context sContext;
    public static int sWindowHeightPix;//设备分辨率
    public static int sWindowWidthPix;

    public static int sMapWidth;
    public static int sMapHeight;
    public static Bitmap bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        sWindowHeightPix = displayMetrics.heightPixels;
        sWindowWidthPix = displayMetrics.widthPixels;
        //Log.d("MainActivity", "宽：" + sWindowWidthPix + " 高：" + sWindowHeightPix);
        bg = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        sMapWidth = bg.getWidth();
        sMapHeight = bg.getHeight();

        sContext = this;
        setContentView(new MainView(this));
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity", "onStop");
        super.onStop();
    }
}
