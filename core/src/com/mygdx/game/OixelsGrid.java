package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alexander on 08.06.2015.
 */
public class OixelsGrid {
    public Random random = new Random();
    int minx, maxx, width;
    int miny, maxy, height;
    Cell[][] grid;
    LinkedList<Oixel> oixels = new LinkedList<Oixel>();

    private void generateRandomOixels(double percent){
        for(int i = minx+1; i <= maxx-1; i++)
            for(int j = miny+1; j <= maxy-1; j++) {
                if(Math.random() > percent){
                    continue;
                }
                Oixel oixel = new Oixel(i, j, this);
                oixel.pX = 10;
                oixels.add(oixel);
            }
    }
    private void generateCells(){
        for(int i = minx; i <= maxx; i++)
            for(int j = miny; j <= maxy; j++) {
                grid[i - minx][j - miny] = new Cell(i, j);
            }
    }
    private void generateBorder(){
        for(int x = minx; x <= maxx; x++){
            Border oixel = new Border(x, miny, this);
            Border oixel1 = new Border(x, maxy, this);
            oixels.add(oixel);
            oixels.add(oixel1);

            getCell(x, miny).add(oixel);
            getCell(x, maxy).add(oixel1);
        }
        for(int y = miny + 1; y <= maxy - 1; y++){
            Border oixel = new Border(minx, y, this);
            Border oixel1 = new Border(maxx, y, this);
            oixels.add(oixel);
            oixels.add(oixel1);

            getCell(minx, y).add(oixel);
            getCell(maxx, y).add(oixel1);
        }

    }

    public OixelsGrid(int minx, int maxx, int miny, int maxy) {
        this.minx = minx;
        this.maxx = maxx;
        width = maxx - minx + 1;
        this.miny = miny;
        this.maxy = maxy;
        height = maxy - miny + 1;
        grid = new Cell[width][height];

        generateCells();
        generateBorder();

        generateRandomOixels(0.99);
//        for(int j = -9; j <= 9; j++)
//        for(int i = miny; i <= maxy; i+=1) {
//            if(Math.random() > 0.9)
//                continue;
//            Oixel oixel = new Oixel(j, i, this);
//            oixel.pY = 0;
//            oixels.add(oixel);
//        }

//        Oixel oixel2 = new Oixel(0, -8, this);
//        oixel2.pY = 0;
//        oixels.add(oixel2);

        swapBuffers();
    }

    public void update(float dt){
//        dt = 1f / 60;
//        float sumSpeed = 0f;

        for(Oixel oixel : oixels){
            oixel.step();
        }
        swapBuffers();
        updateP();



        int sumSpeedX = 0;
        int sumSpeedY = 0;
        for(Oixel oixel : oixels) {
            sumSpeedX += Math.abs(oixel.pX);
            sumSpeedY += Math.abs(oixel.pY);
        }
        GameData.map.put("sumSpeedX", String.valueOf(sumSpeedX));
        GameData.map.put("sumSpeedY", String.valueOf(sumSpeedY));
    }

    public void draw(ShapeRenderer sr){
        for(Oixel o : oixels){
            o.draw(sr);
        }
    }

    public boolean put(Oixel oixel) {
        return getCell(oixel.x, oixel.y).add(oixel);
    }

    public void remove(Oixel oixel) {
        getCell(oixel.x, oixel.y).remove(oixel);
    }

