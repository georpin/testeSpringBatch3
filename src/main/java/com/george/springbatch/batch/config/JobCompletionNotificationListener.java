package com.george.springbatch.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);


    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
//            
//            List<Autobot> results = this.jdbcTemplate.query("SELECT name, car FROM autobot",
//                    (rs, row) -> new Autobot(rs.getString(1), rs.getString(2)));

//            for (Product autobot : results) {
//                log.info("Found <" + autobot.toString() + "> in the database.");
//            }
        	

        }
    }
}
