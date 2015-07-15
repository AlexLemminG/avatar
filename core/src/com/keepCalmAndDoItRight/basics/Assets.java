package com.keepCalmAndDoItRight.basics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Alexander on 29.06.2015.
 */
public class Assets {
    public static final Texture WALL_TEXTURE = new Texture(Gdx.files.internal("wall.png"));
    public static final Texture BULLET_TEXTURE = new Texture(Gdx.files.internal("bullet.png"));
    public static Texture DEFAULT_TEXTURE = new Texture(Gdx.files.internal("badlogic.jpg"));
    public static Texture UNIT_TEXTURE = new Texture(Gdx.files.internal("unit.png"));
    public static Texture BACKGROUND_TEXTURE = new Texture(Gdx.files.internal("background.png"));
    public static BitmapFont FONT = new BitmapFont();


    static {
        Assets.WALL_TEXTURE.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Assets.BACKGROUND_TEXTURE.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        FONT.getData().setScale(0.01f);
    }
}
