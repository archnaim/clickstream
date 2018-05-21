package acn.clickstream.dsc.api;

import acn.clickstream.dsc.model.DefaultErrorMessage;
import acn.clickstream.dsc.model.FaqSummary;
import acn.clickstream.dsc.service.DbOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummaryApiController {

    @Autowired
    private DbOperation dbOperation;


    @GetMapping("/faqsummary")
    public Object getFaqSummary()
    {
        Object response = dbOperation.getAllFaqSummary();

        if(response instanceof DefaultErrorMessage){
            return new ResponseEntity<Object>(response, HttpStatus.valueOf(((DefaultErrorMessage) response).getCode()));
        }

        return (Iterable<FaqSummary>)response;

    }



}
