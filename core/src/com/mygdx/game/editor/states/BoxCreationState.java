package com.mygdx.game.editor.states;

import com.mygdx.game.editor.SimpleBox;
import com.mygdx.game.editor.Consts;
import com.mygdx.game.editor.State;
import com.mygdx.game.editor.editorActions.CreateAction;

/**
 * Created by Alexander on 23.06.2015.
 */
public class BoxCreationState extends State{
    public BoxCreationState() {
    }

    @Override
    public void touchDown(float x, float y, int button) {
        super.touchDown(x, y, button);
        action = new CreateAction(new SimpleBox(x,y));
//        action.doIt();
        Consts.input.doAction(action);
    }
}
