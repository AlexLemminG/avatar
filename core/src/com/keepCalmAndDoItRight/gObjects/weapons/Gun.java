package com.keepCalmAndDoItRight.gObjects.weapons;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.gObjects.Bullet;
import com.keepCalmAndDoItRight.gObjects.Item;
import com.keepCalmAndDoItRight.gObjects.Unit;

/**
 * Created by Alexander on 06.07.2015.
 */
public class Gun extends Item {
    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    boolean fire;
    protected float timeNextBulletFired = 0f;
    protected float fireDelayTime = 1f;
    float bulletInitVelocity = 10;

    Vector2 tempVec2 = new Vector2();
    Vector2 tempVec1 = new Vector2();
    @Override
    public void update(float dt) {
        super.update(dt);
        if(fire && timeNextBulletFired <= owner.level.time && owner.hp > 0) {
            shoot();
            timeNextBulletFired = owner.level.time + fireDelayTime;
        }
    }

    protected void shoot(){
        tempVec2.set(owner.control.x-getPosition().x, owner.control.y-getPosition().y).setLength(bulletInitVelocity).rotate(0);
        for(int i = 0; i < 30; i++) {
            Bullet bullet = new Bullet(owner.level, owner);
            bullet.getBody().setTransform(getPosition().cpy(), owner.getBody().getAngle());
            tempVec1.set(tempVec2).setLength(MathUtils.random(bulletInitVelocity*0.9f, bulletInitVelocity*1.1f)).rotate(MathUtils.random(-15f, 15f));
            bullet.getBody().setLinearVelocity(tempVec1.x, tempVec1.y);
        }
    }



    public Gun(Unit unit) {
        super(unit.level);
        owner = unit;
        init();
        getActor().getParent().removeActor(getActor());
        unit.getActor().addActor(getActor());

        setPosition(getActor().getParent().getWidth() / 2f, getActor().getParent().getHeight());
//        level.os.removeBody(this);
    }

    public Gun(Level level) {
        super(level);
        init();
        getActor().setName("Gun");
    }

    @Override
    public Body createBody(World world) {
        return owner != null ? null : super.createBody(world);
    }
}
