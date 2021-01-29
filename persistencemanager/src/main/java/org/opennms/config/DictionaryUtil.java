package org.opennms.config;

import java.util.Collections;
import java.util.Dictionary;

public class DictionaryUtil {
    public static boolean equalsWithoutRevision(Dictionary<String, String> a, Dictionary<String, String> b) {
        if(a == null && b==null) {
            return true;
        } else if (a == null || b == null) {
            return false;
        } else if(a.size() != b.size()) {
            return false;
        }

        return Collections.list(a.keys())
                .stream()
                .filter(key -> !":org.apache.felix.configadmin.revision:".equals(key))
                .allMatch(key -> a.get(key).equals(b.get(key)));
    }
}
