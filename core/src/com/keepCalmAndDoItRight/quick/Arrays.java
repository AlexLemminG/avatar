package com.keepCalmAndDoItRight.quick;

/**
 * Created by Alexander on 27.06.2015.
 */
public class Arrays {
    public static float[] copy(float[] orig){
        float[] result = new float[orig.length];
        for(int i = 0; i < result.length; i++){
            result[i] = orig[i];
        }
        return result;
    }
}
