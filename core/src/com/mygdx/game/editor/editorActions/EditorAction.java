package com.mygdx.game.editor.editorActions;

/**
 * Created by Alexander on 23.06.2015.
 */
public class EditorAction {
    int id;
    public void doIt(){}
    public void undo(){}

    public EditorAction reversed() {
        final EditorAction a = this;
        return new EditorAction(){
            @Override
            public void doIt() {
                a.undo();
            }
            @Override
            public void undo() {
                a.doIt();
            }
        };
    }
}
