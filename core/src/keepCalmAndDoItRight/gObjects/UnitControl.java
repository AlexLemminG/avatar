package keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander on 27.06.2015.
 */
public class UnitControl {
    public static final int MOVE_FORWARD = 1;
    public static final int NONE = 0;
    public static final int MOVE_BACKWARD = 2;
    public static final int ROTATE_COUNTER_CLOCKWISE = 8;
    public static final int ROTATE_CLOCKWISE = 4;
    Unit unit;

    public UnitControl(Unit unit) {
        this.unit = unit;
    }

    float maxSpeed = 5;
    float maxAngularSpeed = 3f;
    int currentAction;
    public void addAction(int action){
        currentAction |= action;
    }

    public void removeAction(int action){
        currentAction = (~action) & currentAction;
    }
    public void update(){
        float speed = 0;
        float aSpeed = 0;

        if((currentAction & MOVE_FORWARD) != 0){
            speed += maxSpeed;
        }
        if((currentAction & MOVE_BACKWARD)!= 0){
            speed -= maxSpeed;
        }
        if((currentAction & ROTATE_CLOCKWISE) != 0){
            aSpeed -= maxAngularSpeed;
        }
        if((currentAction & ROTATE_COUNTER_CLOCKWISE) != 0){
            aSpeed += maxAngularSpeed;
        }
        Vector2 vel = new Vector2(1,0).setAngleRad(unit.getBody().getAngle() + ((float) (Math.PI / 2))).scl(speed);
        unit.getBody().setLinearVelocity(vel);
        unit.getBody().setAngularVelocity(aSpeed);
    }
}
