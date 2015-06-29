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
import com.keepCalmAndDoItRight.PhysicsContactListener;


/**
 * Created by Alexander on 23.06.2015.
 */
public class Level implements Disposable{
    AssetManager assetManager;
    public ObjectSet os;
    public World world;
    public Stage stage;
    public Body superStone;
    public float time = 0;

    public Level() {
        assetManager = new AssetManager();
        this.os = new ObjectSet(this);
        this.world = new World(new Vector2(0, 0), true);
        world.setContactListener(new PhysicsContactListener());
        world.setContinuousPhysics(true);
        this.stage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()),
                new PolygonSpriteBatch());
//        world.setContactListener(new PhysicsContactListener());
        superStone = world.createBody(new BodyDef());
    }


    //TODO
//    public GObject getObjectsAt(float x, float y) {
//        float closestDist2 = Float.MAX_VALUE;
//        GObject closest = null;
//        for(GObject o : os.allGObjects)
//            if(o.getPos().dst2(x, y) < closestDist2){
//                closest = o;
//                closestDist2 = o.getPos().dst2(x, y);
//            }
//        return closest;
//    }

    @Override
    public void dispose() {
        world.dispose();
        os.dispose();
    }

    public void update(float dt) {
        time += dt;
        world.step(dt, 8, 3);
        os.update(dt);
        stage.act(dt);
    }
}
