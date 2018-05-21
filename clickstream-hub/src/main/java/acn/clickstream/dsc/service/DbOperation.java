package acn.clickstream.dsc.service;


import acn.clickstream.dsc.model.ClickstreamPayload;
import acn.clickstream.dsc.model.FaqSummary;
import acn.clickstream.dsc.repository.ClickstreamRepo;
import acn.clickstream.dsc.repository.FaqSummaryRepo;
import acn.clickstream.dsc.util.Log;
import acn.clickstream.dsc.util.UtilityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;

@Service
public class DbOperation {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ClickstreamRepo clickstreamRepo;

    @Autowired
    private FaqSummaryRepo faqSummaryRepo;

    public void saveOrUpdateClickStream(String payload)
    {
        try {
            ClickstreamPayload payload_ = mapper.readValue(payload,ClickstreamPayload.class);
            clickstreamRepo.save(payload_);
        } catch (IOException e) {
            Log.getLogger().error("Error in parsing click stream payload to Java Object, "+e.toString());
        } catch (DataException e) {
            Log.getLogger().error("Error in saving click stream payload to Database, "+e.toString());
        }

    }

    public void saveOrUpdateFaqSummary(FaqSummary faqSummary)
    {
        try {
            faqSummaryRepo.save(faqSummary);
        }
        catch (Exception e)
        {
            Log.getLogger().error("Error in saving faq summary to Database, "+e.toString());
        }
    }

    public Object getAllFaqSummary()
    {
        Iterable<FaqSummary> list = new LinkedList<FaqSummary>();

        try{
            list = faqSummaryRepo.findAll();
        }
        catch (Exception e)
        {
            Log.getLogger().error("Error in getting all faq summary from Database, "+e.toString());
            return UtilityService.buildHttpErrorMessage(e);
        }
        return list;
    }

}

