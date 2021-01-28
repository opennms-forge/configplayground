
package org.opennms.config;

import java.util.Dictionary;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

@Command(scope = "onmsconfig", name = "updateconfig", description = "updates the config")
@Service
public class UpdateConfigCommand implements Action {

    @Reference
    private ConfigurationAdmin configurationAdmin;

    @Argument(name = "argument", description = "Argument to the command", required = false, multiValued = false)
    private String argument;

    @Override
    public Object execute() throws Exception {
        Configuration config = configurationAdmin.getConfiguration("org.opennms.config.echo");
        Dictionary props = config.getProperties();
        props.put("myConfig", argument);
        config.update(props);
        System.out.printf("Updated myConfig to %s." , argument);
        return null;
    }
}
