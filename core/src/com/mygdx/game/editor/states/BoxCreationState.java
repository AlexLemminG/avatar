package com.mygdx.game.editor.states;

import com.mygdx.game.editor.SimpleBox;
import com.mygdx.game.editor.Consts;
import com.mygdx.game.editor.State;
import com.mygdx.game.editor.editorActions.CreateAction;
import com.mygdx.game.editor.editorActions.CreateBody;
import com.mygdx.game.editor.editorActions.MultipleAction;

/**
 * Created by Alexander on 23.06.2015.
 */
public class BoxCreationState extends State{
    public BoxCreationState() {
    }

    @Override
    public void touchDown(float x, float y, int button) {
        super.touchDown(x, y, button);
//        action = ;
//        movePointTo.doIt();
        SimpleBox box = new SimpleBox(x,y);
        Consts.input.doAction(new MultipleAction(new CreateAction(box), new CreateBody(box)));
    }
}
