package helpers;

import com.next2.rest.util.ResourceReader;
import org.json.JSONObject;

public class RecordedResponses {

    ResourceReader resourceReader = new ResourceReader();
    public static final String SESSION_INIT = "responses/com.next2.rest.api.Session.init.json";

    public JSONObject sessionInit() {
        return new JSONObject(resourceReader.readFromResources(SESSION_INIT));
    }
}
