package com.mygdx.game.leftRight;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.editor.Curve;
import com.mygdx.game.leftRight.geometry.GeometryUtils;
import com.mygdx.game.leftRight.geometry.PolygonShape;

/**
 * Created by Alexander on 22.06.2015.
 */
public class CurvyWall extends GObject implements CanCreateBody{
    public Curve initShape;
    public CatmullRomSpline<Vector2> spline;


    @Override
    public Body getBody() {
        return body;
    }

    Body body;

    public CurvyWall(float x, float y, Curve initShape) {
        this.initShape = initShape;
        setPos(new Vector2(x,y));
    }

    public CurvyWall(Curve curve){
        setPos(curve.getPos());
        initShape = curve;
    }

    @Override
    public Body createBody(World world) {
        if(initShape.localPoints.size() <= 1)
            return null;
        Vector2[] cPoints;
        Curve c = new Curve();

        if(initShape.localPoints.size() <= 3){
            cPoints = new Vector2[initShape.localPoints.size() + 2];
            cPoints[0] = initShape.localPoints.get(0);
            for(int i = 0; i < initShape.localPoints.size(); i++ ){
                cPoints[i+1] = initShape.localPoints.get(i);
            }
            cPoints[initShape.localPoints.size() +1] = initShape.localPoints.get(initShape.localPoints.size() -1);
        }else {
            cPoints = new Vector2[initShape.localPoints.size()];
            for(int i = 0; i < initShape.localPoints.size(); i++ ){
                cPoints[i] = initShape.localPoints.get(i);
            }
        }

        spline = new CatmullRomSpline<Vector2>(cPoints, false);


//        BSpline<Vector2> spline = new BSpline<Vector2>(cPoints, 3, false);
            int n = 30;
            for (int i = 0; i <= n; i++) {
                float t = i * 1f / n;
                Vector2 point = new Vector2();
                spline.valueAt(point, t);
                c.localPoints.add(point);
            }


        PolygonShape[] polygonShapes = GeometryUtils.poligonToWallSegments(new PolygonShape(c.getVertex()), 10f, false);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getPos());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        for(PolygonShape p : polygonShapes){
            com.badlogic.gdx.physics.box2d.PolygonShape s = new com.badlogic.gdx.physics.box2d.PolygonShape();
            s.set(p.toPolygon());
            body.createFixture(s, 1);
        }

        return body;
    }
}
