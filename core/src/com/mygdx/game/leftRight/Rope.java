package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alexander on 20.06.2015.
 */
public class Rope implements ShapeDrawable, Updatable{
    Body[] particles;
    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        float[] vertices = new float[particles.length * 2];
        Vector2 vector2;
        for(int i = 0; i < particles.length; i++){
            vector2 = particles[i].getPosition();
            vertices[i*2] = vector2.x;
            vertices[i*2+1] = vector2.y;
        }
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.polyline(vertices);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    @Override
    public void update(float dt) {
        float V = 0;
        for(int i = 1; i < particles.length; i++){
            Vector2 delta = new Vector2();
            delta.x = particles[i-1].getPosition().x - particles[i].getPosition().x;
            delta.y = particles[i-1].getPosition().y - particles[i].getPosition().y;
            V += (particles[i].getPosition().y + particles[i-1].getPosition().y) * Math.abs(delta.x * 0.5f);
            delta.sub(delta.cpy().nor());
            delta.scl(0.5f);

//            if(delta.len2() > 1)
//            Vector2 vel1 = particles[i].getLinearVelocity();
//                    particles[i].applyForceToCenter(delta.scl(10), true);
//            particles[i-1].applyForceToCenter(delta.scl(-1), true);
        }
        System.out.println(V);
        float vMust = 5;
        float yMust = vMust / (particles.length-1) * 10;
        for(int i = 0; i < particles.length; i++){
            Body b = particles[i];
            float y = b.getPosition().y;
            float x = i/10f-20;
            b.setTransform(x,y,0);
//            b.applyForceToCenter(0, -(V-vMust)*10, true);
            b.applyForceToCenter(0, -(y-yMust)*100, true);
            b.applyForceToCenter(b.getLinearVelocity().cpy().scl(-1f),true);
        }

    }

    public void createBody(World world){
        particles = new Body[100];
        FixtureDef fDef;
        for(int i = 0; i < 100; i++){
            BodyDef bDef = new BodyDef();
            bDef.type = BodyDef.BodyType.DynamicBody;
            bDef.position.set(i/10f-20, 5);
            bDef.fixedRotation = true;
            Body b = world.createBody(bDef);
            fDef = new FixtureDef();
            CircleShape shape = new CircleShape();
            shape.setRadius(0f);
            fDef.density = 0.001f;
            fDef.friction = 1f;
            shape.setPosition(new Vector2(0, 0));
            fDef.shape = shape;
            b.createFixture(fDef);
            particles[i] = b;

        }

    }
}
