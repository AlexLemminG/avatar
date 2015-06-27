package keepCalmAndDoItRight.basics;

import com.badlogic.gdx.Gdx;
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
    PolygonSpriteBatch polygonSpriteBatch;

    public View(Level level){
        this.level = level;
        sr = new ShapeRenderer();
        box2DDebugRenderer = new Box2DDebugRenderer(true,true,false,false,false,false);
        camera = (OrthographicCamera) level.stage.getViewport().getCamera();
        polygonSpriteBatch = new PolygonSpriteBatch();
        polygonSpriteBatch.enableBlending();
        camera.position.set(0,0,0);
    }

    public void render(){
        clear();
        camera.zoom = 1f/100;

        camera.update();
        sr.setProjectionMatrix(camera.combined);

        polygonSpriteBatch.begin();

        polygonSpriteBatch.end();
        level.stage.draw();

        box2DDebugRenderer.render(level.world, camera.combined);
//        ui.stage.draw();
    }

    private void clear(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void resize(float w, float h){
        Gdx.gl.glViewport(0,0,(int)w,(int)h);
        camera.viewportWidth = w;
        camera.viewportHeight = h;
    }

    @Override
    public void dispose() {
        sr.dispose();
        box2DDebugRenderer.dispose();
        polygonSpriteBatch.dispose();
    }
}
