package org.bazara.saudigitus.bazaratranscliente.model;

import java.util.List;
import java.util.Map;

/**
 * Created by dalves on 9/29/17.
 */

public class ApiError{

    String message;
    Map<String, List<String>> errors;

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
