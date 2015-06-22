package com.mygdx.game.editor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.leftRight.ObjectSet;

/**
 * Created by Alexander on 23.06.2015.
 */
public class Level {
    public ObjectSet os;
    public Camera camera;
    public World world;

    public Level(ObjectSet os, Camera camera, World world) {
        this.os = os;
        this.camera = camera;
        this.world = world;
    }
}
