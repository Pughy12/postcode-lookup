package com.pug.postcodelookup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * https://api.postcodes.io/postcodes/POSTCODE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LookupResult {

    private PostcodeDetails result;

    public LookupResult() {
    }

    public LookupResult(PostcodeDetails result) {
        this.result = result;
    }

    public PostcodeDetails getResult() {
        return result;
    }

    public void setResult(PostcodeDetails result) {
        this.result = result;
    }
}
