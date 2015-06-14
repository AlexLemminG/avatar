package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.NoSuchElementException;

/**
 * Created by Alexander on 08.06.2015.
 */
public class Oixel {
    //sdfasdfas
    static final int halfPhaseX = 100;
    static final int halfPhaseY = 100;
    public OixelsGrid grid;
    int x, y;
    int pX, pY;
    int phaseX, phaseY;
    Color color;

    int dx;
    int dy;

    public Oixel(int x, int y, OixelsGrid grid){
        this.x = x;
        this.y = y;
        pX = 0;
        pY = 0;
        phaseX = 0;
        phaseY = 0;
        this.grid = grid;
        grid.put(this);
        color = Utils.getRandomColor();
    }

    public Oixel(int x, int y, OixelsGrid grid, Color color){
        this(x, y, grid);
        this.color = color;
    }

    public void draw(ShapeRenderer sr){
        sr.setColor(color);
        sr.rect(x,y, 1, 1);
    }

    public void step(){
        int t = (int)(System.currentTimeMillis() % 1000);

//        pY -= (int)(100 * Math.sin(t / 1000.0 * 2 * Math.PI));
//        pX -= (int)(100 * Math.cos(t / 1000.0 * 2 * Math.PI));

        grid.remove(this);
        phaseX += pX;
        phaseY += pY;
        dx = phaseX / halfPhaseX;
        dy = phaseY / halfPhaseY;
        dx = Math.min(1, Math.max(-1,dx));
        dy = Math.min(1, Math.max(-1,dy));
        x += dx;
        y += dy;
        phaseX %= halfPhaseX;
        phaseY %= halfPhaseY;
//        phaseX -= (halfPhaseX - 1) * dx;
//        phaseY -= (halfPhaseY - 1) * dy;
        if(!grid.put(this)){
            x -= dx;
            y -= dy;
            grid.putToClosest(this);
        }
        pY -= 1;
    }


    public void checkP(){
        int dx = Utils.sign(pX);
        int dy = Utils.sign(pY);
        Oixel xO = null;
        try {
            xO = grid.getCell(x + dx, y).getFirstCurrent();
        }catch (NoSuchElementException ignored){}
        Oixel yO = null;
        try{
            yO = grid.getCell(x, y + dy).getFirstCurrent();
        }catch (NoSuchElementException ignored){}
        Oixel d = null;
        try{
            d = grid.getCell(x + dx, y + dy).getFirstCurrent();
        }catch (NoSuchElementException ignored){}

        if(xO != null)
            if(Utils.sign(xO.pX) == Utils.sign(pX))
                pX = Utils.minAbs(pX, xO.pX);
            else
                pX = 0;
        if(yO != null)
            if(Utils.sign(yO.pY) == Utils.sign(pY))
                pY = Utils.minAbs(pY, yO.pY);
            else
                pY = 0;
        if(d != null && xO == null && yO == null){
            if(Utils.sign(d.pX) == Utils.sign(pX))
                pX = Utils.minAbs(pX, d.pX);
            else
                pX = 0;
            if(Utils.sign(d.pY) == Utils.sign(pY))
                pY = Utils.minAbs(pY, d.pY);
            else
                pY = 0;
        }
    }
}
