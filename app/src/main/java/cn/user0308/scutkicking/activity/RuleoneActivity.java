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

public class RuleoneActivity extends BaseActivity {
     Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏电池时间等
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏结束
        context=this;
        setContentView(R.layout.activity_ruleone);
        ImageButton gameRuleReturnButton=(ImageButton)findViewById(R.id.gamerulereturn);
        ImageButton leftArrowButton=(ImageButton)findViewById(R.id.leftarrow);
        ImageButton rightArrowButton=(ImageButton)findViewById(R.id.rightarrow);

        gameRuleReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"return",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(RuleoneActivity.this,HelpActivity.class);
                startActivity(intent);//通过intent类来另外启动一个activity
                //FirstActivity.this.finish();
            }
        });

        leftArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"return",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(RuleoneActivity.this,HelpActivity.class);
                startActivity(intent);//通过intent类来另外启动一个activity
                //FirstActivity.this.finish();
            }
        });

        rightArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"return",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(RuleoneActivity.this,RuletwoActivity.class);
                startActivity(intent);//通过intent类来另外启动一个activity
                //FirstActivity.this.finish();
            }
        });
    }
}