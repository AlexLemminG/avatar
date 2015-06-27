package com.mygdx.game.topDown;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.editor.SimpleBox;
import com.mygdx.game.izi.Level;
import com.mygdx.game.izi.View;

/**
 * Created by Alexander on 24.06.2015.
 */
public class TopDown extends ApplicationAdapter{
    public static TopDown instance;
    Level level;
    View view;
    PlayerController playerController;


    @Override
    public void create() {
        super.create();
        instance = this;
        level = new Level();
        view = new View();
        view.camera.zoom = 0.02f;


        initObjects();
    }

    private void initObjects() {
        final SimpleBox o = new SimpleBox(2, 0);
        o.createBody(level.world);
        level.os.put(o);
        view.ui.putInfo("HI", "THERE");
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        Carpet c = new Carpet(shape);
        c.createBody(level.world);
        level.os.put(c);

        c.links.add(new CarpetLink(c, o){
            @Override
            public void activate() {
                super.activate();
                if(activated)
                    o.getBody().setAngularVelocity(1f);
                else
                    o.getBody().setAngularVelocity(-1f);
            }
        });



        Player player = new Player();
        player.createBody(level.world);
        level.os.put(player);
        playerController = new PlayerController(player);
        Gdx.input.setInputProcessor(playerController);

    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        playerController.update();

        level.update(dt);
        view.render(level);
    }

    @Override
    public void dispose() {
        super.dispose();
        view.dispose();
        level.dispose();
    }
}
