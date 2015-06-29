package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.basics.Level;

/**
 * Created by Alexander on 27.06.2015.
 */
public class Door extends GObject{

    public Door(Level level, Polygon polygon) {
        super(level, polygon);
        init();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        j.setMotorSpeed(-j.getJointAngle());
    }
    RevoluteJoint j;


    @Override
    public Body createBody(World world) {
        body =  super.createBody(world);
        friction.setMaxForce(0);
        friction.setMaxTorque(0);

        BodyDef st = new BodyDef();
        Body staticBody = world.createBody(st);
        RevoluteJointDef jDef = new RevoluteJointDef();
        jDef.bodyA = body;
        jDef.bodyB = staticBody;
        jDef.collideConnected = false;
        jDef.lowerAngle = -MathUtils.PI;
        jDef.upperAngle = MathUtils.PI;
        jDef.enableLimit = true;
        jDef.initialize(body, staticBody, new Vector2(0, -1));
        jDef.enableMotor = true;
//        jDef.motorSpeed = 100f;
        jDef.maxMotorTorque = 10;
//        staticBody.setTransform(0, -1, 0);
        j = (RevoluteJoint) world.createJoint(jDef);
        body.getFixtureList().first().setFilterData(Wall.wallFilter);
//        body.getFixtureList().get(1).setFilterData(Wall.wallFilter);
        return body;
    }
}
