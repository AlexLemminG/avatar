package com.mygdx.game.leftRight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.izi.ObjectSet;

import java.util.LinkedList;

/**
 * Created by Alexander on 17.06.2015.
 */
public class InputAd extends GObject implements Updatable, InputProcessor{
    final int LEFT = -1;
    final int RIGHT = -2;
    ObjectSet os = LeftRight.instance.os;
//    boolean isTouching = false;
    Vector2 touchBegin = new Vector2();
    boolean leftSideOfScreenBegin = false;

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        if(screenX < Gdx.graphics.getWidth() / 2)
//            keyUp(LEFT);
//        else
//            keyUp(RIGHT);
        if(leftSideOfScreenBegin){
            boolean x;
            boolean y;
            touchBegin.sub(screenX, screenY);
            float threshold = 25;
            x = Math.abs(touchBegin.x) > threshold;
            y = touchBegin.y > threshold;
            if(y && Math.abs(touchBegin.x / touchBegin.y) > 3)
                y = false;
            if(x && Math.abs(touchBegin.x / touchBegin.y) < 1f/3)
                x = false;


            if(x) {
                if (touchBegin.x > threshold)
                    player.controller.runLeft();
                else if (touchBegin.x < -threshold)
                    player.controller.runRight();
            }
            if(y) {
                player.controller.jumpOnce();
            }
            if(x && y) {
                player.controller.thenStand();
            }
            if(!x && !y)
                player.controller.stand();
        }else{
            Vector3 v = LeftRight.camera.unproject(new Vector3(screenX, screenY, 0));
            Mob mob = new Mob(v.x, v.y);
            mob.createBody(LeftRight.instance.world);
            os.put(mob);
        }
        leftSideOfScreenBegin = false;


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
        if (screenX < Gdx.graphics.getWidth() *0.5f && screenY > Gdx.graphics.getHeight() * 0.5f) {
//            keyDown(LEFT);
            touchBegin.set(screenX, screenY);
            leftSideOfScreenBegin = true;
        }
//        else
//            keyDown(RIGHT);
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
//        player.speed = 0;
//        player.controller.stand();
        for(int keycode : pressed)
            switch (keycode){
                case Input.Keys.SPACE : {
                    HitingBox e = new HitingBox(player.x + player.width, player.y - player.height / 2, 0.2);
                    os.put(e);
                }break;
//                case LEFT:
                case Input.Keys.A:{
                    player.controller.runLeft();
                }break;
//                case RIGHT:
                case Input.Keys.D:{
                    player.controller.runRight();
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
        if(pressed.contains(Input.Keys.O)){
            pressed.remove((Integer)Input.Keys.O);
            new SaveLoader().save(LeftRight.instance.player);
        }
        if(pressed.contains(Input.Keys.P)){
            pressed.remove((Integer)Input.Keys.P);
            Mob m = (Mob)new SaveLoader().load();
//            m.createBody(LeftRight.instance.world);
//            os.put(m);
        }
        pressed.clear();
    }
}
