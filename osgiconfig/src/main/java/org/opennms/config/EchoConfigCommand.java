
package org.opennms.config;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;

@Command(scope = "onmsconfig", name = "echoconfig", description = "shows the config")
@Service
public class EchoConfigCommand implements Action {

    @Reference
    Configuration config;

    @Override
    public Object execute() throws Exception {
        System.out.printf("Hello my friend!\nToday my config is: %s.\n" , config.get());
        return null;
    }
}
