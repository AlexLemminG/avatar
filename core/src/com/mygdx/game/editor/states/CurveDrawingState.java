package com.mygdx.game.editor.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.editor.Consts;
import com.mygdx.game.editor.Curve;
import com.mygdx.game.editor.State;
import com.mygdx.game.editor.editorActions.*;
import com.mygdx.game.leftRight.CurvyWall;

/**
 * Created by Alexander on 23.06.2015.
 */
public class CurveDrawingState extends State{
    Curve curve = null;

    @Override
    public void touchDown(float x, float y, int button) {
        super.touchDown(x, y, button);
        if (button == Input.Buttons.LEFT){
            if(curve == null){
                curve = new Curve();
                curve.setPos(new Vector2(x, y));
                Consts.level.os.put(curve);
            }
            Consts.input.doAction(new AddPointToCurveAction(curve, new Vector2(x,y)));
        }else {
            if (curve != null) {
                CurvyWall wall = new CurvyWall(curve);
                Consts.input.doAction(new MultipleAction(new CreateAction(wall), new CreateBody(wall)));
            }

        }
    }

    @Override
    public void mouseMoved(float x, float y) {
        super.mouseMoved(x, y);
    }
}
