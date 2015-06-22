package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Alexander on 20.06.2015.
 */
public class Graph2 extends GObject implements Updatable, ShapeDrawable{
    int n;
    PhysPoint[] points;
    float[] x;
    float[] y;
    float[] dy;
    float[] dy2;
    float a;
    float b;

    public Graph2(float a, float b) {
        a = -((float) Math.PI);
        b = -a;
        this.a = a;
        this.b = b;
        n = 10;
        x = new float[n];
        y = new float[n];
        dy = new float[n];
        dy2 = new float[n];
        points = new PhysPoint[n];

        for(int i = 0; i < n; i++){
            float t = 1f * i / (n-1);
            x[i] = b * t + a * (1-t);
            y[i] = 2-(float)Math.sin(x[i]); //+ LeftRight.instance.time);
            points[i] = new PhysPoint(x[i],y[i], LeftRight.instance.world);
        }

    }



    @Override
    public void render(ShapeRenderer shapeRenderer) {
        for (int i = 0; i < n; i++) {
            y[i] = points[i].getPosition().y;
        }
        shapeRenderer.setColor(Color.BLACK);
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
    }

    int t = 0;
    @Override
    public void update(float dt) {
        int steps = 100;
        for (int i = 0; i < n; i++) {
            y[i] = points[i].getPosition().y;
        }
        for(int i2 = 0; i2 < steps; i2++){
            for (int i = 0; i < n; i++) {
                dy2[i] = 10*dydx2(i) - 0.2f*dy[i];
//                System.out.println((y[i] - 3) + " " + (dy[i]));

//            dy[i] = ((float) Math.cos(x[i]));
//            points[i].setVelocity(0, dy[i]);
            }
            if (t++ > 0)
                for (int i = 0; i < n; i++) {
                    dy[i] += dt / steps * dy2[i];
                    y[i]+=dt/steps*dy[i];
//            y[i] = dydx2(i);
//            points[i].body.applyForceToCenter(0, -y[i]*points[i].body.getMass(),true);
                }
            y[0] = 2;
            y[n - 1] = 2;

        }
        for (int i = 0; i < n; i++) {
            points[i].setVelocity(0, 0/dt);
            points[i].setPosition(x[i], y[i]);
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
