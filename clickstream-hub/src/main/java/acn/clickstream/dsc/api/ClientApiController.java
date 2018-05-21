package acn.clickstream.dsc.api;

import acn.clickstream.dsc.model.DefaultErrorMessage;
import acn.clickstream.dsc.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientApiController{

    @Autowired
    private KafkaSender kafkaSender;


    @PostMapping("/clickstream-in")
    public ResponseEntity<Object> receiveClickStream(@RequestBody String payload)
    {
        Object response = kafkaSender.send(payload);

        if(response instanceof DefaultErrorMessage){
            return new ResponseEntity<Object>(response, HttpStatus.valueOf(((DefaultErrorMessage) response).getCode()));
        }

        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

}
