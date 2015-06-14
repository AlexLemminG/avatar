package com.mygdx.game;

import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Alexander on 08.06.2015.
 */
public class GameData {
    public static HashMap<String, String> map = new HashMap<String, String>();
    public static LinkedList<Updatable> updatables = new LinkedList<Updatable>();
    public static LinkedList<Disposable> disposables = new LinkedList<Disposable>();
}
