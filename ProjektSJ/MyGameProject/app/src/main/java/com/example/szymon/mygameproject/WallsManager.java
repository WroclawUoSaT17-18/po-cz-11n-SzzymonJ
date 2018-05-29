package com.example.szymon.mygameproject;

import android.graphics.Canvas;
import java.util.ArrayList;

public class WallsManager {

    private ArrayList<Wall> walls;
    private int xGap;
    private int yGap;
    private int wallHeight;
    private int color;

    private long startTime;
    private long initTime;

    public static int SCORE=0;

    public WallsManager(int xGap, int yGap, int wallHeight, int color) {
        this.xGap = xGap;
        this.yGap = yGap;
        this.wallHeight = wallHeight;
        this.color=color;

        startTime= initTime = System.currentTimeMillis();

        walls = new ArrayList<>();

        makeWalls();
    }

    public boolean playerCollide(Player player){
        for (Wall ob: walls){
            if (ob.playerCollide(player))
                return true;
        }
        return false;
    }

//tworzenie scian
    private void makeWalls(){
        int currY = -5*GameActivity.SCREEN_HIGHT/4;
        while (currY < 0){
            int xStart = (int)(Math.random()*(GameActivity.SCREEN_WIDTH- xGap));
            walls.add(new Wall(wallHeight, color, xStart,currY, xGap));
            currY += wallHeight + yGap;

        }
    }

    public void update(){
        if(startTime< Game.INIT_TIME)
            startTime= Game.INIT_TIME;
        int elapsedTime = (int)(System.currentTimeMillis()-startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)((1+GameActivity.DIFFICULTY*0.1)*(Math.sqrt(1 + (startTime-initTime)/2000.0))*GameActivity.SCREEN_HIGHT/10000.0f);
        for (Wall ob: walls){
            ob.moveWall(speed * elapsedTime);
        }
        if (walls.get(walls.size() - 1).getRectangle().top >= GameActivity.SCREEN_HIGHT){
            int xStart = (int)(Math.random()*(GameActivity.SCREEN_WIDTH- xGap));
            walls.add(0, new Wall(wallHeight, color, xStart, walls.get(0).getRectangle().top - wallHeight - yGap, xGap));
            walls.remove(walls.size()-1);
            SCORE++;
        }
    }

    public void draw(Canvas canvas){
        for (Wall ob: walls)
            ob.draw(canvas);
    }


}
