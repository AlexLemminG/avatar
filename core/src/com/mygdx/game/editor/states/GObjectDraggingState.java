package com.mygdx.game.editor.states;

import com.mygdx.game.editor.Consts;
import com.mygdx.game.editor.State;
import com.mygdx.game.editor.editorActions.MoveToAction;
import com.mygdx.game.leftRight.GObject;

/**
 * Created by Alexander on 23.06.2015.
 */
public class GObjectDraggingState extends State {
    GObject object;
    MoveToAction action = null;
    boolean dragging = false;
    float dx, dy;

    @Override
    public void touchDown(float x, float y, int button) {
        super.touchDown(x, y, button);
        if(dragging){
            action = null;
        }else {
            object = Consts.level.getObjectsAt(x, y);
            if(object == null)
                return;
            dx = x - object.getPos().x;
            dy = y - object.getPos().y;
            action = new MoveToAction(object, object.getPos());
            Consts.input.doAction(action);
        }
//        id++;
        dragging = !dragging;
    }

    @Override
    public void mouseMoved(float x, float y) {
        super.mouseMoved(x, y);
        if(action != null) {
            action.newPos.set(x-dx, y-dy);
            action.doIt();
        }
    }
}
