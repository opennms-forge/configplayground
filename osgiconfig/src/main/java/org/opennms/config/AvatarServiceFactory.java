/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2019-2019 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2019 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.config;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;



public class AvatarServiceFactory implements ManagedServiceFactory {

    private static final String AVATAR_NAME = "avatarName";

    private final BundleContext bundleContext;
    private final Map<String, List<ServiceRegistration<?>>> registrations = new HashMap<>();

    public AvatarServiceFactory(BundleContext bundleContext) {
        this.bundleContext = Objects.requireNonNull(bundleContext);
    }

    @Override
    public String getName() {
        return "This Factory creates AvatarServices";
    }

    @Override
    public void updated(String pid, @SuppressWarnings("rawtypes") Dictionary properties) {
        System.out.println("updated(String, Dictionary) invoked");
        if (!registrations.containsKey(pid)) {
            System.out.printf("Service with pid '%s' is new. Register %s%n", pid, AvatarService.class.getSimpleName());
            final Dictionary<String,Object> metaData = new Hashtable<>();
            metaData.put(Constants.SERVICE_PID, pid);

            // Expose the Container Provider
            final String name = (String)properties.get(AVATAR_NAME);
            final AvatarService avatar = new AvatarService(name);
            registerService(pid, AvatarService.class, avatar, metaData);

        } else {
            System.out.printf("Service with pid '%s' updated. Updating is not supported. Ignoring...%n", pid);
        }
    }

    @Override
    public void deleted(String pid) {
        System.out.println("deleted(String) invoked");
        List<ServiceRegistration<?>> serviceRegistrations = registrations.get(pid);
        if (serviceRegistrations != null) {
            System.out.printf("Unregister services for pid '%s'%n", pid);
            serviceRegistrations.forEach(ServiceRegistration::unregister);
            registrations.remove(pid);
        }
    }

    private <T> void registerService(String pid, Class<T> serviceType, T serviceImpl, Dictionary<String, Object> serviceProperties) {
        final ServiceRegistration<T> serviceRegistration = bundleContext.registerService(serviceType, serviceImpl, serviceProperties);
        registrations.putIfAbsent(pid, new ArrayList<>());
        registrations.get(pid).add(serviceRegistration);
    }
}


