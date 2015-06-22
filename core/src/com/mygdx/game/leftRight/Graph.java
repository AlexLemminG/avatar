package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Alexander on 20.06.2015.
 */
public class Graph extends GObject implements Updatable, ShapeDrawable{
    int n;
    PhysPoint[] points;
    float[] x;
    float[] y;
    float[] dy;
    float[] etta;
    float[] dEtta;
    float a;
    float b;

    public Graph(float a, float b) {
        a = -((float) Math.PI)*10;
        b = -a;
        this.a = a;
        this.b = b;
        n = 30;
        x = new float[n];
        y = new float[n];
        dy = new float[n];
        etta = new float[n];
        dEtta = new float[n];
        points = new PhysPoint[n];

        for(int i = 0; i < n; i++){
            float t = 1f * i / (n-1);
            x[i] = b * t + a * (1-t);
            y[i] = 2-(float)Math.cos(x[i]); //+ LeftRight.instance.time);
//            etta[i] = -2 + y[i];
            etta[i] = 1 / 10f + ((float) Math.cos(x[i]));
            etta[i]=0;
            y[i]=etta[i];
            points[i] = new PhysPoint(x[i],y[i], LeftRight.instance.world);
            y[i]=0;
//            etta[i] = ((float) Math.sin(x[i]));
        }

    }



    @Override
    public void render(ShapeRenderer shapeRenderer) {
//        for (int i = 0; i < n; i++) {
//            y[i] = points[i].getPosition().y;
//        }
        shapeRenderer.setColor(Color.BLACK);
        float[] temp = y;
        y = etta;
        etta = temp;


        float[] vertices = new float[n/1 * 2];
        float[] vertices2 = new float[n/1 * 2];
        for(int i = 0; i < n/1; i++){
            vertices[i*2] = x[i*1];
            vertices[i*2+1] = y[i*1];
            vertices2[i*2] = x[i*1];
            vertices2[i*2+1] = dy[i*1]+2;
        }
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.polyline(vertices);
//        shapeRenderer.polyline(vertices2);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        temp = y;
        y = etta;
        etta = temp;
    }

    int t = 0;
    @Override
    public void update(float dt) {
        int steps = 30;
        for (int i = 0; i < n; i++) {
            etta[i] = points[i].getPosition().y;
        }
        float g = 10f;
        float H = 1f;
        float bb = 1.1f;
        dt/=steps;
        for(int j = 0; j < steps; j++){
            for(int i = 0; i < n; i++){
                float dudt = -g * dedx(i) - bb * y[i];
                y[i] +=(dudt)*dt;
            }
            for(int i = 0; i < n; i++)
                dEtta[i] = -H*(dydx(i));

            for(int i = 0; i < n; i++){
        //            y[i]+=dy[i]*dt;
                etta[i]+=dEtta[i]*dt;
            }
            y[0] = 0.5f;
            y[n-1]= 0.5f;
        }

        for (int i = 0; i < n; i++) {
            points[i].setVelocity(0, 0 / dt);
            points[i].setVelocity(0, 0);
            points[i].setPosition(x[i], etta[i]);
        }
    }


    private float dydx(int i){
        float dx = (b-a) / (n-1);
        float result = 0;
        if(i == 0){
            result = (-3*y[i] + 4*y[i+1] - y[i+2])/dx/2;
        }else{
            if(i==n-1){
                result =  (3*y[i] - 4*y[i-1] + y[i-2])/dx/2;
            }else{
                result = (y[i+1]-y[i-1])/dx/2;
            }
        }
        return result;
    }

    private float dedx(int i){
        float[] temp = y;
        y = etta;
        etta = temp;
        float dx = (b-a) / (n-1);
        float result = 0;
        if(i == 0){
            result = (-3*y[i] + 4*y[i+1] - y[i+2])/dx/2;
        }else{
            if(i==n-1){
                result =  (3*y[i] - 4*y[i-1] + y[i-2])/dx/2;
            }else{
                result = (y[i+1]-y[i-1])/dx/2;
            }
        }

        temp = y;
        y = etta;
        etta = temp;

        return result;
    }

    private float dydx2(int i){
        float dx = (b-a) / (n-1);
        float result = 0;
        if(i == 0){
            result = (2 * y[i] - 5 * y[i+1] + 4 * y[i+2] - y[i+3])/dx/dx;
//            result = 0;
        }else{
            if(i==n-1){
                result = (2 * y[i] - 5 * y[i-1] + 4 * y[i-2] - y[i-3])/dx/dx;
//                result = 0;
            }else{
                result = (y[i+1] - 2*y[i] + y[i-1])/dx/dx;
            }
        }
        return result;
    }
}
