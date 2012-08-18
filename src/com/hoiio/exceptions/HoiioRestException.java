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
package com.hoiio.exceptions;

import com.hoiio.util.APIRequest;
import com.hoiio.util.APIResponse;
import com.hoiio.util.APIStatus;

public class HoiioRestException extends Exception {

    String stackTrace;
    String className;
    APIRequest req;
    APIResponse res;
    /***
     * Default constructor
     */
    public HoiioRestException() {
        super();
        stackTrace = "";
        className = "";
    }

    /***
     * Constructor taking in an Exception as a parameter to store the stack trace
     * of the Exception parameter into the HoiioRestException class.
     * @param e
     */
    public HoiioRestException(Exception e)
    {
        String trace = "";
        for(StackTraceElement element : e.getStackTrace())
        {
            trace += element.toString() + "\n";
        }
        this.stackTrace = trace;
        this.className = e.getClass().getName();
    }

    /***
     * Constructor taking in the request and response of an API call
     * @param req APIRequest for the call which results in this Exception
     * @param res APIResponse for the call which results in this Exception
     */
    public HoiioRestException(APIRequest req, APIResponse res) {
        super(res.getStatus().toString());
        this.req = req;
        this.res = res;
    }

    /***
     * Constructor taking in a String which describes the Exception message
     * @param msg
     */
    public HoiioRestException(String msg) {
        super(msg);
    }

    /***
     * Gets the APIRequest which results in this Exception being thrown
     * @return
     */
    public APIRequest getRequest()
    {
        return req;
    }

    /***
     * Gets the APIResponse which results in this Exception being thrown
     * @return
     */
    public APIResponse getResponse()
    {
        return res;
    }

    /***
     * Gets the APIStatus of the APIResponse which results in this Exception being thrown
     * @return
     */
    public APIStatus getStatus()
    {
        return res.getStatus();
    }

    /***
     * Gets the HTTP GET String for the APIRequest which results in this Exception being thrown
     * @return
     */
    public String getPostString()
    {
        return req.getRequestUrl() + "?" + req.getPostString();
    }

    /***
     * Gets the HTTP Response String which results in this Exception being thrown
     * @return
     */
    public String getResponseString()
    {
        return res.getResponseString();
    }

    /***
     * Gets the stack trace in String format
     * @return The stack trace
     */
    public String getStringStackTrace() {
        return stackTrace;
    }

    /***
     * Sets the stack trace
     * @param stackTrace The stack trace
     */
    public void setStringStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    /***
     * Gets the String representation of this Exception which best describes
     * the problem
     * @return The String representation of this Exception
     */
    @Override
    public String toString(){
        if(stackTrace != null)
            return "HoiioRestException > " + className + " : " + stackTrace;
        else if(getStatus() == APIStatus.error_unknown_error_code)
            return "HoiioRestException > " + getResponse().getStatusString();
        else if(this.getMessage() != null)
            return "HoiioRestException > " + this.getMessage();
        else
            return super.toString();
    }

}
