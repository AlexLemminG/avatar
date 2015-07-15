package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.keepCalmAndDoItRight.basics.SimplePath;
import com.keepCalmAndDoItRight.gObjects.weapons.Gun;

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
    public static final int FOLLOW_UNIT = 2048;
    public static final int FOLLOW_DIRECTION = 4096;
    public static final int LOOK_AT_MOVEMENT = 8192;
    public Vector2 direction = new Vector2();
    public Unit unit;
    public float fireDelayTime = 0.1f;
    Unit owner;
    public float x;
    public float y;
    public SimplePath path;
    private SimplePath lastPath;
    private float beginT;
    public float timeNextBulletFired;
    public UnitControl(Unit owner) {
        this.owner = owner;
    }

    public float maxSpeed = 5;
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
        mouseXY.set(Gdx.input.getX(), Gdx.input.getY());
        temp3.set(mouseXY.x, mouseXY.y, 0);
        temp3 = owner.level.view.camera.unproject(temp3);
        x = temp3.x;
        y = temp3.y;

        float speedY = 0;
        float speedX = 0;
        float aSpeed = 0;

        if((currentAction & GOTO_MOUSE) != 0){
            speedX += x - owner.getBody().getPosition().x;
            speedY += y - owner.getBody().getPosition().y;
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
            owner.actionBox.activate();
        }
        if((currentAction & LOOK_AT_MOVEMENT) != 0){
            float dAngle = (vel.angleRad()- owner.getBody().getAngle() - MathUtils.PI/2);
            while(dAngle > MathUtils.PI)
                dAngle -= MathUtils.PI2;
            while(dAngle < -MathUtils.PI)
                dAngle += MathUtils.PI2;
            aSpeed += dAngle * 10;
        }

        if(((currentAction) & FOLLOW_UNIT) != 0 && unit != null && unit.getBody() != null) {

            speedX += unit.getBody().getPosition().x - owner.getBody().getPosition().x;
            speedY += unit.getBody().getPosition().y - owner.getBody().getPosition().y;
        }
        if(((currentAction) & FOLLOW_PATH) != 0) {
            if(lastPath != path) {
                t = owner.level.time;
                beginT = t;
                finalT = t + path.approxLength(100) / maxSpeed;
                lastPath = path;
            }
            t = (owner.level.time - beginT)/(finalT - beginT);
            t += 0.1 / (finalT - beginT);
            if(t > 1) {
                t = 1;
            }
            Vector2 v = new Vector2();
            path.valueAt(v, t);
//            owner.getBody().setTransform(v, owner.getBody().getAngle());
            if(t == 1) {
//                removeAction(FOLLOW_PATH);
//                finalT = 0;
            }
            float x = v.x;
            float y = v.y;

            speedX += x - owner.getBody().getPosition().x;
            speedY += y - owner.getBody().getPosition().y;
            v.set(speedX, speedY);
            if(v.len() > 0.1f)
                v.setLength(maxSpeed);
            if(t >= 1 && v.len2() < 0.01f)
                removeAction(FOLLOW_PATH);
            speedX = v.x;
            speedY = v.y;
        }
        if((currentAction & FOLLOW_DIRECTION) != 0){
            speedX += direction.x;
            speedY += direction.y;
        }

            vel.set(speedX,speedY);
        //movement
        if(speedY != 0 || speedX != 0){
            float v = MathUtils.clamp(vel.len(), 0, maxSpeed);
            vel.setLength(v);
            owner.getBody().setLinearVelocity(vel);

        }
        Vector2 direction2 = null;
        if((currentAction & LOOK_AT__MOUSE) != 0){
            aSpeed = 0;
            direction2 = new Vector2(x - owner.getBody().getPosition().x, y - owner.getBody().getPosition().y);
            float dAngle = (direction.angleRad()- owner.getBody().getAngle() - MathUtils.PI/2);
            while(dAngle > MathUtils.PI)
                dAngle -= MathUtils.PI2;
            while(dAngle < -MathUtils.PI)
                dAngle += MathUtils.PI2;
            aSpeed += dAngle * 10;
        }
        if(direction2 == null)
            owner.getBody().setAngularVelocity(aSpeed);
        else
            owner.getBody().setTransform(owner.getBody().getPosition(), direction2.angleRad() - MathUtils.PI/2);

        Gun g = ((Gun) owner.items.getOne("Gun"));
        if(g != null)
            g.setFire(((currentAction) & FIRE) != 0);
    }
    Vector2 mouseXY = new Vector2();
    private Vector3 temp3 = new Vector3();
    public void addAction(int action, float x, float y) {
        this.x = x;
        this.y = y;
        temp3.set(x, y, 0);
        temp3.set(owner.level.view.camera.project(temp3));
        mouseXY.set(temp3.x, temp3.y);
        addAction(action);
    }

    public void render(ShapeRenderer sr){
        sr.begin(ShapeRenderer.ShapeType.Line);
        if(path != null)
            sr.polyline(path.getVertices());
        sr.end();
    }
}
