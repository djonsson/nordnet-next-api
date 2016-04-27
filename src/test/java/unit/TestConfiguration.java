package unit;

import com.next2.rest.util.ResponseHandler;
import com.next2.rest.util.ResourceReader;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;


public class TestConfiguration {

    ResourceReader resourceReader = new ResourceReader();

    String nextTestEnvironmentPropertiesFilename = "next-test-environment.properties";
    String log4jPropertiesFilename = "log4j.properties";

    @Test
    public void nextTestEnvironmentPropertiesShouldExist() {
        Properties properties = resourceReader.getProperties(nextTestEnvironmentPropertiesFilename);
        assertThat("The properties file for test environment was not found",
                properties, instanceOf(Properties.class));
    }

    @Test
    public void propertiesFileShouldContainExpectedParameters() {
        Properties properties = resourceReader.getProperties(nextTestEnvironmentPropertiesFilename);
        assertNotNull("Expected 'username' to be found in properties file", properties.getProperty("username"));
        assertNotNull("Expected 'password' to be found in properties file", properties.getProperty("password"));
        assertNotNull("Expected 'baseUrl' to be found in properties file", properties.getProperty("baseurl"));
        assertNotNull("Expected 'pemfile' to be found in properties file", properties.getProperty("pemfile"));
    }

    @Test
    public void validPemFileShouldBeFoundInPropertiesFile() {
        Properties properties = resourceReader.getProperties(nextTestEnvironmentPropertiesFilename);
        String pem = resourceReader.readFromResources(properties.getProperty("pemfile"));
        String[] lines = pem.split(System.getProperty("line.separator"));
        assertThat("The first line of the pem file is what we expect",
                lines[0], equalTo("-----BEGIN PUBLIC KEY-----"));
    }

    @Test
    public void log4jShouldBeConfiguredWithNoLogging() {
        Properties properties = resourceReader.getProperties(log4jPropertiesFilename);
        assertThat("log4j.rootLogger should be set to debug, stdout, file in test configuration",
                properties.getProperty("log4j.rootLogger"), equalTo("info, stdout, file"));
    }

    @Test
    public void recordingShouldBeTurnedOff() {
        assertThat("ResponseHandler should not be enabled when packaging",
                ResponseHandler.recordResponse, equalTo(false));
    }


}
