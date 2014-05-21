package org.cjcoders.hexfight.gui.utils.resources;

import org.newdawn.slick.*;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

import java.awt.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class ResourcesContainer {

    private Dimension resolution;
    private String theme;
    private Set<ResourcesFileReader> resourcesFileReaders;

    public ResourcesContainer(Dimension initResolution, String theme){
        this.resolution = initResolution;
        this.theme = theme;
        this.resourcesFileReaders = new HashSet<>();
    }

    public void reload() throws IOException, SlickException, FontFormatException {
        for(ResourcesFileReader resourcesFileReader : resourcesFileReaders){
            reloadImages(resourcesFileReader);
            reloadFonts(resourcesFileReader);
        }
    }

    private void reloadFonts(ResourcesFileReader resourcesFileReader) throws IOException, FontFormatException {
        Collection<Resource> fonts = resourcesFileReader.getFonts();
        for(Resource font : fonts){
            Resources.get().fonts.load(font.ref, font.path);
        }
    }

    private void reloadImages(ResourcesFileReader resourcesFileReader) throws IOException, SlickException {
        Collection<Resource> images = resourcesFileReader.getImages(resolution, theme);
        for(Resource img : images){
            Resources.get().images.load(img.ref, img.path);
        }
    }

    public Image getImage(String ref){
        return Resources.get().images.get(ref);
    }

    public Font getFont(String ref, int size, String options){
        return Resources.get().fonts.get(ref, size, options);
    }
    public Font getFont(String ref, int size){
        return getFont(ref, size, "");
    }

    private Collection<Resource> getAllResourcesMap() {
        Set<Resource> result = new HashSet<>();
        for(ResourcesFileReader resourcesFileReader : resourcesFileReaders){
            result.addAll(resourcesFileReader.getImages(resolution, theme));
        }
        return result;
    }

    public void addFileReader(ResourcesFileReader fileReader){
        resourcesFileReaders.add(fileReader);
    }

    public void setResolution(Dimension resolution) throws IOException, SlickException, FontFormatException {
        this.resolution = resolution;
        reload();
    }
}
