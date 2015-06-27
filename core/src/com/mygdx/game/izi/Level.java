package com.mygdx.game.izi;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.leftRight.GObject;
import com.mygdx.game.topDown.PhysicsContactListener;

/**
 * Created by Alexander on 23.06.2015.
 */
public class Level implements Disposable{
    public ObjectSet os;
    public World world;

    public Level() {
        this.os = new ObjectSet();
        this.world = new World(new Vector2(0, 0), true);
        world.setContactListener(new PhysicsContactListener());
    }

    //TODO
    public GObject getObjectsAt(float x, float y) {
        float closestDist2 = Float.MAX_VALUE;
        GObject closest = null;
        for(GObject o : os.allGObjects)
            if(o.getPos().dst2(x, y) < closestDist2){
                closest = o;
                closestDist2 = o.getPos().dst2(x, y);
            }
        return closest;
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    public void update(float dt) {
        world.step(dt, 3,5);
        os.update(dt);
    }
}
