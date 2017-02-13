package com.pug.postcodelookup;

import com.pug.postcodelookup.model.LookupResult;
import com.pug.postcodelookup.model.PostcodeDetails;
import com.pug.postcodelookup.service.PostcodeLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * A command-line runnable application which takes a postcode as a string for an input and performs a few actions:
 * - Validates the postcode
 * - Looks up the country and region of the postcode, if it was valid
 * - Looks up the nearest postcode(s) to the postcode, and prints their country and regions (again only if it was valid)
 * It uses the postcodes.io API to retrieve the information.
 *
 * To start, run the application in IntelliJ as a Spring Boot app. If nothing is passed in to the "Program Arguments",
 * an error will occur. If you pass a string into it, you will get the previously mentioned functionality printed out.
 *
 * To build using maven, run "mvn clean package" to build the application into a runnable jar file.
 * To run the built jar, run "java -jar target/postcode-lookup-0.0.1-SNAPSHOT.jar ARGUMENTS" where ARGUMENT
 * would be the postcode as a string.
 */
@SpringBootApplication
public class PostcodeLookupApplication implements CommandLineRunner {

	private final PostcodeLookupService lookupService;

	@Autowired
	public PostcodeLookupApplication(PostcodeLookupService lookupService) {
		this.lookupService = lookupService;
	}

	@Override
	public void run(String... args) throws Exception {

		// Sanity checks
		if (args.length < 1) {
			throw new IllegalArgumentException("You must enter a postcode");
		}

		final String postcode = args[0];

		if (postcode.length() < 6 || postcode.length() > 8) {
			throw new IllegalArgumentException("That doesn't look like a postcode to me");
		}

		/*
		 * 1) - VALIDATE THE POSTCODE
 		 */
		final boolean isValidPostcode = lookupService.isValidPostcode(postcode);

		System.out.println("1) Is " + postcode + " a valid postcode?");
		printIsValidPostcode(postcode, isValidPostcode);


        /*
         * 2) - PRINT COUNTRY AND REGION FOR POSTCODE
         */
		final LookupResult lookupResult = lookupService.lookupPostcode(postcode);
		System.out.println("\n\n2) Print country and region for " + postcode);

		if (lookupResult != null && lookupResult.getResult() != null) {
			final PostcodeDetails postcodeDetails = lookupResult.getResult();

			printCountry(postcodeDetails);
			printRegion(postcodeDetails);
		} else {
			System.out.println("Sorry, that postcode appears to be invalid");
		}

        /*
         * 3) - PRINT NEAREST POSTCODES AND THEIR COUNTRY AND REGIONS TOO
         */
		final List<PostcodeDetails> nearestResult = lookupService.getNearestPostcodesForPostcode(postcode);
		System.out.println("\n\n3) Print nearest postcode to " + postcode + " and their details");

		if (nearestResult != null && nearestResult.size() != 0) {

			// Remove the first result, because it's the postcode we looked up in the first place
			//TODO: If the input postcode has a space in it, this won't work. How can we fix that?
			nearestResult.removeIf(postcodeDetails -> postcode.equals(postcodeDetails.getPostcode()));

			System.out.println("The nearest postcode to " + postcode + " is: " + nearestResult.get(0).getPostcode());

			// Print the country and region for each nearest postcode
			nearestResult.forEach(PostcodeLookupApplication::printCountry);
			nearestResult.forEach(PostcodeLookupApplication::printRegion);
		} else {
			System.out.println("Sorry, that postcode appears to be invalid");
		}

		// Application will terminate here since there's nothing left to do
	}

	/**
	 * Main method to run the spring application
	 * @param args Arguments passed in at runtime
	 */
	public static void main(String[] args) {
		SpringApplication.run(PostcodeLookupApplication.class, args);
	}

	/**
	 * Prints to the console whether a postcode is valid or not
	 *
	 * @param postcode The postcode that was validated
	 * @param isValidPostcode True if the postcode was valid, false otherwise
	 */
	private static void printIsValidPostcode(String postcode, boolean isValidPostcode) {
		final StringBuilder sb = new StringBuilder();

		sb.append(postcode);
		sb.append(" is ");
		if (!isValidPostcode) sb.append(" not ");
		sb.append(" a valid postcode");

		System.out.println(sb.toString());

		if (isValidPostcode) {
			System.out.println(postcode + " is a valid postcode");
		} else {
			System.out.println(postcode + " is not a valid postcode");
		}
	}

	/**
	 * Prints to the console the country a postcode is in
	 *
	 * @param postcodeDetails Detailed information about the postcode
	 */
	private static void printCountry(PostcodeDetails postcodeDetails) {
		System.out.println(postcodeDetails.getPostcode() + " is in " + postcodeDetails.getCountry());
	}

	/**
	 * Prints to the console the region a postcode is in
	 *
	 * @param postcodeDetails Detailed information about the postcode
	 */
	private static void printRegion(PostcodeDetails postcodeDetails) {
		System.out.println(postcodeDetails.getPostcode() + " is in " + postcodeDetails.getRegion());
	}
}

