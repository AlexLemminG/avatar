package keepCalmAndDoItRight.basics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ShortArray;
import keepCalmAndDoItRight.GeometryUtils;

/**
 * Created by Alexander on 26.06.2015.
 */
public class GObject implements Disposable{
    private static EarClippingTriangulator triangulator = new EarClippingTriangulator();

    private boolean hasBody;
    private static Texture DEFAULT_TEXTURE = new Texture("assets/badlogic.jpg");

    Texture texture;
    Body body;
    Actor actor;
    PolygonRegion polygonRegion;
    Polygon polygon;
    float height = 0;
    float width = 0;

    public Body getBody() {
        return body;
    }
    public void setBody(Body body) {
        this.body = body;
    }
    public Actor getActor() {
        return actor;
    }
    public void setActor(Actor actor) {
        this.actor = actor;
    }


    public GObject(Level level){
        createPolygon();
        createBody(level.world);
        createTexture();
        createPolygonRegion();
        createActor(level.stage);
        hasBody = getBody() != null;
        level.os.put(this);
    }

    public void createTexture() {
        setTexture(DEFAULT_TEXTURE);
    }

    public void update(float dt){
        Vector2 pos = body.getPosition();
        float angle = body.getAngle() * MathUtils.radDeg;
        if(hasBody) {
            actor.setPosition(pos.x - actor.getOriginX(), pos.y - actor.getOriginY());
            actor.setRotation(angle);
        }
    }

    public Body createBody(World world){
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bDef);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = GeometryUtils.polygonToBox2dPolygon(polygon);
        fDef.isSensor = false;
        fDef.density = 1f;
        body.createFixture(fDef);
        setBody(body);
        return body;
    }

    public Polygon getPolygon(){
        return polygon;
    }
    public Polygon createPolygon(){
        Polygon polygon = GeometryUtils.boxPolygon(0.5f, 0.5f);
        polygon = new Polygon(polygon.getTransformedVertices());
        setPolygon(polygon);
        return polygon;
    }

    public void setPolygon(Polygon polygon) {

        this.polygon = polygon;
    }
    public PolygonRegion createPolygonRegion(){
        TextureRegion tr = new TextureRegion(texture);
        Polygon p = getPolygon();
        float[] vertices = p.getTransformedVertices();
        ShortArray triangles = triangulator.computeTriangles(vertices);
        float minx = Float.MAX_VALUE;
        float maxx = Float.MIN_VALUE;
        float miny =  Float.MAX_VALUE;
        float maxy =  Float.MIN_VALUE;
        for(int i = 0; i < vertices.length / 2; i++){
            minx = Math.min(minx, vertices[i*2]);
            miny = Math.min(miny, vertices[i * 2 + 1]);
            maxx = Math.max(maxx, vertices[i * 2]);
            maxy = Math.max(maxy, vertices[i*2+1]);
        }
        width = maxx - minx;
        height = maxy - miny;
        float scaleX = tr.getRegionWidth() / width;
        float scaleY = tr.getRegionHeight() / height;
        p.setOrigin(minx, miny);
        p.setPosition(-minx, -miny);
        p.setScale(scaleX, scaleY);
        polygonRegion = new PolygonRegion(tr, p.getTransformedVertices(), triangles.items);
        return polygonRegion;
    }

    public Actor createActor(Stage stage){
        Actor actor = new ActorWithPolygonRegion(polygonRegion);
        actor.setSize(width, height);
        actor.setOrigin(Align.center);

        stage.addActor(actor);
        setActor(actor);
        return actor;
    }

    @Override
    public void dispose() {
        if(polygonRegion != null)
            polygonRegion.getRegion().getTexture().dispose();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
