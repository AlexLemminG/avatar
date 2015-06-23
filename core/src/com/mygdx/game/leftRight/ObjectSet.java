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
    public List<ShapeDrawable> drawables;
    public List<GObject> allGObjects;
    List<GObject> others;
    List<GObject> toAdd;

    public ObjectSet(){
        updatables = new LinkedList<Updatable>();
        drawables = new LinkedList<ShapeDrawable>();
        others = new LinkedList<GObject>();
        toRemove = new LinkedList<GObject>();
        toAdd = new LinkedList<GObject>();
        allGObjects = new LinkedList<GObject>();
    }

    public void put(GObject o){
        toAdd.add(o);
    }

    public void actuallyPut(GObject o){
        if(o instanceof Updatable)
            updatables.add(((Updatable) o));
        if(o instanceof ShapeDrawable)
            drawables.add(((ShapeDrawable) o));
        allGObjects.add(o);
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
        allGObjects.remove(o);
    }

    public void update(float dt){

        for(Updatable u : updatables){
            if(u.isActive())
                u.update(dt);
        }
        for(GObject r : toAdd){
            actuallyPut(r);
        }
        toAdd.clear();

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
