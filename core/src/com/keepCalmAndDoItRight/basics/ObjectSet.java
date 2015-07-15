package com.keepCalmAndDoItRight.basics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander on 20.06.2015.
 */
public class ObjectSet {
    Level level;
    List<GObject> toRemove;
    public Array<GObject> allGObjects;
    List<GObject> others;
    List<GObject> toAdd;
    List<GObject> toRemoveBody;

    public ObjectSet(Level level){
        this.level = level;
        others = new LinkedList<GObject>();
        others = new LinkedList<>();
        toRemove = new LinkedList<GObject>();
        toAdd = new LinkedList<GObject>();
        allGObjects = new Array<GObject>();
        toRemoveBody = new LinkedList<GObject>();
    }

    public void put(GObject o){
        toAdd.add(o);
    }

    public void actuallyPut(GObject o){
        allGObjects.add(o);
    }

    public void remove(GObject o){
        toRemove.add(o);
    }

    public void actuallyRemove(GObject o){
        actuallyRemoveBody(o);
        if(o.actor != null){
            level.stage.getActors().removeValue(o.actor, true);
        }
        allGObjects.removeValue(o, true);
    }

    private void actuallyRemoveBody(GObject o) {
        if(o.hasBody){
            level.world.destroyBody(o.getBody());
            o.setBody(null);
        }
    }

    public void update(float dt){

        for(GObject u : allGObjects){
            u.update(dt);
        }

        actuallyPutAndRemoveAll();
    }

    public void actuallyPutAndRemoveAll(){
        for(GObject r : toAdd){
            actuallyPut(r);
        }
        toAdd.clear();

        for(GObject r : toRemove){
            actuallyRemove(r);
        }
        toRemove.clear();

        for(GObject r : toRemoveBody){
            actuallyRemoveBody(r);
        }
        toRemoveBody.clear();
    }

    public void dispose() {
        for(GObject o : allGObjects){
            o.dispose();
        }
    }

    public void drawDebug(ShapeRenderer sr) {
        for(GObject u : allGObjects){
            u.debugRender(sr);
        }
    }

    public void removeBody(GObject gObject) {
        toRemoveBody.add(gObject);
    }
}
