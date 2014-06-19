package org.cjforge.hexed.context.config;

import org.cjforge.hexed.context.resources.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mrakr_000 on 2014-05-22.
 */
public class ConfigReader {

    private final ConfigContainer configContainer;

    public ConfigReader(ConfigContainer configContainer) {
        this.configContainer = configContainer;
    }

    public void read(Collection<Resource> configs) throws IOException {
        for (Resource r : configs) {
            Properties props = loadConfig(r);
            Map<String, Object> config = propsToConfig(props);
            configContainer.addAll(config);
        }
    }

    private Map<String, Object> propsToConfig(Properties props) {
        Map<String, Object> result = new HashMap<>();
        for (String propName : props.stringPropertyNames()) {
            result.put(propName, props.getProperty(propName));
        }
        return result;
    }

    private Properties loadConfig(Resource r) throws IOException {
        InputStream input = getClass().getResourceAsStream(r.path);
        Properties props = new Properties();
        props.load(input);
        return props;
    }
}
