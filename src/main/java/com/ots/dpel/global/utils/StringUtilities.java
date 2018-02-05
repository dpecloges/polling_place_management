package com.ots.dpel.global.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.OctalUnescaper;
import org.apache.commons.lang3.text.translate.UnicodeUnescaper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 */
@SuppressWarnings("rawtypes")
public class StringUtilities {
    
    private static final Locale LOCALE_EL_GR = new Locale("el", "GR");
    private static final DecimalFormatSymbols GR_DECIMAL_FORMAT_SYMBOLS = DecimalFormatSymbols
        .getInstance(LOCALE_EL_GR);
    private static final DecimalFormat GR_DECIMAL_FORMAT = new DecimalFormat(
        "#,##0.00", GR_DECIMAL_FORMAT_SYMBOLS);
    
    public static Object clearNull(Object value) {
        if (value != null && value instanceof String
            && ((String) value).equalsIgnoreCase("null")) {
            value = null;
        }
        return value;
    }
    
    public static String toUpperEliminateAccents(final String str) {
        String data = str;
        if (data != null) {
            data = Normalizer.normalize(data, Normalizer.Form.NFD);
            Pattern pattern = Pattern
                .compile("\\p{InCombiningDiacriticalMarks}+");
            data = pattern.matcher(data).replaceAll("");
            return data.toUpperCase(new Locale("el"));
        }
        return data;
    }
    
    /**
     * Parses the specified string and returns true if its value is "truthy",
     * i.e., if it is "true" or "1", false if it has any other value, and
     * <code>defaultValue</code> if the specified string is null.
     * @param boolStr
     * @param defaultValue
     * @return
     */
    public static boolean readBoolean(String boolStr, boolean defaultValue) {
        return (StringUtils.isNoneBlank(boolStr) ? (boolStr
            .equalsIgnoreCase("true") || boolStr.equals("1"))
            : defaultValue);
    }
    
    /**
     * If the specified string (<code>str</code>) is not trivial, then it is
     * returned, otherwise the <code>defaultValue</code> is returned.
     * @param str
     * @param defaultValue
     * @return
     */
    public static String readString(String str, String defaultValue) {
        return (StringUtils.isNoneBlank(str) ? str : defaultValue);
    }
    
    /**
     * Tries to parse the the specified date string. If (<code>str</code>) is
     * not trivial and is parsed properly, then it is returned, otherwise the
     * <code>defaultValue</code> is returned.
     * @param str
     * @param defaultValue
     * @param a            valid {@link SimpleDateFormat} format string.
     * @return
     */
    public static Date readDate(String str, Date defaultValue, String dateFormat) {
        return readDate(str, defaultValue, new SimpleDateFormat(dateFormat));
    }
    
    /**
     * Tries to parse the the specified date string. If (<code>str</code>) is
     * not trivial and is parsed properly, then it is returned, otherwise the
     * <code>defaultValue</code> is returned.
     * @param str
     * @param defaultValue
     * @param a            valid {@link SimpleDateFormat} format string.
     * @return
     */
    public static Date readDate(String str, Date defaultValue,
                                SimpleDateFormat dateFormat) {
        if (StringUtils.isBlank(str)) {
            return defaultValue;
        }
        
        Date retDate = defaultValue;
        
        try {
            retDate = dateFormat.parse(str);
        } catch (ParseException e) {
        
        }
        
        return retDate;
    }
    
