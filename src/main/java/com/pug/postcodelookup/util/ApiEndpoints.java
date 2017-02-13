package com.pug.postcodelookup.util;

/**
 * Endpoints for the postcodes.io API which are used in the application
 */
public class ApiEndpoints {

    public static final String LOOKUP_ENDPOINT = "https://api.postcodes.io/postcodes/%s";

    public static final String VALIDATE_ENDPOINT = LOOKUP_ENDPOINT + "/validate";

    public static final String NEAREST_ENDPOINT = LOOKUP_ENDPOINT + "/nearest";
}
