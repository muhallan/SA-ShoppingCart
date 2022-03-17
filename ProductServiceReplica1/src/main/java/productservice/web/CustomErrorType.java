package productservice.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomErrorType {
    private String errorMessage;

    private static final Logger log = LoggerFactory.getLogger(CustomErrorType.class);

    public CustomErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
        log.error(errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
