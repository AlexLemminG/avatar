package com.keepCalmAndDoItRight.basics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Alexander on 24.06.2015.
 */
public class View implements Disposable{
    Level level;
    ShapeRenderer sr;
    public OrthographicCamera camera;
    Box2DDebugRenderer box2DDebugRenderer;

    public View(Level level){
        this.level = level;
        sr = new ShapeRenderer();
        box2DDebugRenderer = new Box2DDebugRenderer(true,true,false,false,false,false);
        camera = (OrthographicCamera) level.stage.getViewport().getCamera();
        camera.position.set(0,0,0);
    }

    public void render(){
        clear();
        camera.zoom = 1f/100;

        camera.update();
        sr.setProjectionMatrix(camera.combined);
        level.stage.setDebugAll(drawDebug);
        PolygonSpriteBatch polygonSpriteBatch = ((PolygonSpriteBatch) level.stage.getBatch());
        polygonSpriteBatch.setProjectionMatrix(camera.combined);
        polygonSpriteBatch.begin();
        polygonSpriteBatch.setColor(Color.WHITE);
        polygonSpriteBatch.draw(Assets.BACKGROUND_TEXTURE, -50, -50, 100, 100, 0, 0, 1, 1);
        polygonSpriteBatch.end();
        level.stage.draw();

        if(drawDebug)
        box2DDebugRenderer.render(level.world, camera.combined);
//        ui.stage.draw();
    }

    private void clear(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void resize(float w, float h){
        Gdx.gl.glViewport(0,0,(int)w,(int)h);
        level.stage.getViewport().setScreenSize((int)w, (int)h);
        camera.viewportWidth = w;
        camera.viewportHeight = h;
    }

    @Override
    public void dispose() {
        sr.dispose();
        box2DDebugRenderer.dispose();
//        polygonSpriteBatch.dispose();
    }

    boolean drawDebug = false;
    public void drawDebug(boolean draw){
        drawDebug = draw;
    }

    public boolean getDrawDebug() {
        return drawDebug;
    }

    public void togleDebug() {
        drawDebug = !drawDebug;
    }
}
