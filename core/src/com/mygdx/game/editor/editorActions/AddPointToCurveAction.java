package com.mygdx.game.editor.editorActions;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.editor.Curve;

/**
 * Created by Alexander on 23.06.2015.
 */
public class AddPointToCurveAction extends EditorAction{
    Curve curve;

    public final Vector2 point;

    public AddPointToCurveAction(Curve curve, Vector2 point) {
        this.curve = curve;
        this.point = point.sub(curve.getPos());
    }

    @Override
    public void doIt() {
        super.doIt();
        curve.localPoints.add(point);
    }

    @Override
    public void undo() {
        super.undo();
        curve.localPoints.remove(point);
    }
}
