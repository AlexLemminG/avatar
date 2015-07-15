package com.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.keepCalmAndDoItRight.screens.BasicGameScreen;

/**
 * Created by Alexander on 13.07.2015.
 */
public class EditorScreen extends ScreenAdapter{
    Stage stage;
    Table table;
    Skin skin = new Skin(new FileHandle("data/uiskin.json"));
    List box = new List(skin);
    Array items = new Array();
    TextField tf;
    public BasicGameScreen basicGameScreen;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Array ar = new Array();
        if(tf.getText() == "")
            ar = items;
        else
        {
            String t = tf.getText().toLowerCase();
            for (Object o : items) {
                if(o.toString().toLowerCase().contains(t)){
                    ar.add(o);
                }
            }
        }


        box.setItems(ar);

        basicGameScreen.render(delta);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().setScreenSize(width, height);
        basicGameScreen.resize(width, height);
//        stage.getViewport().setWorldSize(width, height);
    }

    public EditorScreen(BasicGameScreen basicGameScreen) {
        super();
        this.basicGameScreen = basicGameScreen;
        stage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()),
                basicGameScreen.getLevel().stage.getBatch());
        table = new Table(skin);
        stage.setDebugAll(true);

        Label l1 = new Label("ABC", skin);
        Label l2 = new Label("EFG", skin);
        Button b1 = new Button(skin);

        b1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Click");
            }
        });
        b1.setWidth(100);

        Object o = new Object(){
            @Override
            public String toString() {
                return "SDF";
            }
        };
        stage.addActor(table);


        tf = new TextField("", skin){
            @Override
            protected InputListener createInputListener() {
                return new TextFieldClickListener(){
                    @Override
                    public boolean handle(Event e) {
                        super.handle(e);
                        return false;
                    }
                };
            }
        };

        items.add(l1);
        items.add(l2);
        items.add(o);
        items.add(b1);

        Table table2 = new Table(skin);
        table2.add(tf).align(Align.topLeft);;
        table2.row();
        table2.add(box).expand().align(Align.left);
        table2.align(Align.topLeft);

        table.align(Align.left | Align.bottom);
        table.add(table2).align(Align.topLeft);
        basicGameScreen.getLevel().stage.getRoot().setScale(1);
        table.add().align(Align.center).expand();
        table.setFillParent(true);

//        table.setSize(500, 500);
        tf.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ENTER){
                    System.out.println(box.getSelected());
                }
                if(keycode == Input.Keys.DOWN){
                    box.setSelectedIndex(Math.abs(box.getSelectedIndex() + 1) % (box.getItems().size));
                }
                if(keycode == Input.Keys.UP){
                    box.setSelectedIndex(Math.abs(box.getSelectedIndex() - 1 + box.getItems().size) % (box.getItems().size));
                }
                return false;
            }
        });


        Gdx.input.setInputProcessor(new InputMultiplexer(stage, basicGameScreen.getLevel().stage));

    }
}
