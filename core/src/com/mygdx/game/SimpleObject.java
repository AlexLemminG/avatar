package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Alexander on 07.06.2015.
 */
public class SimpleObject implements Updatable, Disposable{
    private final short[] triangles;
    private final float[] vertices;
    PolygonSprite sprite;
    Texture texture;
    Pixmap pixmap;
    TextureRegion textureRegion;
    PolygonRegion polygonRegion;

    public SimpleObject(float[] vertices) {
        pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0xDEADBEEF);

        int red = (int)(Math.random() * 0x7F + 0x80);
        int green = (int)(Math.random() * 0x7F + 0x80);
        int blue = (int)(Math.random() * 0x7F + 0x80);
        int alpha = (int)(0x80);

        int color = alpha | blue << 8 | green << 16 | red << 24;
        pixmap.setColor(color);

        pixmap.fill();
        texture = new Texture(pixmap);
        textureRegion = new TextureRegion(texture);
        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        short[] triangles = triangulator.computeTriangles(vertices).toArray();
        polygonRegion = new PolygonRegion(textureRegion, vertices, triangles);
        sprite = new PolygonSprite(polygonRegion);
        this.vertices = vertices;
        this.triangles = triangles;
    }

    public void draw(PolygonSpriteBatch batch){
        sprite.draw(batch);
    }

    public void dispose(){
        texture.dispose();
        pixmap.dispose();
    }

    public void update(float dt) {
        sprite.rotate(0);
    }

    public void setPosition(float x, float y){
        sprite.setPosition(x, y);
    }
}
