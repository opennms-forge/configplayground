package org.opennms.config;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Objects;

import org.apache.felix.cm.PersistenceManager;
import org.apache.felix.cm.file.FilePersistenceManager;
import org.osgi.framework.BundleContext;

/**
 * Our own implementation of a PersistenceManager (subclass of FilePersistenceManager).
 * Must be activated in custom.properties: felix.cm.pm=org.opennms.config.OpenNMSPersistenceManager
 */
public class OpenNMSPersistenceManager extends FilePersistenceManager implements PersistenceManager {


    public OpenNMSPersistenceManager(String location) {
        super(location);
    }

    public OpenNMSPersistenceManager(BundleContext bundleContext, String location) {
        super(bundleContext, location);
    }

    public Dictionary load(String pid) throws IOException {
        System.out.printf("OpenNMSPersistenceManager: Loading configuration for pid = %s.\n", pid);
        return super.load(pid);
    }

    public void store(String pid, Dictionary props) throws IOException {
        Objects.requireNonNull(props);
        System.out.printf("OpenNMSPersistenceManager: Writing configuration for pid = %s.\n", pid);
        if (DictionaryUtil.equalsWithoutRevision(props, super.load(pid))) {
            // nothing to do.
            System.out.println("Won't save props since they haven't changed.");
        } else {
            super.store(pid, props);
        }
    }
}
