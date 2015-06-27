package keepCalmAndDoItRight.quick;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;


/**
 * Created by Alexander on 26.06.2015.
 */
public class ShapesCreator {
    public static CircleShape circle(float radius){
        CircleShape result = new CircleShape();
        result.setRadius(radius);
        return result;
    }

    public static PolygonShape box(float hw, float hh){
        PolygonShape result = new PolygonShape();
        result.setAsBox(hw, hh);
        return result;
    }

    public static Polygon gismo(float radius, float width){
        float w2 = width / 2;
        int n = 8;
        int n2 = n*2;
        float[] x = new float[n];
        float[] y = new float[n];

        float phi = MathUtils.PI / (n / 2-1);
        for(int i = 0; i < n/2; i++){
            x[i] = MathUtils.sin(phi * i)*radius+w2;
            y[i] = MathUtils.cos(phi * i)*radius;
        }
        for(int i = n/2; i < n; i++){
            x[i] = -x[n-i-1];
            y[i] = y[n-i-1];
        }



        float[] vertices = new float[n2];
        for(int i = 0; i < n; i++){
            vertices[i*2] = x[i];
            vertices[i*2+1]=y[i];
        }

        return new Polygon(vertices);
    }
}
