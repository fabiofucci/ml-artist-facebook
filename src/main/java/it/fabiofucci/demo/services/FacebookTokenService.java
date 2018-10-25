package it.fabiofucci.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@ConfigurationProperties(prefix = "facebook")
public class FacebookTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(FacebookTokenService.class);

    private String clientId;

    private String clientSecret;

    private String accessTokenEndPoint;

    private RestTemplate template = new RestTemplate();


    public String getToken() {
        String getTokenUrl = this.accessTokenEndPoint;

        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("ENDPOINT per ottenere il Facobook Token: [%s]", this.accessTokenEndPoint));
        }

        HashMap<String, String> tokenResponse = new HashMap<>();

        try {
            tokenResponse = template.getForObject(
                    getTokenUrl,
                    HashMap.class,
                    (Object[]) new String[] {
                            this.clientId,
                            this.clientSecret
                    });
        } catch (HttpClientErrorException e) {
            LOG.error("ERRORE nel recupero del Token Facebook", e.getResponseBodyAsString());
        } catch (Exception e) {
            LOG.error("ERRORE nel recupero del Token Facebook", e);
        }

        String token = tokenResponse.get("access_token");

        if (LOG.isDebugEnabled()) {
            LOG.debug("Token Facebook generato: ", token);
        }

        return token;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessTokenEndPoint() {
        return accessTokenEndPoint;
    }

    public void setAccessTokenEndPoint(String accessTokenEndPoint) {
        this.accessTokenEndPoint = accessTokenEndPoint;
    }
}
