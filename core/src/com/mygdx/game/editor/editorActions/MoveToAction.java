package com.mygdx.game.editor.editorActions;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.leftRight.GObject;

/**
 * Created by Alexander on 23.06.2015.
 */
public class MoveToAction extends EditorAction{
    Vector2 oldPos;
    public Vector2 newPos;
    GObject object;

    public MoveToAction(GObject object, Vector2 newPos) {
        this.newPos = newPos;
        oldPos = new Vector2(object.getPos());
        this.object = object;
    }

    @Override
    public void doIt() {
        super.doIt();
        object.setPos(newPos);
    }

    @Override
    public void undo() {
        super.undo();
        object.setPos(oldPos);
    }
}
