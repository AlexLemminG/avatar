package com.keepCalmAndDoItRight.quick;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander on 27.06.2015.
 */
public class Segment {
    private Vector2 pointA;
    private Vector2 pointB;

    public Segment(Vector2 pointA, Vector2 pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public Vector2 getNormal() {
        return pointB.cpy().sub(pointA).nor().rotate90(1);
    }

    public void set(Segment segment) {
        pointA = segment.pointA;
        pointB = segment.pointB;
    }

    public void set(Vector2 pointA, Vector2 pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }
}
