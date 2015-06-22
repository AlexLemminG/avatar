package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Alexander on 17.06.2015.
 */
public class Player extends Mob{
    public float width = 0.4f;
    public float height = 1;
    Controller controller;

    public Player(float x, float y) {
        super(x, y);
        color = Color.YELLOW;
        controller = new Controller();
    }

    @Override
    public void update(float dt) {
        controller.update();
        super.update(dt);
    }

    public class Controller{
        boolean run = false;
        boolean left = false;
        boolean thenStand = true;
        double thenStandTime;

        public void update(){
            if(run){
                speed = left ? -MAX_SPEED : MAX_SPEED;
            }else{
                speed = 0;
            }
            if(thenStand && thenStandTime > LeftRight.instance.time) {
                stand();
                thenStand = false;
            }
        }

        public void runLeft(){
            run = !(run && !left);
            left = true;
        }
        public void runRight(){
            run = !(run && left);
            left = false;
        }

        public void stand() {
            run = false;
        }

        public void jumpOnce() {
            jump = true;
        }

        public void thenStand() {
            thenStand = true;
            thenStandTime = LeftRight.instance.time + 1d;
        }
    }
}
