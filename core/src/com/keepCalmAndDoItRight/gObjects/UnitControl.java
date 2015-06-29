package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.keepCalmAndDoItRight.basics.SimplePath;

/**
 * Created by Alexander on 27.06.2015.
 */
public class UnitControl {
    public static final int NONE = 0;
    public static final int ALL = -1;
    public static final int MOVE_FORWARD = 1;
    public static final int MOVE_BACKWARD = 2;
    public static final int ROTATE_CLOCKWISE = 4;
    public static final int ROTATE_COUNTER_CLOCKWISE = 8;
    public static final int ACTIVATE = 16;
    public static final int FIRE = 32;
    public static final int MOVE_RIGHT = 64;
    public static final int MOVE_LEFT = 128;
    public static final int GOTO_MOUSE = 256;
    public static final int LOOK_AT__MOUSE = 512;
    public static final int FOLLOW_PATH = 1024;
    Unit unit;
    float x;
    float y;
    public SimplePath path;
    private SimplePath lastPath;
    private float beginT;

    public UnitControl(Unit unit) {
        this.unit = unit;
    }

    float maxSpeed = 5;
    float maxAngularSpeed = 3f;
    int currentAction;
    public void addAction(int action){
        currentAction |= action;
    }
    float t = 0;
    float finalT;
    public void removeAction(int action){
        currentAction = (~action) & currentAction;
    }
    Vector2 vel = new Vector2();
    public void update(){
        float speedY = 0;
        float speedX = 0;
        float aSpeed = 0;

        if((currentAction & GOTO_MOUSE) != 0){
            speedX += x - unit.getBody().getPosition().x;
            speedY += y - unit.getBody().getPosition().y;
        }
        if((currentAction & MOVE_FORWARD) != 0){
            speedY += maxSpeed;
        }
        if((currentAction & MOVE_BACKWARD)!= 0){
            speedY -= maxSpeed;
        }
        if((currentAction & MOVE_RIGHT) != 0){
            speedX += maxSpeed;
        }
        if((currentAction & MOVE_LEFT)!= 0){
            speedX -= maxSpeed;
        }
        if((currentAction & ROTATE_CLOCKWISE) != 0){
            aSpeed -= maxAngularSpeed;
        }
        if((currentAction & ROTATE_COUNTER_CLOCKWISE) != 0){
            aSpeed += maxAngularSpeed;
        }
        if((currentAction & ACTIVATE) != 0){
            unit.actionBox.activate();
        }
        if(((currentAction) & FIRE) != 0){
            Bullet bullet = new Bullet(unit.level);
            Body b = unit.getBody();
            bullet.getBody().setTransform(b.getPosition().cpy().add(new Vector2(0, .3f).rotateRad(b.getAngle())), b.getAngle());
            bullet.getBody().setLinearVelocity(new Vector2(0,50).rotateRad(b.getAngle()));
//            currentAction ^= FIRE;
        }
        if(((currentAction) & FOLLOW_PATH) != 0) {
            if(lastPath != path) {
                t = unit.level.time;
                beginT = t;
                finalT = t + path.approxLength(100) / maxSpeed;
                lastPath = path;
            }
            t = (unit.level.time - beginT)/(finalT - beginT);
            if(t > 1) {
                t = 1;
            }
            Vector2 v = new Vector2();
            path.valueAt(v, t);
//            unit.getBody().setTransform(v, unit.getBody().getAngle());
            if(t == 1) {
//                removeAction(FOLLOW_PATH);
//                finalT = 0;
            }
            float x = v.x;
            float y = v.y;

            speedX += x - unit.getBody().getPosition().x;
            speedY += y - unit.getBody().getPosition().y;
            v.set(speedX, speedY);
            if(v.len() > 0.1f)
                v.setLength(maxSpeed);
            if(t >= 1 && v.len2() < 0.01f)
                removeAction(FOLLOW_PATH);
            speedX = v.x;
            speedY = v.y;
        }


            vel.set(speedX,speedY);
//        .rotateRad(unit.getBody().getAngle());
        if(speedY != 0 || speedX != 0){
            float v = MathUtils.clamp(vel.len(), 0, maxSpeed);
            vel.setLength(v);
            unit.getBody().setLinearVelocity(vel);
            float dAngle = (vel.angleRad()-unit.getBody().getAngle() - MathUtils.PI/2);
            while(dAngle > MathUtils.PI)
                dAngle -= MathUtils.PI2;
            while(dAngle < -MathUtils.PI)
                dAngle += MathUtils.PI2;
            aSpeed += dAngle * 10;
        }
//        if(vel.cpy().nor().dot(unit.getBody().getLinearVelocity()) < maxSpeed)
//        unit.getBody().applyForceToCenter(vel, true);

        if((currentAction & LOOK_AT__MOUSE) != 0){
            aSpeed = 0;
            Vector2 direction = new Vector2(x - unit.getBody().getPosition().x, y - unit.getBody().getPosition().y);
            float dAngle = (direction.angleRad()-unit.getBody().getAngle() - MathUtils.PI/2);
            while(dAngle > MathUtils.PI)
                dAngle -= MathUtils.PI2;
            while(dAngle < -MathUtils.PI)
                dAngle += MathUtils.PI2;
            aSpeed += dAngle * 10;
        }

        unit.getBody().setAngularVelocity(aSpeed);
    }

    public void addAction(int action, float x, float y) {
        this.x = x;
        this.y = y;
        addAction(action);
    }
}
