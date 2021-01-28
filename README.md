# Playground for new configuration approach to OpenNMS

# Set up Scenario
* Download and extract apache-karaf-4.2.10
* Edit startup.properties, add the line `mvn\:org.opennms.config/persistencemanager/1.0.0-SNAPSHOT = 7`
* Edit custom.properties, add the line `felix.cm.pm=org.opennms.config.OpenNMSPersistenceManager`
* Go into each of the the 2 subprojects and build them: `mvn install`
* Copy `./target/persistencemanager-1.0.0-SNAPSHOT.jar` to `apache-karaf-4.2.10/system/org/opennms/config/persistencemanager/1.0.0-SNAPSHOT/` (not sure why it doesn't take the jar from the local maven repo)
* Start Karaf: `./bin/karaf`
* Install blueprint: `feature:install aries-blueprint`
* Install plugin: `install -s mvn:org.opennms.config/osgiconfig`
* Try the scenario:
  * `onmsconfig:echoconfig`
  * `onmsconfig:updateconfig "the milk is white"`
  * `onmsconfig:echoconfig`

