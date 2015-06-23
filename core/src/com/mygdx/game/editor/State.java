package com.mygdx.game.editor;

import com.mygdx.game.editor.editorActions.EditorAction;

/**
 * Created by Alexander on 23.06.2015.
 */
public class State {
    public static final State DEFAULT = new State();
    public Level level;
    protected EditorAction action;

    public State() {
    }

    public void touchDown(float x, float y, int button) {

    }

    public void update(float dt){}

    public void mouseMoved(float x, float y) {

    }
}
