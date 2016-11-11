/**
 * @author Kostewicz Michał S11474
 */

package pl.codeaddict.service;


import org.json.JSONObject;
import pl.codeaddict.api.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {
    private static final String CELSIUS = "C\u00B0";
    public static final String POLISH_CURRENCY_CODE = "PLN";
    private static String REQUEST_CHAR_CODING = "US-ASCII";
    private String country;
    private String countryCode;
    private String city;
    private static Pattern JSONpattern = Pattern.compile("\\{\\s*?.*\\}", Pattern.CASE_INSENSITIVE);

    private String choosenCountryCurrencyCode;

    public Service(String providedCountry) {
        country = providedCountry;
        JSONObject jsonObjectCountryInfo = getCountryInfo(providedCountry);
        choosenCountryCurrencyCode = (String) jsonObjectCountryInfo.getJSONArray("currencies").get(0);
        countryCode = jsonObjectCountryInfo.getString("alpha2Code");
    }

    private JSONObject getCountryInfo(String providedCountry) {
        CountryInformationApi countryInfoAPI = new CountryInformationApi(providedCountry);
        return apiReader(countryInfoAPI);
    }

    public String getWeather(String providedCity) {
        this.city = providedCity;
        OpenWeatherInformationApi openWeatherInformationApi = new OpenWeatherInformationApi(providedCity, countryCode);
        JSONObject openWeatherJsonObject = apiReader(openWeatherInformationApi);
        return openWeatherJsonObject.toString();
    }

    public String getWeatherInfo(String providedCity) {
        JSONObject openWeatherJsonObject = new JSONObject(getWeather(providedCity));
        String sky = openWeatherJsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
        Double tempMin = openWeatherJsonObject.getJSONObject("main").getDouble("temp_min");
        Double tempMax = openWeatherJsonObject.getJSONObject("main").getDouble("temp_max");
        Double temp = openWeatherJsonObject.getJSONObject("main").getDouble("temp");
        Double pressure = openWeatherJsonObject.getJSONObject("main").getDouble("pressure");

        StringBuilder weatherInfo = new StringBuilder();
        String city = providedCity.substring(0, 1).toUpperCase() + providedCity.substring(1);
        weatherInfo.append(city)
                .append(", sky - ").append(sky)
                .append(", TEMP. MIN - ").append(tempMin).append(CELSIUS)
                .append(", TEMP. - ").append(temp).append(CELSIUS)
                .append(", TEMP. MAX - ").append(tempMax).append(CELSIUS)
                .append(", pressure - ").append(pressure);
        return weatherInfo.toString();
    }

    public Double getRateFor(String currencyCode) {
        if (currencyCode.equalsIgnoreCase(choosenCountryCurrencyCode)) {
            return 1.0;
        }
        CurrencyInformationApi currencyInformationApi = new CurrencyInformationApi(choosenCountryCurrencyCode, currencyCode);
        JSONObject currencyRateJsonObject = apiReader(currencyInformationApi);
        Double rate = currencyRateJsonObject.getJSONObject("rates").getDouble(currencyCode);
        return rate;
    }

    public Double getNBPRate() {
        if (choosenCountryCurrencyCode.equals(POLISH_CURRENCY_CODE)) {
            return 1.0;
        }
        NbpInformationApi nbpInformationApi = new NbpInformationApi(choosenCountryCurrencyCode);
        JSONObject nbpRateJsonObject = apiReader(nbpInformationApi);
        Double rate = nbpRateJsonObject.getJSONArray("rates").getJSONObject(0).getDouble("mid");
        return rate;
    }

    private JSONObject apiReader(InformationApi informationApi) {
        JSONObject jsonObject = null;
        String host = informationApi.getHost();
        int port = informationApi.getPort();
        String fullPath = informationApi.getFullApiPath();
        SocketAddress remote = new InetSocketAddress(host, port);
        try (SocketChannel channel2 = SocketChannel.open(remote)) {
            String request = "GET " + fullPath + " HTTP/1.1\r\n" + "User-Agent: HTTPGrab\r\n"
                    + "Accept: application/json\r\n" + "Connection: close\r\n" + "Host: " + host + "\r\n" + "\r\n";
            ByteBuffer header = ByteBuffer.wrap(request.getBytes(REQUEST_CHAR_CODING));
            channel2.write(header);
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            while (channel2.read(buffer) != -1) {
                buffer.flip();
                String line = new String(buffer.array());
                //System.out.println(line);         //debug
                Matcher matcher = JSONpattern.matcher(line);
                while (matcher.find()) {
                    String result = line.substring(matcher.start(), matcher.end());
                    jsonObject = new JSONObject(result);
                }
                buffer.clear();
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsupported Encoding " + e.getLocalizedMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Connection problem!" + e.getLocalizedMessage());
            System.exit(2);
        }
        return jsonObject;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

