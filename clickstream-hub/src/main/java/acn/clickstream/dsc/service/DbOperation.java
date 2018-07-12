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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DbOperation {

    private ObjectMapper mapper = new ObjectMapper();

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

    public Object getAllFaqSummary(Integer limit)
    {
        Iterable<FaqSummary> list = new ArrayList<>();

        try{
            list = faqSummaryRepo.findAllByOrderByCounterDesc(new PageRequest(0,limit));
        }
        catch (Exception e)
        {
            Log.getLogger().error("Error in getting all faq summary from Database, "+e.toString());
            return UtilityService.buildHttpErrorMessage(e);
        }
        return list;
    }

    public void backupFaqCountToDB(Map<List<String>,List<String>> redis)
    {
        try {
            String[] keys = (String[]) redis.keySet().toArray();
            String[] values = (String[]) redis.values().toArray();

            for (int i = 0; i < keys.length; i++) {
                String[] key = keys[i].split("||");
                FaqSummary faqSummary = new FaqSummary(key[0],key[1],Long.valueOf(values[i]),Instant.now().toEpochMilli());
                saveOrUpdateFaqSummary(faqSummary);
            }
        }catch (Exception e)
        {
            Log.getLogger().error("Error in saving all faq summary from redis to db, "+e.toString());
        }
    }

}

