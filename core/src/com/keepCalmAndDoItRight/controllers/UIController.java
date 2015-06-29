package com.keepCalmAndDoItRight.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.keepCalmAndDoItRight.basics.View;

/**
 * Created by Alexander on 29.06.2015.
 */
public class UIController extends InputListener{
    View view;

    public UIController(View view) {
        this.view = view;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if(keycode == Input.Keys.F1)
            view.togleDebug();
        return super.keyDown(event, keycode);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return super.touchDown(event, x, y, pointer, button);
    }
}
