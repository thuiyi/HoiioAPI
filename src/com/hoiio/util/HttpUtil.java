/*
Copyright (C) 2011 Hoiio Pte Ltd (http://www.hoiio.com)

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.
*/
package com.hoiio.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import com.hoiio.exceptions.HttpPostConnectionException;

import org.apache.log4j.Logger;

/***
 * This is a utility class which performs HTTP POST for Hoiio API
 * @author Max
 */
public class HttpUtil {
    private static final String APP_ID_WEB = "web";
    private static final String APP_VERSION = "1.0.0";
    private static Logger log = Logger.getLogger(HttpUtil.class);

    public static APIResponse doHttpPost(APIRequest request) throws HttpPostConnectionException {

        String str = doHttpPostString(request);
        log.info(request.getRequestUrl() + "?" + request.getPostString());
        return new APIResponse(str);
    }

    public static String doHttpPostString(APIRequest request) throws HttpPostConnectionException {

        if (request.getParam("appId") == null) {
            request.addParam("appId", APP_ID_WEB);
        }
        if (request.getParam("appVersion") == null) {
            request.addParam("appVersion", APP_VERSION);
        }
        if (request.getParam("ipAddress") == null) {
            request.addParam("ipAddress", getIp());
        }
        String urlString = request.getRequestUrl();
        String content = request.getPostString();

        String output = "";
        HttpURLConnection urlConn = null;
        if (!urlString.toLowerCase().startsWith("http://") && !urlString.toLowerCase().startsWith("https://")) {
            urlString = "http://" + urlString;
        }
        try {
            URL url = new URL(urlString);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            output = doHttpURLConnectionPost(urlConn, content);
            log.debug("Response " + output);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            throw new HttpPostConnectionException();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return output;
    }

    private static String doHttpURLConnectionPost(HttpURLConnection con, String content) throws HttpPostConnectionException {
        String output = "";

        BufferedReader br = null;
        InputStream is = null;
        OutputStream os = null;

        try {
            os = con.getOutputStream();
            os.write(content.getBytes("UTF-8"));
            os.flush();

            boolean error = false;
            if (con.getResponseCode() >= 400) {
                error = true;
                log.error("Response: " + con.getResponseCode() + " - "
                        + con.getResponseMessage());
                is = con.getErrorStream();
            } else {
                is = con.getInputStream();
            }

            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String str;
            while (null != ((str = br.readLine()))) {
                sb.append(str);
            }
            output = sb.toString();

            if (!error) {
                // log.debug("Output: {}", output);
            } else {
                log.debug("Error: " + output);
            }
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
            throw new HttpPostConnectionException();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            throw new HttpPostConnectionException();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }

    private static String getIp() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }
    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }

    public void checkClientTrusted(
            java.security.cert.X509Certificate[] certs, String authType) {
    }

    public void checkServerTrusted(
            java.security.cert.X509Certificate[] certs, String authType) {
    }
}};
}
