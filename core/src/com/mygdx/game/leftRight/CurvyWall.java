package com.mygdx.game.leftRight;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.leftRight.geometry.GeometryUtils;
import com.mygdx.game.leftRight.geometry.PolygonShape;

/**
 * Created by Alexander on 22.06.2015.
 */
public class CurvyWall extends GObject implements CanCreateBody{
    private PolygonShape initShape;
    Vector2 xy;
    Body b;

    public CurvyWall(float x, float y, PolygonShape initShape) {
        this.initShape = initShape;
        this.xy = new Vector2(x, y);
    }

    @Override
    public Body createBody(World world) {
        PolygonShape[] polygonShapes = GeometryUtils.poligonToWallSegments(initShape, 0.25f, false);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xy);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        b = world.createBody(bodyDef);
        for(PolygonShape p : polygonShapes){
            com.badlogic.gdx.physics.box2d.PolygonShape s = new com.badlogic.gdx.physics.box2d.PolygonShape();
            s.set(p.toPolygon());
            b.createFixture(s, 1);
        }

        return b;
    }
}
