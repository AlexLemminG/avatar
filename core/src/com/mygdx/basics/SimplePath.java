package com.mygdx.basics;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Alexander on 29.06.2015.
 */
public class SimplePath implements Path<Vector2> {
    boolean oneDot = false;
    Vector2 dot;
    public SimplePath(Array<Vector2> p) {
        if(p.size == 1) {
            oneDot = true;
            dot = p.first();
            length = 1;
        }
        points = new Vector2[p.size];
        lengths = new float[p.size-1];
        for(int i = 0; i < p.size; i++){
            points[i] = p.get(i);
        }
        for(int i = 0; i < p.size-1; i++){
            lengths[i] = points[i].dst(points[i+1]);
            length += lengths[i];
        }
    }

    public Vector2[] getPoints() {
        return points;
    }

    Vector2[] points;
    float[] lengths;
    float length = 0;

    @Override
    public Vector2 derivativeAt(Vector2 out, float t) {
        return null;
    }

    @Override
    public Vector2 valueAt(Vector2 out, float t) {
        if(oneDot) return out.set(dot);
        t *= length;
        int i = 0;
        while(i < lengths.length && t > lengths[i])
            t -= lengths[i++];
        i = Math.min(i, lengths.length-1);
        t /= lengths[i];
        out.set((1-t)*points[i].x + t * points[i+1].x, (1-t)*points[i].y + t * points[i+1].y);
        return out;
    }

    @Override
    public float approximate(Vector2 v) {
        return 0;
    }

    @Override
    public float locate(Vector2 v) {
        return 0;
    }

    @Override
    public float approxLength(int samples) {
        return length;
    }

    public float[] getVertices() {
        float[] result = new float[points.length * 2];
        for(int i = 0; i < points.length; i++){
            result[i * 2] = points[i].x;
            result[i * 2 + 1] = points[i].y;
        }
        return result;
    }
}
