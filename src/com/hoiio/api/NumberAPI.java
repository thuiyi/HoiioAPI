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
import com.hoiio.util.APIRequest;
import com.hoiio.util.APIUrls;
import com.hoiio.util.APIResponse.ResponseMap;
import com.hoiio.dto.ActiveNumbers;
import com.hoiio.dto.HoiioAuth;
import com.hoiio.exceptions.HoiioRestException;
import com.hoiio.exceptions.HttpPostConnectionException;
import com.hoiio.util.APIResponse;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
Hoiio provides dedicated virtual phone numbers to developers for your IVR applications. This service enables your IVR application to receive incoming calls and allows you to create customer hotlines, call filtering services etc using our IVR API.<br>
<br>
The Number API will allow developers to have full control over how they assign their Hoiio Numbers to their IVR applications.<br>
<br>
<img src="http://developer.hoiio.com/docs/images/hoiio_number.png">
 * @author Max
 */
public class NumberAPI {

    static Logger log = Logger.getLogger(NumberAPI.class);

    /***
     * This API retrieves the list of Hoiio Numbers that is assigned to your application and their current configuration.
     * @see <a href="http://developer.hoiio.com/docs/number_status.html">http://developer.hoiio.com/docs/number_status.html</a>
     * @param auth The user authorization object for the request
     * @return The list of active numbers for a user
     * @throws HttpPostConnectionException if there is a connection failure
     * @throws HoiioRestException if there were problems using this REST API
     */
    public static ActiveNumbers getActive(HoiioAuth auth) throws HttpPostConnectionException, HoiioRestException {
        APIRequest request = new APIRequest(APIUrls.SUBSCRIBED_NUMBERS);

        request.addParam("access_token", auth.getToken());
        request.addParam("app_id", auth.getAppId());

        APIResponse response = HttpUtil.doHttpPost(request);
        log.info(response.getResponseString());
        if (response.isOK()) {

            ActiveNumbers numbers = new ActiveNumbers();
            numbers.setTotalEntries(response.getResponseMap().getInt("entries_count"));

            ArrayList<ActiveNumbers.HoiioNumber> items = new ArrayList<ActiveNumbers.HoiioNumber>();
            ResponseMap[] faxs = response.getResponseMap().getArray("entries");
            for (int i = 0; i < faxs.length; i++) {
                ActiveNumbers.HoiioNumber item = new ActiveNumbers.HoiioNumber();
                item.setNumber(faxs[i].getString("number"));
                item.setForwardTo(faxs[i].getString("forward_to"));
                item.setExpiry(faxs[i].getString("expiry"));
                item.setCountry(faxs[i].getString("country"));
                item.setState(faxs[i].getString("state"));
                items.add(item);
            }
            numbers.setHoiioNumbers(items);
            return numbers;
        } else {
            log.error("Error with request: " + request.getRequestUrl() + "?" + request.getPostString());
            log.error(response.getResponseString());
            throw new HoiioRestException(request, response);
        }
    }

    /***
     * This API allows you to configure the URL that the <a href="http://developer.hoiio.com/docs/ivr_answer.html">incoming call notification</a> for your Hoiio Number will be sent to.
     * @see <a href="http://developer.hoiio.com/docs/number_configure.html">http://developer.hoiio.com/docs/number_configure.html</a>
     * @param auth The user authorization object for the request
     * @param number The Hoiio Number to configure. Phone numbers should start with a "+" and country code (E.164 format), e.g. +6511111111.
     * @param forwardTo A fully-qualified HTTP/S URL on your web server to be notified when there is an incoming voice call/fax. See IVR API - Incoming Calls and FAX API - Receive Fax for details.
     * @param mode Indicate whether to use the number for incoming voice call or fax. By default, numbers are configured for incoming voice. Possible values are: voice, fax.
     * @return true if updating was successful
     * @throws HttpPostConnectionException if there is a connection failure
     * @throws HoiioRestException if there were problems using this REST API
     */
    public static boolean updateForward(HoiioAuth auth, String number, String forwardTo, String mode) throws HttpPostConnectionException, HoiioRestException {
        APIRequest request = new APIRequest(APIUrls.CONFIGURE_FORWARD);

        request.addParam("access_token", auth.getToken());
        request.addParam("app_id", auth.getAppId());
        request.addParam("number", number);
        request.addParam("forward_to", forwardTo);
        request.addParam("mode", mode);

        APIResponse response = HttpUtil.doHttpPost(request);
        log.info(response.getResponseString());
        if (response.isOK()) {
            return true;

        } else {
            log.error("Error with request: " + request.getRequestUrl() + "?" + request.getPostString());
            log.error(response.getResponseString());
            throw new HoiioRestException(request, response);
        }
    }

    /***
     * This API allows you to configure the URL that the <a href="http://developer.hoiio.com/docs/sms_receive.html">incoming SMS notification</a> for your Hoiio Number will be sent to.
     * @see <a href="http://developer.hoiio.com/docs/number_configure.html">http://developer.hoiio.com/docs/number_configure.html</a>
     * @param auth The user authorization object for the request
     * @param number The Hoiio Number to configure. Phone numbers should start with a "+" and country code (E.164 format), e.g. +6511111111.
     * @param forwardToSms A fully-qualified HTTP/S URL on your web server to be notified when there is an incoming SMS. See Receive SMS for details.
     * @return true if updating was successful
     * @throws HttpPostConnectionException if there is a connection failure
     * @throws HoiioRestException if there were problems using this REST API
     */
    public static boolean updateForwardSms(HoiioAuth auth, String number, String forwardToSms) throws HttpPostConnectionException, HoiioRestException {
        APIRequest request = new APIRequest(APIUrls.CONFIGURE_FORWARD);

        request.addParam("access_token", auth.getToken());
        request.addParam("app_id", auth.getAppId());
        request.addParam("number", number);
        request.addParam("forward_to_sms", forwardToSms);

        APIResponse response = HttpUtil.doHttpPost(request);
        log.info(response.getResponseString());
        if (response.isOK()) {
            return true;

        } else {
            log.error("Error with request: " + request.getRequestUrl() + "?" + request.getPostString());
            log.error(response.getResponseString());
            throw new HoiioRestException(request, response);
        }
    }
}
