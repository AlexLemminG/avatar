package com.mygdx.game.editor.states;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.editor.Consts;
import com.mygdx.game.editor.SimpleBox;
import com.mygdx.game.editor.State;
import com.mygdx.game.leftRight.CurvyWall;
import com.mygdx.game.leftRight.GObject;

/**
 * Created by Alexander on 23.06.2015.
 */
public class NormalToSplineTestState extends State {
    SimpleBox box;

    public NormalToSplineTestState() {
        this.box = new SimpleBox(0,0);
        box.createBody(Consts.level.world);
    }

    @Override
    public void mouseMoved(float x, float y) {
        super.mouseMoved(x, y);
        Vector2 pointer = new Vector2(x,y);
        box.setPos(x, y);
        Vector2 closest = new Vector2();
        Vector2 normal = new Vector2();
        float closestDist2 = Float.MAX_VALUE;
        for(GObject o : level.os.allGObjects){
            if(o instanceof CurvyWall){
                float t = ((CurvyWall) o).spline.locate(pointer.cpy().sub(o.getPos()));
                Vector2 proj = new Vector2();
                proj = ((CurvyWall) o).spline.valueAt(proj, t);

                System.out.println(t);
                proj.add(o.getPos());
                if(proj.dst2(pointer) < closestDist2){
                    closest.set(proj);
                    closestDist2 = proj.dst2(pointer);
                    normal.set(((CurvyWall) o).spline.derivativeAt(normal, t));
                }
            }
        }

        float angle = closest.cpy().sub(pointer).angleRad();
        angle = normal.angleRad();
        box.getBody().setTransform(closest.x,closest.y, angle);
//        box.setPos(closest);
    }
}
