package com.mygdx.game.editor.editorActions;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.editor.Curve;

/**
 * Created by Alexander on 23.06.2015.
 */
public class AddPointToCurveAction extends EditorAction{
    Curve curve;
    int lastAddedIndex = -1;
    boolean added = false;

    public final Vector2 point;

    public AddPointToCurveAction(Curve curve, Vector2 point) {
        this.curve = curve;
        this.point = point;
    }

    @Override
    public void doIt() {
        super.doIt();
        added = curve.add(point);
        lastAddedIndex = curve.localPoints.size()-1;
    }

    @Override
    public void undo() {
        super.undo();
        if(added)
            curve.localPoints.remove(lastAddedIndex);
    }
}
