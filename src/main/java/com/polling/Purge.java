package com.polling;

import com.polling.model.Poll;
import com.polling.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;


@Component
public class Purge {
  private PollService pollService;
  private Logger logger = LoggerFactory.getLogger(Purge.class);

  @Autowired
  public Purge(PollService pollService) {
    this.pollService = pollService;
  }

  /**
   * This task will be executed every day 00:05 to delete expired poll.
   */
  @Scheduled(cron = "0 5 0 * * ?")
  public void clearExpired() {

    logger.info("purging.....");
    List<Poll> polls = pollService.findAllPolls();
    Timestamp current = new Timestamp(System.currentTimeMillis());
    for (Poll p : polls) {
      Timestamp expire = Timestamp.valueOf(p.getEndTime());
      if (expire.before(current)) {
        this.pollService.deletePoll(p);
        logger.info(p.getTitle() + "has been deleted.");
      };
    }
  }
}
