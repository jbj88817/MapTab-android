package com.bojie.materialtest.services;


import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by bojiejiang on 5/17/15.
 */
public class MyService extends JobService{
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
