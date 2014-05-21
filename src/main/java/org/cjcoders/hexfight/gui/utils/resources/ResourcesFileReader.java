package org.cjcoders.hexfight.gui.utils.resources;

import com.sun.glass.ui.*;
import com.sun.glass.ui.Window;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mrakr_000 on 2014-05-19.
 */
public class ResourcesFileReader {

    private final Document resources;
    private Logger log = Logger.getLogger(this.getClass().getName());

    /** Creates new ResourcesReader using specified resources file
     *
     * @param resourcesFileName resources file to read
     */
    public ResourcesFileReader(String resourcesFileName) throws ParserConfigurationException, IOException, SAXException {
        InputStream resourcesStream = ResourceLoader.getResourceAsStream(resourcesFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        resources = dBuilder.parse(resourcesStream);
        log.setLevel(Level.ALL);
        if(!log.isTraceEnabled()) log.setLevel(Level.TRACE);
    }

    public Collection<Resource> getAllResources(Dimension resolution, String theme){
        Collection<Resource> result = getImages(resolution, theme);
        result.addAll(getFonts());
        return result;
    }

    public Collection<Resource> getFonts() {
        Set<Resource> result = new HashSet<>();
        NodeList fonts = resources.getElementsByTagName("fonts");
        for(int i = 0; i < fonts.getLength(); ++i){
            Element n = (Element) fonts.item(i);
            result.addAll(loadFonts(n));
        }
        return result;
    }

    private Collection<Resource> loadFonts(Element n) {
        Set<Resource> result = new HashSet<>();
        NodeList fonts = n.getElementsByTagName("font");
        for(int j = 0; j < fonts.getLength(); ++j){
            Element e = (Element) fonts.item(j);
            result.add(loadFont(e, "fonts"));
        }
        return result;
    }

    private Resource loadFont(Element e, String basePath) {
        String path = basePath + "/" + e.getAttribute("path");
        String name = e.getAttribute("name");
        Resource r = new Resource(name, path);
        log.trace("Loading font: " + r);
        return r;
    }

    public Collection<Resource> getImages(Dimension resolution, String theme) {
        Set<Resource> result = new HashSet<>();
        NodeList images = resources.getElementsByTagName("images");
        for(int i = 0; i < images.getLength(); ++i){
            Element n = (Element) images.item(i);
            result.addAll(loadImages(n, resolution));
            result.addAll(loadTheme(n, resolution, theme));
        }
        return result;
    }

    private Collection<Resource> loadTheme(Element n, Dimension resolution, String theme) {
        Set<Resource> result = new HashSet<>();
        NodeList themes = n.getElementsByTagName("theme");
        for(int i = 0; i < themes.getLength(); ++i){
            Element themeE = (Element) themes.item(i);
            if(themeE.getAttribute("name").equals(theme)){
                result.add(loadThemeTiles(themeE));
                result.add(loadThemeBackground(themeE, resolution));
            }
        }
        return result;
    }

    private Resource loadThemeBackground(Element themeE, Dimension resolution) {
        NodeList bgEL = themeE.getElementsByTagName("background");
        Element bgE = (Element) bgEL.item(0);
        String bgName = "theme-bg";
        String path = "images/themes/" + themeE.getAttribute("name") + "/" + getImgPathWithResolution(bgE, resolution);
        Resource r = new Resource(bgName, path);
        log.trace("Loading theme bg: " + r);
        return r;
    }

    private String getImgPathWithResolution(Element bgE, Dimension resolution) {
        return (int) resolution.getWidth() + "x" + (int) resolution.getHeight() +"/" + bgE.getAttribute("path");
    }

    private Resource loadThemeTiles(Element themeE){
        NodeList tilesEL = themeE.getElementsByTagName("tiles");
        Element tilesE = (Element) tilesEL.item(0);
        String bgName = "tiles";
        String path = "images/themes/" + themeE.getAttribute("name") + "/" + tilesE.getAttribute("path");
        Resource r = new Resource(bgName, path);
        log.trace("Loading tiles: " + r);
        return r;
    }

    private Collection<Resource> loadImages(Element n, Dimension resolution) {
        Set<Resource> result = new HashSet<>();
        NodeList imgs = n.getElementsByTagName("img");
        for(int j = 0; j < imgs.getLength(); ++j){
            Element e = (Element) imgs.item(j);
            if(e.hasChildNodes()) result.add(loadImageByResolution(e, resolution, "images"));
            else result.add(loadImage(e, "images"));
        }
        return result;
    }

    private Resource loadImage(Element e, String basePath) {
        String path = basePath + "/" + e.getAttribute("path");
        String name = e.getAttribute("name");
        Resource r = new Resource(name, path);
        log.trace("Loading img: " + r);
        return r;
    }

    private Resource loadImageByResolution(Element e, Dimension resolution, String basePath) {
        String path = basePath + "/" + getImgPathWithResolution(e, resolution);
        String name = e.getAttribute("name");
        Resource r = new Resource(name, path);
        log.trace("Loading img: " + r);
        return r;
    }

    public static void main(String... args) throws ParserConfigurationException, SAXException, IOException {
        ResourcesFileReader rr = new ResourcesFileReader("resources.xml");
        rr.getImages(new Dimension(1902, 1080), "space");
    }
}
