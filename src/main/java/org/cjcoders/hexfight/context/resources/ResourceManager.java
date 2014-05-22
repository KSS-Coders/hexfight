package org.cjcoders.hexfight.context.resources;

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