    private void swapBuffers(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++)
                grid[i][j].swapBuffers();
        }
    }

    public Cell getCell(int x, int y){
        return x >= minx && x <= maxx && y >= miny && y <= maxy ? grid[(x-minx)][(y - miny)] : null;
    }

    private int[] x1,x2,x3,x4,x5,x6,x7,x8;
    private int[] y1,y2,y3,y4,y5,y6,y7,y8;
    {
        x1 = new int[]{0, -1, 1, -1, 1, -1, 1, 0};
        y1 = new int[]{1, 1, 1, 0, 0, -1, -1, -1};
        x2 = new int[]{1, 0, 1, -1, 1, -1, 0, -1};
        y2 = new int[]{1, 1, 0, 1, -1, 0, -1, -1};
        x3 = new int[]{1, 1, 1, 0, 0, -1, -1, -1};
        y3 = new int[]{0, 1, -1, 1, -1, 1, -1, 0};
        x4 = new int[]{1, 1, 0, 1, -1, 0, -1, -1};
        y4 = new int[]{-1, 0, -1, 1, -1, 1, 0, 1};
        x5 = new int[]{0, -1, 1, -1, 1, -1, 1, 0};
        y5 = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        x6 = new int[]{-1, -1, 0, -1, 1, 0, 1, 1};
        y6 = new int[]{-1, 0, -1, 1, -1, 1, 0, 1};
        x7 = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        y7 = new int[]{0, 1, -1, 1, -1, 1, -1, 0};
        x8 = new int[]{-1, -1, 0, -1, 1, 0, 1, 0};
        y8 = new int[]{1, 0, 1, -1, 1, -1, 0, -1};
    }
    static List<Cell> toCheck = new ArrayList<Cell>();
    public void putToClosest(Oixel oixel) {
        toCheck.clear();
        toCheck.add(getCell(oixel.x, oixel.y));
        int[] dxCheck = null;
        int[] dyCheck = null;
        int initX = oixel.x;
        int initY = oixel.y;
        int xx = 0;
        int yy = 0;

        if(Math.abs(oixel.phaseX) > Oixel.halfPhaseX / 2){
            xx = oixel.phaseX > 0 ? 1 : -1;
        }
        if(Math.abs(oixel.phaseY) > Oixel.halfPhaseX / 2){
            yy = oixel.phaseY > 0 ? 1 : -1;
        }
//        xx = -xx;
//        yy = -yy;
        if(xx == 0 && yy == 0){
            yy = 1;
        }
        /*
        0 0 0
        0 . 0
        0 0 0

         */
        if(xx == 0 && yy == 1){
            dxCheck = x1;
            dyCheck = y1;
        }
        if(xx == 1 && yy == 1){
            dxCheck = x2;
            dyCheck = y2;
        }
        if(xx == 1 && yy == 0){
            dxCheck = x3;
            dyCheck = y3;
        }
        if(xx == 1 && yy == -1){
            dxCheck = x4;
            dyCheck = y4;
        }
        if(xx == 0 && yy == -1){
            dxCheck = x5;
            dyCheck = y5;
        }
        if(xx == -1 && yy == -1){
            dxCheck = x6;
            dyCheck = y6;
        }
        if(xx == -1 && yy == 0){
            dxCheck = x7;
            dyCheck = y7;
        }
        if(xx == -1 && yy == 1){
            dxCheck = x8;
            dyCheck = y8;
        }




        int i = 0;
        while (toCheck.size() > i) {
            Cell current = toCheck.get(i++);
            if(current.isBufferEmpty()){
                oixel.x = current.x;
                oixel.y = current.y;
                break;
            }
            for(int j = 0; j < 8; j++){
                int x = current.x + dxCheck[j];
                int y = current.y + dyCheck[j];
                Cell temp = getCell(x, y);
                if(temp != null && !temp.inChecking) {
                    temp.inChecking = true;
                    toCheck.add(temp);
                }
            }
        }
        for(int j = 0; j < toCheck.size(); j++)
            toCheck.get(j).inChecking = false;
//        for(Cell c : toCheck)
//            c.inChecking = false;

//        oixel.pX += (oixel.x - initX) * Oixel.halfPhaseX * 2;
//        oixel.pY += (oixel.y - initY) * Oixel.halfPhaseY * 2;
//        oixel.pX = 0;
//        oixel.pY = 0;
        put(oixel);
    }

    public void updateP(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(grid[i][j].current != null)
                    grid[i][j].current.checkP();
            }
            for(int j = height - 1; j >= 0; j--){
                if(grid[i][j].current != null)
                    grid[i][j].current.checkP();
            }
        }
        for(int j = 0; j < height; j++){
            for(int i = 0; i < width; i++){
                if(grid[i][j].current != null)
                    grid[i][j].current.checkP();
            }
            for(int i = width - 1; i >= 0; i--){
                if(grid[i][j].current != null)
                    grid[i][j].current.checkP();
            }
        }
//        for(Oixel o : oixels){
//            o.checkP();
//        }
    }

    public static class Cell{
        boolean inChecking = false;
//        private LinkedList<Oixel> current = new LinkedList<Oixel>();
//        private LinkedList<Oixel> buffer = new LinkedList<Oixel>();
        Oixel current = null;
        Oixel buffer = null;
        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        final int x, y;

        public boolean add(Oixel oixel){
//            if(buffer.size() == 1)
//                return false;
//            buffer.add(oixel);
//            return true;
            if(buffer != null)
                return false;
            else{
                buffer = oixel;
                return true;
            }

        }

        public void remove(Oixel oixel){
//            current.remove(oixel);
            current = null;
        }

        static Oixel temp;
        public void swapBuffers(){
//            LinkedList<Oixel> temp = current;
//            current = buffer;
//            buffer = temp;
            temp = buffer;
            buffer = current;
            current = temp;
        }

        public Oixel getFirstCurrent() {
//            return current.getFirst();
            return current;
        }

        public boolean isBufferEmpty() {
//            return buffer.isEmpty();
            return buffer == null;
        }
    }

    private static class Border extends Oixel{

        public Border(int x, int y, OixelsGrid grid) {
            super(x, y, grid);
            color = Color.WHITE;
        }

        @Override
        public void step() {
            grid.put(this);
        }

        @Override
        public void checkP() {

        }
    }
}
