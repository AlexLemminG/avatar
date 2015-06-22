package com.mygdx.game.editor.editorActions;

import com.mygdx.game.editor.Consts;
import com.mygdx.game.leftRight.GObject;

/**
 * Created by Alexander on 23.06.2015.
 */
public class CreateAction extends EditorAction{
    //TODO labudi labudai
    GObject object;

    public CreateAction(GObject object) {
        this.object = object;
    }

    @Override
    public void doIt() {
        super.doIt();
        Consts.level.os.put(object);
    }

    @Override
    public void undo() {
        super.undo();
        Consts.level.os.remove(object);

    }
}
