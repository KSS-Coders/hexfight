package org.cjforge.hexed.context.resources;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class Resource {
    public final String ref;
    public final String path;

    public Resource(String ref, String path) {
        this.ref = ref;
        this.path = path;
    }

    @Override
    public String toString() {
        return "[Ref: " + ref + " ; Path: " + path + "]";
    }
}
