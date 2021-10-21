package com.example.daisyling.common.util.download;

import java.io.File;

/**
 * Created by Emily on 10/11/21
 */
public interface DownloadListener {
    void onFinish(File file);

    void onProgress(int progress, long downloadedLengthKb, long totalLengthKb);

    void onFailed(String errMsg);
}
