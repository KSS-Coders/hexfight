package org.cjforge.hexed.utils.components;

import java.util.*;

/**
 * Created by mrakr_000 on 2014-06-05.
 */
public class InputController {

    private LinkedHashMap<String, InputLayer> layers;
    private List<String> refs;
    private int key;

    public InputController(int key) {
        layers = new LinkedHashMap<>();
        refs = new LinkedList<>();
    }

    public void addAction(String ref, InputAction action){
        layers.put(ref, new InputLayer(action));
        NavigableSet n;
    }



    private class InputLayer{
        boolean isEnabled;
        InputAction action;

        InputLayer(InputAction action){
            this.action = action;
            isEnabled = true;
        }
    }
}
