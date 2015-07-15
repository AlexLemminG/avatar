package com.keepCalmAndDoItRight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.MathUtils;
import com.keepCalmAndDoItRight.basics.Level;

/**
 * Created by Alexander on 25.06.2015.
 */
public class BasicGameScreen extends ScreenAdapter {
    FPSLogger logger;

    public Level getLevel() {
        return level;
    }

    Level level;

    @Override
    public void render(float delta) {
        delta = MathUtils.clamp(delta, 1f/60, 1f/30);
        level.update(delta);
        level.view.render();
        logger.log();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        level.view.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        level.dispose();
    }

    public void setLevel(Level level){
        this.level = level;
        Gdx.input.setInputProcessor(level.stage);
    }

    public BasicGameScreen(Level level) {
        setLevel(level);
        logger = new FPSLogger();
        Gdx.graphics.setTitle("ASDWF_SPACE_F1_LEFTMOUSE");
    }
}
