package com.shensou.newpress.dokhttp;

/**
 * Created by h2h on 2015/9/8.
 */
public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
