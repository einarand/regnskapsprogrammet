package org.einar.regnskap.rema1000.client;

import java.util.UUID;

public class GetHeaderLogic implements GetHeader {
    private static GetHeader getHeader;

    private GetHeaderLogic() {
    }

    public static GetHeader getInstance() {
        if (getHeader == null) {
            getHeader = new GetHeaderLogic();
        }
        return getHeader;
    }

    public String getXDeviceId() {
        return "328b8170-684c-436a-9786-aa3f3f08ba9e"; //PreferenceHelper.getUUIDString(Application.getAppContext());
    }

    public String getXCorrelationId() {
        return UUID.randomUUID().toString();
    }

    public String getUserMobileNumber() {
        return "90948784";
    }

    public String getAppName() {
        return "bella";
    }

    public String getAppVersion() {
        return "1.2.8"; //Utils.getAppVersionName(Application.getAppContext());
    }
}
