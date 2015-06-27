package keepCalmAndDoItRight.basics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;


/**
 * Created by Alexander on 23.06.2015.
 */
public class Level implements Disposable{
    public ObjectSet os;
    public World world;
    public Stage stage;

    public Level() {
        this.os = new ObjectSet();
        this.world = new World(new Vector2(0, 0), true);
        this.stage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()),
                new PolygonSpriteBatch());
//        world.setContactListener(new PhysicsContactListener());
    }


    //TODO
//    public GObject getObjectsAt(float x, float y) {
//        float closestDist2 = Float.MAX_VALUE;
//        GObject closest = null;
//        for(GObject o : os.allGObjects)
//            if(o.getPos().dst2(x, y) < closestDist2){
//                closest = o;
//                closestDist2 = o.getPos().dst2(x, y);
//            }
//        return closest;
//    }

    @Override
    public void dispose() {
        world.dispose();
        os.dispose();
    }

    public void update(float dt) {
        world.step(dt, 3,5);
        os.update(dt);
        stage.act(dt);
    }
}
