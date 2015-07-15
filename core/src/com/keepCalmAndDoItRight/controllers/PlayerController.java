package com.keepCalmAndDoItRight.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.keepCalmAndDoItRight.basics.SimplePath;
import com.keepCalmAndDoItRight.gObjects.Unit;

import static com.badlogic.gdx.Input.Keys.*;
import static com.keepCalmAndDoItRight.gObjects.UnitControl.*;

/**
 * Created by Alexander on 29.06.2015.
 */
public class PlayerController extends InputListener{
    com.badlogic.gdx.utils.ArrayMap<Integer, Integer> keysBind = new ArrayMap<>();
    {
        keysBind.put(W, MOVE_FORWARD);
        keysBind.put(S, MOVE_BACKWARD);
        keysBind.put(A, MOVE_LEFT);
        keysBind.put(D, MOVE_RIGHT);
        keysBind.put(SPACE, ACTIVATE);
        keysBind.put(F, FIRE);
    }
    com.badlogic.gdx.utils.ArrayMap<Integer, Integer> mouseBind = new ArrayMap<>();
    {
        mouseBind.put(Input.Buttons.LEFT, FOLLOW_PATH);
//        mouseBind.put(Input.Buttons.RIGHT, FOLLOW_PATH);
    }

    boolean writingPath = false;
    private Unit player;

    public PlayerController(Unit player){
        this.player = player;
        player.control.addAction(LOOK_AT__MOUSE);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if(keysBind.containsKey(keycode))
            player.control.removeAction(keysBind.get(keycode));

        return super.keyUp(event, keycode);
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if(keysBind.containsKey(keycode))
            player.control.addAction(keysBind.get(keycode));
        return super.keyDown(event, keycode);
    }

    Array<Vector2> draggedPoss = new Array<>();
    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        player.control.addAction(NONE, x, y);
        if (writingPath) {
            Vector2 lastPos = draggedPoss.get(draggedPoss.size-1);
            float epsilon = 0.01f;
            if(lastPos.dst2(x,y) > epsilon){
                draggedPoss.add(new Vector2(x, y));
            }
        }
        super.touchDragged(event, x, y, pointer);
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
//        Vector3 v = view.camera.unproject(new Vector3(x,y,0));
//        Gdx.graphics.setTitle(x + " " + y);
        player.control.addAction(NONE, x, y);
        return super.mouseMoved(event, x, y);
    }

    int mouseAction = 0;
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//        if(mouseBind.containsKey(button))
//            player.control.removeAction(mouseBind.get(button));
        player.control.addAction(NONE, x, y);

        if(player.getBody().getPosition().dst(x, y) < radiusToMove){
            writingPath = true;
            mouseAction = FOLLOW_DIRECTION;
            player.control.removeAction(mouseAction);

            draggedPoss.add(player.getBody().getPosition().cpy());
            draggedPoss.add(new Vector2(x, y));
        }else{
            mouseAction = FIRE;
            player.control.addAction(mouseAction);
            player.level.setTimeSpeed(1f);
        }


        return mouseBind.containsKey(button);
    }
    float slowedTimeSpeed = 0.1f;

    float radiusToMove = 1.2f;
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//        if(mouseBind.containsKey(button))
//            player.control.addAction(mouseBind.get(button), x, y);
        writingPath = false;
        if(mouseAction == FIRE) {
            player.control.removeAction(mouseAction);
            player.level.setTimeSpeed(slowedTimeSpeed);
        }
        if(mouseAction == FOLLOW_PATH) {
            player.control.addAction(mouseAction);
            player.control.path = new SimplePath(draggedPoss);
            draggedPoss.clear();
        }
        if(mouseAction == FOLLOW_DIRECTION){
            player.level.setTimeSpeed(1);
            player.control.direction.set(draggedPoss.get(draggedPoss.size-1).cpy().sub(draggedPoss.first()));
            player.control.addAction(FOLLOW_DIRECTION);

            player.control.addAction(mouseAction);
            player.control.path = new SimplePath(draggedPoss);
            draggedPoss.clear();

        }
        player.control.addAction(NONE, x, y);

        super.touchUp(event, x, y, pointer, button);
    }
}
