package com.mygdx.game.editor.states;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.editor.Consts;
import com.mygdx.game.editor.State;
import com.mygdx.game.editor.editorActions.CreateBody;
import com.mygdx.game.editor.editorActions.EditorAction;
import com.mygdx.game.editor.editorActions.MovePointTo;
import com.mygdx.game.editor.editorActions.MultipleAction;
import com.mygdx.game.leftRight.CurvyWall;
import com.mygdx.game.leftRight.GObject;

/**
 * Created by Alexander on 23.06.2015.
 */
public class CurveEditingState extends State{
    CurvyWall curve;
    EditorAction action;


    public CurveEditingState() {
//        this.curve = curvyWall;
    }

    GObject object;
    MovePointTo movePointTo = null;
    boolean dragging = false;
//    float dx, dy;

    @Override
    public void touchDown(float x, float y, int button) {
        Vector2 pointer = new Vector2(x,y);

        super.touchDown(x, y, button);
        if(curve == null) {
            float minDist2 = Float.MAX_VALUE;
            for (GObject o : Consts.level.os.allGObjects) {
                if(o instanceof CurvyWall) {
                    for(Vector2 p : ((CurvyWall) o).initShape.localPoints)
                        if(p.cpy().add(o.getPos()).dst2(pointer) < minDist2) {
                            curve = (CurvyWall) o;
                            minDist2 = p.cpy().add(o.getPos()).dst2(pointer);
                        }
                }
            }
            return;
        }

        if(dragging){
            movePointTo = null;
        }else {
            object = Consts.level.getObjectsAt(x, y);
            if(object == null)
                return;
//            dx = x - object.getPos().x;
//            dy = y - object.getPos().y;
            Vector2 closest = null;
            float closestDist2 = Float.MAX_VALUE;
            pointer.sub(curve.getPos());

            for(int i = 0; i < curve.initShape.localPoints.size(); i++)
                if(curve.initShape.localPoints.get(i).dst2(pointer) < closestDist2){
                    closest = curve.initShape.localPoints.get(i);
                    closestDist2 = curve.initShape.localPoints.get(i).dst2(pointer);
                }


            if(closest != null) {
                movePointTo = new MovePointTo(closest, pointer);
                action = new MultipleAction(movePointTo, new CreateBody(curve).reversed(), new CreateBody(curve));
                Consts.input.doAction(action);
            }

        }
//        id++;
        dragging = !dragging;
    }

    @Override
    public void mouseMoved(float x, float y) {
        super.mouseMoved(x, y);
        if(movePointTo != null) {
            movePointTo.newPos.set(x-curve.getPos().x, y-curve.getPos().y);
            action.undo();
            action.doIt();
        }
    }
}
