package com.next2.rest.api;

import com.next2.rest.model.AccountList;
import com.next2.rest.model.CountryList;
import com.next2.rest.model.TTL;
import com.next2.rest.util.RequestHandler;
import com.next2.rest.util.ResponseHandler;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * The main class for country operations with capabilities to perform the following actions:
 *
 * {@link #getAllCountries()}            - Returns a list of Country objects that are available in the system
 * {@link #getCountry(java.lang.String)} - Returns one, or several, countries as a CountryList
 *
 */
public class Countries extends Session {
    private final static Logger LOG = Logger.getLogger(Countries.class);
    private final static String API = "countries";

    CountryList countryList;
    TTL countryListTTL = new TTL(1000);

    CountryList customCountryList;
    TTL customCountryListTTL = new TTL(1000);

    /**
     * Returns a list of all countries that are in the next system. Please not that trading is not available everywhere.
     *
     * This method return an CountryList of with Country objects. The response is returned from the API as a JSONArray
     * that we parse into a CountryList object.
     *
     * @see <a href="https://api.test.nordnet.se/api-docs/index.html#!/countries/get_countries">Get All Countries</a>
     *
     * @return countryList
     */
    public CountryList getAllCountries() {
        LOG.info("Entering getAllCountries");

        if (countryListTTL.isStale()) {
            countryListTTL.resetRequestTime();

            Invocation.Builder invocation = webTarget.path(API).request(responseType);

            Response response = RequestHandler.GET(invocation);
            JSONArray jsonArray = ResponseHandler.asJsonArray(response);

            this.countryList = new CountryList(jsonArray);
        }
        return this.countryList;
    }

    /**
     * Similar to getAllCountries but allow the user to lookup one or more specified countries.
     * Multiple countries can be queried at the same time by comma separating the country codes.
     *
     * This method return an CountryList of with Country objects. The response is returned from the API as a JSONArray
     * that we parse into a CountryList object.
     *
     * @see <a href="https://api.test.nordnet.se/api-docs/index.html#!/countries/get_country">Get Country</a>
     *
     * @param countryCodes A comma separated list of country coddes (SE,FI,NO,DK)
     * @return CountryList containing the countries.
     */
    public CountryList getCountry(String countryCodes) {
        LOG.info("Entering getCountry");
        if (customCountryListTTL.isStale()) {
            customCountryListTTL.resetRequestTime();

            Invocation.Builder invocation = webTarget.path(API).path(countryCodes).request(responseType);
            Response response = RequestHandler.GET(invocation);
            JSONArray jsonArray = ResponseHandler.asJsonArray(response);
            this.customCountryList = new CountryList(jsonArray);
        }
    return this.customCountryList;
    }

    /**
     * Helper method to getCountry that accepts a countryList as parameter and sends it to getCountry as a comma
     * separated list
     *
     * @param countryList a CountryList containing countries
     * @return countryList of the response from the API
     */
    public CountryList getCountry(CountryList countryList) {
        return getCountry(countryList.getCountryCodesAsCommaSeparatedString());
    }
}
