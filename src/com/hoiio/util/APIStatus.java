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

/**
 * This is an enumeration of all possible status string that can result from calling the Hoiio API
 * @author Max
 */
public enum APIStatus {
    success_ok("success_ok", "The request has been processed successfully."),
    error_invalid_http_method("error_invalid_http_method", "Invalid HTTP method. Only GET or POST are allowed."),
    error_malformed_params("error_malformed_params", "HTTP POST request parameters contains non-readable bytes."),
    error_invalid_access_token("error_invalid_access_token", "Your Access Token is invalid, expired or has been revoked."),
    error_invalid_app_id("error_invalid_app_id", "Your Application ID is invalid or has been revoked."),
    error_tag_invalid_length("error_tag_invalid_length", "Tag parameter is too long, must be 256 characters or less."),
    error_service_not_available("error_service_not_available", "The required service is currently not available"),
    error_destination_not_supported("error_destination_not_supported", "Destination is not supported"),
    error_invalid_destination("error_invalid_destination", "Destination is invalid"),
    error_invalid_notify_url("error_invalid_notify_url", "Invalid URL in notify_url parameter."),
    error_unable_to_resolve_notify_url("error_unable_to_resolve_notify_url", "Cannot resolve URL in notify_url parameter."),
    error_insufficient_credit("error_insufficient_credit", "You have insufficient credit in your developer account to perform this action."),
    error_concurrent_call_limit_reached("error_concurrent_call_limit_reached", "You have exceeded the maximum number of concurrent calls. Each application is allowed only 8 active call/fax at any point of time."),
    error_file_too_big("error_file_too_big", "The file you are uploading exceeds 2MB."),
    error_file_invalid("error_file_invalid", "file is not a PDF file."),
    error_page_size_invalid("error_page_size_invalid", "file contains pages that are neither A4 nor Letter size."),
    error_rate_limit_exceeded("error_rate_limit_exceeded", "You have exceeded your request limit for this API. Refer to API Limits for details."),
    error_internal_server_error("error_internal_server_error", "There is an unexpected error. Please contact Hoiio support for assistance."),
    error_unknown_error_code("error_unknown_error_code", "The error code cannot be found"),
    error_from_invalid("error_from_invalid", "from parameter is invalid."),
    error_to_invalid("error_to_invalid", "to parameter is invalid."),
    error_to_before_from("error_to_before_from", "to parameter is earlier than from parameter."),
    error_page_invalid("error_page_invalid", "page parameter is invalid."),
    error_app_id_param_missing("error_app_id_param_missing", "A required parameter app_id is missing."),
    error_access_token_param_missing("error_access_token_param_missing", "A required parameter access_token is missing."),
    error_dest_param_missing("error_dest_param_missing", "A required parameter dest is missing."),
    error_file_param_missing("error_file_param_missing", "A required parameter file is missing."),
    error_caller_id_param_missing("error_caller_id_param_missing", "A required parameter caller_id is missing."),
    error_msg_param_missing("error_msg_param_missing", "A required parameter msg is missing."),
    error__param_missing("error__param_missing", "A required parameter is missing.");



    String code, msg;
    APIStatus(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    /***
     * Gets the APIStatus that matches the provided @code
     * @param code The code to match
     * @return The APIStatus that matches the provided code
     */
    public static APIStatus get(String code)
    {
        for(APIStatus c : APIStatus.values())
        {
            if(c.getCode().equals(code))
                return c;
        }
        return APIStatus.error_unknown_error_code;
    }

    @Override
    public String toString()
    {
        return getCode() + " - " + getMsg();
    }

    /***
     * Gets the Hoiio API status code
     * @return The Hoiio API status code
     */
    public String getCode()
    {
        return code;
    }

    /***
     * Gets the Hoiio API status message
     * @return The Hoiio API status message
     */
    public String getMsg()
    {
        return msg;
    }

    //Test the enum
    public static void main(String[]args)
    {
        APIStatus code = APIStatus.get("success_ok");
        System.out.println(code.getMsg());
    }
}
