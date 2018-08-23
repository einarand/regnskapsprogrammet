package org.einar.regnskap.rema1000.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerCorrelationHeaders {
    private static Map<String, List<String>> headers;

    static {
        Map hashMap = new HashMap(2);
        headers = hashMap;
        hashMap.put("ADRUM", Collections.singletonList("isAjax:true"));
        headers.put("ADRUM_1", Collections.singletonList("isMobile:true"));
        headers = Collections.unmodifiableMap(headers);
    }

    private ServerCorrelationHeaders() {
    }

    public static Map<String, List<String>> generate() {
        return headers;
    }
}
