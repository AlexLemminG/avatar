package keepCalmAndDoItRight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import keepCalmAndDoItRight.basics.Level;
import keepCalmAndDoItRight.basics.View;
import keepCalmAndDoItRight.gObjects.Unit;
import keepCalmAndDoItRight.gObjects.UnitControl;

/**
 * Created by Alexander on 25.06.2015.
 */
public class SDFsd extends ScreenAdapter {
    Skin skin;
    Stage stage;
    Level level;
    View view;

    @Override
    public void render(float delta) {

        level.update(delta);

        view.render();
        logger.log();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        view.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        level.dispose();
    }
    FPSLogger logger;


    public SDFsd() {
        level = new Level();
        view = new View(level);
        stage = level.stage;
        stage.setDebugAll(true);
        skin = new Skin(Gdx.files.internal("C:\\Users\\Alexander\\Downloads\\libgdx-master\\libgdx-master\\tests\\gdx-tests-android\\assets\\data\\uiskin.json"));
        logger = new FPSLogger();

        Gdx.input.setInputProcessor(stage);

        for(int i = 0; i < 10; i++) {
            Unit box = new Unit(level);
            float av = (float) (Math.random() - 0.5f);
            float vx = ((float) (Math.random() - 0.5f));
            float vy = ((float) (Math.random() - 0.5f));
            float x = ((float) (Math.random() - 0.5f));
            float y = ((float) (Math.random() - 0.5f));
            float a = ((float) (Math.random() - 0.5f));


//            vx = vy = av = 0;
//            x = y = a = 0;
//            x*=10;
//            y*=10;

            box.getBody().setAngularVelocity(av);
            box.getBody().setTransform(x,y,a);
            box.getBody().setLinearVelocity(vx, vy);
        }
        final Unit player = new Unit(level);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(keycode == Input.Keys.W){
                    player.control.removeAction(UnitControl.MOVE_FORWARD);
                }
                if(keycode == Input.Keys.S){
                    player.control.removeAction(UnitControl.MOVE_BACKWARD);
                }
                if(keycode == Input.Keys.D){
                    player.control.removeAction(UnitControl.ROTATE_CLOCKWISE);
                }
                if(keycode == Input.Keys.A){
                    player.control.removeAction(UnitControl.ROTATE_COUNTER_CLOCKWISE);
                }
                return super.keyUp(event, keycode);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.W){
                    player.control.addAction(UnitControl.MOVE_FORWARD);
                }
                if(keycode == Input.Keys.S){
                    player.control.addAction(UnitControl.MOVE_BACKWARD);
                }
                if(keycode == Input.Keys.D){
                    player.control.addAction(UnitControl.ROTATE_CLOCKWISE);
                }
                if(keycode == Input.Keys.A){
                    player.control.addAction(UnitControl.ROTATE_COUNTER_CLOCKWISE);
                }
                return super.keyDown(event, keycode);
            }
        });


    }
}
