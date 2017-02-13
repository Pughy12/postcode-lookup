package com.pug.postcodelookup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * https://api.postcodes.io/postcodes/POSTCODE/nearest
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NearestResult {

    private int status;
    private PostcodeDetails[] result;

    public NearestResult() {
    }

    public NearestResult(int status, PostcodeDetails[] result) {
        this.status = status;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public PostcodeDetails[] getResult() {
        return result;
    }
}
