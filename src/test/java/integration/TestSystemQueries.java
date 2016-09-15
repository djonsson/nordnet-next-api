package integration;

import com.next2.rest.api.SystemQueries;
import com.next2.rest.object.SystemStatusInfo;
import org.junit.Test;

public class TestSystemQueries {

    @Test
    public void systemReturnsSystemStatus() {
        SystemQueries system = new SystemQueries();
        SystemStatusInfo systemStatusInfo = system.getSystemStatusInfo();
        long serverTimestamp = systemStatusInfo.getTimeStamp();
        long localTimestamp = java.lang.System.currentTimeMillis();

        assert(systemStatusInfo.isValidVersion());
        assert(systemStatusInfo.isSystemRunning());
        assert(localTimestamp - serverTimestamp < 1000) :
                "Expected time difference to be below 1000ms";
    }
}
