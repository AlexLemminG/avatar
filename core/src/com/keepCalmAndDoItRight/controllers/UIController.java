package com.keepCalmAndDoItRight.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.keepCalmAndDoItRight.Main;
import com.keepCalmAndDoItRight.basics.View;
import com.keepCalmAndDoItRight.quick.SaveLoad;
import com.keepCalmAndDoItRight.screens.BasicGameScreen;

import java.io.IOException;

/**
 * Created by Alexander on 29.06.2015.
 */
public class UIController extends InputListener{
    View view;

    public UIController(View view) {
        this.view = view;
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {
        if(character == 'r'){
            try {
                ((BasicGameScreen) Main.instance.getScreen()).setLevel(new SaveLoad().loadLevel("level1.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.keyTyped(event, character);
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
