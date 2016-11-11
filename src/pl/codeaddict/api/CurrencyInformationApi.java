package pl.codeaddict.api;

/**
 * Created by Michal Kostewicz on 30.10.16.
 */
public class CurrencyInformationApi implements InformationApi {
    private String currencyApi = "api.fixer.io";
    private String currencyApiPath1 = "/latest?base=";
    private String currencyApiPath2 = ";symbols=";
    private String baseCurrency;
    private String exchangeCurrency;

    public CurrencyInformationApi(String baseCurrencyProvided, String exchangeCurrencyProvided){
        baseCurrency = baseCurrencyProvided;
        exchangeCurrency = exchangeCurrencyProvided;
    }

    @Override
    public String getHost() {
        return currencyApi;
    }

    @Override
    public String getFullApiPath(String... params) {
        StringBuilder currencyApiPathFull = new StringBuilder();
        currencyApiPathFull.append(currencyApiPath1)
                .append(baseCurrency)
                .append(currencyApiPath2)
                .append(exchangeCurrency);
    return  currencyApiPathFull.toString();
    }

    @Override
    public int getPort() {
        return apiDefaultPort;
    }
}
