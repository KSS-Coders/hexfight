package org.cjcoders.hexfight.gui.utils.resources;

import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.ResourceLocation;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrakr_000 on 2014-05-12.
 */
public abstract class ResourceManager<T> {
    private Map<String, T> resources;

    ResourceManager(){
        resources = new HashMap<>();
    }

    protected void addResource(String name, T resource){
        resources.put(name, resource);
    }

    public boolean refExists(String ref){
        return resources.keySet().contains(ref);
    }

    public T get(String name){
        return resources.get(name);
    }

}
