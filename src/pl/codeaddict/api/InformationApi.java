package pl.codeaddict.api;

import com.sun.istack.internal.NotNull;

/**
 * Created by Michal Kostewicz on 30.10.16.
 */
public interface InformationApi {
    int apiDefaultPort = 80;

    @NotNull String getHost();

    String getFullApiPath(String ... params);

    int getPort();
}
