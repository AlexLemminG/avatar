package com.mygdx.game.leftRight;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.HashSet;

/**
 * Created by Alexander on 17.06.2015.
 */
public class InputAd extends InputAdapter implements Updatable{
    public InputAd(Player player) {
        this.player = player;
    }

    Player player;

    @Override
    public boolean keyUp(int keycode) {
        pressed.remove(keycode);
        return super.keyUp(keycode);
    }

    HashSet<Integer> pressed = new HashSet<Integer>();
    @Override
    public boolean keyDown(int keycode) {
        pressed.add(keycode);
        return super.keyDown(keycode);
    }

    public void update(float dt){
        player.speed = 0;
        for(int keycode : pressed)
            switch (keycode){
                case Input.Keys.SPACE : {
                    HitingBox e = new HitingBox(player.x + player.width, player.y - player.height / 2, 0.2);
                    LeftRight.instance.updatables.add(e);
                    LeftRight.instance.shapeDrawables.add(e);
                }break;
                case Input.Keys.A:{
                    player.speed = -1;
                }break;
                case Input.Keys.D:{
                    player.speed = 1;
                }break;
                case Input.Keys.W:{
                    player.jump = true;
                }break;
                case Input.Keys.R:{
                    LeftRight.instance.restart();
                }break;
                case Input.Keys.I:{
                    LeftRight.instance.player.setIsInAir();
                }break;
        }
    }
}
