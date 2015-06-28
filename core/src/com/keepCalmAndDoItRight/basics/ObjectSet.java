package com.keepCalmAndDoItRight.basics;

import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander on 20.06.2015.
 */
public class ObjectSet {
    List<GObject> toRemove;
    public Array<GObject> allGObjects;
    List<GObject> others;
    List<GObject> toAdd;

    public ObjectSet(){
        others = new LinkedList<GObject>();
        others = new LinkedList<>();
        toRemove = new LinkedList<GObject>();
        toAdd = new LinkedList<GObject>();
        allGObjects = new Array<GObject>();
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
        allGObjects.removeValue(o, true);
    }

    public void update(float dt){

        for(GObject u : allGObjects){
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

    public void dispose() {
        for(GObject o : allGObjects){
            o.dispose();
        }
    }

//    public void render(ShapeRenderer sr) {
//        for(GObject sd : allGObjects){
//            sd.render(sr);
//        }
//    }
}
