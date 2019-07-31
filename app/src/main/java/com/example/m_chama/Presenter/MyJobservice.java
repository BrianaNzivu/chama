package com.example.m_chama.Presenter;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class MyJobservice extends JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {
        clearingcache.deletecache(this);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters)
    {

        return false;

    }
}
