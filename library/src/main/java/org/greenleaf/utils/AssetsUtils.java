package org.greenleaf.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Assets操作
 */
public class AssetsUtils {
    private static final String ENCODING = "UTF-8";

    public static InputStream getFileFromAssets(@NonNull Context context, String fileName) throws IOException {
        AssetManager am = context.getAssets();
        return am.open(fileName);
    }

    @WorkerThread
    public static String asset2String(@NonNull Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();

        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufReader = null;

        try {
            inputStream = getFileFromAssets(context, fileName);
            bufferedInputStream = new BufferedInputStream(inputStream);
            inputStreamReader = new InputStreamReader(bufferedInputStream);
            bufReader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            // ignore
        } finally {
            IOUtils.closeQuietly(inputStream, bufferedInputStream, inputStreamReader, bufReader);
        }
        return stringBuilder.toString();
    }

    @WorkerThread
    public static void copyAssetsFile(@NonNull Context context, @NonNull final String assetsFileName, @NonNull final String destFilePath) throws IOException {

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {

            File desFile = new File(destFilePath);
            if (desFile.exists()) {
                return;
            }

            fileOutputStream = new FileOutputStream(desFile);
            inputStream = getFileFromAssets(context, assetsFileName);
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(inputStream, fileOutputStream);
        }
    }
}
