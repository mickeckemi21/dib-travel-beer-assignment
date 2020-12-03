package io.mickeckemi21.beerassignment.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mickeckemi21.beerassignment.exception.ClientException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service
public class SyncPunkApiClientImpl implements PunkApiClient<PunkApiBeer> {

    public static final String URL = "https://api.punkapi.com/v2";
    public static final String ENDPOINT_BEERS_RANDOM = "/beers/random";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SyncPunkApiClientImpl() {
        final CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        this.restTemplate = new RestTemplate(requestFactory);

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public PunkApiBeer getRandomBeer() {
        try {
            final String response = this.restTemplate.getForObject(URL + ENDPOINT_BEERS_RANDOM, String.class);

            return this.objectMapper
                    .readValue(response, new TypeReference<Collection<PunkApiBeer>>() {
                    })
                    .stream().findFirst().orElseThrow(() ->
                            new ClientException("Exception occurred when trying to deserialize PunkApiBeer response"));
        } catch (JsonProcessingException ex) {
            throw new ClientException("Exception occurred when trying to get random beer", ex);
        }
    }
}
