package com.next2.rest.object;

import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * SystemStatusInfo
 *
 * Returns SystemStatusInfo for the next2 backend.
 *
 * timeStamp (long):
 *     Server timestamp. UNIX timestamp in milliseconds,
 *
 * validVersion (boolean):
 *      True if the API version is a valid version
 *
 * systemRunning (boolean):
 *      Indicates if the system is running or temporarily stopped
 *
 * message (string);
 *      Additional info. Usually empty.
 */

public class SystemStatusInfo {
    Long timeStamp;
    boolean validVersion;
    boolean systemRunning;
    String message;

    public SystemStatusInfo(JSONObject jsonObject) {
        timeStamp       = jsonObject.getLong("timestamp");
        validVersion    = jsonObject.getBoolean("valid_version");
        systemRunning   = jsonObject.getBoolean("system_running");
        message         = jsonObject.getString("message");
    }

    public Long getTimeStamp() {
        return this.timeStamp;
    }

    public String getTimeStampHumanRedable() {
        return timeMillisToReadable(this.timeStamp);
    }

    public boolean isValidVersion() {
        return this.validVersion;
    }

    public boolean isSystemRunning() {
        return this.systemRunning;
    }

    public String getMessage() {
        return this.message;
    }

    /**
     * Converts epoch time to human readable
     *
     * @return String containing a human readable date object
     */
    private String timeMillisToReadable(long currenTimeMillis) {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(currenTimeMillis));
    }
}

