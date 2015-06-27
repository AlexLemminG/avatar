package com.mygdx.game.editor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.editor.editorActions.EditorAction;
import com.mygdx.game.editor.states.*;

import java.util.ArrayList;
import java.util.LinkedList;

import static com.badlogic.gdx.Input.Keys.*;

/**
 * Created by Alexander on 23.06.2015.
 */
public class InputListener extends InputAdapter{
    Level level;
    private State state;
    private LinkedList<Integer> keyDown = new LinkedList<Integer>();
    public ArrayList<EditorAction> actions = new ArrayList<EditorAction>();
    Vector2 pointerPos = new Vector2();

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 temp = level.camera.unproject(new Vector3(screenX, screenY, 0));
        pointerPos.set(temp.x, temp.y);
        state.mouseMoved(pointerPos.x, pointerPos.y);
        return super.mouseMoved(screenX, screenY);
    }

    int lastDoneAction = 0;
    public void undo(){
        if(lastDoneAction >= 0)
            actions.get(lastDoneAction--).undo();
    }
    public void redoAll(){
        for(int i = lastDoneAction + 1; i < actions.size(); i++){
            actions.get(i).doIt();
        }
        lastDoneAction = actions.size()-1;
    }
    public void doAction(EditorAction action){
        for(int i = actions.size()-1; i > lastDoneAction; i--)
            actions.remove(i);
        actions.add(action);
        action.doIt();
        lastDoneAction = actions.size()-1;
    }


    @Override
    public boolean keyUp(int keycode) {
        keyDown.remove(Integer.valueOf(keycode));
        return super.keyUp(keycode);

    }

    @Override
    public boolean keyDown(int keycode) {
        keyDown.add(keycode);


        if(keycode == Z && keyDown.contains(Input.Keys.CONTROL_LEFT) && keyDown.contains(Input.Keys.SHIFT_LEFT)){
            redoAll();
        }else
        if(keycode == Z && keyDown.contains(Input.Keys.CONTROL_LEFT)){
            undo();
        }
        if(keycode == M){
            setState(new GObjectDraggingState());
        }
        if(keycode == P){
            setState(new CurveDrawingState());
        }
        if(keycode == O){
            setState(new CurveEditingState());
        }
        if(keycode == B){
            setState(new BoxCreationState());
        }
        if(keycode == N){
            setState(new NormalToSplineTestState());
        }
        return super.keyDown(keycode);
    }

    public InputListener(Level level) {
        this.level = level;
        state = State.DEFAULT;
    }

    @Override
    public boolean keyTyped(char character) {

        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 p = level.camera.unproject(new Vector3(screenX, screenY, 0));
        float x = p.x;
        float y = p.y;
        touchDown(x, y, button);
        return true;
    }

    private void touchDown(float x, float y, int button) {
        state.touchDown(x,y,button);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        state.level = level;
    }
}
