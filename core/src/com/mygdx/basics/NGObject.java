package com.mygdx.basics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Alexander on 12.07.2015.
 */
public class NGObject {
    ObjectMap<String, GComp> components = new ObjectMap<>();
    Vector2 pos = new Vector2();
    float aRad;
    public int lastPosChange = 0;

    public void update(float dt){
        for(GComp component : components.values()){
            component.update(dt);
        }
    }

    public void render(Batch batch){
        for(GComp component : components.values()){
            component.render(batch);
        }
    }

    public void setTransform(Vector2 pos, float aRad){
        for(GComp component : components.values()){
            component.setTransform(pos, aRad);
        }
    }

    public void addGComp(GComp gComp){
        if (components.containsKey(gComp.getName())) {
            throw new IllegalArgumentException("Такое имя уже есть в компонентах");
        }else
            components.put(gComp.getName(), gComp);
    }




    public Vector2 getPos(){
        return pos;
    }

    public float getAngleRad(){
        return aRad;
    }
}
