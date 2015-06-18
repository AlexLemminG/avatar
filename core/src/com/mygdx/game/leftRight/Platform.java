package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


/**
 * Created by Alexander on 19.06.2015.
 */
public class Platform implements Updatable, ShapeDrawable{
    float x, y;
    float width2, height2;
    Color color = Color.GRAY;
    float height, width;
    Body body;

    public Platform(float x, float y, float width2, float height2) {
        this.x = x;
        this.y = y;
        this.width2 = width2;
        this.height2 = height2;
        width = width2 * 2;
        height = height2 * 2;
    }

    public Body createBody(World world){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width2, height2, new Vector2(0,0), 0);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);
        Body body = world.createBody(bodyDef);
        body.setType(BodyDef.BodyType.KinematicBody);
        body.createFixture(shape, 1);
        this.body = body;
        return body;
    }

    public void render(ShapeRenderer sr){
        sr.setColor(color);
        System.out.println(body.getPosition());
        sr.rect(body.getPosition().x-width2, body.getPosition().y-height2, width, height);
    }
    public void update(float dt){
        float wantedX = x + (float)Math.sin(LeftRight.instance.time / 2 * Math.PI);
        body.setLinearVelocity(wantedX - body.getPosition().x, 0);
    }
}
