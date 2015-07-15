package com.keepCalmAndDoItRight;

import com.badlogic.gdx.Game;
import com.editor.EditorScreen;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.gObjects.weapons.Gun;
import com.keepCalmAndDoItRight.quick.SaveLoad;
import com.keepCalmAndDoItRight.screens.BasicGameScreen;

import java.io.IOException;

/**
 * Created by Alexander on 25.06.2015.
 */
public class Main extends Game {
    public static Main instance;
    @Override
    public void create() {
        instance = this;
        try {
            Level level = new SaveLoad().loadLevel("level1.png");
            BasicGameScreen screen2 = new BasicGameScreen(level);
            this.setScreen(screen2);
            Gun g = new Gun(level){
                @Override
                protected void shoot() {
                    super.shoot();
                    System.out.println("FIRE!!");
                }
            };
            g.setPosition(-6,0);

            EditorScreen screen1 = new EditorScreen(screen2);
            screen1.basicGameScreen = screen2;
            setScreen(screen1);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gdx.app.exit();
    }
}
