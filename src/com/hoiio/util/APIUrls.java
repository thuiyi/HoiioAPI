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

/***
 * This class stores all the URLs for Hoiio API
 * @author Max
 */
public class APIUrls {
        public static String DOMAIN = "https://secure.hoiio.com/";
	public static String BASE_URL = DOMAIN + "public/";

        // Connect Urls
        public static String CONNECT_TOKEN = DOMAIN + "open/connect/get_token";
       

        // Profile Urls
        public static String GET_PROFILE = DOMAIN + "open/user/get_info";

        // Fax Urls
        public static String GET_HISTORY = DOMAIN + "open/fax/get_history";
        public static String SEND_FAX = DOMAIN + "open/fax/send";

        // Number Urls
        public static String SUBSCRIBED_NUMBERS = DOMAIN + "open/number/get_active";
        public static String CONFIGURE_FORWARD = DOMAIN + "open/number/update_forwarding";


        // SMS
        public static String SEND_SMS = DOMAIN + "open/sms/send";

}
