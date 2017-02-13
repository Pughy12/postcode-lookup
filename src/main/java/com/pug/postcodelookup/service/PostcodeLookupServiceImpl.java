package com.pug.postcodelookup.service;

import com.pug.postcodelookup.model.LookupResult;
import com.pug.postcodelookup.model.NearestResult;
import com.pug.postcodelookup.model.PostcodeDetails;
import com.pug.postcodelookup.model.ValidateResult;
import com.pug.postcodelookup.util.ApiEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * An implementation of the PostcodeLookupService which uses the postcodes.io API to get information on a postcode
 */
@Service
public class PostcodeLookupServiceImpl implements PostcodeLookupService {

    private RestTemplate restTemplate;

    @Autowired
    public PostcodeLookupServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Using the Postcodes.io API, find out whether a given postcode is valid or not
     *
     * @param postcode The postcode to be validated
     *
     * @return True if the postcode is considered valid according to the API, otherwise false
     */
    public boolean isValidPostcode(String postcode) {
        final String uri = String.format(ApiEndpoints.VALIDATE_ENDPOINT, postcode);

        final ValidateResult response = restTemplate.getForObject(uri, ValidateResult.class);

        return response.isValidPostcode();
    }

    /**
     * Using the postcodes.io API, look up the details for a given postcode
     *
     * @param postcode The postcode to get more info of
     *
     * @return A {@link LookupResult} object which contains information about a postcode
     */
    public LookupResult lookupPostcode(String postcode) {
        final String uri = String.format(ApiEndpoints.LOOKUP_ENDPOINT, postcode);

        LookupResult lookupResult = null;

        try {
            lookupResult = restTemplate.getForObject(uri, LookupResult.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw e;
            }
        }

        if (lookupResult != null) {
            return lookupResult;
        } else {
            return null;
        }
    }

    /**
     * Using the postcodes.io API, get the nearest postcode(s) for a given postcode
     *
     * @param postcode The postcode to look up and get the nearest one(s) to
     *
     * @return A {@link List} of {@link PostcodeDetails} objects which contain information about each postcode
     */
    public List<PostcodeDetails> getNearestPostcodesForPostcode(String postcode) {
        final String uri = String.format(ApiEndpoints.NEAREST_ENDPOINT, postcode);

        NearestResult nearestResult = null;

        try {
            nearestResult = restTemplate.getForObject(uri, NearestResult.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw e;
            }
        }

        if (nearestResult != null) {
            return new LinkedList<>(Arrays.asList(nearestResult.getResult()));
        } else {
            return null;
        }
    }
}
