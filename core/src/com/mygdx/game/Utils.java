package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Alexander on 13.06.2015.
 */
public class Utils {
    public static Color getRandomColor(){
        float r = (float)Math.random() / 2 + .5f;
        float g = (float)Math.random() / 2 + .5f;
        float b = (float)Math.random() / 2 + .5f;
        return new Color(r, g, b, 1);
    }

    public static int mod(int a, int b){
        return (a % b + b) % b;
    }

    public static int sign(int a){
        return a > 0 ? 1 : (a < 0 ? -1 : 0);
    }

    public static int minAbs(int a, int b){
        return Math.abs(a) <= Math.abs(b) ? a : b;
    }

    public static double clip(double x, double a, double b){
        return Math.min(Math.max(x, a), b);
    }

    public static boolean isInside(double x, double a, double b){
        return x >= a && x <= b;
    }
}