    /**
     * If the specified string (<code>str</code>) is not trivial, then an
     * attempt is made to parse for an integer value, otherwise the default
     * integer value is returned.
     * @param intStr
     * @param defaultValue
     * @return
     */
    public static int readInt(String intStr, int defaultValue) {
        try {
            return (StringUtils.isNoneBlank(intStr) ? Integer.parseInt(intStr)
                : defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    /**
     * If the specified string (<code>str</code>) is not trivial, then an
     * attempt is made to parse for an integer value, otherwise the default
     * integer value is returned.
     * @param longStr
     * @param defaultValue
     * @return
     */
    public static Long readLong(String longStr, Long defaultValue) {
        try {
            return (StringUtils.isNoneBlank(longStr) ? Long.parseLong(longStr)
                : defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static boolean isNumber(String str) {
        return NumberUtils.isNumber(str);
    }
    
    public static boolean isDigits(String str) {
        return NumberUtils.isDigits(str);
    }
    
    /**
     * This method ensures that the output String has only valid XML unicode
     * characters as specified by the XML 1.0 standard. For reference, please
     * see <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
     * standard</a>. This method will return an empty String if the input is
     * null or empty.
     *
     * <p>
     * This method was taken from: <a href=
     * "http://benjchristensen.com/2008/02/07/how-to-strip-invalid-xml-characters/"
     * >Ben J. Christensen blog</a> and is used by OpenData API in old Diavgeia
     * system.
     * </p>
     * @param str The String whose non-valid characters we want to remove.
     * @return The in String, stripped of non-valid characters.
     */
    public static String stripNonValidXMLCharacters(String str) {
        if (str == null) {
            return null;
        }
        
        StringBuilder out = new StringBuilder(); // Used to hold the output.
        int codePoint; // Used to reference the current character.
        int i = 0;
        while (i < str.length()) {
            codePoint = str.codePointAt(i); // This is the unicode code of the
            // character.
            if ((codePoint == 0x9)
                || // Consider testing larger ranges first to improve speed.
                (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF))) {
                out.append(Character.toChars(codePoint));
                
            }
            i += Character.charCount(codePoint); // Increment with the number of
            // code units(java chars)
            // needed to represent a
            // Unicode char.
        }
        return out.toString();
    }
    
    /**
     * Concatenates the String representations of the specified objects using
     * the {@code delimiter}.
     * @param delimiter
     * @param objects
     * @return Non-null String if delimiter and objects are not null, null if
     * the delimiter is not null but no input objects are provided.
     * @throws NullPointerException if any of the arguments is null
     */
    public static String join(String delimiter, Object... objects) {
        if (delimiter == null) {
            throw new NullPointerException();
        }
        String joined = null;
        if (objects != null && objects.length > 0) {
            StringBuilder str = new StringBuilder(objects[0].toString());
            for (int i = 1; i < objects.length; i++) {
                str.append(delimiter).append(objects[i].toString());
            }
            joined = str.toString();
        }
        return joined;
    }
    
    /**
     * Concatenates the String representations of the specified objects using
     * the {@code delimiter}.
     * @param delimiter
     * @param objects
     * @return Non-null String if delimiter and objects are not null, null if
     * the delimiter is not null but the collection of input objects is
     * empty.
     * @throws NullPointerException if any of the arguments is null
     */
    public static String join(String delimiter, Collection objects) {
        if (delimiter == null) {
            throw new NullPointerException();
        }
        String joined = null;
        if (objects != null && objects.size() > 0) {
            Iterator iter = objects.iterator();
            StringBuilder str = new StringBuilder(iter.next().toString());
            while (iter.hasNext()) {
                str.append(delimiter).append(iter.next().toString());
            }
            joined = str.toString();
        }
        return joined;
    }
    
    /**
     * Concatenates the String representations of the specified objects using
     * the {@code delimiter}, after enclosing each individual String between
     * {@code prefix} and {@code suffix}.
     * @param delimiter
     * @param prefix
     * @param suffix
     * @param objects
     * @return Non-null String none of the arguments is null, null if the
     * delimiter, prefix and suffix are not null but the collection of
     * input objects is empty.
     * @throws NullPointerException if any of the arguments is null
     */
    public static String joinWrapped(String delimiter, String prefix,
                                     String suffix, Object... objects) {
        if (delimiter == null || prefix == null || suffix == null) {
            throw new NullPointerException();
        }
        String joined = null;
        if (objects != null && objects.length > 0) {
            StringBuilder str = new StringBuilder(prefix).append(
                objects[0].toString()).append(suffix);
            for (int i = 1; i < objects.length; i++) {
                str.append(delimiter).append(prefix)
                    .append(objects[i].toString()).append(suffix);
            }
            joined = str.toString();
        }
        return joined;
    }
    
    /**
     * Concatenates the String representations of the specified objects using
     * the {@code delimiter}, after enclosing each individual String between
     * {@code prefix} and {@code suffix}.
     * @param delimiter
     * @param prefix
     * @param suffix
     * @param objects
     * @return Non-null String none of the arguments is null, null if the
     * delimiter, prefix and suffix are not null but the collection of
     * input objects is empty.
     * @throws NullPointerException if any of the arguments is null
     */
    public static String joinWrapped(String delimiter, String prefix,
                                     String suffix, Collection objects) {
        if (delimiter == null || prefix == null || suffix == null) {
            throw new NullPointerException();
        }
        String joined = null;
        if (objects != null && objects.size() > 0) {
            Iterator iter = objects.iterator();
            StringBuilder str = new StringBuilder(prefix).append(
                iter.next().toString()).append(suffix);
            while (iter.hasNext()) {
                str.append(delimiter).append(prefix)
                    .append(iter.next().toString()).append(suffix);
            }
            joined = str.toString();
        }
        return joined;
    }
    
    /**
     * Concatenates the String representations of the specified objects using
     * the {@code delimiter}, and encloses the concatenated String between
     * {@code prefix} and {@code suffix}.
     * @param delimiter
     * @param prefix
     * @param suffix
     * @param objects
     * @return Non-null String none of the arguments is null, null if the
     * delimiter, prefix and suffix are not null but the collection of
     * input objects is empty.
     * @throws NullPointerException if any of the arguments is null
     */
    public static String joinAndWrap(String delimiter, String prefix,
                                     String suffix, Object... objects) {
        if (delimiter == null || prefix == null || suffix == null) {
            throw new NullPointerException();
        }
        String joined = null;
        if (objects != null && objects.length > 0) {
            StringBuilder str = new StringBuilder(prefix).append(objects[0]
                .toString());
            for (int i = 1; i < objects.length; i++) {
                str.append(delimiter).append(objects[i].toString());
            }
            joined = str.append(suffix).toString();
        }
        return joined;
    }
    
    /**
     * Concatenates the String representations of the specified objects using
     * the {@code delimiter}, and encloses the concatenated String between
     * {@code prefix} and {@code suffix}.
     * @param delimiter
     * @param prefix
     * @param suffix
     * @param objects
     * @return Non-null String none of the arguments is null, null if the
     * delimiter, prefix and suffix are not null but the collection of
     * input objects is empty.
     * @throws NullPointerException if any of the arguments is null
     */
    public static String joinAndWrap(String delimiter, String prefix,
                                     String suffix, Collection objects) {
        if (delimiter == null || prefix == null || suffix == null) {
            throw new NullPointerException();
        }
        String joined = null;
        if (objects != null && objects.size() > 0) {
            Iterator iter = objects.iterator();
            StringBuilder str = new StringBuilder(prefix).append(iter.next()
                .toString());
            while (iter.hasNext()) {
                str.append(delimiter).append(iter.next().toString());
            }
            joined = str.append(suffix).toString();
        }
        return joined;
    }
    
    /**
     * Replaces characters that may be confused by a HTML parser with their
     * equivalent character entity references.
     *
     * Any data that will appear as text on a web page should be be escaped.
     * This is especially important for data that comes from untrusted sources
     * such as Internet users. A common mistake in CGI programming is to ask a
     * user for data and then put that data on a web page. For example:
     *
     * <pre>
     * Server: What is your name?
     * User: &lt;b&gt;Joe&lt;b&gt;
     * Server: Hello <b>Joe</b>, Welcome
     * </pre>
     *
     * If the name is put on the page without checking that it doesn't contain
     * HTML code or without sanitizing that HTML code, the user could reformat
     * the page, insert scripts, and control the the content on your web server.
     *
     * This method will replace HTML characters such as &gt; with their HTML
     * entity reference (&amp;gt;) so that the html parser will be sure to
     * interpret them as plain text rather than HTML or script.
     *
     * This method should be used for both data to be displayed in text in the
     * html document, and data put in form elements. For example:<br>
     * <code>&lt;html&gt;&lt;body&gt;<i>This in not a &amp;lt;tag&amp;gt;
     * in HTML</i>&lt;/body&gt;&lt;/html&gt;</code><br>
     * and<br>
     * <code>&lt;form&gt;&lt;input type="hidden" name="date" value="<i>This data could
     * be &amp;quot;malicious&amp;quot;</i>"&gt;&lt;/form&gt;</code><br>
     * In the second example, the form data would be properly be resubmitted to
     * your cgi script in the URLEncoded format:<br>
     * <code><i>This data could be %22malicious%22</i></code>
     * @param s String to be escaped
     * @return escaped String
     * @throws NullPointerException if s is null.
     * @since ostermillerutils 1.00.00
     */
    public static String escapeHTML(String s) {
        int length = s.length();
        int newLength = length;
        boolean someCharacterEscaped = false;
        // first check for characters that might
        // be dangerous and calculate a length
        // of the string that has escapes.
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            int cint = 0xffff & c;
            if (cint < 32) {
                switch (c) {
                    case '\r':
                    case '\n':
                    case '\t':
                    case '\f': {
                    }
                    break;
                    default: {
                        newLength -= 1;
                        someCharacterEscaped = true;
                    }
                }
            } else {
                switch (c) {
                    case '\"': {
                        newLength += 5;
                        someCharacterEscaped = true;
                    }
                    break;
                    case '&':
                    case '\'': {
                        newLength += 4;
                        someCharacterEscaped = true;
                    }
                    break;
                    case '<':
                    case '>': {
                        newLength += 3;
                        someCharacterEscaped = true;
                    }
                    break;
                }
            }
        }
        if (!someCharacterEscaped) {
            // nothing to escape in the string
            return s;
        }
        StringBuffer sb = new StringBuffer(newLength);
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            int cint = 0xffff & c;
            if (cint < 32) {
                switch (c) {
                    case '\r':
                    case '\n':
                    case '\t':
                    case '\f': {
                        sb.append(c);
                    }
                    break;
                    default: {
                        // Remove this character
                    }
                }
            } else {
                switch (c) {
                    case '\"': {
                        sb.append("&quot;");
                    }
                    break;
                    case '\'': {
                        sb.append("&#39;");
                    }
                    break;
                    case '&': {
                        sb.append("&amp;");
                    }
                    break;
                    case '<': {
                        sb.append("&lt;");
                    }
                    break;
                    case '>': {
                        sb.append("&gt;");
                    }
                    break;
                    default: {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }
    
    public static String unescapeQuotes(String msg) {
        return msg.replace("\\'", "'").replace("\\\"", "\"");
    }
    
    private static CharSequenceTranslator INVALID_XML_CHARS_TRANSLATOR = new AggregateTranslator(
        new OctalUnescaper(), new UnicodeUnescaper());
    
    /**
     * @param jsonString
     * @return
     */
    public static String unescapeInvalidXmlCharacters(String jsonString) {
        return INVALID_XML_CHARS_TRANSLATOR.translate(jsonString);
    }
    
    public static boolean areNotSame(String oldValue, String newValue) {
        return (StringUtils.isNotBlank(oldValue)
            && StringUtils.isNotBlank(newValue) && !oldValue
            .equals(newValue));
    }
    
    public static String formatValue(Object val) {
        if (val == null) {
            return "";
        } else if (val instanceof Double) {
            return GR_DECIMAL_FORMAT.format((Double) val);
        } else if (val instanceof Long) {
            return Long.toString((Long) val);
        } else if (val instanceof Integer) {
            return Integer.toString((Integer) val);
        } else {
            return val.toString();
        }
    }
    
    /**
     * Decodes a Url encoded string, taking into account special cases like
     * space character and '+' plus sign.
     * <p>
     * For example if we provide the string
     *
     * <code>
     * &quot;%CE%8E%CE%B4%CF%89%CF%81%2B%CE%A7%CE%BF%CF%8D%CF%82%20%3D%20h%C3%A4sslich!&quot;
     * </code>
     *
     * it will be converted to <code>"Ύδωρ+Χούς = hässlich!"</code>
     * @param value A string value
     */
    public static String urlDecode(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        
        try {
            return URLDecoder
                .decode(value.replaceAll(Pattern.quote("+"), "%2B"),
                    "UTF-8").trim()
                .replaceAll("%2B", Pattern.quote("+"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        
    }
    
    /**
     * Removes all spaces, tabs or dashes ("-") from a telephone number string
     * and returns a new string. The given string remains untouched.
     *
     * <p>
     * Examples:
     * <ol>
     * <li>"2310 233-222" will be converted to "2310 233-222"</li>
     * <li>"+302310 233-222" will be converted to "+302310233222"</li>
     * <li>"2310-233-222" will be converted to "2310233222"</li>
     *
     * </ol>
     * </p>
     * @param tel initial telephone string
     * @return a telephone number string without any spaces, tabs or dashes.
     */
    public static String sanitiseTelephoneNumber(String tel) {
        if (StringUtils.isBlank(tel)) {
            return null;
        }
        
        return tel.replaceAll("[ \t-]", "");
    }
}
