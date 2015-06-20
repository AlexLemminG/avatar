package com.mygdx.game.leftRight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.LinkedList;

/**
 * Created by Alexander on 17.06.2015.
 */
public class InputAd extends GObject implements Updatable, InputProcessor{
    final int LEFT = -1;
    final int RIGHT = -2;
    ObjectSet os = LeftRight.instance.os;

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(screenX < Gdx.graphics.getWidth() / 2)
            keyUp(LEFT);
        else
            keyUp(RIGHT);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX < Gdx.graphics.getWidth() / 2)
            keyDown(LEFT);
        else
            keyDown(RIGHT);
        return true;
    }

    public InputAd(Player player) {
        this.player = player;
    }

    Player player;

    @Override
    public boolean keyUp(int keycode) {
        pressed.remove(Integer.valueOf(keycode));
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    LinkedList<Integer> pressed = new LinkedList<Integer>();
    @Override
    public boolean keyDown(int keycode) {
        pressed.add(keycode);
        return true;
    }
    int temp = 0;
    public void update(float dt){
        player.speed = 0;
        for(int keycode : pressed)
            switch (keycode){
                case Input.Keys.SPACE : {
                    HitingBox e = new HitingBox(player.x + player.width, player.y - player.height / 2, 0.2);
                    os.put(e);
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
        if(pressed.contains(Input.Keys.O) && temp++ < 1){
//            Rope rope = new Rope();
//            rope.createBody(LeftRight.instance.world);
//            LeftRight.instance.updatables.add(rope);
//            LeftRight.instance.shapeDrawables.add(rope);
//            VerletPoint p = new VerletPoint(0,5,LeftRight.instance.world);
//            LeftRight.instance.updatables.add(p);
        }
    }
}
