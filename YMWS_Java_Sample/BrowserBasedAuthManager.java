import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URLEncoder;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.io.IOException;

/**
 * A simple class for generating a WSSID and a "Y" cookie given an application ID, shared
 * secret and a user token. For more details, see the Yahoo! Developer Network pages
 * on Browser Based Authentication: http://developer.yahoo.com/auth/
 */
public class BrowserBasedAuthManager {
    private String appid;
    private String secret;
    private String token;
    private String cookie;
    private String wssid;

    public BrowserBasedAuthManager(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void authenticate() throws AuthException {
        try {
            // Get the current time. Needed to sign the request.
            long time = System.currentTimeMillis() / 1000;

            // Generate the portion of the URL that's used for signing.
            String signatureUrl = "/WSLogin/V1/wspwtoken_login?appid=" +
                    URLEncoder.encode(this.appid, "UTF-8") + "&token=" + URLEncoder.encode(this.token, "UTF-8") +
                    "&ts=" + time;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            String signature = new BigInteger(1, digest.digest((signatureUrl + this.secret).getBytes())).toString(16);

            // Build the URL from the hostname, the URL and the signature.
            String requestUrl = "https://api.login.yahoo.com" + signatureUrl + "&sig=" + URLEncoder.encode(signature, "UTF-8");

            // Parse the response XML, it will contain the cookie and the WSSID so long as there
            // is no authentication error.
            Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(requestUrl).openStream());
            if (response.getElementsByTagName("Error").getLength() == 0) {
                this.cookie = response.getElementsByTagName("Cookie").item(0).getTextContent();
                this.wssid = response.getElementsByTagName("WSSID").item(0).getTextContent();
            }
            else {
                // Oops...authentication error. Throw an exception.
                throw new AuthException(response.getElementsByTagName("ErrorDescription").item(0).getTextContent());
            }
        }
        catch (NoSuchAlgorithmException e) {
            // Unlikely to happen unless you don't have MD5 capabilities in your version of Java.
            throw new RuntimeException(e);
        }
        catch (SAXException e) {
            throw new AuthException("Error parsing XML", e);
        }
        catch (IOException e) {
            throw new AuthException("Communication failure", e);
        }
        catch (ParserConfigurationException e) {
            // Unlikely to happen unless your DOM parsers aren't properly configured.
            throw new RuntimeException(e);
        }
    }

    public String getCookie() {
        return cookie;
    }

    public String getWssid() {
        return wssid;
    }

    public class AuthException extends Exception {
        public AuthException(String message) {
            super(message);
        }

        public AuthException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
