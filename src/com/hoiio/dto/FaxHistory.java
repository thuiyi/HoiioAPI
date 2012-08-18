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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class encapsulates the History information for Fax
 * @author Max
 */
public class FaxHistory implements Serializable {
    private int totalEntries;
    private int numEntries;
    ArrayList<FaxItem> faxItems = new ArrayList<FaxItem>();

    /***
     * Gets an array list of Fax history items
     * @return An array list of Fax history items
     */
    public ArrayList<FaxItem> getFaxItems() {
        return faxItems;
    }

    /***
     * Sets the Fax history items
     * @param faxItems An array list of Fax history items
     */
    public void setFaxItems(ArrayList<FaxItem> faxItems) {
        this.faxItems = faxItems;
    }

    /***
     * Gets the number of fax history entries
     * @return The number of fax history entries returned in this response.
     */
    public int getNumEntries() {
        return numEntries;
    }

    /***
     * Sets the number of entries returned in this response
     * @param numEntries The number of fax history entries
     */
    public void setNumEntries(int numEntries) {
        this.numEntries = numEntries;
    }

    /***
     * Gets the total number of fax history entries in the period specified in the request
     * @return The total number of fax history entries in the period specified in the request
     */
    public int getTotalEntries() {
        return totalEntries;
    }

    /***
     * Sets the total number of fax history entries in the period specified in the request
     * @param totalEntries The total number of fax history entries in the period specified in the request
     */
    public void setTotalEntries(int totalEntries) {
        this.totalEntries = totalEntries;
    }

    /***
     * Each Fax Item represents a single transaction
     */
    public static class FaxItem implements Serializable {
        String txnRef;
        String tag;
        Date date;
        String pages;
        String src;
        String dest;
        String status;
        String currency;
        String rate;
        String debit;
        String url;

        /***
         * Gets the currency used for this transaction. Refer to <a href="http://developer.hoiio.com/docs/rest.html#currency">Currency Code</a> for the list of currency code.
         * @return Currency used for this transaction.
         */
        public String getCurrency() {
            return currency;
        }

        /***
         * Sets the currency used for this transaction
         * @param currency Currency used for this transaction.
         */
        public void setCurrency(String currency) {
            this.currency = currency;
        }

        /***
         * Gets the Date/time (GMT+8) of this transaction
         * @return Date/time (GMT+8) of this transaction
         */
        public Date getDate() {
            return date;
        }

        /***
         * Sets the Date/time (GMT+8) of this transaction
         * @param date Date/time (GMT+8) of this transaction
         */
        public void setDate(Date date) {
            this.date = date;
        }

        /***
         * Gets the total amount billed for this transaction.
         * @return Total amount billed for this transaction.
         */
        public String getDebit() {
            return debit;
        }

        /***
         * Sets the total amount billed for this transaction.
         * @param debit Total amount billed for this transaction.
         */
        public void setDebit(String debit) {
            this.debit = debit;
        }

        /***
         * Gets the destination of the fax transaction. 
         * @return The destination of the fax transaction. Phone numbers start with a "+" and country code (E.164 format), e.g. +6511111111.
         */
        public String getDest() {
            return dest;
        }

        /***
         * Sets the destination of the fax transaction
         * @param dest The destination of the fax transaction. Phone numbers start with a "+" and country code (E.164 format), e.g. +6511111111.
         */
        public void setDest(String dest) {
            this.dest = dest;
        }

        /***
         * Gets the number of fax pages sent.
         * @return The number of fax pages sent.
         */
        public String getPages() {
            return pages;
        }

        /***
         * Sets the number of fax pages sent
         * @param pages The number of fax pages sent.
         */
        public void setPages(String pages) {
            this.pages = pages;
        }

        /***
         * Gets the per-minute charges for this fax transaction.
         * @return Per-minute charges for this fax transaction.
         */
        public String getRate() {
            return rate;
        }

        /***
         * Sets the per-minute charges for this fax transaction.
         * @param rate Per-minute charges for this fax transaction.
         */
        public void setRate(String rate) {
            this.rate = rate;
        }

        /***
         * Gets the source of the fax transaction. 
         * @return The source of the fax transaction. For outgoing fax this value is empty. Phone numbers start with a "+" and country code (E.164 format), e.g. +6511111111.
         */
        public String getSrc() {
            return src;
        }

        /***
         * Sets the source of the fax transaction
         * @param src The source of the fax transaction. For outgoing fax this value is empty. Phone numbers start with a "+" and country code (E.164 format), e.g. +6511111111.
         */
        public void setSrc(String src) {
            this.src = src;
        }


        /***
         * Gets the link to download the PDF file of the fax.
         * @return Link to download the PDF file of the fax.
         */
        public String getUrl() {
            return url;
        }

        /***
         * Sets the link to download the PDF file of the fax.
         * @param url Link to download the PDF file of the fax.
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /***
         * Gets the dial status of the fax.
         * @return The dial status of the fax. Possible values for Outgoing fax are:
         *  <ul>
         *      <li>answered</li>
         *      <li>unanswered</li>
         *      <li>ongoing</li>
         *      <li>failed</li>
         *      <li>busy</li>
         *   </ul>
         *  Possible values for Incoming fax are:
         *  <ul>
         *      <li>ongoing</li>
         *      <li>answered</li>
         *      <li>failed</li>
         *  </ul>
         */
        public String getStatus() {
            return status;
        }

        /***
         * Sets the dial status of the fax
         * @param status The dial status of the fax. Possible values for Outgoing fax are:
         *  <ul>
         *      <li>answered</li>
         *      <li>unanswered</li>
         *      <li>ongoing</li>
         *      <li>failed</li>
         *      <li>busy</li>
         *   </ul>
         *  Possible values for Incoming fax are:
         *  <ul>
         *      <li>ongoing</li>
         *      <li>answered</li>
         *      <li>failed</li>
         *  </ul>
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /***
         * Gets your own reference ID submitted in the initial voice/fax request.
         * @return Your own reference ID submitted in the initial voice/fax request.
         */
        public String getTag() {
            return tag;
        }

        /***
         * Sets your own reference ID submitted in the initial voice/fax request.
         * @param tag Your own reference ID submitted in the initial voice/fax request.
         */
        public void setTag(String tag) {
            this.tag = tag;
        }

        /***
         * Gets the unique reference ID for this transaction.
         * @return The unique reference ID for this transaction.
         */
        public String getTxnRef() {
            return txnRef;
        }

        /***
         * Sets the unique reference ID for this transaction.
         * @param txnRef The unique reference ID for this transaction.
         */
        public void setTxnRef(String txnRef) {
            this.txnRef = txnRef;
        }
    }
}
