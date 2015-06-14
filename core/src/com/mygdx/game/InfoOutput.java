package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Alexander on 08.06.2015.
 */
public class InfoOutput implements Updatable{
    Label label;
    String currentInfo;
    String mapKey;
    boolean showKey = true;
    static Stage stage = new Stage();
    static Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
    static float y;

    public InfoOutput(String mapKey) {
        this.mapKey = mapKey;
        currentInfo = (showKey ? mapKey : "") + GameData.map.get(mapKey);
        label = new Label(currentInfo, labelStyle);
        label.setPosition(5, y);
        stage.addActor(label);
        y += 20;
    }

    public void update(float dt){
        currentInfo = (showKey ? mapKey + ": " : "") + GameData.map.get(mapKey);
        label.setText(currentInfo);
    }
}
