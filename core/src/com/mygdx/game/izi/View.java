package com.mygdx.game.izi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Alexander on 24.06.2015.
 */
public class View implements Disposable{
    ShapeRenderer sr;
    public OrthographicCamera camera;
    Box2DDebugRenderer box2DDebugRenderer;
    public UI ui;

    public View(){
        sr = new ShapeRenderer();
        camera = new OrthographicCamera();
        box2DDebugRenderer = new Box2DDebugRenderer(true,true,false,false,false,false);
        ui = new UI();
    }

    public void render(Level level){
        clear();
        camera.update();
        sr.setProjectionMatrix(camera.combined);

        level.os.render(sr);
        box2DDebugRenderer.render(level.world, camera.combined);
        ui.stage.draw();
    }

    private void clear(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void resize(float w, float h){
        camera.viewportWidth = w;
        camera.viewportHeight = h;
    }

    @Override
    public void dispose() {
        sr.dispose();
        box2DDebugRenderer.dispose();
        ui.dispose();
    }
}
