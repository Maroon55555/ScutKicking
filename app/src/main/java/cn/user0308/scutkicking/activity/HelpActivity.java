package cn.user0308.scutkicking.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import cn.user0308.scutkicking.R;

public class HelpActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏电池时间等
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏结束
        context = this;


        setContentView(R.layout.activity_help);


        ImageButton helpReturnButton=(ImageButton)findViewById(R.id.helpreturn);
        ImageButton gameRuleButton=(ImageButton)findViewById(R.id.gamerule);
        ImageButton aboutButton=(ImageButton)findViewById(R.id.about);

        helpReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"return",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(HelpActivity.this,FirstActivity.class);
                startActivity(intent);//通过intent类来另外启动一个activity
                //FirstActivity.this.finish();
            }
        });
       gameRuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"gamerule",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(HelpActivity.this,RuleoneActivity.class);
                startActivity(intent);//通过intent类来另外启动一个activity
                //FirstActivity.this.finish();
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"about",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(HelpActivity.this,AboutActivity.class);
                startActivity(intent);//通过intent类来另外启动一个activity
                //FirstActivity.this.finish();
            }
        });
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


//所以这里也是  一片荒野
