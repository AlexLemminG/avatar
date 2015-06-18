package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.leftRight.physics.sensors.FixtureFabric;
import com.mygdx.game.leftRight.physics.sensors.OnGroundSensor;

import static com.mygdx.game.Utils.*;

/**
 * Created by Alexander on 17.06.2015.
 */
public class Player {
    Color color = Color.BLACK;
    float x, y;
    float speed = 0;
    boolean dead;
    double MAX_SPEED = 3;
    public float width = 0.4f;
    public float height = 1;
    private float width2 = width / 2;
    private float height2 = height / 2;
    OnGroundSensor sensor;

    Body body;
    public boolean jump;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float dt){
        x = body.getPosition().x;
        y = body.getPosition().y;
        
        if(isInside(body.getLinearVelocity().x, -MAX_SPEED, MAX_SPEED)){
            body.applyLinearImpulse(sign((int)speed)/10.0f, 0, 0, 0, true);
        }
        if(jump){
            jump = false;
            tryJump();
        }
        System.out.println(sensor.isOnGround());
    }

    private void tryJump(){
        if(sensor.isOnGround()){
            jump = false;
            body.applyLinearImpulse(0, 2, 0, 0, true);
        }
    }

    public void render(ShapeRenderer sr){
        if(dead)
            return;
        sr.setColor(color);
        sr.rect(x - width2, y - height2, width, height);
    }

    public Body createBody(World world) {
        //fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.friction = 2;
        fixtureDef.restitution = 0;
        PolygonShape shape = new PolygonShape();
        shape.set(new float[]{-width / 2, -height / 2,
                width / 2,  -height / 2,
                width / 2, height / 2,
                -width / 2, height / 2});
        fixtureDef.shape = shape;

        //body
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(width2, 0.01f, new Vector2(0, -height2), 0);

        //sensors
        sensor = new OnGroundSensor();
        FixtureFabric.createFixtureSensor(body, sensorShape, sensor);
        this.body = body;
        return body;
    }
}
