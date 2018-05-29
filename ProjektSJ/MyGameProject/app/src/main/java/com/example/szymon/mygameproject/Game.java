package com.example.szymon.mygameproject;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private Player player;
    private Point playerPoint;
    private WallsManager wallsManager;
    private boolean movement = false;
    private boolean gameOver = false;

    private OrientationData orientationData;
    private long frameTime;

    public static Context CURRENT_CONTEXT;
    public static long INIT_TIME;

    public Game(Context context){
        super(context);

        getHolder().addCallback(this);

        CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        setFocusable(true);


        player = new Player(new Rect(100, 100, 200, 200));
        playerPoint = new Point(GameActivity.SCREEN_WIDTH/2, 3*GameActivity.SCREEN_HIGHT/4);
        player.update(playerPoint);

        wallsManager = new WallsManager(((int)(300*(1-GameActivity.DIFFICULTY*0.1))), 350, 75, Color.BLACK);


        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int hight) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
            }catch(Exception e) {e.printStackTrace(); retry=false;}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(), (int)event.getY()))
                    movement =true;
                if(gameOver){

                    reset();
                    gameOver=false;
                    orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver&& movement && GameActivity.CONTROL_TYPE!=0)
                    playerPoint.set((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movement =false;
                break;
        }
return true;

    }

    public void update(){

        if(!gameOver){
            if(frameTime< Game.INIT_TIME)
                frameTime = Game.INIT_TIME;
            int elapseTime = (int)(System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            if(GameActivity.CONTROL_TYPE !=1) {
                if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                    float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                    float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                    float xSpeed = 2 * roll * GameActivity.SCREEN_WIDTH / 1000f;
                    float ySpeed = pitch * GameActivity.SCREEN_HIGHT / 1000f;

                    playerPoint.x += Math.abs(xSpeed * elapseTime) > 5 ? xSpeed * elapseTime : 0;
                    playerPoint.y -= Math.abs(ySpeed * elapseTime) > 5 ? ySpeed * elapseTime : 0;
                }
            }

            if(playerPoint.x <0)
                playerPoint.x = 0;
            else if (playerPoint.x > GameActivity.SCREEN_WIDTH)
                playerPoint.x = GameActivity.SCREEN_WIDTH;
            if(playerPoint.y <0)
                playerPoint.y = 0;
            else if (playerPoint.y > GameActivity.SCREEN_HIGHT)
                playerPoint.y = GameActivity.SCREEN_HIGHT;

            player.update(playerPoint);
            wallsManager.update();
            if(wallsManager.playerCollide(player)){
                gameOver=true;
            }
        }

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        wallsManager.draw(canvas);

        if(gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            canvas.drawText("Game Over", 50, 50 + paint.descent() - paint.ascent(), paint);
            canvas.drawText("Score: " + WallsManager.SCORE, 50, 200 + paint.descent() - paint.ascent(), paint);
         }
    }

    public void reset(){
        playerPoint = new Point(GameActivity.SCREEN_WIDTH/2, 3*GameActivity.SCREEN_HIGHT/4);
        player.update(playerPoint);
        wallsManager = new WallsManager(((int)(300*(1-GameActivity.DIFFICULTY*0.1))), 350, 75, Color.BLACK);
        movement =false;
        WallsManager.SCORE=0;
    }

}
