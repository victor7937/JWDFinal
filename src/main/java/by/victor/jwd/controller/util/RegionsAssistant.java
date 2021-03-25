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

public class RegionsAssistant {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("application");
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private static final List<String> regions;
    private final static Logger logger = Logger.getLogger(RegionsAssistant.class);
    private final static int flagOffset = 0x1F1E6;
    private final static int asciiOffset = 0x41;

    static {
        regions = Arrays.asList(bundle.getString("regions").split(","));
    }

    public static List<String> displayCodes (){
        return regions.stream().map(r -> "+" + phoneNumberUtil.getCountryCodeForRegion(r))
                .collect(Collectors.toList());
    }

    public static List<String> displayCountries(String lang){
        return regions.stream().map(r -> new Locale.Builder().setRegion(r).build().getDisplayCountry(
                new Locale.Builder().setLanguage(lang).build())).collect(Collectors.toList());
    }

    public static boolean isPhoneValid(String phone){
        boolean phoneValid = false;
        try {
            for (String region : regions) {
                Phonenumber.PhoneNumber phonenumber = phoneNumberUtil.parse(phone,region);
                if (phoneNumberUtil.isValidNumber(phonenumber)){
                    phoneValid = true;
                    break;
                }
            }

        } catch (NumberParseException e) {
            logger.error("Error while parsing phone number" + e);
            phoneValid = false;
        }
        return phoneValid;

    }

    public static String getCountryFlag (String countryCode) {
        String country = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));

        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        return new String(Character.toChars(firstChar)) + new String(Character.toChars(secondChar));
    }
}
