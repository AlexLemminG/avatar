package com.mygdx.game.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.editor.states.BoxCreationState;
import com.mygdx.game.leftRight.ObjectSet;

/**
 * Created by Alexander on 21.06.2015.
 */
public class Editor extends ApplicationAdapter{
    Stage stage;
    Skin skin;
    InputListener input;
    OrthographicCamera camera;
    Level level;
    ShapeRenderer sr;
    Box2DDebugRenderer debugRenderer;

    @Override
    public void render() {
        super.render();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.os.update(1);

        camera.update();
//        stage.getViewport().apply(true);
//        stage.draw();

        sr.setProjectionMatrix(camera.combined);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        level.os.render(sr);
        sr.end();

        debugRenderer.render(Consts.level.world, camera.combined);

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

//        camera.update();
        camera.viewportHeight = height;
        camera.viewportWidth = width;
//        stage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void create() {
        super.create();
        stage = new Stage();
        sr = new ShapeRenderer();
        camera = new OrthographicCamera();
        skin = new Skin(Gdx.files.internal("C:\\Users\\Alexander\\Downloads\\libgdx-master\\libgdx-master\\tests\\gdx-tests-android\\assets\\data\\uiskin.json"));
        stage.getViewport().setCamera(camera);
        stage.setDebugAll(true);
        level = new Level(new ObjectSet(), camera, null);
        input = new InputListener(level);
        input.setState(new BoxCreationState());
        Gdx.input.setInputProcessor(input);
        Consts.level = level;
        Consts.input = input;
        Consts.level.world = new World(new Vector2(0,10), true);
        debugRenderer = new Box2DDebugRenderer(true, false, false, true, true, false);

        Table table = new Table(skin);
        stage.addActor(table);
        table.setFillParent(true);


        Button button = new Button(skin);
        Button button2 = new Button(skin);
        table.right();

        table.add(button).fillX().space(10f);
        table.add(button2);
        table.row();
//        table.center().row().setActor(button);
//        table.row().setActor(button2);

        button.setWidth(100);
        button.add("ABC");



//        Label label = new Label("asds", skin);
//       stage.addActor(label);
    }
}
