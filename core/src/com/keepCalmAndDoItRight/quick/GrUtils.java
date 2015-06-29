package com.keepCalmAndDoItRight.quick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ShortArray;

/**
 * Created by Alexander on 27.06.2015.
 */
public class GrUtils {
    static EarClippingTriangulator triangulator = new EarClippingTriangulator();
    public static PolygonRegion createPolygonRegion(Texture texture, Polygon polygon, float scaleX, float scaleY){
        if(texture == null) return null;
        TextureRegion tr = new TextureRegion(texture);
        Polygon p = polygon;
        float origX = p.getX();
        float origY = p.getY();
        float origOX = p.getOriginX();
        float origOY = p.getOriginY();
        float origA = p.getRotation();
        float origSX = p.getScaleX();
        float origSY = p.getScaleY();

        float[] vertices = p.getTransformedVertices();
        ShortArray triangles = triangulator.computeTriangles(vertices);
        float minx = Float.MAX_VALUE;
        float maxx = -Float.MAX_VALUE;
        float miny =  Float.MAX_VALUE;
        float maxy =  -Float.MAX_VALUE;
        for(int i = 0; i < vertices.length / 2; i++){
            minx = Math.min(minx, vertices[i*2]);
            miny = Math.min(miny, vertices[i * 2 + 1]);
            maxx = Math.max(maxx, vertices[i * 2]);
            maxy = Math.max(maxy, vertices[i*2+1]);
        }
//        float width = maxx - minx;
//        float height = maxy - miny;
//        float scaleX = tr.getRegionWidth() / width;
//        float scaleY = tr.getRegionHeight() / height;
        p.setOrigin(minx, miny);
        p.setPosition(-minx, -miny);
//        p.setScale(scaleX, scaleY);
        p.setScale(scaleX, scaleY);
        PolygonRegion polygonRegion = new PolygonRegion(tr, Arrays.copy(p.getTransformedVertices()), Arrays.copy(triangles.items, triangles.size));

        p.setOrigin(origOX, origOY);
        p.setPosition(origX, origY);
        p.setScale(origSX, origSY);
        p.setRotation(origA);
        return polygonRegion;
    }
    public static PolygonRegion createPolygonRegion(Texture texture, Polygon polygon){
        if(texture == null) return null;
        TextureRegion tr = new TextureRegion(texture);
        Polygon p = polygon;
        float origX = p.getX();
        float origY = p.getY();
        float origOX = p.getOriginX();
        float origOY = p.getOriginY();
        float origA = p.getRotation();
        float origSX = p.getScaleX();
        float origSY = p.getScaleY();

        float[] vertices = p.getTransformedVertices();
        ShortArray triangles = triangulator.computeTriangles(vertices);
        Rectangle b = polygon.getBoundingRectangle();

        float minx = b.getX();
        float miny =  b.getY();

        float width = polygon.getBoundingRectangle().getWidth();
        float height = polygon.getBoundingRectangle().getHeight();
        float scaleX = tr.getRegionWidth() / width;
        float scaleY = tr.getRegionHeight() / height;
        p.setOrigin(minx, miny);
        p.setPosition(-minx, -miny);
//        if(imageScaleX == 1/0f || imageScaleY == 1/0f)
            p.setScale(scaleX, scaleY);
//        else
//            p.setScale(imageScaleX, imageScaleY);

        PolygonRegion polygonRegion = new PolygonRegion(tr, Arrays.copy(p.getTransformedVertices()), Arrays.copy(triangles.items, triangles.size));

        p.setOrigin(origOX, origOY);
        p.setPosition(origX, origY);
        p.setScale(origSX, origSY);
        p.setRotation(origA);
        return polygonRegion;
    }
}
