package com.hard.code.tech.drinkapp.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {

    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private File file;
    private UploadCallBack uploadCallBack;

    public ProgressRequestBody(File file, UploadCallBack uploadCallBack) {
        this.file = file;
        this.uploadCallBack = uploadCallBack;
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("image/*");
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {

        long fileLength = file.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream fileInputStream = new FileInputStream(file);
        long uploaded = 0;


        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = fileInputStream.read(buffer)) != -1) {
                handler.post(new ProgressUpdater(uploaded, fileLength));

                uploaded += read;
                sink.write(buffer, 0, read);

            }
        } finally {
            fileInputStream.close();
        }


    }

    private class ProgressUpdater implements Runnable {
        long uploaded, fileLength;

        ProgressUpdater(long uploaded, long fileLength) {
            this.uploaded = uploaded;
            this.fileLength = fileLength;
        }

        @Override
        public void run() {
            uploadCallBack.onProgressUpdate((int) (100 * uploaded / fileLength));
        }
    }
}
