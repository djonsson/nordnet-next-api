package integration;

import com.next2.rest.api.Countries;
import com.next2.rest.model.Country;
import com.next2.rest.model.CountryList;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TestCountries {
    Countries countries = new Countries();

    @Test
    public void getCountryListShouldReturnCountries() {
        CountryList countryList = countries.getAllCountries();
        int numberOfCountries = countryList.getNumberOfCountries();
        assertThat("Got the wrong amount of countries", numberOfCountries, equalTo(251));
    }


    @Test
    public void getSwedenAsCountryShouldReturnSeAsCountryCode() {
        Country sweden = new Country("SE", "Sweden");
        assertThat("Sweden should return 'SE' as country code",
                countries.getAllCountries().getNamedCountry("Sweden").getCountryCode(), equalTo(sweden.getCountryCode()));
    }

    @Test
    public void getFailingAsCountryShouldReturnNull() {
        assertThat("Failing should return 'null' as country code",
                countries.getAllCountries().getNamedCountry("Failing").getCountryCode(), equalTo(null));
    }

    @Test
    public void getCountryWithCountryListContainingTwoCountryObjectsShouldReturnCountryList() {
        Country sweden = new Country("SE", "Sweden");
        Country finland = new Country("FI", "Finland");
        CountryList countryList = new CountryList();
        countryList.add(sweden);
        countryList.add(finland);
        assertThat("getCountry with a countryList containing two valid country objects did not return valid two objects",
                countries.getCountry(countryList).getNumberOfCountries(), equalTo(2));
    }

    @Test
    public void getCountryWithCountryListAsParameterAndOneBadParameterShouldReturnOne() {
        Country sweden = new Country("SE", "Sweden");
        Country finland = new Country("XX", "XXXX");
        CountryList countryList = new CountryList();
        countryList.add(sweden);
        countryList.add(finland);
        assertThat("getCountry with a countryList containing two valid country objects did not return valid two objects",
                countries.getCountry(countryList).getNumberOfCountries(), equalTo(1));
    }
}
