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
package com.hoiio.api;

import com.hoiio.util.HttpUtil;
import com.hoiio.util.APIResponse;
import com.hoiio.util.APIRequest;
import com.hoiio.util.APIUrls;
import com.hoiio.dto.HoiioAuth;
import com.hoiio.exceptions.HttpPostConnectionException;
import com.hoiio.dto.UserDto;
import com.hoiio.exceptions.HoiioRestException;
import org.apache.log4j.Logger;

/***
The Profile API provides developers access to obtaining user information by using our simple API.
 * 
 * @author Max Tan
 */
public class ProfileAPI {

    static Logger log = Logger.getLogger(ProfileAPI.class);

    /***
     * Gets the user profile for specified auth information
     * @param auth The user authorization object for the request
     * @return The user profile to obtain
     * @throws HttpPostConnectionException if there is a connection failure
     * @throws HoiioRestException if there were problems using this REST API
     */
    public static UserDto getProfile(HoiioAuth auth) throws HttpPostConnectionException, HoiioRestException {
        APIRequest request = new APIRequest(APIUrls.GET_PROFILE);

        request.addParam("app_id", auth.getAppId());
        request.addParam("access_token", auth.getToken());

        APIResponse response = HttpUtil.doHttpPost(request);

        if (response.isOK()) {
            UserDto ans = new UserDto();
            ans.setCountry(response.getResponseMap().getString("country"));
            ans.setCurrency(response.getResponseMap().getString("currency"));
            ans.setEmail(response.getResponseMap().getString("email"));
            ans.setMobileNumber(response.getResponseMap().getString("mobile_number"));
            ans.setName(response.getResponseMap().getString("name"));
            ans.setPrefix(response.getResponseMap().getString("prefix"));

            return ans;
        } else {
            log.error("Error with request: " + request.getRequestUrl() + "?" + request.getPostString());
            log.error(response.getResponseString());
            throw new HoiioRestException(request, response);
        }
    }
}
