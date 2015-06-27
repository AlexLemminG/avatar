package com.mygdx.game.izi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.GameData;
import com.mygdx.game.Updatable;

/**
 * Created by Alexander on 08.06.2015.
 */
public class InfoOutput implements Updatable{
    Label label;
    String currentInfo;
    String mapKey;
    boolean showKey = true;
    Stage stage;
    static Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
    static float y;
    private String showing;

    public InfoOutput(String mapKey, Stage stage) {
        this.stage = stage;
        this.mapKey = mapKey;
        currentInfo = (showKey ? mapKey : "") + GameData.map.get(mapKey);
        label = new Label(currentInfo, labelStyle);
        label.setPosition(5, y);
        this.stage.addActor(label);
        y += 20;
    }

    public void update(float dt){
        showing = (showKey ? mapKey + ": " : "") + currentInfo;
        label.setText(showing);
    }

    public void setString(String value) {
        currentInfo = value;
        update(0);
    }
}
