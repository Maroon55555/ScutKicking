package cn.user0308.scutkicking.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import cn.user0308.scutkicking.R;

public class GameOverActivity extends BaseActivity {
    private TextView returnGame;
    private TextView quitGame;

    public static void startGameOverActivity(Context context){
        Intent intent = new Intent(context, GameOverActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//禁用返回键
        if (keyCode == event.getKeyCode())
            return true;
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        returnGame = (TextView) findViewById(R.id.return_game);
        quitGame = (TextView) findViewById(R.id.quit_game);

        returnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenManager.getScreenManager().popAllActivityExceptOne(FirstActivity.class);
            }
        });
        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenManager.getScreenManager().quit();
            }
        });
    }
}
