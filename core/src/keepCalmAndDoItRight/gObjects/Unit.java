package keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import keepCalmAndDoItRight.basics.GObject;
import keepCalmAndDoItRight.basics.Level;
import keepCalmAndDoItRight.quick.ShapesCreator;

/**
 * Created by Alexander on 26.06.2015.
 */
public class Unit extends GObject{

    private static Texture UNIT_TEXTURE = new Texture("assets/unit.png");
    public UnitControl control;

    @Override
    public void update(float dt) {
        control.update();
        super.update(dt);
    }

    public Unit(Level level) {
        super(level);
        control = new UnitControl(this);
    }

    @Override
    public void createTexture() {
        setTexture(UNIT_TEXTURE);
    }

    @Override
    public Polygon createPolygon() {
        Polygon polygon = ShapesCreator.gismo(0.5f, .5f);
        setPolygon(polygon);
        return polygon;
    }
}
