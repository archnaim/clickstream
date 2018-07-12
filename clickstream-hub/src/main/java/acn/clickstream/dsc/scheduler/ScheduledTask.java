package acn.clickstream.dsc.scheduler;

import acn.clickstream.dsc.service.DbOperation;
import acn.clickstream.dsc.service.RedisOperation;
import acn.clickstream.dsc.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ScheduledTask {

    @Value("save.count.db")
    private String backupToDB;

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private DbOperation dbOperation;

    @Scheduled(cron = "${scheduler.backup.toDB}")
    public void SaveFaqCounterToDB() {
        if(backupToDB.equals("true"))
        {
            Log.getLogger().info("Saving FAQ Counter from redis to DB");
            try {
                Map<List<String>,List<String>> redisResult = (Map<List<String>, List<String>>) redisOperation.getAllFaq();
                dbOperation.backupFaqCountToDB(redisResult);
            }catch (Exception e){
                Log.getLogger().error("Error in running scheduler backup all faq summary from redis to db");
            }
        }
    }
}
