package com.keepCalmAndDoItRight;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Alexander on 26.06.2015.
 */
public class GeometryUtils {
    public static PolygonShape polygonToBox2dPolygon(Polygon polygon){
        PolygonShape result = new PolygonShape();
        result.set(polygon.getTransformedVertices());
        return result;
    }

    public static Polygon boxPolygon(float hx, float hy){
        Polygon p = new Polygon();
        p.setVertices(new float[]{
                -hx, -hy,
                -hx, hy,
                hx, hy,
                hx, -hy
        });
        return p;
    }

    public static float[] vector2ToFloat(Vector2... vertices){
        float[] v = new float[vertices.length * 2];
        for(int i = 0; i < vertices.length; i++){
            v[i*2] = vertices[i].x;
            v[i*2 + 1] = vertices[i].y;
        }
        return v;
    }
}
