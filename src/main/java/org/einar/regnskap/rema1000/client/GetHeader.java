package org.einar.regnskap.rema1000.client;

import java.io.Serializable;

public interface GetHeader extends Serializable {
    String getAppName();

    String getAppVersion();

    String getUserMobileNumber();

    String getXCorrelationId();

    String getXDeviceId();
}
