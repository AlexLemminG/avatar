package com.mygdx.game.editor.editorActions;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.editor.Consts;
import com.mygdx.game.leftRight.CanCreateBody;

/**
 * Created by Alexander on 23.06.2015.
 */
public class CreateBody extends EditorAction{
    Body body;
    CanCreateBody gObject;

    public CreateBody(CanCreateBody gObject) {
        this.gObject = gObject;
    }

    @Override
    public void doIt() {
        super.doIt();
        body = gObject.createBody(Consts.level.world);
    }

    @Override
    public void undo() {
        super.undo();
        Consts.level.world.destroyBody(body);
    }
}
