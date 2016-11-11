package pl.codeaddict.api;

/**
 * Created by Michal Kostewicz on 30.10.16.
 */
public class OpenWeatherInformationApi implements InformationApi {
    private String openWeatherAPIkey = "&APPID=a058f1a3e872728782cce48743b2c29a";
    private String openWeatherPath = "/data/2.5/weather?q=";
    private String openWeatherPathLast = "&units=metric";
    private String openWeatherAPI = "api.openweathermap.org";
    private String city;
    private String countryCode;

    public OpenWeatherInformationApi(String cityProvided , String countryCodeProvided){
        city = cityProvided;
        countryCode = countryCodeProvided;
    }

    @Override
    public String getHost() {
        return openWeatherAPI;
    }

    @Override
    public String getFullApiPath(String... params) {
        StringBuilder openWheaterFullPath = new StringBuilder()
                .append(openWeatherPath)
                .append(city)
                .append(",")
                .append(countryCode)
                .append(openWeatherPathLast)
                .append(openWeatherAPIkey);
        return openWheaterFullPath.toString();
    }

    @Override
    public int getPort() {
        return apiDefaultPort;
    }
}
