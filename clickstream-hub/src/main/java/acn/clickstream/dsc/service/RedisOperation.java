package acn.clickstream.dsc.service;

import acn.clickstream.dsc.model.FaqSummary;
import acn.clickstream.dsc.util.Log;
import acn.clickstream.dsc.util.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RedisOperation {

    private final String REDIS_KEY = "clickstream-faq";

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private HashOperations hashOperations;

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    public void saveOrUpdateFaqSummary(FaqSummary faqSummary)
    {
        try {
            hashOperations.put(REDIS_KEY,faqSummary.getFaqId().getFaqId()+"||"+faqSummary.getFaqId().getUserType(), String.valueOf(faqSummary.getCounter()));
        }
        catch (Exception e)
        {
            Log.getLogger().error("Error in saving faq summary to Redis, "+e.toString());
        }
    }

    public Object getAllFaq()
    {
        Map<List<String>,List<String>> result = new HashMap<>();
        try {
            result = hashOperations.entries(REDIS_KEY);
            Log.getLogger().info(String.format("FAQ SUMMARY -->> %s", result.toString()));
        }
        catch (Exception e)
        {
            Log.getLogger().error("Error in getting all faq summary from Redis, "+e.toString());
            return UtilityService.buildHttpErrorMessage(e);
        }

        return result;
    }

}
