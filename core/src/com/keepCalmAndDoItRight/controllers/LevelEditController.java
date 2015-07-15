package com.keepCalmAndDoItRight.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.keepCalmAndDoItRight.Main;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.basics.View;
import com.keepCalmAndDoItRight.quick.SaveLoad;
import com.keepCalmAndDoItRight.screens.BasicGameScreen;

/**
 * Created by Alexander on 05.07.2015.
 */
public class LevelEditController extends InputListener{
    View view;

    public LevelEditController(View view) {
        this.view = view;
        if(zoom == 0)
            zoom = view.camera.zoom;

        view.camera.zoom = zoom;
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {
        if(character == 'r'){
            forceReloadLevelFromFile();
        }
        if(character == '+'){
            zoom *= 1.1f;
        }
        if(character == '-')
            zoom /= 1.1f;
        view.camera.zoom = zoom;
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

    static long lastFileModified =  new FileHandle("level1.png").lastModified();
    static float zoom = 0f;
    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        reloadLevelFromFile();
        Gdx.graphics.setTitle(" " + x + " " + y);
        return super.mouseMoved(event, x, y);
    }

    public void reloadLevelFromFile(){
        if(lastFileModified != new FileHandle("level1.png").lastModified()){
            forceReloadLevelFromFile();
        }
    }
    public void forceReloadLevelFromFile() {
        lastFileModified = new FileHandle("level1.png").lastModified();
        try {
            Level level = new SaveLoad().loadLevel("level1.png");
            if(level != null) {

                ((BasicGameScreen) Main.instance.getScreen()).setLevel(level);
            }
        } catch (Exception e) {
            Gdx.app.log("ERROR", e.toString());
        }
    }
}
