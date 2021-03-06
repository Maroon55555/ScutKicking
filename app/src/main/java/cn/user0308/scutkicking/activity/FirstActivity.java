package cn.user0308.scutkicking.activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.user0308.scutkicking.activity.SearchDeviceActivity;
import cn.user0308.scutkicking.R;


public class FirstActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_first);

        Button startGameButton = (Button) findViewById(R.id.start);
        Button settingButton = (Button) findViewById(R.id.setting);
        Button helpButton = (Button) findViewById(R.id.help);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"start game",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(FirstActivity.this,SearchDeviceActivity.class);
                startActivity(intent);
                FirstActivity.this.finish();
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"setting game",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(FirstActivity.this,SettingActivity.class);
                startActivity(intent);
                FirstActivity.this.finish();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"help press ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(FirstActivity.this,HelpActivity.class);
                startActivity(intent);
                FirstActivity.this.finish();
            }
        });
    }
}
