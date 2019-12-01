package com.banico.downloader;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import androidx.core.content.ContextCompat;
import com.huxq17.download.DownloadInfo;
import com.huxq17.download.Pump;
import com.huxq17.download.message.DownloadListener;
import java.io.File;

public class Downloader {

    public void download(Context context, String url, DownloadObserver observer) {
        Pump.newRequest(url, new File(getRoot(context),"file.mp3").getAbsolutePath()).threadNum(5).submit();
        Intent intent = new Intent(context, DownloaderService.class);
        context.startService(intent);
        observe(context, observer);
    }

    public void download(Context context, Iterable<String> urls, DownloadObserver observer) {
        Pump.newRequest(urls.iterator().next(), "").threadNum(5).submit();
        Intent intent = new Intent(context, DownloaderService.class);
        context.startService(intent);
        observerDownloads(urls, observer);
    }

    private void observerDownloads(final Iterable<String> urls, final DownloadObserver observer) {
        DownloadListener listener = new DownloadListener() {
            @Override
            public void onProgress(int progress) {
                super.onProgress(progress);
                observer.onProgress(progress, getDownloadInfo());
                Pump.newRequest(urls.iterator().next(), "").threadNum(5).submit();
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                observer.onSuccess(getDownloadInfo());

            }

            @Override
            public void onFailed() {
                super.onFailed();
                observer.onFailure(getDownloadInfo());
            }
        };

        listener.enable();
    }

    private void observe(final Context context, final DownloadObserver observer) {
        final DownloadListener listener = new DownloadListener() {
            @Override
            public void onProgress(int progress) {
                super.onProgress(progress);
                observer.onProgress(progress, getDownloadInfo());
                Intent intent = new Intent(context, DownloaderService.class);
                intent.putExtra("action", "action_download");
                context.startService(intent);
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                observer.onSuccess(getDownloadInfo());
                disable();
            }

            @Override
            public void onFailed() {
                super.onFailed();
                observer.onFailure(getDownloadInfo());
            }
        };

        listener.enable();
    }

    private String getRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return ContextCompat.getExternalFilesDirs(context, null)[0].getAbsolutePath();
        } else {
            return context.getFilesDir().getAbsolutePath();
        }
    }

    interface DownloadObserver {

        void onProgress(int progress, DownloadInfo info);

        void onSuccess(DownloadInfo info);

        void onFailure(DownloadInfo info);
    }
}
