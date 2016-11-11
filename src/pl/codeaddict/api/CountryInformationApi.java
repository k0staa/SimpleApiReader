package pl.codeaddict.api;

/**
 * Created by Michal Kostewicz on 30.10.16.
 */
public class CountryInformationApi implements InformationApi {
    private String countryInfoAPI = "restcountries.eu";
    private String countryInfoApiPath1 = "/rest/v1/name/";
    private String countryInfoApiPath2 = "?fullText=true";
    private String country;

    public CountryInformationApi(String providedCountry){
        country = providedCountry;
    }

    @Override
    public String getHost() {
        return countryInfoAPI;
    }

    @Override
    public String getFullApiPath(String ...params) {
        StringBuilder countryApiPathFull = new StringBuilder();
        countryApiPathFull.append(countryInfoApiPath1)
                .append(country)
                .append(countryInfoApiPath2);
        return countryApiPathFull.toString();

    }

    @Override
    public int getPort() {
        return apiDefaultPort;
    }
}
