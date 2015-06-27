package com.mygdx.game.topDown;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import static com.badlogic.gdx.Input.Keys.*;

/**
 * Created by Alexander on 24.06.2015.
 */
public class PlayerController extends InputAdapter{
    private Player player;

    public PlayerController(Player player){
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        pressed.add(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        pressed.remove((Integer) keycode);
        return true;
    }

    LinkedList<Integer> pressed = new LinkedList<Integer>();
    public void update(){
        Vector2 vec = new Vector2();
        if(pressed.contains(A)){
            vec.x -= 1;
        }
        if(pressed.contains(D)){
            vec.x += 1;
        }
        if(pressed.contains(W)){
            vec.y += 1;
        }
        if(pressed.contains(S)){
            vec.y -= 1;
        }
        if(pressed.contains(SPACE)){
            pressed.remove((Integer)SPACE);
            for(Carpet c : player.carpets)
                c.activate();
        }
        player.getBody().setLinearVelocity(vec);
    }
}
