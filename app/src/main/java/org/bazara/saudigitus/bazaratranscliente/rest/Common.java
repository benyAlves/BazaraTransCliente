package org.bazara.saudigitus.bazaratranscliente.rest;

/**
 * Created by SD on 1/9/2018.
 */

public class Common {
    public static final String baseURL = "https://maps.googleapis.com";

    public static InterfaceAPI getGoogleAPI(){
        return ClientAPI.getClient(baseURL).create(InterfaceAPI.class);
    }
}
