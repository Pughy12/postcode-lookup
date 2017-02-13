package com.pug.postcodelookup.service;

import com.pug.postcodelookup.model.LookupResult;
import com.pug.postcodelookup.model.PostcodeDetails;

import java.util.List;

/**
 * An interface detailing certain things that can be looked up for a specific postcode
 */
public interface PostcodeLookupService {

    public boolean isValidPostcode(String postcode);

    public LookupResult lookupPostcode(String postcode);

    public List<PostcodeDetails> getNearestPostcodesForPostcode(String postcode);
}
