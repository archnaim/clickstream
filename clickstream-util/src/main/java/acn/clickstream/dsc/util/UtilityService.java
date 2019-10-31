package acn.clickstream.dsc.util;

import acn.clickstream.dsc.model.DefaultErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

public class UtilityService {

    public static DefaultErrorMessage buildHttpErrorMessage(Exception e) {
        DefaultErrorMessage error = new DefaultErrorMessage();
        if (e instanceof HttpClientErrorException) {
            HttpClientErrorException hce = (HttpClientErrorException) e;
            error.setCode(hce.getRawStatusCode());
            error.setStatus(hce.getStatusText());
            error.setError(hce.getMessage());
            error.setMessage(hce.getResponseBodyAsString());
        } else if (e instanceof HttpServerErrorException) {
            HttpServerErrorException hse = (HttpServerErrorException) e;
            Log.getLogger().error(hse.getRawStatusCode()+"");
            error.setCode(hse.getRawStatusCode());
            error.setStatus(hse.getStatusText());
            error.setError(hse.getMessage());
            error.setMessage(hse.getResponseBodyAsString());
        } else if (e instanceof RestClientException) {
            RestClientException rce = (RestClientException) e;
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setError(rce.getClass().getSimpleName());
            error.setMessage(rce.getMessage());
        } else {
            Exception rce = (Exception) e;
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setError(rce.getClass().getSimpleName());
            error.setMessage(rce.getMessage());
        }

        return error;
    }
}
