package com.mygdx.game.editor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.leftRight.GObject;
import com.mygdx.game.leftRight.ShapeDrawable;

import java.util.LinkedList;

/**
 * Created by Alexander on 23.06.2015.
 */
public class Curve extends GObject implements ShapeDrawable{
    public LinkedList<Vector2> localPoints = new LinkedList<Vector2>();

    public boolean add(Vector2 point){
        point = point.cpy().sub(getPos());
        if(localPoints.isEmpty() || !localPoints.getLast().epsilonEquals(point, 0.01f)) {
            localPoints.add(point);
            return true;
        }else return false;

    }

    public Curve(float... xy) {
        super();
        for(int i = 0; i < xy.length / 2; i++){
            localPoints.add(new Vector2(xy[i*2], xy[i*2 + 1]));
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        float[] vertices = new float[localPoints.size()*2];
        for(int i = 0; i < localPoints.size(); i++){
            vertices[i*2] = localPoints.get(i).x + getPos().x;
            vertices[i*2 + 1] = localPoints.get(i).y + getPos().y;
        }


        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        if(vertices.length > 2)
            shapeRenderer.polyline(vertices);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    public double[] getVertex() {
        double[] vertices = new double[localPoints.size()*2];
        for(int i = 0; i < localPoints.size(); i++){
            vertices[i*2] = localPoints.get(i).x;
            vertices[i*2 + 1] = localPoints.get(i).y;
        }
        return vertices;
    }
}
