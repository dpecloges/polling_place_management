package com.ots.dpel.global.utils;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author sdimitriadis
 */
public class DpRegexUtils {
    
    private static final String greekUpperNoSpecialChar = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ";
    private static final String greekUpperNoSpecialCharExceptSpace = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ ";
    private static final String greekUpperChar = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩΆΈΉΊΌΎΏΪΫ";
    private static final String greekLowerChar = "αβγδεζηθικλμνξοπρστυφχψωάέήίόύώϊϋΐΰς";
    private static final String greekAccentedChar = "ΆΈΉΊΌΎΏάέήίόύώΐΰ";
    private static final String latinChar = "A-Za-z";
    
    private static final String lightSymbol = "#\"";
    private static final String otherSymbol = "`~\\!@\\$%\\^&\\*\\(\\)\\-\\_\\=\\+\\[\\]\\{\\}\\;\\:'\\\\|<>,\\.\\?/΄";
    private static final String allSymbol = lightSymbol + otherSymbol;
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private static final String oneUpperCaseGreekLetter = "[" + greekUpperChar + "]";
    private static final String zeroOrMoreNonUpperCaseGreekLetters = "[^" + greekUpperChar + "]*";
    private static final String oneUpperOrLowerCaseGreekLetter = "[" + greekUpperChar + greekLowerChar + "]";
    private static final String oneAccentedGreekLetter = "[" + greekAccentedChar + "]";
    private static final String notOneAccentedGreekLetter = "[^" + greekAccentedChar + "]";
    private static final String oneOrMoreAccentedGreekLetters = "[" + greekAccentedChar + "]+";
    
    private static final String zeroOrMoreNonNumbers = "[^0-9]*";
    private static final String zeroOrMoreNonAllSymbols = "[^" + allSymbol + "]*";
    private static final String zeroOrMoreNonOtherSymbols = "[^" + otherSymbol + "]*";
    private static final String zeroOrMoreNonSpaces = "[^ ]*";
    
    private static final String zeroOrMoreEprLetters = "[" + greekUpperNoSpecialChar + " \\-\\." + "]*";
    private static final String zeroOrMoreGreekUpperNoSpecialCharLettersExceptSpace = "[" + greekUpperNoSpecialCharExceptSpace + "]*";
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private static final String wordNoLatin = "^[^" + latinChar + "]+$";
    private static final String wordNoNumbers = "^" + zeroOrMoreNonNumbers + "$";
    private static final String wordNoSymbols = "^" + zeroOrMoreNonAllSymbols + "$";
    private static final String wordNoSymbolsExceptLight = "^" + zeroOrMoreNonOtherSymbols + "$";
    
    private static final String wordNoSpaces = "^" + zeroOrMoreNonSpaces + "$";
    private static final String wordCapitalized = "^" + oneUpperCaseGreekLetter + zeroOrMoreNonUpperCaseGreekLetters + "$";
    private static final String wordOneAccent = "^" + notOneAccentedGreekLetter + "*" + oneAccentedGreekLetter + "" + notOneAccentedGreekLetter +
        "*$";
    
    private static final String wordFirstCapital = "^" + oneUpperCaseGreekLetter + ".*$";
    private static final String wordAtLeastOneAccent = "^.*" + oneOrMoreAccentedGreekLetters + ".*$";
    private static final String wordCapitalizedOrNot = "^" + oneUpperOrLowerCaseGreekLetter + zeroOrMoreNonUpperCaseGreekLetters + "$";
    private static final String wordZeroAccent = "^" + notOneAccentedGreekLetter + "*$";
    
    private static final String wordNoGreek = "^[^" + greekUpperChar + greekLowerChar + "]+$";
    
    private static final String wordEprLetters = "^" + zeroOrMoreEprLetters + "$";
    private static final String wordGreekUpperNoSpecialCharLettersExceptSpace = "^" + zeroOrMoreGreekUpperNoSpecialCharLettersExceptSpace + "$";
    
    private static final String wordEprMunicipalRegistryNo = "^[0-9]+\\/[0-9]+$";
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    
    
    public static Boolean wordNoLatin(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordNoLatin);
    }
    
    public static Boolean wordNoNumbers(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordNoNumbers);
    }
    
    public static Boolean wordNoSymbols(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordNoSymbols);
    }
    
    public static Boolean wordNoSymbolsExceptLight(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordNoSymbolsExceptLight);
    }
    
    public static Boolean wordNoSpaces(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordNoSpaces);
    }
    
    public static Boolean wordCapitalized(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordCapitalized);
    }
    
    public static Boolean wordOneAccent(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordOneAccent);
    }
    
    public static Boolean wordFirstCapital(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordFirstCapital);
    }
    
    public static Boolean wordAtLeastOneAccent(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordAtLeastOneAccent);
    }
    
    public static Boolean wordCapitalizedOrNot(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordCapitalizedOrNot);
    }
    
    public static Boolean wordOneAccentMax(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordZeroAccent) || word.matches(wordOneAccent);
    }
    
    public static Boolean wordNoGreek(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordNoGreek);
    }
    
    public static Boolean wordEprLetters(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordEprLetters);
    }
    
    public static Boolean wordGreekUpperNoSpecialCharLettersExceptSpace(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(zeroOrMoreGreekUpperNoSpecialCharLettersExceptSpace);
    }
    
    public static Boolean wordEprMunicipalRegistryNo(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        return word.matches(wordEprMunicipalRegistryNo);
    }
    
    /**
     * Κληση μιας στατικής μεθόδου της κλάσης DpRegexUtils για ταύτιση με regular expression
     * Δίνεται η τιμή και το όνομα της μεθόδου.
     * Eπιστρέφεται το αποτέλεσμα της ταύτισης.
     */
    public static Boolean regexMatches(String input, String methodName) {
        
        try {
            Method method = DpRegexUtils.class.getMethod(methodName, String.class);
            return (Boolean) method.invoke(null, input);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
}
