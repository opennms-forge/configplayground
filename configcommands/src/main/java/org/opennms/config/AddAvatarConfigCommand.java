
package org.opennms.config;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Optional;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

@Command(scope = "onmsconfig", name = "add-avatar", description = "updates the config")
@Service
public class AddAvatarConfigCommand implements Action {

    @Reference
    private ConfigurationAdmin configurationAdmin;

    @Argument(name = "argument", description = "Argument to the command", required = true, multiValued = false)
    private String argument;

    @Override
    public Object execute() throws Exception {
        if(argument == null || argument.isEmpty()) {
            System.out.println("You must supply a name such as: add-avatar \"Obi Wan Kenobi\"");
            return null;
        }
        Configuration config = configurationAdmin.getFactoryConfiguration("org.opennms.config.avatar", argument, "?org.opennms");
        Dictionary props = Optional.ofNullable(config.getProperties()).orElse(new Hashtable<>());
        props.put("avatarName", argument);
        config.update(props);
        System.out.printf("Added Avatar config %s." , argument);
        return null;
    }
}
