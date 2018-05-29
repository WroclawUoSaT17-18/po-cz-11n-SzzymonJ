package com.example.szymon.mygameproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;


public class Player {

//pola klasy

    private Rect rectangle;

    private Animation stand;
    private Animation dirR;
    private Animation dirL;
    private AnimationManager animationManager;

    public Rect getRectangle(){
        return rectangle;
    }


//konstruktor

    public Player(Rect rectangle){
        this.rectangle=rectangle;


//tworzenie bitmap do animacji
        BitmapFactory bf = new BitmapFactory();
        Bitmap static1 =bf.decodeResource(Game.CURRENT_CONTEXT.getResources(), R.drawable.bat);
        Bitmap static2 =bf.decodeResource(Game.CURRENT_CONTEXT.getResources(), R.drawable.bat_fly);
        Bitmap mov1 =bf.decodeResource(Game.CURRENT_CONTEXT.getResources(), R.drawable.bat_fly);
        Bitmap mov2 =bf.decodeResource(Game.CURRENT_CONTEXT.getResources(), R.drawable.bat);


//tworzenie animacji wykorzystuj�c utworzone bitmany
        stand = new Animation(new Bitmap[]{static1, static2},0.5f);
        dirR = new Animation(new Bitmap[]{mov1,mov2},0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1); //do odwracania bitmapy
        mov1 = Bitmap.createBitmap(mov1, 0, 0, mov1.getWidth(), mov1.getHeight(), m, false);
        mov2 = Bitmap.createBitmap(mov2, 0, 0, mov2.getWidth(), mov2.getHeight(), m, false);
        dirL = new Animation(new Bitmap[]{mov1,mov2},0.5f); //tworzenie animacji
        animationManager = new AnimationManager(new Animation[]{stand, dirL, dirR});

    }

    public void draw(Canvas canvas) {
        animationManager.draw(canvas,rectangle);
    }


    public void update(Point point){
       float prevL = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2,point.y + rectangle.height()/2);

        int dir = 0; //przypadek kiedy stoimy (domy�lny)
        if(rectangle.left - prevL > 10) //je�eku przesuwamy si� w lewo
            dir = 1; //uruchomienie animacji 1 (patrz indx animationManager )
        else if (rectangle.left - prevL < -10)//je�eli przesuwamy si� w prawo
            dir=2;

        animationManager.playAnim(dir);
        animationManager.update();
    }
}

