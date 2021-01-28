# Playground for new configuration approach to OpenNMS

We want to verify that we can use a central component in OpenNMS as a Configuration Service.
Here we verify that our concept works in the osgi context.

# Open questions:
1. Can we hook into the persistence mechanism and use something different than the file storage?
2. Can we notify OSGI that a configuration has changed?

## Set up Scenario
* Download and extract apache-karaf-4.2.10
* Edit startup.properties, add the line `mvn\:org.opennms.config/persistencemanager/1.0.0-SNAPSHOT = 7` This starts the persistencemanager at startup.
* Edit custom.properties, add the line `felix.cm.pm=org.opennms.config.OpenNMSPersistenceManager`. This defines which persistence manager to use.
* Go into each of the the 3 subprojects and build them: `mvn install`
* Copy `./target/persistencemanager-1.0.0-SNAPSHOT.jar` to `apache-karaf-4.2.10/system/org/opennms/config/persistencemanager/1.0.0-SNAPSHOT/` (not sure why it doesn't take the jar from the local maven repo)
* Start Karaf: `./bin/karaf`
* Install blueprint: `feature:install aries-blueprint`
* Install target plugin: `install -s mvn:org.opennms.config/osgiconfig` This is the plugin with a configuration that we want to manage.
* Install command plugin: `install -s mvn:org.opennms.config/configcommands` This plugin allows to change the configuration of the target plugin via the `ConfigurationAdmin`
* Try the scenario in the Karaf shell:
  * `onmsconfig:echoconfig` Command that lives in the osgiconfig plugin.
     Displays the current configuration:
    `Today my config is: the milk is black.`
  * `onmsconfig:updateconfig "the milk is white"`
    Command that lives in the configcommands plugin (different plugin as target plugin).
    It changes the configuration for the target plugin.
    In the shell we see that OpenNMSPersistenceManager was called: `OpenNMSPersistenceManager: Writing configuration for pid = org.opennms.config.echo.` 
  * `onmsconfig:echoconfig`
    Displays the changed configuration:
    `Today my config is: the milk is white.`

# Answers:
1. Replacing the persistence manager with our own implentation is possible.
   We can provide to Felix / Karaf our own implementation of `PersistenceManager`.
2. We can update the configuration via ConfigurationAdmin:
   ```
      configurationAdmin.getConfiguration("org.opennms.config.echo", "?org.opennms");
      Dictionary props = Optional.ofNullable(config.getProperties()).orElse(new Hashtable<>());
      props.put("myConfig", argument);
      config.update(props);
   ```
   This is not an ideal scenario since it triggers again an update to the persistence manager.
   We can mitigate that by checking if someting really changed and only write then.
   Another option is to write our own `ConfigurationAdmin` implementation.
   
