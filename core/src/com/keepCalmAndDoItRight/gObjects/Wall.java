package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.keepCalmAndDoItRight.basics.Assets;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.quick.GrUtils;
import com.keepCalmAndDoItRight.quick.ShapesCreator;

/**
 * Created by Alexander on 27.06.2015.
 */
public class Wall extends GObject{

    public static Filter wallFilter = new Filter();
    static{
        wallFilter.categoryBits = 0b0010;
        wallFilter.maskBits = 0b1101;
        wallFilter.groupIndex = 0;
    }


//    private Polygon basisPolygon;
    private float wallWidth;
    private boolean circled;

    public Wall(Level level, Polygon basisPolygon, float wallWidth, boolean circled) {
        super(level, basisPolygon);
        this.wallWidth = wallWidth;
        this.circled = circled;
        init();
    }

    @Override
    public Body createBody(World world) {
        Polygon[] segmentsPolygons = ShapesCreator.poligonToWallSegments(getPolygon(), wallWidth, circled);
        for(Polygon p : segmentsPolygons){
            new WallSegment(level, p);
        }
        return null;
    }

    @Override
    public void createTexture() {
        setTexture(null);
    }

    public static class WallSegment extends GObject{

        public WallSegment(Level level, Polygon polygon) {
            super(level, polygon);
            setTexture(Assets.WALL_TEXTURE);
            init();
            getBody().setType(BodyDef.BodyType.StaticBody);
            getBody().getFixtureList().first().setFilterData(wallFilter);
//            getBody().getFixtureList().get(1).setFilterData(wallFilter);
        }

        @Override
        public PolygonRegion createPolygonRegion() {
            polygonRegion =  GrUtils.createPolygonRegion(texture, polygon, 10, 10);
            return polygonRegion;
        }
    }
}
