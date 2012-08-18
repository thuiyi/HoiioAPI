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
import com.hoiio.exceptions.HoiioRestException;
import com.hoiio.exceptions.HttpPostConnectionException;
import org.apache.log4j.Logger;

/***
SMS is one of the most popular forms of mobile communications. Chatting between friends, 2-factor authentication by your bank's online portal, receiving alerts from your stock market applications and SMS marketing are just some of the more popular uses of SMS.<br>
<br>
The SMS API component of Hoiio API allows developers to send/receive SMS to/from more than 200 countries around the world at a very competitive rate.<br>
 * @author Max
 */
public class SMSAPI {

    static Logger log = Logger.getLogger(SMSAPI.class);

    /***
     * Sends a sms based on the parameters specified
     * @param auth The user authorization object for the request
     * @param sender This is the sender name that the recipient of your SMS will see. For more details, refer to Sender Rebranding.
     * @param dest The recipient number of the SMS. Phone numbers should start with a "+" and country code (E.164 format), e.g. +6511111111.
     * @param msg Contents of the SMS message limited to 459 characters
     * @return A unique reference ID for this SMS transaction. This parameter will not be present if the request was not successful.
     * @throws HttpPostConnectionException if there is a connection failure
     * @throws HoiioRestException if there were problems using this REST API
     */
    public static String sendSMS(HoiioAuth auth, String sender, String dest, String msg) throws HttpPostConnectionException, HoiioRestException
    {
        return sendSMS(auth, sender, dest, msg, null, null);
    }

    /***
     * Sends a sms based on the parameters specified
     * @param auth The user authorization object for the request
     * @param sender This is the sender name that the recipient of your SMS will see. For more details, refer to Sender Rebranding.
     * @param dest The recipient number of the SMS. Phone numbers should start with a "+" and country code (E.164 format), e.g. +6511111111.
     * @param msg Contents of the SMS message limited to 459 characters
     * @param tag This is a text string containing your own reference ID for this transaction. This value will be included in the response for Notification, sms/query_status and sms/get_history for your reference. Max 256 characters.
     * @return A unique reference ID for this SMS transaction. This parameter will not be present if the request was not successful.
     * @throws HttpPostConnectionException if there is a connection failure
     * @throws HoiioRestException if there were problems using this REST API
     */
    public static String sendSMS(HoiioAuth auth, String sender, String dest, String msg, String tag) throws HttpPostConnectionException, HoiioRestException
    {
        return sendSMS(auth, sender, dest, msg, tag, null);
    }

    /***
     * Sends a sms based on the parameters specified
     * @param auth The user authorization object for the request
     * @param sender This is the sender name that the recipient of your SMS will see. For more details, refer to Sender Rebranding.
     * @param dest The recipient number of the SMS. Phone numbers should start with a "+" and country code (E.164 format), e.g. +6511111111.
     * @param msg Contents of the SMS message limited to 459 characters
     * @param tag This is a text string containing your own reference ID for this transaction. This value will be included in the response for Notification, sms/query_status and sms/get_history for your reference. Max 256 characters.
     * @param notifyUrl A fully-qualified HTTP/S callback URL on your web server to be notified when the SMS has been delivered. Refer to Notification parameters for details.
     * @return A unique reference ID for this SMS transaction. This parameter will not be present if the request was not successful.
     * @throws HttpPostConnectionException if there is a connection failure
     * @throws HoiioRestException if there were problems using this REST API
     */
    public static String sendSMS(HoiioAuth auth, String sender, String dest, String msg, String tag, String notifyUrl) throws HttpPostConnectionException, HoiioRestException {
        APIRequest request = new APIRequest(APIUrls.SEND_SMS);

        request.addParam("app_id", auth.getAppId());
        request.addParam("access_token", auth.getToken());
        request.addParam("dest", dest);
        request.addParam("sender_name", sender);
        request.addParam("msg", msg);
        if(tag != null)
            request.addParam("tag", tag);
        if (notifyUrl != null) 
            request.addParam("notify_url", notifyUrl);

        APIResponse response = HttpUtil.doHttpPost(request);

        if (response.isOK()) {
            return response.getResponseMap().getString("txn_ref");

        } else {
            log.error("Error with request: " + request.getRequestUrl() + "?" + request.getPostString());
            log.error(response.getResponseString());
            throw new HoiioRestException(request, response);
        }
    }
}
