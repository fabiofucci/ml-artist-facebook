package it.fabiofucci.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "google")
@CrossOrigin()
public class ArtistGoogleRestController {
    private static final Logger LOG = LoggerFactory.getLogger(ArtistGoogleRestController.class);

    private RestTemplate template = new RestTemplate();

    private String apiKey;

    private String csEngineId;

    private String searchEndPoint;

    @RequestMapping(value = "/google_search_page", method = RequestMethod.POST, produces = "application/json")
    public Object getPageByGoogle(@RequestParam String artistName) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Ricerca del cantante '%s'", artistName));
        }

        // Object response = new HashMap<>();
        String response = "";

        if (!artistName.trim().isEmpty()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String backendServiceUrl = this.searchEndPoint;

            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("Endpoint del servizio di ricerca: [%s]", backendServiceUrl));
            }

            try {
                response = template.getForObject(
                        backendServiceUrl,
                        String.class,
                        (Object[]) new String[] {
                                this.apiKey,
                                this.csEngineId,
                                artistName
                        });
            } catch (HttpClientErrorException e) {
                LOG.error("Errore: %s", e.getResponseBodyAsString());

                response = e.getResponseBodyAsString();

            } catch (Exception e) {
                LOG.error("Errore: %s", e);
            }
        }

        return response;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCsEngineId() {
        return csEngineId;
    }

    public void setCsEngineId(String csEngineId) {
        this.csEngineId = csEngineId;
    }

    public String getSearchEndPoint() {
        return searchEndPoint;
    }

    public void setSearchEndPoint(String searchEndPoint) {
        this.searchEndPoint = searchEndPoint;
    }
}
