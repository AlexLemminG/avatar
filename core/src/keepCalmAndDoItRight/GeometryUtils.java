package keepCalmAndDoItRight;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Alexander on 26.06.2015.
 */
public class GeometryUtils {
    public static PolygonShape polygonToBox2dPolygon(Polygon polygon){
        PolygonShape result = new PolygonShape();
        result.set(polygon.getTransformedVertices());
        return result;
    }

    public static Polygon boxPolygon(float hx, float hy){
        Polygon p = new Polygon();
        p.setVertices(new float[]{
                -hx, -hy,
                -hx, hy,
                hx, hy,
                hx, -hy
        });
        return p;
    }
}
