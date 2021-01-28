package org.opennms.config;

import java.util.Hashtable;

import org.apache.felix.cm.PersistenceManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceReference<PersistenceManager> reference;
    private ServiceRegistration<PersistenceManager> registration;


    @Override
    public void start(BundleContext context) throws Exception {
        Hashtable<String,String> config = new Hashtable<>();
        config.put("service.ranking", "1000");
        config.put("name", OpenNMSPersistenceManager.class.getName());
        System.out.printf("Registering service %s.%n", OpenNMSPersistenceManager.class.getSimpleName());
        registration = context.registerService(
                PersistenceManager.class,
                new OpenNMSPersistenceManager(null),
                config);
        reference = registration
                .getReference();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("stopped");
    }

}
