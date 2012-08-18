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
import com.hoiio.dto.FaxHistory;
import com.hoiio.dto.HoiioAuth;
import com.hoiio.exceptions.*;
import com.hoiio.util.APIResponse;
import com.hoiio.util.APIStatus;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/***
Fax API provides developers access to faxing capabilities by using our simple API. Developers can use the Fax API to send, and also to receive fax via Hoiio virtual numbers.<br>
<br>
Charges for fax is similar to our Voice API - by per minute usage. This should not come as a surprise since fax is delivered over phone lines.<br>
<br>
Note that Fax is only supported in the following countries:<br>
<ul>
  <li>Singapore</li>
</ul>
 * @author Max Tan
 */
public class FaxAPI {

    static Logger log = Logger.getLogger(FaxAPI.class);

    /**
     * Possible types of history to fetch
     */
    public enum FaxType {

        /**
         * Incoming Faxes
         */
        INCOMING,
        /**
         * Outgoing Faxes
         */
        OUTGOING,
        /**
         * Both incoming and outgoing faxes
         */
        ALL
    };

    /***
     * This API will retrieve the history of faxes sent by this application.
     * @see <a href="http://developer.hoiio.com/docs/fax_history.html">http://developer.hoiio.com/docs/fax_history.html</a>
     * @param auth The user authorization object for the request
     * @param type The type of history to retrieve
     * @param page The page of the history to retrieve, starting from page 1
     * @return The fax history specified by the parameters, starting at the first entry specified in @page with at most 100 entries
     * @throws HttpPostConnectionException if there is a connection failure
     * @throws HoiioRestException if there were problems using this REST API
     */
    public static FaxHistory getHistory(HoiioAuth auth, FaxType type, int page) throws HttpPostConnectionException, HoiioRestException {

        APIRequest request = new APIRequest(APIUrls.GET_HISTORY);
        request.addParam("access_token", auth.getToken());
        request.addParam("app_id", auth.getAppId());
        if (type == FaxType.INCOMING) {
            request.addParam("type", "incoming");
        } else if (type == FaxType.OUTGOING) {
            request.addParam("type", "outgoing");
        } else if (type == FaxType.ALL) {
            request.addParam("type", "all");
        }
        request.addParam("page", page + "");

        APIResponse response = HttpUtil.doHttpPost(request);
        log.info(response.getResponseString());

        if (response.isOK()) {

            FaxHistory hist = new FaxHistory();
            hist.setNumEntries(response.getResponseMap().getInt("entries_count"));
            hist.setTotalEntries(response.getResponseMap().getInt("total_entries_count"));

            ArrayList<FaxHistory.FaxItem> items = new ArrayList<FaxHistory.FaxItem>();
            ResponseMap[] faxs = response.getResponseMap().getArray("entries");
            for (int i = 0; i < faxs.length; i++) {
                FaxHistory.FaxItem item = new FaxHistory.FaxItem();
                item.setCurrency(faxs[i].getString("currency"));
                item.setTxnRef(faxs[i].getString("txn_ref"));
                item.setTag(faxs[i].getString("tag"));
                item.setDate(parseDate(faxs[i].getString("date")));
                item.setSrc(faxs[i].getString("src"));
                item.setDest(faxs[i].getString("dest"));
                item.setStatus(faxs[i].getString("fax_status"));
                item.setPages(faxs[i].getString("fax_pages"));
                item.setRate(faxs[i].getString("rate"));
                item.setDebit(faxs[i].getString("debit"));
                item.setUrl(faxs[i].getString("fax_url"));
                items.add(item);
            }
            hist.setFaxItems(items);
            return hist;
        } else {
            log.error("Error with request: " + request.getRequestUrl() + "?" + request.getPostString());
            log.error(response.getResponseString());
            throw new HoiioRestException(request, response);
        }
    }

    private static Date parseDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S z");
        try {
            Date ans = formatter.parse(date.trim() + " SGT");
            return ans;
        } catch (ParseException ex) {

            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            try {
                Date ans = formatter.parse(date.trim() + " SGT");
                return ans;
            } catch (ParseException ex2) {
                log.error("Bug in Hoiio API - invalid date format: " + ex2.fillInStackTrace());
                return null;
            }
        }
    }

    /***
     * Sends a fax specified by the method parameters
     * @see <a href="http://developer.hoiio.com/docs/fax_send.html">http://developer.hoiio.com/docs/fax_send.html</a>
     * @param auth The user authorization object for the request
     * @param sender The sender number or caller id to be displayed
     * @param dest The destination to send the fax to. Phone numbers should start with a "+" and country code (E.164 format), e.g. +6511111111.
     * @param filepath The path of the fax to be sent
     * @param filename The file name of the fax desired
     * @return A unique reference ID for this fax transaction. This parameter will not be present if the request was not successful.
     * @throws HttpPostConnectionException if there is a connection failure
     * @throws HoiioRestException if there were problems using this REST API
     */
    public static String sendFax(HoiioAuth auth, String sender, String dest, String filepath, String filename) throws HttpPostConnectionException, HoiioRestException {

        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost method = new HttpPost(APIUrls.SEND_FAX);

            MultipartEntity entity = new MultipartEntity();

            entity.addPart("app_id", new StringBody(auth.getAppId(), Charset.forName("UTF-8")));
            entity.addPart("access_token", new StringBody(auth.getToken(), Charset.forName("UTF-8")));
            entity.addPart("dest", new StringBody(dest, Charset.forName("UTF-8")));
            entity.addPart("tag", new StringBody(filename, Charset.forName("UTF-8")));
            entity.addPart("filename", new StringBody(filename, Charset.forName("UTF-8")));
            if (sender != null && sender.length() > 0) {
                entity.addPart("caller_id", new StringBody(sender, Charset.forName("UTF-8")));
            }
            FileBody fileBody = new FileBody(new File(filepath));
            entity.addPart("file", fileBody);
            method.setEntity(entity);

            log.info("executing request " + method.getRequestLine());
            HttpResponse httpResponse = httpclient.execute(method);
            String responseBody = IOUtils.toString(httpResponse.getEntity().getContent(), "UTF-8");
            final APIResponse response = new APIResponse(responseBody);

            if (response.getStatus() == APIStatus.success_ok) {
                return response.getResponseMap().getString("txn_ref");
            } else {
                log.error("Error with request: " + method.getRequestLine());
                log.error(response.getResponseString());
                throw new HoiioRestException(new APIRequest(APIUrls.SEND_FAX, method.getRequestLine().toString()), response);
            }
        } catch (IOException ex) {
            throw new HttpPostConnectionException(ex);
        }

    }
}
