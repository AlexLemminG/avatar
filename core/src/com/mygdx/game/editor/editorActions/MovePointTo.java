package com.mygdx.game.editor.editorActions;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander on 23.06.2015.
 */
public class MovePointTo extends EditorAction{
    Vector2 oldPos;
    public Vector2 newPos;
//    GObject object;
    Vector2 point;

    public MovePointTo(Vector2 point, Vector2 newPos) {
        this.newPos = newPos;
        oldPos = new Vector2(point);
//        this.object = object;
        this.point = point;
    }

    @Override
    public void doIt() {
        super.doIt();
        point.set(newPos);
//        .setPos(newPos);
    }

    @Override
    public void undo() {
        super.undo();
        point.set(oldPos);
    }
}
