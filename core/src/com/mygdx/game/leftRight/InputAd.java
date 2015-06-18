package com.mygdx.game.leftRight;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by Alexander on 17.06.2015.
 */
public class InputAd extends InputAdapter{
    public InputAd(Player player) {
        this.player = player;
    }

    Player player;

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.D:
                player.speed = 0;
                break;
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.SPACE : {
                LeftRight.instance.hitingBoxes.add(new HitingBox(player.x + player.width, player.y-player.height / 2, 0.2));
            }break;
            case Input.Keys.A:{
                player.speed = -100;
            }break;
            case Input.Keys.D:{
                player.speed = 100;
            }break;
            case Input.Keys.W:{
                player.jump = true;
            }break;
        }
        return super.keyDown(keycode);
    }
}
