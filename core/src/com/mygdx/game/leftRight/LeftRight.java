package com.mygdx.game.leftRight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.editor.Curve;
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
//    public LinkedList<Updatable>        updatables     ;
//    public LinkedList<ShapeDrawable>    shapeDrawables ;

    public ObjectSet os;

    LinkedList<Mob> mobs;
    LinkedList<HitingBox> hitingBoxes;
    public double time;
    Player player;
    World world;
    Platform platform;



    @Override
    public void create() {
        super.create();
        os = new ObjectSet();
//        updatables = new LinkedList<Updatable>();
//        shapeDrawables = new LinkedList<ShapeDrawable>();
        mobs = new LinkedList<Mob>();
        hitingBoxes = new LinkedList<HitingBox>();
        time = 0;
        instance = this;
        camera = new OrthographicCamera();
        sr = new ShapeRenderer();
        box2DDebugRenderer = new Box2DDebugRenderer(true, true, false, true, false, false);

        world = new World(new Vector2(0, -10), true);
        world.setContinuousPhysics(false);

        System.out.println(world.getAutoClearForces());
        world.setAutoClearForces(true);
        double x = 1;
        for(int i = 0; i < 100; i++) {
            x = x + 3.5 + Math.random() * 2.5;
            Mob mob = new Mob((float) x, 0);
            mobs.add(mob);
            mob.createBody(world);
//            shapeDrawables.add(mob);
//            updatables.add(mob);
            os.put(mob);
        }
        new CurvyWall(0, 0, new Curve(0, 0, 1, 1, 2, 1, 3, 1)).createBody(world);

//        platform = new Platform(0, 2, 5, 0.2f);
//        platform.createBody(world);
//        updatables.add(platform);
//        shapeDrawables.add(platform);

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
        os.put(input);
//        updatables.add(input);
        Gdx.input.setInputProcessor(input);

        os.put(player);
//        shapeDrawables.add(player);
//        updatables.add(player);
        Graph g = new Graph(1,1);
        os.put(g);
//        updatables.add(g);
//        shapeDrawables.add(g);

        Graph2 g2 = new Graph2(-1,1);
        os.put(g2);
//        updatables.add(g2);
//        shapeDrawables.add(g2);

        player.createBody(world);
        {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(0,-0.95f);
            PolygonShape groundShape = new PolygonShape();
            groundShape.set(new float[]{-100, 0, 100, 0, 100, -2.5f, -100, -2.5f});
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = groundShape;
            fixtureDef.density = 1;
            fixtureDef.friction = 1;
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

//        for(int i = 0; i < updatables.size(); i++){
//            updatables.get(i).update(dt);
//        }
        os.update(dt);



        camera.zoom = 0.015f;
        camera.position.set(player.x, player.y, 0);
        camera.update();
        sr.setProjectionMatrix(camera.combined);

        sr.begin(ShapeRenderer.ShapeType.Filled);

//        for(ShapeDrawable sd : shapeDrawables){
//            sd.render(sr);
//        }
        os.render(sr);

        sr.setColor(Color.GRAY);
//        sr.rect(-1000,-1000,2000,1000);
        sr.end();
        box2DDebugRenderer.render(world, camera.combined);
        if(restart){
            invokeRestart();
        }
    }

    boolean restart;




    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        sr.dispose();
    }

    public void invokeRestart(){
        float w = camera.viewportWidth;
        float h = camera.viewportHeight;
        dispose();
        create();
        resize((int)w, (int)h);
        restart = false;
    }

    public void restart(){
        restart = true;
    }
}
