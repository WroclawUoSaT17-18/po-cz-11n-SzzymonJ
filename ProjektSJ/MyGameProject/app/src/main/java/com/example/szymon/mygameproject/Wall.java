package com.example.szymon.mygameproject; //pkaiet (tak jakby �cie�ka dost�pu)

import android.graphics.Canvas; //imorty czyli to co wykorzystujemy i jest nam ju� dane
import android.graphics.Paint;
import android.graphics.Rect;



public class Wall {

    private Rect rectangle; //parametry prostokatow i kolor
    private Rect rectangle2;
    private int color;


    public Rect getRectangle(){
        return rectangle;
    }

//przesuwanie w dol
    public void moveWall(float y){
        rectangle.top += y;
        rectangle.bottom +=y;
        rectangle2.top +=y;
        rectangle2.bottom +=y;

    }

//tworzenie sciany
    public Wall(int rectHeight, int color, int x1, int y, int xGap){
        this.color=color;
        rectangle = new Rect(0, y, x1, y+rectHeight);
        rectangle2 = new Rect(x1 + xGap, y, GameActivity.SCREEN_WIDTH, y+rectHeight);

    }

//funkcja wykrywaj�ca kolizje
    public boolean playerCollide(Player player){
     return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());
    }


    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);


    }

}
