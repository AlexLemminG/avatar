package com.keepCalmAndDoItRight.quick;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.keepCalmAndDoItRight.GeometryUtils;


/**
 * Created by Alexander on 26.06.2015.
 */
public class ShapesCreator {
    public static CircleShape circle(float radius){
        CircleShape result = new CircleShape();
        result.setRadius(radius);
        return result;
    }

    public static PolygonShape box(float hw, float hh){
        PolygonShape result = new PolygonShape();
        result.setAsBox(hw, hh);
        return result;
    }

    public static Polygon gismo(float radius, float width){
        float w2 = width / 2;
        int n = 16;
        int n2 = n*2;
        float[] x = new float[n];
        float[] y = new float[n];

        float phi = MathUtils.PI / (n / 2-1);
        for(int i = 0; i < n/2; i++){
            x[i] = MathUtils.sin(phi * i)*radius+w2;
            y[i] = MathUtils.cos(phi * i)*radius;
        }
        for(int i = n/2; i < n; i++){
            x[i] = -x[n-i-1];
            y[i] = y[n-i-1];
        }



        float[] vertices = new float[n2];
        for(int i = 0; i < n; i++){
            vertices[i*2] = x[i];
            vertices[i*2+1]=y[i];
        }

        return new Polygon(vertices);
    }

    public static Polygon[] poligonToWallSegments(Polygon basis, float width, boolean circled){
        float r = width / 2;
        int size = basis.getVertices().length / 2;

        float x1, x2;
        Vector2 normal = new Vector2();
        Vector2 normal2 = new Vector2();
        Vector2 normalR90 = new Vector2();


        Vector2 a = new Vector2() ,b = new Vector2(), c = new Vector2(), d = new Vector2();
        Segment a1, a2;

        Vector2[] basisVertex = new Vector2[size];
        float[] vertices = basis.getTransformedVertices();
        for(int i = 0; i < size; i++){
            float x = vertices[i*2];
            float y = vertices[i*2+1];
            basisVertex[i] = new Vector2(x, y);
        }
        if(!circled)
            size--;
        Polygon[] result = new Polygon[size];


        a1 = new Segment(basisVertex[basisVertex.length - 1], basisVertex[0]);
        a2 = new Segment(basisVertex[0], basisVertex[1]);


        int k = 0;


        if(!circled){
            x1 = r; x2 = 0;
            normal = a2.getNormal();
            a = basisVertex[k].cpy().add(normal.scl(x1));
            b = basisVertex[k].cpy().sub(normal);
        }else{
            normal = a2.getNormal();
            normalR90 = normal.cpy().rotate90(1);
            normal2 = a1.getNormal();

            x1 = r;
            float t = normalR90.dot(normal2);
            if(t == 0)
                x2 = 0;
            else
                x2 = r * (1 - normal.dot(normal2)) / t;

            a = basisVertex[k].cpy().add(normal.scl(x1).add(normalR90.scl(x2)));
            b = basisVertex[k].cpy().sub(normal);
        }



        for(k = 1; k < size; k++){
            a1.set(a2);
            a2.set(basisVertex[k], basisVertex[(k + 1) % basisVertex.length]);

            normal = a2.getNormal();
            normalR90 = normal.cpy().rotate90(1);
            normal2 = a1.getNormal();

            x1 = r;
            float t = normalR90.dot(normal2);
            if(t == 0)
                x2 = 0;
            else
                x2 = r * (1 - normal.dot(normal2)) / t;

            c = basisVertex[k].cpy().add(normal.scl(x1).add(normalR90.scl(x2)));
            d = basisVertex[k].cpy().sub(normal);

            result[k - 1] = new Polygon(GeometryUtils.vector2ToFloat(a, b, d, c));
            a = c;
            b = d;

        }

        if(!circled){
            x1 = r; x2 = 0;
            normal = a2.getNormal();
            c = basisVertex[k].cpy().add(normal.scl(x1));
            d = basisVertex[k].cpy().sub(normal);
        }else{
            a1.set(a2);
            a2.set(basisVertex[0], basisVertex[1]);
            normal = a2.getNormal();
            normalR90 = normal.cpy().rotate90(1);
            normal2 = a1.getNormal();

            x1 = r;
            float t = normalR90.dot(normal2);
            if(t == 0)
                x2 = 0;
            else
                x2 = r * (1 - normal.dot(normal2)) / t;

            c = basisVertex[0].cpy().add(normal.scl(x1).add(normalR90.scl(x2)));
            d = basisVertex[0].cpy().sub(normal);
        }
        result[k - 1] = new Polygon(GeometryUtils.vector2ToFloat(a, b, d, c));

        return result;
    }

}
