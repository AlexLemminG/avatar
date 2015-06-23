package com.mygdx.game.editor.editorActions;

import com.mygdx.game.editor.Consts;
import com.mygdx.game.leftRight.CanCreateBody;

/**
 * Created by Alexander on 23.06.2015.
 */
public class CreateBody extends EditorAction{
//    Body body;
    CanCreateBody gObject;

    public CreateBody(CanCreateBody gObject) {
        this.gObject = gObject;
//        body = gObject.getBody();
    }

    @Override
    public void doIt() {
        super.doIt();
        gObject.createBody(Consts.level.world);
    }

    @Override
    public void undo() {
        super.undo();
        if(gObject.getBody() != null)
        Consts.level.world.destroyBody(gObject.getBody());
    }
}
