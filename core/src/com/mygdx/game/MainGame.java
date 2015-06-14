package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class MainGame extends ApplicationAdapter {
    public static MainGame instance;
    public static OrthographicCamera camera;
	PolygonSpriteBatch batch;
    ShapeRenderer sr;
    SimpleObject obj;
    MouseInput input;
    float[] vertices;
    static OixelsGrid grid;

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        InfoOutput.stage.getViewport().getCamera().viewportWidth = width;
        InfoOutput.stage.getViewport().getCamera().viewportHeight = height;
        InfoOutput.stage.getViewport().getCamera().position.set(width / 2, height / 2, 0);
    }

    @Override
	public void create () {
        instance = this;
        camera = new OrthographicCamera();
        camera.zoom = 0.1f;
        input = new MouseInput();
        Gdx.input.setInputProcessor(input);
		batch = new PolygonSpriteBatch();
        sr = new ShapeRenderer();



        int size = 2;
        grid = new OixelsGrid(-size, size, -size, size);
        camera.zoom = 0.01f * size;


        GameData.updatables.add(new InfoOutput("mouseXY"));
        GameData.updatables.add(new InfoOutput("fps"));
        GameData.updatables.add(new InfoOutput("sumSpeedX"));
        GameData.updatables.add(new InfoOutput("sumSpeedY"));
        GameData.disposables.add(obj);
        GameData.disposables.add(sr);
        GameData.disposables.add(batch);
    }

	@Override
	public void render () {
        float dt = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
//        for(Updatable obj : GameData.updatables){
//            if(obj instanceof Particle){
//                ((Particle) obj).draw(batch);
//            }
//        }
//        obj.draw(batch);
		batch.end();

        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        grid.draw(sr);
        sr.end();

        GameData.map.put("mouseXY", String.format("%-9.3f %-9.3f", input.x, input.y));
        GameData.map.put("fps", String.valueOf(Gdx.graphics.getFramesPerSecond()));
        grid.update(dt);
        for(Updatable obj : GameData.updatables)
            obj.update(dt);

        InfoOutput.stage.draw();
	}

    @Override
    public void dispose() {
        super.dispose();
        for(Disposable obj : GameData.disposables)
            obj.dispose();
    }

    public void recreate() {
        int size = 20;
        grid = new OixelsGrid(-size, size, -size, size);
    }
}
