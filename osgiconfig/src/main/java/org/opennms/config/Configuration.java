package org.opennms.config;

import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;

public class Configuration implements ConfigurationListener {
    private final String myConfig;

    public Configuration (String myConfig) {
        this.myConfig = myConfig;
    }

    public String get(){
        return  myConfig;
    }

    @Override
    public void configurationEvent(ConfigurationEvent configurationEvent) {
        System.out.println(configurationEvent);
    }
}
