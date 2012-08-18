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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/***
 * This class encapsulates a Hoiio API request
 * @author Max
 */
public class APIRequest {

    String requestUrl;
    String postStr;
    Map<String, String> params;

    /***
     * Creates an API request which contains information for the RPC URL and parameters.
     * @param requestUrl
     */
    public APIRequest(String requestUrl) {
        super();
        params = new HashMap<String, String>();
        this.requestUrl = requestUrl;
    }

    public APIRequest(String requestUrl, String postStr) {
        super();
        params = new HashMap<String, String>();
        this.requestUrl = requestUrl;
        this.postStr = postStr;
    }

    /***
     * This method allows a user to add request parameters and their corresponding value.
     * If a param already exists, then it will be overridden by the new value specified in the method call.
     * @param param The parameter name.
     * @param value The value of the parameter.
     */
    public void addParam(String param, String value) {
        params.put(param, value);
    }

    /***
     * This method allows a user to retrieve an added parameter's value
     * @param param The parameter name
     */
    public String getParam(String param) {
        return params.get(param);
    }

    /***
     * Gets the http post string of the api request.
     * @return The http post string.
     */
    public String getPostString() {
        if (postStr != null) {
            return postStr;
        } else if (params != null) {
            StringBuilder content = new StringBuilder();

            for (Entry<String, String> item : params.entrySet()) {
                if (content.length() > 0) {
                    content.append("&");
                }

                try {
                    content.append(item.getKey()
                            + "="
                            + URLEncoder.encode(
                            String.valueOf(item.getValue()), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return content.toString();
        } else {
            return "";
        }
    }

    /***
     * Gets the request URL of the request
     * @return The request URL.
     */
    public String getRequestUrl() {
        return requestUrl;
    }
}
