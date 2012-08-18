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

/**
 * This class encapsulates the Hoiio numbers for a user
 * @author Max
 */
public class ActiveNumbers implements Serializable {

    private int totalEntries;
    ArrayList<HoiioNumber> hoiioNumbers = new ArrayList<HoiioNumber>();

    /***
     * Gets an array list of Hoiio Numbers
     * @return An array list of Hoiio Numbers
     */
    public ArrayList<HoiioNumber> getHoiioNumbers() {
        return hoiioNumbers;
    }

    /***
     * Sets the Hoiio Numbers
     * @param hoiioNumbers The Hoiio Numbers to set
     */
    public void setHoiioNumbers(ArrayList<HoiioNumber> hoiioNumbers) {
        this.hoiioNumbers = hoiioNumbers;
    }


    /***
     * Gets the total number of Hoiio Numbers for the account
     * @return The total number of entries
     */
    public int getTotalEntries() {
        return totalEntries;
    }

    /***
     * Sets the total number of Hoiio Numbers
     * @param totalEntries The total number of entries
     */
    public void setTotalEntries(int totalEntries) {
        this.totalEntries = totalEntries;
    }

    

    /***
     * This class represents a single Hoiio Number for each user
     */
    public static class HoiioNumber implements Serializable {
        String number;
        String forwardTo;
        String expiry;
        String country;
        String state;

        /***
         * Gets the country the hoiio number belongs to
         * @return The country that this Hoiio Number belongs to in ISO 3166-1 alpha-2 format.
         */
        public String getCountry() {
            return country;
        }

        /***
         * Sets the country this hoiio number belongs to
         * @param country The country this hoiio number belongs to
         */
        public void setCountry(String country) {
            this.country = country;
        }

        /***
         * Gets the expiry date for this Hoiio Number
         * @return The expiry date for this Hoiio Number in "YYYY-MM-DD" format (GMT+8).
         */
        public String getExpiry() {
            return expiry;
        }

        /***
         * Sets the expiry date for this Hoiio Number
         * @param expiry The expiry date for this Hoiio Number in "YYYY-MM-DD" format (GMT+8).
         */
        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        /***
         * The url on your web server to be notified when there is an incoming call to this Hoiio Number
         * @return A fully-qualified HTTP/S URL on your web server to be notified when there is an incoming call to this Hoiio Number. See <a href="http://developer.hoiio.com/docs/ivr_answer.html">IVR API - Incoming Calls</a> for details.
         */
        public String getForwardTo() {
            return forwardTo;
        }

        /***
         * Sets the url on your web server to be notified when there is an incoming call to this Hoiio Number
         * @param forwardTo A fully-qualified HTTP/S URL on your web server to be notified when there is an incoming call to this Hoiio Number. See <a href="http://developer.hoiio.com/docs/ivr_answer.html">IVR API - Incoming Calls</a> for details.
         */
        public void setForwardTo(String forwardTo) {
            this.forwardTo = forwardTo;
        }

        /***
         * Gets the Hoiio Number
         * @return The Hoiio Number assigned to your application. Phone numbers start with a "+" and country code (E.164 format), e.g. +6511111111.
         */
        public String getNumber() {
            return number;
        }

        /***
         * Sets the Hoiio Number
         * @param number The Hoiio Number assigned to your application. Phone numbers start with a "+" and country code (E.164 format), e.g. +6511111111.
         */
        public void setNumber(String number) {
            this.number = number;
        }

        /***
         * Gets the state the Hoiio Number belongs to
         * @return The state that this Hoiio Number belong to in ISO 3166-2 format. Is an empty string if the number is not state specific.
         */
        public String getState() {
            return state;
        }

        /***
         * Sets the state the Hoiio Number belongs to
         * @param state The state that this Hoiio Number belong to in ISO 3166-2 format. Is an empty string if the number is not state specific.
         */
        public void setState(String state) {
            this.state = state;
        }


    }
}
