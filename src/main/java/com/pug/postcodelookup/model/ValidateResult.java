package com.pug.postcodelookup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * https://api.postcodes.io/postcodes/POSTCODE/validate
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateResult {

    private boolean result;

    public ValidateResult() {
    }

    public ValidateResult(boolean result) {
        this.result = result;
    }

    public boolean isValidPostcode() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
