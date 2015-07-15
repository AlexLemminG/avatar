package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.utils.Array;
import com.keepCalmAndDoItRight.basics.*;
import com.keepCalmAndDoItRight.quick.Consts;
import com.keepCalmAndDoItRight.quick.GeometryUtils;

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

    public Bullet(final Level level, final Unit owner) {
        super(level);
        timeDisableBullet = level.time + 0f;
        timeRemoveBullet = level.time + 3f;
        setPolygon(GeometryUtils.boxPolygon(0.05f, 0.05f));
        init();
        friction.setMaxTorque(0.0f);
        friction.setMaxForce(0.0f);
        body.getFixtureList().first().setRestitution(0.03f);
        body.setBullet(true);
        final GObject t = this;

        body.getFixtureList().first().setUserData(new ReactToContacts() {
            Array<GObject> toched = new Array<>();
            @Override
            public void touched(final GObject b) {
                if(b instanceof Unit && ((Unit) b).unitType != owner.unitType)
                if(!toched.contains(b, true)) {
                    t.actions.add(new Act() {
                        @Override
                        public void act() {
                            ((Unit) b).decreeseHP(40);
                        }
                    });
                    toched.add(b);
                }
                if(!(b == owner))
                t.actions.add(new Act() {
                    @Override
                    public void act() {
                        level.os.remove(t);
                    }
                });
            }

            @Override
            public void untouched(GObject b) {
                toched.removeValue(b, true);
            }
        });
        getBody().getFixtureList().first().setFilterData(Consts.BULLET_FILTER);
    }
}
