package com.burchard36.api.data.json.exceptions;

public class JsonFileNotFoundException extends Exception{
    public JsonFileNotFoundException(final String errMsg) {
        super(errMsg);
    }
}
