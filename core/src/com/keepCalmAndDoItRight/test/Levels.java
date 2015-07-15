package com.keepCalmAndDoItRight.test;

import com.badlogic.gdx.math.Polygon;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.basics.View;
import com.keepCalmAndDoItRight.controllers.PlayerController;
import com.keepCalmAndDoItRight.controllers.UIController;
import com.keepCalmAndDoItRight.gObjects.Door;
import com.keepCalmAndDoItRight.gObjects.Unit;
import com.keepCalmAndDoItRight.gObjects.UnitControl;
import com.keepCalmAndDoItRight.gObjects.Wall;
import com.keepCalmAndDoItRight.quick.GeometryUtils;

/**
 * Created by Alexander on 03.07.2015.
 */
public class Levels {
    public static final Level level1 = level1();

    public static Level level1(){
        Level level;
        level = new Level();
        level.view = new View(level);
        Polygon wall1 = new Polygon(new float[]{
                0, 1,
                0, 3,
                -6, 3,
                -8, 4,
                -8, 6,
                -10, 6,
                -10, 4,
                -10, -4,
                -8, -4,
                -6, -3,
                0, -3,
                0, -1
        });
        Wall w1 = new Wall(level, wall1, 0.3f, false);
        int i1 = 15;
        Wall w2 = new Wall(level, new Polygon(new float[]{
                -i1, -i1,
                -i1, i1,
                i1, i1,
                i1, -i1
        }), 1, true);
        new Door(level, GeometryUtils.boxPolygon(0.05f, 1f));
        Unit player;
        player = new Unit(level);
        player.hp = 1000f;
        player.getBody().setTransform(0, 5, 0);
        player.getBody().setFixedRotation(true);

        for(int i = 0; i < 100; i++) {
            Unit box = new Unit(level);
            float av = (float) (Math.random() - 0.5f);
            float vx = ((float) (Math.random() - 0.5f));
            float vy = ((float) (Math.random() - 0.5f));
            float x = ((float) (Math.random() - 0.5f));
            float y = ((float) (Math.random() - 0.5f));
            float a = ((float) (Math.random() - 0.5f));


//            vx = vy = av = 0;
//            x = y = a = 0;
            x*=10;
            y*=10;

            box.getBody().setAngularVelocity(av);
            box.getBody().setTransform(x, y, a);
            box.getBody().setLinearVelocity(vx, vy);
            box.control.addAction(UnitControl.FOLLOW_UNIT);
            box.control.unit = player;
            box.control.maxSpeed /= 5;
            box.control.addAction(UnitControl.FIRE);
            box.control.fireDelayTime = 2f;
            box.activate();
        }
        player.getActor().toFront();
        level.setPlayer(player);
        player.unitType = Unit.UnitType.player;


        level.stage.addListener(new PlayerController(player));
        level.stage.addListener(new UIController(level.view));

        return level;
    }
}
