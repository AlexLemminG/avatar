package com.mygdx.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Alexander on 08.06.2015.
 */
public class MouseInput extends InputAdapter {
    float x = 0;
    float y = 0;
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoord = MainGame.camera.unproject(new Vector3(screenX, screenY, 0));
        x = worldCoord.x;
        y = worldCoord.y;
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 worldCoord = MainGame.camera.unproject(new Vector3(screenX, screenY, 0));
        x = worldCoord.x;
        y = worldCoord.y;
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean keyTyped(char character) {
        switch(character) {
            case '+':
                MainGame.camera.zoom *= 1.05;
                break;
            case '-':
                MainGame.camera.zoom /= 1.05;
                break;
            case 'c':
                MainGame.grid.update(0);
                break;
            case 'r':
               MainGame.instance.recreate();
        }
        return super.keyTyped(character);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 worldCoord = MainGame.camera.unproject(new Vector3(screenX, screenY, 0));
        x = worldCoord.x;
        y = worldCoord.y;
        return super.mouseMoved(screenX, screenY);
    }
}
