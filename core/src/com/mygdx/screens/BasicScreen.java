package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.keepCalmAndDoItRight.basics.Assets;
import com.mygdx.basics.NGObject;
import com.mygdx.gComps.PhysicsBodyComp;
import com.mygdx.gComps.SpriteComp;
import com.mygdx.gComps.TransformChangerComp;

/**
 * Created by Alexander on 12.07.2015.
 */
public class BasicScreen extends ScreenAdapter{
    SpriteBatch batch;
    OrthographicCamera camera;
    Array<NGObject> ngObjects = new Array<>();
    TransformChangerComp transformChangerComp;
    World world;
    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    FPSLogger logger = new FPSLogger();

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }
    Body body;

    public BasicScreen() {
        super();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        world = new World(new Vector2(0, -10), true);
        NGObject o = new NGObject();
        SpriteComp p = new SpriteComp(o, new Sprite(Assets.DEFAULT_TEXTURE));
        transformChangerComp = new TransformChangerComp(o);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(10);
        body = world.createBody(bodyDef);
        body.createFixture(circleShape, 1);
        PhysicsBodyComp physicsBodyComp = new PhysicsBodyComp(o, body);
        o.addGComp(physicsBodyComp);

        o.addGComp(p);
        ngObjects.add(o);
        body.setAngularVelocity(1);
    }
    float time = 0;
    @Override
    public void render(float delta) {
        delta = 1f/60;
        time += delta;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(delta, 8, 5);
        for (NGObject o : ngObjects) {
            o.update(delta);
        }
//        transformChangerComp.setTransform(new Vector2(MathUtils.sin(time), MathUtils.cos(time)).scl(100), time);


        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (NGObject o : ngObjects) {
            o.render(batch);
        }
        batch.end();
        box2DDebugRenderer.render(world, camera.combined);
        logger.log();
    }
}
