package com.mygdx.game.editor.editorActions;

import java.util.LinkedList;

/**
 * Created by Alexander on 23.06.2015.
 */
public class MultipleAction extends EditorAction{
    LinkedList<EditorAction> actions = new LinkedList<EditorAction>();

    public MultipleAction(EditorAction... actions) {
        for(EditorAction a : actions)
            this.actions.add(a);
    }

    @Override
    public void doIt() {
        super.doIt();
        for(int i = 0; i < actions.size(); i++){
            actions.get(i).doIt();
        }
    }

    @Override
    public void undo() {
        super.undo();
        for(int i = actions.size()-1; i >=0; i--){
            actions.get(i).undo();
        }
    }
}
