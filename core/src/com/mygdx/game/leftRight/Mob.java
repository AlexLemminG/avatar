package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.leftRight.physics.sensors.FixtureFabric;
import com.mygdx.game.leftRight.physics.sensors.OnGroundSensor;

import static com.mygdx.game.Utils.isInside;

/**
 * Created by Alexander on 17.06.2015.
 */
public class Mob extends GObject implements Updatable, ShapeDrawable{
    float x, y;
    float MAX_SPEED = 3;
    float speed = 0;
    private boolean dead;
    float width = 0.4f;
    float height = 1f;
    float width2 = width / 2;
    float height2 = height / 2;
    Body body;
    boolean jump = false;
    OnGroundSensor sensor;
    protected Color color = Color.BLACK;
    Fixture fix1;
    Fixture fix2;
    float friction = 1f;
    boolean onGround = false;

    public Mob(float x, float y) {
        this.x = x;
        this.y = y;
    }



    public void update(float dt){
        if(sensor.isOnGround() != onGround){
            onGround = sensor.isOnGround();
            if(onGround){
                setNotInAir();
            }else
                setIsInAir();
        }
        x = body.getPosition().x;
        y = body.getPosition().y;

        Vector2 v = body.getLinearVelocity();
        Vector2 groundVel = sensor.getAvarageGroundVelocity();
        if(Math.signum(groundVel.x - v.x) == Math.signum(speed) || isInside(groundVel.x - v.x + speed, -MAX_SPEED, MAX_SPEED)){
//            body.setLinearVelocity(v.x + speed / 2, v.y);
            body.applyForceToCenter(speed*10, 0, true);
        }
//        System.out.println(body.getFixtureList().get(0).getFriction() + "\t\t" + body.getFixtureList().get(1).getFriction() + "\t\t" + body.getFixtureList().get(2).getFriction());
        if(jump){
            jump = false;
            tryJump();
        }
    }

    int i = 0;
    public void setIsInAir(){
        fix1.setFriction(0f);
        fix2.setFriction(0f);
//        if(i++ > 0){
//            fix1.setFriction(1f);
//            fix2.setFriction(1f);
//        }
        updateFriction();
        System.out.println("In AIR");
    }
    private void setNotInAir(){
        fix1.setFriction(1f);
        fix2.setFriction(1f);
//        fix1.setFriction(1f);
//        fix2.setFriction(1f);
        updateFriction();
        System.out.println("Not in AIR");
    }

    private void updateFriction(){
//        fix1.setDensity((float)Math.random());
//        fix2.setDensity(1f);
//        body.resetMassData();

    }

    double delayBetweenJumps = 0.1;
    double lastJumpTime = -delayBetweenJumps;
    private void tryJump(){
        if(LeftRight.instance.time > delayBetweenJumps + lastJumpTime && sensor.isOnGround()){
            jump = false;
            lastJumpTime = LeftRight.instance.time;
            body.applyLinearImpulse(0, 3, 0, 0, true);
        }
    }

    public void render(ShapeRenderer sr){
        sr.setColor(color);
        sr.rect(x-width2, y-height2, width, height);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        if(!this.dead) {
            jump = true;
            for(Fixture f : body.getFixtureList())
                f.setSensor(true);
        }
        this.dead = dead;
    }

    public Body createBody(World world) {
        //fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.friction = friction;
        fixtureDef.restitution = 0;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width2, height2 - width2/2, new Vector2(0, width2/2), 0f);
        fixtureDef.shape = shape;

        CircleShape legsShape = new CircleShape();
        legsShape.setRadius(width2);
        legsShape.setPosition(new Vector2(0, -height2 + width2));
//        PolygonShape legsShape = new PolygonShape();
//        legsShape.setAsBox(width2, width2, new Vector2(0, -height2 + width2), 0);


        //body
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);
        fix1 = body.createFixture(fixtureDef);
        fixtureDef.shape = legsShape;
        fix2 = body.createFixture(fixtureDef);
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(width2 * 0.7f, 0.01f, new Vector2(0, -height2), 0);

        //sensors
        sensor = new OnGroundSensor(null);
        FixtureFabric.createFixtureSensor(body, sensorShape, sensor);
        this.body = body;
        return body;
    }
}
