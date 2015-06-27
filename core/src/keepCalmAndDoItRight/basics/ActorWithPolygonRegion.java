package keepCalmAndDoItRight.basics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Scaling;

/**
 * Created by Alexander on 26.06.2015.
 */
public class ActorWithPolygonRegion extends Actor{

    private PolygonRegion polygonRegion;

    public ActorWithPolygonRegion(PolygonRegion polygonRegion) {
        super();
        this.polygonRegion = polygonRegion;
        minW = polygonRegion.getRegion().getRegionWidth();
        minH = polygonRegion.getRegion().getRegionHeight();
        minW = 1;
        minH = 1;
        float[] vertices = polygonRegion.getVertices();
        for(int i = 0; i < vertices.length / 2; i++){
            minW = Math.max(minW, vertices[i*2]);
            minH = Math.max(minH, vertices[i*2+1]);
        }
    }

    float minH;
    float minW;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        PolygonSpriteBatch b = (PolygonSpriteBatch) batch;
        Color color = getColor();
        if(polygonRegion != null){
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

            float x = getX();
            float y = getY();
            float scaleX = getScaleX();
            float scaleY = getScaleY();

            float rotation = getRotation();
            Scaling scaling = Scaling.stretch;
            float width = getWidth();
            float height = getHeight();
            Vector2 size = scaling.apply(minW, minH, width, height);
            float imageWidth = size.x;
            float imageHeight = size.y;
//            imageHeight = 1;
//            imageWidth = 1;
            b.flush();
            b.draw(polygonRegion, x, y, getOriginX(), getOriginY(), imageWidth, imageHeight, scaleX, scaleY, rotation);
//                    ((TransformDrawable)drawable).draw(batch, x + imageX, y + imageY, getOriginX() - imageX, getOriginY() - imageY,
//                            imageWidth, imageHeight, scaleX, scaleY, rotation);
        }
        super.draw(batch, parentAlpha * color.a);

    }
}
