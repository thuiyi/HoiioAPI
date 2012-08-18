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
package com.hoiio.examples;


import com.hoiio.api.NumberAPI;
import com.hoiio.api.ProfileAPI;
import com.hoiio.dto.ActiveNumbers;
import com.hoiio.dto.HoiioAuth;
import com.hoiio.dto.UserDto;
import com.hoiio.exceptions.HoiioRestException;
import com.hoiio.exceptions.HttpPostConnectionException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Max
 */
public class TestProfile {
    public static void main(String[]args)
    {
        String appId = "HY1UBwEZzqi1iqyy";
        String accessToken = "J4KTYa1m0kafcq10";

        HoiioAuth auth = new HoiioAuth(appId, accessToken);
        try {
            UserDto user = ProfileAPI.getProfile(auth);
            System.out.println("Your number is " + user.getMobileNumber() + " and name is " + user.getName());
        } catch (HttpPostConnectionException ex) {
            ex.printStackTrace();
        } catch (HoiioRestException ex) {
            ex.printStackTrace();
        }
    }
}
