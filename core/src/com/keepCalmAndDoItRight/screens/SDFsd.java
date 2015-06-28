package com.keepCalmAndDoItRight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.keepCalmAndDoItRight.GeometryUtils;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.basics.View;
import com.keepCalmAndDoItRight.gObjects.Door;
import com.keepCalmAndDoItRight.gObjects.Unit;
import com.keepCalmAndDoItRight.gObjects.UnitControl;
import com.keepCalmAndDoItRight.gObjects.Wall;

/**
 * Created by Alexander on 25.06.2015.
 */
public class SDFsd extends ScreenAdapter {
    Skin skin;
    Stage stage;
    Level level;
    View view;
    Texture tex = new Texture(Gdx.files.internal("unit.png"));
    static{
        FileHandle f = Gdx.files.internal("unit.png");
        System.out.println(Gdx.files.getExternalStoragePath());
        System.out.println(Gdx.files.getLocalStoragePath());
        System.out.println(f.exists());
        System.out.println("BEGIN__________________________________________________________________");
        for(FileHandle ff : f.list()){
            System.out.println(ff.name());
        }
        System.out.println("END__________________________________________________________________");
    }
    TextureRegion tr = new TextureRegion(tex);
    {
        tex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
//        tr.setRegion(0,0, 100, 100);
    }
    PolygonRegion sp = new PolygonRegion(tr, new float[]{0, 0, 500, 500, 500, 0}, new short[]{1, 2, 0});

    @Override
    public void render(float delta) {

        level.update(delta);
        Vector2 newUp = new Vector2(0, 1).rotateRad(player.getBody().getAngle());
        float scalar = 0.1f;
        Vector2 newUpDelta = newUp.sub(view.camera.up.x, view.camera.up.y).scl(scalar);
        Vector2 newPos = player.getBody().getPosition();
        Vector2 newPosDelta = newPos.sub(view.camera.position.x, view.camera.position.y).scl(scalar);

        view.camera.position.add(newPosDelta.x, newPosDelta.y, 0);
        view.camera.up.add(newUpDelta.x, newUpDelta.y, 0);
        view.camera.normalizeUp();



        view.render();

        view.camera.update();
        PolygonSpriteBatch b = ((PolygonSpriteBatch) level.stage.getBatch());
        b.setProjectionMatrix(view.camera.combined);
        b.begin();
//        b.draw(sp, 0, 0, 1, 1);
        b.end();
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

    final Unit player;

    public SDFsd() {
        level = new Level();
        view = new View(level);
        stage = level.stage;
        stage.setDebugAll(true);
//        skin = new Skin(Gdx.files.internal("C:\\Users\\Alexander\\Downloads\\libgdx-master\\libgdx-master\\tests\\gdx-tests-android\\assets\\data\\uiskin.json"));
        logger = new FPSLogger();

        Gdx.input.setInputProcessor(stage);

        Polygon wall = new Polygon(new float[]{
                0, 1,
                0, 3,
                -6, 3,
                -8, 4,
                -8, 6,
                -10, 6,
                -10, 4,
                -10, -4,
                -8, -4,
                -6, -3,
                0, -3,
                0, -1
        });
        new Wall(level, wall, 0.1f, false);
        new Door(level, GeometryUtils.boxPolygon(0.05f, 1f));
        player = new Unit(level);
        player.getBody().setTransform(0, 5, 0);
        player.getBody().setFixedRotation(true);


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
            x*=10;
            y*=10;

            box.getBody().setAngularVelocity(av);
            box.getBody().setTransform(x,y,a);
            box.getBody().setLinearVelocity(vx, vy);
            box.activate();
        }
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
                if(keycode == Input.Keys.SPACE){
                    player.control.removeAction(UnitControl.ACTIVATE);
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
                if(keycode == Input.Keys.SPACE){
                    player.control.addAction(UnitControl.ACTIVATE);
                }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                Vector3 v = view.camera.unproject(new Vector3(x,y,0));
                Gdx.graphics.setTitle(x + " " + y);
                return super.mouseMoved(event, x, y);
            }
        });


    }
}
