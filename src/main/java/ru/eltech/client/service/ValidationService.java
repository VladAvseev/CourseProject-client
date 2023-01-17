package ru.eltech.client.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {
    public static boolean checkPhone(String phone) {
        Pattern pattern = Pattern.compile("^7\\d{10}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean checkLicense(String license) {
        Pattern pattern = Pattern.compile("^[A-Z]{1}\\d{3}[A-Z]{2}\\d{3}$");
        Matcher matcher = pattern.matcher(license);
        return matcher.matches();
    }
}
