package it.fabiofucci.demo.controllers;

import it.fabiofucci.demo.services.FacebookTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "facebook")
@CrossOrigin()
public class ArtistFacebookRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistFacebookRestController.class);

    private RestTemplate template = new RestTemplate();

    private String pageSearchEndPoint;

    @Autowired
    private FacebookTokenService facebookTokenService;

    @RequestMapping(value = "/fb_search_page", method = RequestMethod.POST, produces = "application/json")
    public Object getPage(@RequestParam String artistName) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("CANTANTE CERCATO: '%s'", artistName));
        }

        Object response = new HashMap<>();

        if (!artistName.trim().isEmpty()) {
            /*
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            String token = this.facebookTokenService.getToken();

            String backendServiceUrl = this.pageSearchEndPoint;

            if (LOG.isDebugEnabled()) {
                LOG.debug("Endpoint della ricerca pagine di Facebook: " + this.pageSearchEndPoint);
            }

            try {
                response = template.getForObject(
                        backendServiceUrl,
                        HashMap.class,
                        (Object[]) new String[]{
                                artistName,
                                token
                        });
            } catch (HttpClientErrorException e) {

                response = e.getResponseBodyAsString();

                LOG.error(String.format("Errore nella ricerca delle pagine Facebook. [%s]", response));

            } catch (Exception e) {
                LOG.error("Errore nella ricerca delle pagine Facebook", e);
            }
        }

        return response;
    }

    public String getPageSearchEndPoint() {
        return pageSearchEndPoint;
    }

    public void setPageSearchEndPoint(String pageSearchEndPoint) {
        this.pageSearchEndPoint = pageSearchEndPoint;
    }
}
