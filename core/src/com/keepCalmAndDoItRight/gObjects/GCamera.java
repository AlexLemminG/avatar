package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.basics.Level;

/**
 * Created by Alexander on 03.07.2015.
 */
public class GCamera extends GObject {
    OrthographicCamera camera;
    Unit player;



    public GCamera(Level level, OrthographicCamera camera) {
        super(level);
            this.player = level.player;
        this.camera = camera;
        init();
        for(Fixture f : getBody().getFixtureList()){
            f.setSensor(true);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (player != null && player.getBody() != null) {
            Vector2 newUp = new Vector2(0, 1).rotateRad(player.getBody().getAngle());
            float scalar = dt * 10;
            Vector2 newPos = player.getBody().getPosition();
            Vector2 newPosDelta = newPos.sub(camera.position.x, camera.position.y).scl(scalar);
//
            camera.position.add(newPosDelta.x, newPosDelta.y, 0);
            camera.normalizeUp();
        }
    }

    public void setPlayer(Unit player) {
        this.player = player;
    }
}
