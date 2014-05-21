package org.cjcoders.hexfight;

import org.cjcoders.hexfight.gui.utils.resources.ResourcesContainer;
import org.cjcoders.hexfight.gui.utils.resources.ResourcesFileReader;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.state.StateBasedGame;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class Context {

    private static final String DEFAULT_THEME = "space";
    private static final String DEFAULT_RESOURCE_FILE = "resources.xml";

    private ResourcesContainer resourcesContainer;
    private Dimension resolution;
    private String theme;

    public void init(GUIContext container) throws IOException, SAXException, ParserConfigurationException, SlickException, FontFormatException {
        updateResolution(container);
        resourcesContainer = new ResourcesContainer(resolution, DEFAULT_THEME);
        resourcesContainer.addFileReader(new ResourcesFileReader(DEFAULT_RESOURCE_FILE));
        resourcesContainer.reload();
    }

    public void update(GUIContext container) throws FontFormatException, SlickException, IOException {
        updateResolution(container);
    }

    private void updateResolution(GUIContext container) throws FontFormatException, SlickException, IOException {
        Dimension newRes = getScreenResolution(container);
        if(!newRes.equals(resolution)){
            resolution = newRes;
            if(resourcesContainer != null) resourcesContainer.setResolution(resolution);
        }
    }

    private Dimension getScreenResolution(GUIContext container) {
        return new Dimension(container.getScreenWidth(), container.getScreenHeight());
    }

    public ResourcesContainer resources(){ return resourcesContainer; }
}
