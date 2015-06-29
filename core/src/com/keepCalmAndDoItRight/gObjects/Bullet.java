package com.keepCalmAndDoItRight.gObjects;

import com.keepCalmAndDoItRight.GeometryUtils;
import com.keepCalmAndDoItRight.basics.Assets;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.basics.Level;

/**
 * Created by Alexander on 28.06.2015.
 */
public class Bullet extends GObject {
    float timeRemoveBullet = 0;
    float timeDisableBullet = 0;
    boolean bullet = true;
    @Override
    public void update(float dt) {
        super.update(dt);
        if(bullet && level.time > timeDisableBullet) {
            bullet = false;
            getBody().setBullet(false);
        }
        if(level.time > timeRemoveBullet){
            level.os.remove(this);
        }

    }

    @Override
    public void createTexture() {
        setTexture(Assets.BULLET_TEXTURE);
    }

    public Bullet(final Level level) {
        super(level);
        timeDisableBullet = level.time + 0.5f;
        timeRemoveBullet = level.time + 3f;
        setPolygon(GeometryUtils.boxPolygon(0.1f, 0.1f));
        init();
        friction.setMaxTorque(0.1f);
        friction.setMaxForce(0.5f);
        body.getFixtureList().first().setRestitution(0.03f);
        body.setBullet(true);
        final GObject t = this;
        /*
        body.getFixtureList().first().setUserData(new ReactToContacts() {
            Array<GObject> toched = new Array();
            @Override
            public void touched(final GObject b) {
                if(!toched.contains(b, true)) {
                    t.actions.add(new Act() {
                        @Override
                        public void act() {
//                            level.os.remove(b);
                            level.os.remove(t);
                        }
                    });
                    toched.add(b);
                }
//                body.getWorld().destroyBody(b.getBody());
            }

            @Override
            public void untouched(GObject b) {
                toched.removeValue(b, true);
            }
        });
        */
    }
}
