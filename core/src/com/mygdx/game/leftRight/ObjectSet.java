package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander on 20.06.2015.
 */
public class ObjectSet {
    List<GObject> toRemove;
    List<Updatable> updatables;
    List<ShapeDrawable> drawables;
    List<GObject> others;

    public ObjectSet(){
        updatables = new LinkedList<Updatable>();
        drawables = new LinkedList<ShapeDrawable>();
        others = new LinkedList<GObject>();
        toRemove = new LinkedList<GObject>();
    }

    public void put(GObject o){
        if(o instanceof Updatable)
            updatables.add(((Updatable) o));
        if(o instanceof ShapeDrawable)
            drawables.add(((ShapeDrawable) o));
    }

    public void remove(GObject o){
        o.setActive(false);
        toRemove.add(o);
    }

    public void actuallyRemove(GObject o){
        if(o instanceof Updatable)
            updatables.remove(o);
        if(o instanceof ShapeDrawable)
            drawables.remove(o);
    }

    public void update(float dt){
        for(Updatable u : updatables){
            if(u.isActive())
                u.update(dt);
        }
        for(GObject r : toRemove){
            actuallyRemove(r);
        }
        toRemove.clear();

    }

    public void render(ShapeRenderer sr) {
        for(ShapeDrawable sd : drawables){
            sd.render(sr);
        }
    }
}
