package integration;

import com.next2.rest.api.Indicators;
import com.next2.rest.model.Indicator;
import com.next2.rest.model.IndicatorList;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class TestIndicators {

    Indicators indicators = new Indicators();

    @Test
    public void getIndicatorsShouldReturn30indicators() {
        assertThat(("Got wrong number of indicators"), indicators.getIndicators().getNumberOfIndicators(), equalTo(30));
    }

    @Test
    public void getIndicatorsShouldReturnAnIndicatorList() {
        IndicatorList indicatorList = indicators.getIndicators();
        assertThat(("Did not return an IndicatorList"), indicatorList, instanceOf(IndicatorList.class));
    }

    @Test
    public void getIndicatorListShouldContaintIndicators() {
        assertThat(("The IndicatorList did not contain an Indicator at index 0"),
                indicators.getIndicators().getIndicatorByIndex(0), instanceOf(Indicator.class));
    }
}