package com.keepCalmAndDoItRight.gObjects.weapons;

import com.badlogic.gdx.math.MathUtils;
import com.keepCalmAndDoItRight.gObjects.Bullet;
import com.keepCalmAndDoItRight.gObjects.Unit;

/**
 * Created by Alexander on 06.07.2015.
 */
public class GunsGenerator {
    public static Gun getShotgun(Unit owner){
        Gun shotgun = new Gun(owner){
            @Override
            protected void shoot() {
                tempVec2.set(owner.control.x-getPosition().x, owner.control.y-getPosition().y).setLength(bulletInitVelocity).rotate(0);
                for(int i = 0; i < 30; i++) {
                    Bullet bullet = new Bullet(owner.level, owner);
                    bullet.getBody().setTransform(getPosition().cpy(), owner.getBody().getAngle());
                    tempVec1.set(tempVec2).setLength(MathUtils.random(bulletInitVelocity * 0.9f, bulletInitVelocity * 1.1f)).rotate(MathUtils.random(-15f, 15f));
                    bullet.getBody().setLinearVelocity(tempVec1.x, tempVec1.y);
                }
            }
        };
        shotgun.bulletInitVelocity = 10f;
        shotgun.fireDelayTime = 1f;
        shotgun.getActor().setName("Gun");
        return shotgun;
    }
    public static Gun getPistol(Unit owner){
        Gun shotgun = new Gun(owner){
            @Override
            protected void shoot() {
                tempVec2.set(owner.control.x-getPosition().x, owner.control.y-getPosition().y).setLength(bulletInitVelocity).rotate(0);
                Bullet bullet = new Bullet(owner.level, owner);
                bullet.getBody().setTransform(getPosition().cpy(), owner.getBody().getAngle());
                bullet.getBody().setLinearVelocity(tempVec2.x, tempVec2.y);
            }
        };
        shotgun.bulletInitVelocity = 20f;
        shotgun.fireDelayTime = 0.3f;
        shotgun.getActor().setName("Gun");
        return shotgun;
    }
}
