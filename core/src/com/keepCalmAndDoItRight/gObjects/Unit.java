package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.keepCalmAndDoItRight.basics.Assets;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.gObjects.weapons.GunsGenerator;
import com.keepCalmAndDoItRight.quick.GeometryUtils;
import com.keepCalmAndDoItRight.quick.Items;

/**
 * Created by Alexander on 26.06.2015.
 */
public class Unit extends GObject implements Activatable{
    public enum UnitType{
        player, zombie
    }
    Items items = new Items();
    public UnitType unitType = UnitType.zombie;
    public UnitControl control;
    public ActionBox actionBox;
    public float hp = 100f;
    public void decreeseHP(float dhp){
        hp -= dhp;
        if(hp <= 0){
//            level.os.remove(this);
//            getBody().getWorld().destroyBody(getBody());
            destroyBody();
            getActor().setColor(1, 0, 0, 0.5f);
            getActor().toBack();
        }
    }

    public void debugRender(ShapeRenderer sr){
        control.render(sr);
    }

    @Override
    public void update(float dt) {
        if(getBody() != null)
            control.update();

            super.update(dt);
    }

    public Unit(Level level) {
        super(level);
        control = new UnitControl(this);
//        activate();
        init();
        getPolygonRegion().getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        items.put(GunsGenerator.getPistol(this));
    }


    @Override
    public void createTexture() {
        setTexture(Assets.UNIT_TEXTURE);
    }

    float gismoHeight = .01f;
    float gismoRadius = 0.3f;
    @Override
    public Polygon createPolygon() {
        Polygon polygon = GeometryUtils.boxPolygon(gismoRadius + gismoHeight/2, gismoRadius);
        setPolygon(polygon);
        return polygon;
    }

    @Override
    public Body createBody(World world) {
        CircleShape s1= new CircleShape();
        s1.setPosition(new Vector2(-gismoHeight/2/2, 0));
        s1.setRadius(gismoRadius);
        CircleShape s2= new CircleShape();
        s2.setPosition(new Vector2(gismoHeight/2, 0));
        s2.setRadius(gismoRadius);
        PolygonShape s3 = new PolygonShape();
        s3.setAsBox(gismoHeight/2, gismoRadius);

        Body body = super.createBody(world, s1, s2, s3);
        for(Fixture f : body.getFixtureList()){
            f.setFriction(0);
        }

        actionBox = new ActionBox();
        actionBox.setFixture(body.createFixture(actionBox.getFixtureDef()));
        return body;
    }

    @Override
    public void activate() {
        float r = MathUtils.random(.5f, 1f);
        float g = MathUtils.random(.5f, 1f);
        float b = MathUtils.random(.5f, 1f);
        getActor().setColor(r, g, b, 1);
        int action = Math.random() > 0.5 ? UnitControl.ROTATE_CLOCKWISE : UnitControl.ROTATE_COUNTER_CLOCKWISE;
//        control.removeAction(UnitControl.ALL);
//        control.addAction(UnitControl.ACTIVATE);
    }
}
