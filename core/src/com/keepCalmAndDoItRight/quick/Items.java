package com.keepCalmAndDoItRight.quick;

import com.badlogic.gdx.utils.ObjectMap;
import com.keepCalmAndDoItRight.gObjects.Item;

import java.util.LinkedList;

/**
 * Created by Alexander on 06.07.2015.
 */
public class Items {
    ObjectMap<String, LinkedList<Item>> values;
    public Items(){
        values = new ObjectMap<>();
    }

    public void put(Item v){
        if(!values.containsKey(v.getActor().getName()))
            values.put(v.getActor().getName(), new LinkedList<Item>());

        values.get(v.getActor().getName()).add(v);
    }

    public Item getOne(String k){
        LinkedList<Item> array = values.get(k);
        if (array != null) {
            return array.getFirst();
        }else
            return null;
    }

    public LinkedList<Item> getAll(String key){
        LinkedList<Item> result = values.get(key);
        return result == null ? new LinkedList<Item>() : result;
    }

    public void putFirst(Item v){
        if(!values.containsKey(v.getActor().getName()))
            values.put(v.getActor().getName(), new LinkedList<Item>());

        values.get(v.getActor().getName()).addFirst(v);
    }

}
