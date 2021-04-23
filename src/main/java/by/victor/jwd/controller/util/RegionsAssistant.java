package by.victor.jwd.controller.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Util class for working with phones and regions
 */
public class RegionsAssistant {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("application");
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private static final List<String> regions;
    private final static Logger logger = Logger.getLogger(RegionsAssistant.class);
    private final static int flagOffset = 0x1F1E6;
    private final static int asciiOffset = 0x41;
    private final static String DEFAULT_REGION = "BY";
    private static final String PLUS_SIGN = "+";

    static {
        regions = Arrays.asList(bundle.getString("regions").split(","));
    }

    /**
     * @return list of countries phone codes
     */
    public static List<String> displayCodes (){
        return regions.stream().map(r -> PLUS_SIGN + phoneNumberUtil.getCountryCodeForRegion(r))
                .collect(Collectors.toList());
    }

    /**
     * @param lang - language to translate
     * @return list of countries translated to given language
     */
    public static List<String> displayCountries(String lang){
        return regions.stream().map(r -> new Locale.Builder().setRegion(r).build().getDisplayCountry(
                new Locale.Builder().setLanguage(lang).build())).collect(Collectors.toList());
    }

    /**
     * Checks if phone is valid for country code given
     * @param countryCode - code of country which phone is checking
     * @param phone - locale phone number without code
     * @return true if phone is valid, else false
     */
    public static boolean isPhoneValid(String countryCode, String phone){
        String region = convertCountryCodeToRegion(countryCode);
        return isPhoneValidForRegion(phone, region);
    }

    /**
     * Checks if full phone number(with country code) is valid
     * @param phone full phone number
     * @return true if phone is valid, else false
     */
    public static boolean isPhoneValid(String phone){
       return isPhoneValidForRegion(phone, DEFAULT_REGION);
    }

    /**
     * Returns flag of country by given code
     * @param countryCode code of country
     * @return country flag unicode symbol
     */
    public static String getCountryFlag (String countryCode) {
        String region = convertCountryCodeToRegion(countryCode);

        int firstChar = Character.codePointAt(region, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(region, 1) - asciiOffset + flagOffset;

        return new String(Character.toChars(firstChar)) + new String(Character.toChars(secondChar));
    }

    private static String convertCountryCodeToRegion (String countryCode) {
        return phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
    }

    private static boolean isPhoneValidForRegion(String phone, String region){
        boolean phoneValid = false;
        try {
            Phonenumber.PhoneNumber phonenumber = phoneNumberUtil.parse(phone, region);
            if (phoneNumberUtil.isValidNumber(phonenumber)){
                phoneValid = true;
            }
        } catch (NumberParseException e) {
            logger.error("Error while parsing phone number", e);
            phoneValid = false;
        }
        return phoneValid;
    }
}
