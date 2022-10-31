package com.bu.softwareengineering.contest.helpers;

import java.util.Map;

public class ServiceResponseHelper {
    boolean hasError;
    Object content;
    Map responseMessage;

    public  ServiceResponseHelper(boolean hasError, Object content, Map responseMessage){
        this.hasError = hasError;
        this.content = content;
        this.responseMessage = responseMessage;
    }

    public boolean getHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Map getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(Map responseMessage) {
        this.responseMessage = responseMessage;
    }
}
