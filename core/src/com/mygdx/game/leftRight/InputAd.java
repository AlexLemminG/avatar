package com.mygdx.game.leftRight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.LinkedList;

/**
 * Created by Alexander on 17.06.2015.
 */
public class InputAd extends InputAdapter implements Updatable{
    final int LEFT = -1;
    final int RIGHT = -2;

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(screenX < Gdx.graphics.getWidth() / 2)
            keyUp(LEFT);
        else
            keyUp(RIGHT);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(screenX < Gdx.graphics.getWidth() / 2)
            keyDown(LEFT);
        else
            keyDown(RIGHT);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    public InputAd(Player player) {
        this.player = player;
    }

    Player player;

    @Override
    public boolean keyUp(int keycode) {
        pressed.remove(Integer.valueOf(keycode));
        return super.keyUp(keycode);
    }

    LinkedList<Integer> pressed = new LinkedList<Integer>();
    @Override
    public boolean keyDown(int keycode) {
        pressed.add(keycode);
        return super.keyDown(keycode);
    }
    int temp = 0;
    public void update(float dt){
        player.speed = 0;
        for(int keycode : pressed)
            switch (keycode){
                case Input.Keys.SPACE : {
                    HitingBox e = new HitingBox(player.x + player.width, player.y - player.height / 2, 0.2);
                    LeftRight.instance.updatables.add(e);
                    LeftRight.instance.shapeDrawables.add(e);
                }break;
                case LEFT:
                case Input.Keys.A:{
                    if(player.speed == 0)
                    player.speed = -1;
                }break;
                case RIGHT:
                case Input.Keys.D:{
                    if(player.speed == 0)
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
        if(pressed.contains(LEFT) && pressed.contains(RIGHT)){
            LeftRight.instance.player.jump = true;
        }
        if(pressed.contains(Input.Keys.O) && temp++==0){
//            Rope rope = new Rope();
//            rope.createBody(LeftRight.instance.world);
//            LeftRight.instance.updatables.add(rope);
//            LeftRight.instance.shapeDrawables.add(rope);
            Graph p = new Graph(-5,5);
            LeftRight.instance.updatables.add(p);
            LeftRight.instance.shapeDrawables.add(p);

        }
    }
}
