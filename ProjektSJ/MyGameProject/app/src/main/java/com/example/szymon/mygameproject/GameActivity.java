package com.example.szymon.mygameproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HIGHT;
    public static int CONTROL_TYPE;
    public static int DIFFICULTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HIGHT = dm.heightPixels;

        Bundle bundle = getIntent().getExtras();
        String Difficulty = bundle.getString("Difficulty");
        DIFFICULTY = Integer.parseInt(Difficulty);

        String control = bundle.getString("Control");
        if (control.contains("Position"))
            CONTROL_TYPE = 0;
        else if (control.contains("Touch"))
            CONTROL_TYPE = 1;
        else CONTROL_TYPE = 2;


        setContentView(new Game(this));


    }

}
