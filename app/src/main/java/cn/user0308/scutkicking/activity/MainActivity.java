package cn.user0308.scutkicking.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import cn.user0308.scutkicking.MainView;
import cn.user0308.scutkicking.R;

public class MainActivity extends BaseActivity {
    public static void startMainAcitivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        context.startActivity(intent);
    }

    public static final float WIDTH = 1280;//将游戏逻辑限定在1280*720的矩形中
    public static final float HEIGHT = 720;
    public static float widthScale;//用于将逻辑矩形画在实际手机屏幕上，即将逻辑矩形按照这种
    public static float heightScale;//比例缩放

    public static Context sContext;
    public static int sWindowHeightPix;//设备分辨率
    public static int sWindowWidthPix;

    //public static int sMapWidth;
    //public static int sMapHeight;
    //public static Bitmap bg;

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
        widthScale = sWindowWidthPix/WIDTH;
        heightScale = sWindowHeightPix/HEIGHT;
        //Log.d("MainActivity", "宽：" + sWindowWidthPix + " 高：" + sWindowHeightPix);
        //bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        //sMapWidth = bg.getWidth();
        //sMapHeight = bg.getHeight();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//禁用返回键
        if (keyCode == event.getKeyCode())
            return true;
        return false;
    }
}
