package cn.user0308.scutkicking.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import cn.user0308.scutkicking.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏电池时间等
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏结束
        setContentView(R.layout.activity_help);
    }
/*
     @Override
     public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            HelpActivity.this.finish();
            return true;
        }
        return false;
     }
*/
}
