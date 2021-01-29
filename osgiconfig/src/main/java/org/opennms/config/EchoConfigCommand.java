
package org.opennms.config;

import java.util.Objects;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.osgi.framework.BundleContext;

@Command(scope = "onmsconfig", name = "echoconfig", description = "shows the config")
@Service
public class EchoConfigCommand implements Action {

    @Reference
    Configuration config;

    @Reference
    BundleContext bundleContext;

    @Override
    public Object execute() throws Exception {
        System.out.printf("Hello my friend!\nToday my config is: %s.%n" , config.get());

        bundleContext.getServiceReferences(AvatarService.class, null)
        .stream()
        .map(bundleContext::getService)
        .filter(Objects::nonNull)
        .map(AvatarService::getName)
        .forEach(name -> System.out.printf("Avatar with name %s found.%n", name));

        return null;
    }
}
