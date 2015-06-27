package com.mygdx.game.izi;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 24.06.2015.
 */
public class UI implements Disposable{
    Stage stage = new Stage();
    Map<String, InfoOutput> gameData = new HashMap();

    public void putInfo(String key, String value){
        InfoOutput label = gameData.get(key);
        if(label == null) {
            label = new InfoOutput(key, stage);
            gameData.put(key, label);
        }
        label.setString(value);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
