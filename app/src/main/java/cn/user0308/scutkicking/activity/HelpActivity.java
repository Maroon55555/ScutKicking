package cn.user0308.scutkicking.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.user0308.scutkicking.R;

public class HelpActivity extends BaseActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏结束
        context = this;
        setContentView(R.layout.activity_help);

        TextView helpReturnButton = (TextView) findViewById(R.id.help_return);
        TextView gameRuleButton = (TextView) findViewById(R.id.help_rule);
        TextView aboutButton = (TextView) findViewById(R.id.help_about);

        helpReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"return",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(HelpActivity.this,FirstActivity.class);
                startActivity(intent);//通过intent类来另外启动一个activity
                //FirstActivity.this.finish();
            }
        });
        gameRuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"gamerule",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(HelpActivity.this,RuleoneActivity.class);
                startActivity(intent);//通过intent类来另外启动一个activity
                //FirstActivity.this.finish();
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"about",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(HelpActivity.this,AboutActivity.class);
                startActivity(intent);//通过intent类来另外启动一个activity
                //FirstActivity.this.finish();
            }
        });
    }
}
