package by.vshkl.android.saveig;

import android.app.DownloadManager;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ClipboardService extends Service implements ClipboardManager.OnPrimaryClipChangedListener {

    private ClipboardManager clipboardManager;
    private DownloadManager downloadManager;
    private Disposable disposable;

    @Override
    public void onCreate() {
        super.onCreate();
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(this);
        NotificationHelper.showNotification(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (clipboardManager != null) {
            clipboardManager.removePrimaryClipChangedListener(this);
        }
        if (disposable != null) {
            disposable.dispose();
        }
        NotificationHelper.hideNotification(this);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onPrimaryClipChanged() {
        String clipText = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
        if (clipText.startsWith(Constants.URL_START)) {
            downloadImage(clipText);
        }
    }

    private void downloadImage(final String imagePageUrl) {
        disposable = DownloadHelper.getImageUrl(imagePageUrl)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String imageUrl) throws Exception {
                        if (imageUrl != null) {
                            addDownloadTask(imageUrl);
                        }
                    }
                });
    }

    private void addDownloadTask(final String imageUrl) {
        String sunDirectory = Constants.DOWNLOAD_DIR +
                File.separator +
                System.currentTimeMillis() +
                Constants.IMAGE_EXTENSION;
        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(imageUrl));
        downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, sunDirectory);
        downloadManager.enqueue(downloadRequest);
    }
}
