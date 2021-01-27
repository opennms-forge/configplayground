
package org.opennms.config;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Optional;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.osgi.service.cm.ConfigurationAdmin;

@Command(scope = "onmsconfig", name = "echoconfig", description = "shows the config")
@Service
public class EchoConfigCommand implements Action {

    @Reference
    Configuration config;

    @Reference
    ConfigurationAdmin configurationAdmin;

    @Option(name = "-o", aliases = {"--option"}, description = "An option to the command", required = false, multiValued = false)
    private String option;

    @Argument(name = "argument", description = "Argument to the command", required = false, multiValued = false)
    private String argument;

    @Override
    public Object execute() throws Exception {
        System.out.print("Hello my friend! Today the config is: " + config.get());
        System.out.print("Hello my friend! Today the config is: " + getConfiguration());
        return null;
    }

    private String getConfiguration() {
        if(configurationAdmin == null) {
            return "no configurationAdmin";
        }
        try {
            return configurationAdmin.getConfiguration("myConfig", null).toString();
//            return Optional
//                    .ofNullable(configurationAdmin.getConfiguration("myConfig", null))
//                    .map(org.osgi.service.cm.Configuration::getProperties)
//                    .map(Dictionary::toString)
//                    .orElse("Could not extract properties");
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
