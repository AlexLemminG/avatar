package com.mygdx.game.leftRight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.leftRight.physics.ContactAdapter;

import java.util.LinkedList;

/**
 * Created by Alexander on 17.06.2015.
 */
public class LeftRight extends ApplicationAdapter {
    public static LeftRight instance;
    public static OrthographicCamera camera;
    ShapeRenderer sr;
    //    SimpleObject obj;
    Box2DDebugRenderer box2DDebugRenderer;
    InputAd input;

    LinkedList<Mob> mobs = new LinkedList<Mob>();
    LinkedList<HitingBox> hitingBoxes = new LinkedList<HitingBox>();
    public double time = 0;
    Player player;
    World world;



    @Override
    public void create() {
        super.create();
        instance = this;
        camera = new OrthographicCamera();
        sr = new ShapeRenderer();
        box2DDebugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        world = new World(new Vector2(0, -10), false);

        double x = 1;
        for(int i = 0; i < 100; i++) {
            x = x + 7.5 + Math.random() * 2.5;
            Mob mob = new Mob((float) x, 0);
            mobs.add(mob);
            mob.createBody(world);
        }

        world.setContactFilter(new ContactFilter() {
            @Override
            public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
                boolean isDead = false;
                if (fixtureA.getBody().getUserData() instanceof Mob)
                    isDead |= ((Mob) fixtureA.getBody().getUserData()).isDead();
                if (fixtureB.getBody().getUserData() instanceof Mob)
                    isDead |= ((Mob) fixtureB.getBody().getUserData()).isDead();
                return !isDead;
            }
        });

        world.setContactListener(new ContactAdapter());
        player = new Player(0, 0);
        input = new InputAd(player);
        Gdx.input.setInputProcessor(input);


        player.createBody(world);
        {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            PolygonShape groundShape = new PolygonShape();
            groundShape.set(new float[]{-100, 0, 100, 0, 100, -2.5f, -100, -2.5f});
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = groundShape;
            fixtureDef.density = 1;
            world.createBody(bodyDef).createFixture(fixtureDef);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    @Override
    public void render() {
        float dt = 0.02f;
        time += dt;
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        world.step(dt, 5, 10);

        player.update(dt);
        for(Mob mob : mobs){
            mob.update(dt);
        }
        for(HitingBox hb : hitingBoxes){
            hb.update(dt);
        }

        camera.zoom = 0.04f;
        camera.position.set(player.x, player.y, 0);
        camera.update();
        sr.setProjectionMatrix(camera.combined);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        player.render(sr);
        for(Mob mob : mobs){
            mob.render(sr);
        }
        for(HitingBox hb : hitingBoxes){
            hb.render(sr);
        }
        sr.setColor(Color.GRAY);
        sr.rect(-1000,-1000,2000,1000);
        sr.end();
        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        sr.dispose();
    }
}
