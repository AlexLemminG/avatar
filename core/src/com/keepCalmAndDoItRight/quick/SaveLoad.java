package com.keepCalmAndDoItRight.quick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.basics.Level;
import com.keepCalmAndDoItRight.controllers.LevelEditController;
import com.keepCalmAndDoItRight.controllers.PlayerController;
import com.keepCalmAndDoItRight.gObjects.Unit;
import com.keepCalmAndDoItRight.gObjects.UnitSpawner;
import com.keepCalmAndDoItRight.gObjects.Wall;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by Alexander on 05.07.2015.
 */
public class SaveLoad {
    XmlWriter xmlWriter;
    XmlReader xmlReader;
    public SaveLoad() throws IOException {
        xmlReader = new XmlReader();
    }

    public void close() throws IOException {
        xmlWriter.close();
    }

    public void write(Wall wall) throws IOException {
        XmlWriter temp = xmlWriter.element("Wall");
        temp.attribute("circled", wall.circled);
        temp.attribute("wallWidth", wall.wallWidth);
        temp.element("Basis");
        float[] basis = wall.getPolygon().getVertices();
        Vector2 tempV2 = new Vector2();
        for(int i = 0; i < basis.length / 2; i++){
            tempV2.set(basis[i * 2], basis[i * 2 + 1]);
            write(tempV2);
        }
        temp.pop().pop();
    }


    public void write(Unit unit) throws IOException {
        XmlWriter temp = xmlWriter.element("Unit");
        temp.attribute("unitType", unit.unitType);
        temp.element("hp", unit.hp);
        temp.element("position");
        write(unit.getBody().getPosition());
        temp.pop();
        temp.element("angle", unit.getBody().getAngle());
        temp.pop();
    }

    public Unit readUnit(XmlReader.Element e, Level level){
        Unit.UnitType unitType = Unit.UnitType.valueOf(e.getAttribute("unitType"));
        float hp = e.getFloat("hp");
        Vector2 position = readVector2(e.getChildByName("position").getChildByName("Vector2"));
        float angle = e.getFloat("angle");
        Unit u = new Unit(level);
        u.getBody().setTransform(position, angle);
        u.unitType = unitType;
        u.hp = hp;
        return u;
    }

    public void write(Vector2 vector2) throws IOException {
        xmlWriter.element("Vector2").attribute("x", vector2.x).attribute("y", vector2.y).pop();
    }

    public Vector2 readVector2(XmlReader.Element e) throws GdxRuntimeException{
        float x = e.getFloat("x");
        float y = e.getFloat("y");
        return new Vector2(x, y);
    }

    public Wall readWall(XmlReader.Element e, Level level) throws IOException, GdxRuntimeException {
        float width = e.getFloatAttribute("wallWidth");
        boolean circled = e.getBoolean("circled");
        e = e.getChildByName("Basis");
        Array<XmlReader.Element> vector2 = e.getChildrenByName("Vector2");
        Array<Vector2> vertices = new Array<>();
        for(XmlReader.Element vecEl : vector2){
            vertices.add(readVector2(vecEl));
        }
        float[] v = new float[vertices.size * 2];
        for(int i = 0; i < vertices.size; i++){
            v[i*2] = vertices.get(i).x;
            v[i*2+1] = vertices.get(i).y;
        }
        Polygon p = new Polygon(v);

        return new Wall(level, p, width, circled);
    }

    public void write(UnitSpawner unitSpawner) throws IOException, GdxRuntimeException {
        xmlWriter.element("UnitSpawner");
        xmlWriter.attribute("period", unitSpawner.period);
        write(unitSpawner.position);
        xmlWriter.pop();
    }

    public UnitSpawner readUnitSpawner(XmlReader.Element e, Level level){
        float period = e.getFloatAttribute("period");
        Vector2 position = readVector2(e.getChild(0));
        UnitSpawner unitSpawner = new UnitSpawner(level);
        unitSpawner.period = period;
        unitSpawner.setPosition(position);
        return unitSpawner;
    }

    public Level loadLevel(String fileName) throws GdxRuntimeException{
        Level level = new Level();

        try {
            FileHandle local = Gdx.files.internal(fileName);
            System.out.println(local.readString());
            XmlReader.Element e = xmlReader.parse(local);
            e = e.getChildByName("OS");
            Array<XmlReader.Element> walls = e.getChildrenByName("Wall");
            for(XmlReader.Element wallEl : walls){
                readWall(wallEl, level);
            }
            Array<XmlReader.Element> units = e.getChildrenByName("Unit");
            for(XmlReader.Element uEl : units){
                Unit u = readUnit(uEl, level);
                if(u.unitType == Unit.UnitType.player)
                    level.setPlayer(u);
            }

            Array<XmlReader.Element> unitSpawners = e.getChildrenByName("UnitSpawner");
            for(XmlReader.Element usEl : unitSpawners){
                readUnitSpawner(usEl, level);
            }


            level.stage.addListener(new PlayerController(level.player));
            level.stage.addListener(new LevelEditController(level.view));
            level.os.actuallyPutAndRemoveAll();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return level;
    }

    public void save(Level level, String fileName){
        Writer writer = null;
        try {
            writer = new FileWriter(fileName);
            XmlWriter w =
            xmlWriter = new XmlWriter(writer);

            w.element("Level");
            w.element("OS");

            for(GObject g : level.os.allGObjects){
                if(g instanceof Wall){
                    write(((Wall) g));
                }
                if(g instanceof Unit){
                    write(((Unit) g));
                }
                if(g instanceof UnitSpawner){
                    write(((UnitSpawner) g));
                }
            }
            w.pop();

            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
