package pl.codeaddict.api;

/**
 * Created by Michal Kostewicz on 30.10.16.
 */
public class NbpInformationApi implements InformationApi {
    private String nbpAPI = "api.nbp.pl";
    private String nbpAPIPath1 = "/api/exchangerates/rates/a/";
    private String nbpAPIPath2 = "?format=json";
    private String exchangeCurrency;

    public NbpInformationApi(String exchangeCurrencyProvided){
        exchangeCurrency = exchangeCurrencyProvided;
    }

    @Override
    public String getHost() {
        return nbpAPI;
    }

    @Override
    public String getFullApiPath(String... params) {
        StringBuilder nbpApiPathFull = new StringBuilder()
                .append(nbpAPIPath1)
                .append(exchangeCurrency)
                .append(nbpAPIPath2);
        return nbpApiPathFull.toString();
    }

    @Override
    public int getPort() {
        return apiDefaultPort;
    }

}
