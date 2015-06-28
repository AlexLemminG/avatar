package com.keepCalmAndDoItRight.basics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ShortArray;
import com.keepCalmAndDoItRight.GeometryUtils;
import com.keepCalmAndDoItRight.quick.GrUtils;

/**
 * Created by Alexander on 26.06.2015.
 */
public class GObject implements Disposable{
    protected static DelaunayTriangulator triangulator = new DelaunayTriangulator();

    protected boolean hasBody;
    private static Texture DEFAULT_TEXTURE = new Texture(Gdx.files.internal("badlogic.jpg"));

    protected Texture texture;
    protected Body body;
    protected Actor actor;
    protected Level level;

    public GObject(Level level, Polygon polygon) {
        this(level);
        setPolygon(polygon);
    }

    public void init(){
        if(polygon == null) createPolygon();
        if(body == null) createBody(level.world);
        hasBody = getBody() != null;
        if(texture == null) createTexture();
        if(polygonRegion == null) createPolygonRegion();
        if(actor == null) createActor(level.stage);
        if(!level.os.allGObjects.contains(this, true)) level.os.put(this);
    }

    public PolygonRegion getPolygonRegion() {
        return polygonRegion;
    }

    protected PolygonRegion polygonRegion;
    protected Polygon polygon;
    protected float height = 0;
    protected float width = 0;

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
        this.level = level;
    }

    public void createTexture() {
        setTexture(DEFAULT_TEXTURE);
    }

    public void update(float dt){
        if(hasBody) {
            Vector2 pos = body.getPosition();
            float angle = body.getAngle() * MathUtils.radDeg;
            actor.setPosition(pos.x - actor.getOriginX(), pos.y - actor.getOriginY());
            actor.setRotation(angle);
        }
    }

    public Body createBody(World world){
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bDef);
        float[] vertices = polygon.getTransformedVertices();
        ShortArray s = triangulator.computeTriangles(vertices, true);

        for(int i = 0; i < s.size/3; i++) {
            FixtureDef fDef = new FixtureDef();
            PolygonShape p = new PolygonShape();
            int i1 = s.get(i*3)*2;
            int i2 = s.get(i*3+1)*2;
            int i3 = s.get(i*3+2)*2;

            p.set(new float[]{
                    vertices[i1], vertices[i1+1],
                    vertices[i2], vertices[i2+1],
                    vertices[i3], vertices[i3+1]
            });
            fDef.shape = p;
            fDef.isSensor = false;
            fDef.density = 1f;
            body.createFixture(fDef);
        }
        body.setUserData(this);
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
        if(texture == null) return null;
        polygonRegion = GrUtils.createPolygonRegion(texture, polygon);
        return polygonRegion;
    }

    public Actor createActor(Stage stage){
        Actor actor;
        if(polygonRegion != null)
            actor = new ActorWithPolygonRegion(polygonRegion);
        else{
            actor = new Actor();
        }
        if(!hasBody)
            actor.setOrigin(Align.center);
        else {
            Rectangle b = polygon.getBoundingRectangle();
            float minx = b.getX();
            float miny = b.getY();
            float maxx = b.getWidth() + minx;
            float maxy = b.getHeight() + miny;
            actor.setSize(b.getWidth(), b.getHeight());
            Vector2 origin = new Vector2((maxx + minx) / 2, (maxy + miny) / 2);

            actor.setOrigin(actor.getWidth() / 2 - origin.x, actor.getHeight() / 2 - origin.y);
        }
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
