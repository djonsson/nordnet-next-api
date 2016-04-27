package com.next2.rest.model;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TTL {
    Long    requestTimeInMillis;
    Integer timeToLive = 0;

    final static Logger log = Logger.getLogger(TTL.class);

    public TTL(int timeToLive) {
        setTimeToLive(timeToLive);
    }

    public long getRequestTimeInMillis() {
        return this.requestTimeInMillis;
    }

    public int getTimeToLive() {
        return this.timeToLive;
    }

    public int setTimeToLive(Integer timeToLive) {
        this.timeToLive = timeToLive;
        return timeToLive;
    }

    public long resetRequestTime() {
        log.info("Updating the time for the latest request");
        requestTimeInMillis = System.currentTimeMillis();
        return this.requestTimeInMillis;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public boolean isStale() {
        if (requestTimeInMillis == null) {
            return true;
        }

        long lifeTime   = getCurrentTime() - requestTimeInMillis;
        boolean isStale = lifeTime > timeToLive;
        log.debug("The request was made: "  + timeMillisToReadable(requestTimeInMillis) + " ms");
        log.debug("The current time is: "   + timeMillisToReadable(getCurrentTime()) + " ms");
        log.debug("The request ttl is: "    + timeToLive + " ms");
        log.debug("The lifetime is: "       + lifeTime + " ms");
        log.debug("Was the request stale: " + isStale);
        return isStale;
    }

    public String timeMillisToReadable(long currenTimeMillis) {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(currenTimeMillis));
    }
}
