package com.banico.downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.huxq17.download.DownloadInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Downloader downloader = new Downloader();
        downloader.download(this, "https://bit.ly/2R5y1BI", new Downloader.DownloadObserver() {
            @Override
            public void onProgress(int progress, DownloadInfo info) {

            }

            @Override
            public void onSuccess(DownloadInfo info) {

            }

            @Override
            public void onFailure(DownloadInfo info) {

            }
        });
    }
}
