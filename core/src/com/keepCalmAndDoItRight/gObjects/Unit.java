package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.quick.ShapesCreator;

/**
 * Created by Alexander on 26.06.2015.
 */
public class Unit extends GObject implements Activatable{

    private static Texture UNIT_TEXTURE = new Texture(Gdx.files.internal("unit.png"));
    public UnitControl control;
    public ActionBox actionBox;

    @Override
    public void update(float dt) {
        control.update();
        super.update(dt);
    }

    public Unit(Level level) {
        super(level);
        control = new UnitControl(this);
//        activate();
        init();
        getPolygonRegion().getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    @Override
    public void createTexture() {
        setTexture(UNIT_TEXTURE);
    }

    @Override
    public Polygon createPolygon() {
        Polygon polygon = ShapesCreator.gismo(0.3f, .1f);
        setPolygon(polygon);
        return polygon;
    }

    @Override
    public Body createBody(World world) {
        Body body = super.createBody(world);
        actionBox = new ActionBox();
        actionBox.setFixture(body.createFixture(actionBox.getFixtureDef()));
        body.getFixtureList().first().setFilterData(Wall.wallFilter);
        return body;
    }

    @Override
    public void activate() {
        float r = MathUtils.random(.5f, 1f);
        float g = MathUtils.random(.5f, 1f);
        float b = MathUtils.random(.5f, 1f);
        getActor().setColor(r, g, b, 1);
        int action = Math.random() > 0.5 ? UnitControl.ROTATE_CLOCKWISE : UnitControl.ROTATE_COUNTER_CLOCKWISE;
        control.removeAction(UnitControl.ALL);
        control.addAction(UnitControl.ALL);
    }
}
