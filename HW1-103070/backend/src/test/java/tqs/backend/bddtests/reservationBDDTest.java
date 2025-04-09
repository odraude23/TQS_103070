package tqs.backend.bddtests;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("tqs/backend/")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "tqs/backend/bddtests")
public class reservationBDDTest {
    
}
