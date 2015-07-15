package com.keepCalmAndDoItRight.basics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.keepCalmAndDoItRight.gObjects.Unit;


/**
 * Created by Alexander on 23.06.2015.
 */
public class Level implements Disposable{
    AssetManager assetManager;
    private float timeSpeed = 1F;
    public ObjectSet os;
    public World world;
    public Stage stage;
    public View view;
    public Body superStone;
    public float time = 0;
    public Unit player;

    float wishedTimeSpeed = timeSpeed;
    float timeAcceleration = 2f;

    public Level() {

        assetManager = new AssetManager();

        this.os = new ObjectSet(this);

        //init world2d
        this.world = new World(new Vector2(0, 0), true);
        world.setContactListener(new PhysicsContactListener());
        world.setContinuousPhysics(true);
        superStone = world.createBody(new BodyDef());

        //init stage
        this.stage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()),
                new PolygonSpriteBatch());
        view = new View(this);
    }


    @Override
    public void dispose() {
        world.dispose();
        os.dispose();
        stage.dispose();
    }

    public void update(float dt) {
        timeSpeed += (wishedTimeSpeed - timeSpeed) * timeAcceleration * Gdx.graphics.getDeltaTime();
        dt *= timeSpeed;
        time += dt;

        world.step(dt, 8, 3);
        os.update(dt);
        stage.act(dt);

    }

    public float getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(float timeSpeed) {
        this.wishedTimeSpeed = timeSpeed;
    }

    public void setPlayer(Unit player) {
        view.gCamera.setPlayer(player);
        this.player = player;
    }
}
