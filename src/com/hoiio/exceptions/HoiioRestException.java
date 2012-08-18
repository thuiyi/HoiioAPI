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
    public HoiioRestException() {
        super();
        stackTrace = "";
        className = "";
    }

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

    public HoiioRestException(APIRequest req, APIResponse res) {
        super(res.getStatus().toString());
        this.req = req;
        this.res = res;
    }

    public HoiioRestException(String msg) {
        super(msg);
    }

    public APIRequest getRequest()
    {
        return req;
    }

    public APIResponse getResponse()
    {
        return res;
    }

    public APIStatus getStatus()
    {
        return res.getStatus();
    }

    public String getPostString()
    {
        return req.getRequestUrl() + "?" + req.getPostString();
    }

    public String getResponseString()
    {
        return res.getResponseString();
    }

    public String getStringStackTrace() {
        return stackTrace;
    }

    public void setStringStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

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
