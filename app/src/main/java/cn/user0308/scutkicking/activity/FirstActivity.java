package cn.user0308.scutkicking.activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.user0308.scutkicking.activity.SearchDeviceActivity;
import cn.user0308.scutkicking.R;


public class FirstActivity extends BaseActivity {
    public static void startFirstAcitivity(Context context){
        Intent intent = new Intent(context, FirstActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        context.startActivity(intent);
    }

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
        setContentView(R.layout.activity_first);
        ImageButton startGameButton=(ImageButton)findViewById(R.id.startGame);
        ImageButton helpButton=(ImageButton)findViewById(R.id.help);


        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"start game",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//                intent.setClass(FirstActivity.this,SearchDeviceActivity.class);
//                startActivity(intent);//通过intent类来另外启动一个activity
                MainActivity.startMainAcitivity(context);
            }
        });



        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"help press ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(FirstActivity.this,HelpActivity.class);
                startActivity(intent);
                //FirstActivity.this.finish();
            }
        });


    }

}
