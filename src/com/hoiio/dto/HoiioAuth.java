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
package com.hoiio.dto;

/**
 * The Authorization object for HoiioAPI services. This class stores the user's App Id and Access Token pair.
 * @author Max
 */
public class HoiioAuth {
    private String appId;
    private String token;

    /***
     * Constructor for the Authorization object
     * @param appId Your Application Id generated in the developer portal
     * @param token Your Access Token as generated in the developer portal
     */
    public HoiioAuth(String appId, String token) {
        this.appId = appId;
        this.token = token;
    }

    /***
     * Gets the App Id
     * @return The App Id
     */
    public String getAppId() {
        return appId;
    }

    /***
     * Sets the App Id
     * @param appId The App Id
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /***
     * Gets the Access Token
     * @return The Access Token
     */
    public String getToken() {
        return token;
    }

    /***
     * Sets the Access Token
     * @param token The Access Token
     */
    public void setToken(String token) {
        this.token = token;
    }

    
}
