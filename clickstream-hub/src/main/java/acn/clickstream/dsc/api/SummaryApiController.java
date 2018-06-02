package acn.clickstream.dsc.api;

import acn.clickstream.dsc.model.DefaultErrorMessage;
import acn.clickstream.dsc.model.FaqSummary;
import acn.clickstream.dsc.service.DbOperation;
import acn.clickstream.dsc.util.UtilityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SummaryApiController {

    @Autowired
    private DbOperation dbOperation;

    private ObjectMapper mapper = new ObjectMapper();


    @RequestMapping(value = "/faqsummary",method = RequestMethod.GET)
    public Object getFaqSummary(@RequestParam(defaultValue = "10",name = "limit") Integer limit)
    {
        Object response = dbOperation.getAllFaqSummary(limit);

        if(response instanceof DefaultErrorMessage){
            return new ResponseEntity<Object>(response, HttpStatus.valueOf(((DefaultErrorMessage) response).getCode()));
        }

        try {
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            DefaultErrorMessage error = UtilityService.buildHttpErrorMessage(e);
            return new ResponseEntity<Object>(response, HttpStatus.valueOf(((DefaultErrorMessage) error).getCode()));
        }

    }



}
