package com.keepCalmAndDoItRight.basics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Scaling;

/**
 * Created by Alexander on 26.06.2015.
 */
public class ActorWithPolygonRegion extends Actor{

    private PolygonRegion polygonRegion;

    public ActorWithPolygonRegion(PolygonRegion polygonRegion) {
        super();
        this.polygonRegion = polygonRegion;
        minW = polygonRegion.getRegion().getRegionWidth();
        minH = polygonRegion.getRegion().getRegionHeight();
        minW = 100;
        minH = 100;
        float[] vertices = polygonRegion.getVertices();
        float[] tex = polygonRegion.getTextureCoords();
        float minX = tex[0];
        float maxX = tex[0];
        float minY = tex[1];
        float maxY = tex[1];
        for(int i = 0; i < vertices.length / 2; i++){
            minW = Math.max(minW, vertices[i*2]);
            minH = Math.max(minH, vertices[i * 2 + 1]);
            minX = Math.min(minX, tex[i*2]);
            minY = Math.min(minY, tex[i*2+1]);
            maxX = Math.max(maxX, tex[i * 2]);
            maxY = Math.max(maxY, tex[i * 2 + 1]);
        }

        imageScaleX = 1f / Math.abs(minX - maxX);
        imageScaleY = 1f / Math.abs(minY - maxY);

    }

    float minH;
    float minW;
    float imageScaleX;
    float imageScaleY;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        PolygonSpriteBatch b = (PolygonSpriteBatch) batch;
        Color color = getColor();
        if(polygonRegion != null){
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

            float x = getX();
            float y = getY();
            float scaleX = getScaleX();
            float scaleY = getScaleY();

            float rotation = getRotation();
            Scaling scaling = Scaling.stretch;
            float width = getWidth();
            float height = getHeight();
            Vector2 size = scaling.apply(minW, minH, width, height);
            float imageWidth = size.x;
            float imageHeight = size.y;
//            imageHeight = 1;
//            imageWidth = 1;
//            scaleX = 1;
//            scaleY = 1;

            float originX = getOriginX();
            float originY = getOriginY();
//            x = 0;
//            y = 0;
//            originX = 0;
//            originY = 0;

            b.draw(polygonRegion, x, y, originX, originY, imageWidth * imageScaleX, imageHeight * imageScaleY, scaleX, scaleY, rotation);
            b.flush();
//                    ((TransformDrawable)drawable).draw(batch, x + imageX, y + imageY, getOriginX() - imageX, getOriginY() - imageY,
//                            imageWidth, imageHeight, scaleX, scaleY, rotation);
        }
        super.draw(batch, parentAlpha * color.a);

    }
}
