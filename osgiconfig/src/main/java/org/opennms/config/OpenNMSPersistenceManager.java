package org.opennms.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.felix.cm.PersistenceManager;

// In memory dummy implementation.
public class OpenNMSPersistenceManager implements PersistenceManager {

    private final Map<String, Dictionary> allDictionaries;

    public OpenNMSPersistenceManager() {
        allDictionaries = new ConcurrentHashMap<>();
    }

    @Override
    public boolean exists(final String s) {
        return allDictionaries.containsKey(s);
    }

    @Override
    public Dictionary load(String s) throws IOException {
        return copy(allDictionaries.get(s));

    }

    private <K,V> Dictionary<K,V> copy(Dictionary<K,V> dictionary) {
        if (dictionary != null) {
            Hashtable<K,V> table = new Hashtable<>();
            Enumeration<K> enumeration = dictionary.keys();
            while (enumeration.hasMoreElements()) {
                K key = enumeration.nextElement();
                table.put(key, dictionary.get(key));
            }
            return table;
        } else {
            return null;
        }
    }

    @Override
    public Enumeration getDictionaries() throws IOException {
        Collection<Dictionary> list = allDictionaries
                .values();
        Collection<?> list2 = (Collection<?>) list.stream()
                .map(this::copy)
                .collect(Collectors.toList());
        return Collections.enumeration(list2);
    }

    @Override
    public void store(String s, Dictionary dictionary) throws IOException {
        allDictionaries.put(s, copy(dictionary));
    }

    @Override
    public void delete(String s) throws IOException {
        allDictionaries.remove(s);
    }
}
