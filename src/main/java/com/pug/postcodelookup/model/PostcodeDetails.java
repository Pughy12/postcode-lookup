package com.pug.postcodelookup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * https://api.postcodes.io/postcodes/POSTCODE - the result object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostcodeDetails {

    private String postcode;
    private String country;
    private String region;

    public PostcodeDetails() {
    }

    public PostcodeDetails(String postcode, String country, String region) {
        this.postcode = postcode;
        this.country = country;
        this.region = region;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
