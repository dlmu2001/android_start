package org.td21.a3party;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by tom on 17-7-14.
 */
public class FileLog {
    public static final int MAX_NORMAL_LOG_FILE_LENGTH = 5*1024*1024;//5M

    private static int RESERVE_FILE_NUMS = 10;
    private static int MSG_WRITE_LOG_TO_FILE = 1;
    private static String LOG_SEPARATOR = " ";
    private static String LOG_INDEX_SEPARATOR = "_";
    private static String LOGS_SUB_PATH = "logs/";

    private Handler mLogHandler;
    private LogThread mLogThread;
    private final SimpleDateFormat mDateFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    private void FileLog() {
    }
    private static class FileLogHolder{
        private static FileLog instance = new FileLog();
    }

    public static FileLog getInstance() {
        return FileLogHolder.instance;
    }

    private class LogThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();

            mLogHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == MSG_WRITE_LOG_TO_FILE) {
                        writeToFile((String) msg.obj);
                    }
                }
            };
            Looper.loop();
        }
    }

    public void startLogThread() {
        mLogThread = new LogThread();
        mLogThread.start();
    }

    public void write(int level, String tag, String content) {
        if (mLogHandler == null) {
            return;
        }
        Message msg = Message.obtain();
        msg.what = MSG_WRITE_LOG_TO_FILE;
        msg.obj = combineContent(getLevelString(level), tag, content);
        mLogHandler.sendMessage(msg);
    }

    private void writeToFile(String content) {
        String fullFilePath = getAvailableLogFilePath(content);
        File logFile = new File(fullFilePath);

        //创建新Log文件时,检查下是否要删除旧Log文件
        if (!logFile.exists()) {
            cleanExcessLogFiles();
        }

        writeToFileWithFullPath(logFile.getAbsolutePath(),
                content, MAX_NORMAL_LOG_FILE_LENGTH);
    }
    public static boolean checkSDCardMounted() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    public static void writeToFileWithFullPath(String fullFilePath, String logInfo, final int maxLength) {
        if (checkSDCardMounted()) {
            final File file = new File(fullFilePath);
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                buf.write(logInfo);
                buf.newLine();
                buf.flush();
                buf.close();
            }catch (IOException e) {

            }catch(OutOfMemoryError e){

            }finally {

            }
        }
    }


    //找到最新可用log文件的文件名，已满则给出新的文件名
    private String getAvailableLogFilePath(String content) {
        String latestFilePath = getTodayLatestLogFilePath();

        //没有log文件
        if (latestFilePath == null) {
            return buildFullFilePath(getTodayDate(), 1);
        }

        File file = new File(latestFilePath);
        if (isFileFull(file.length(), content.length())) {
            int index = getIndexFromLogFile(latestFilePath);
            return buildFullFilePath(getTodayDate(), index+1);
        } else {
            return latestFilePath;
        }
    }

    private String buildFullFilePath(String date, int index) {
        StringBuffer name = new StringBuffer();
        name.append(getLogsDirPath());
        name.append(date);
        name.append(LOG_INDEX_SEPARATOR);
        name.append(index);
        return name.toString();
    }

    private boolean isFileFull(long fileLength, int contentLength) {
        return  (fileLength + contentLength > MAX_NORMAL_LOG_FILE_LENGTH) ? true : false;
    }

    //保留最后 RESERVE_FILE_NUMS + 1 个log文件
    private void cleanExcessLogFiles() {
        String logDirPath = getLogsDirPath();
        File logDir = new File(logDirPath);
        if (logDir.isDirectory()) {
            File[] allFiles = logDir.listFiles();
            Arrays.sort(allFiles, new FileLastModifiedComparator());
            for (int i=0; i<allFiles.length-RESERVE_FILE_NUMS; i++) {
                File file = allFiles[i];
                file.delete();
            }
        }
    }

    //获取今天最新的log文件名
    private String getTodayLatestLogFilePath() {
        String logDirPath = getLogsDirPath();
        File logDir = new File(logDirPath);
        if (logDir.isDirectory()) {
            File[] allFiles = logDir.listFiles();
            Arrays.sort(allFiles, new FileLastModifiedComparator());
            for (int i=allFiles.length-1; i>=0; i--) {
                if (allFiles[i].getAbsolutePath().contains(getTodayDate())) {
                    return allFiles[i].getAbsolutePath();
                }
            }
        }

        return null;
    }

    //path:2017-03-03_2, 取出index数字 "2"
    private int getIndexFromLogFile(String path) {
        File file = new File(path);
        String fullPath = file.getAbsolutePath();
        int pos = fullPath.indexOf(LOG_INDEX_SEPARATOR);
        if (-1 == pos) {
            return 1;
        } else {
            String curIndex = fullPath.substring(pos+1);
            try {
                return Integer.parseInt(curIndex);
            } catch (Exception e) {
                return 1;
            }
        }
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        if (sdDir != null)
            return sdDir.toString();
        else
            return null;

    }
    private String getLogsDirPath() {
        return (getSDPath()
                + LOGS_SUB_PATH);
    }

    public static Date getNowDate(){
        return new Date(System.currentTimeMillis());
    }
    private String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(getNowDate());
    }

    private String combineContent(String level, String tag, String content) {
        StringBuffer result = new StringBuffer();

        result.append(mDateFormat.format(getNowDate()));
        result.append(LOG_SEPARATOR);
        result.append(Thread.currentThread().getId());
        result.append(LOG_SEPARATOR);
        result.append(level);
        result.append(LOG_SEPARATOR);
        result.append(tag);
        result.append(LOG_SEPARATOR);
        result.append(content);

        return result.toString();
    }

    private String getLevelString(int level) {
        switch (level) {
            case Log.VERBOSE:
                return "V";
            case Log.DEBUG:
                return "D";
            case Log.INFO:
                return "I";
            case Log.WARN:
                return "W";
            case Log.ERROR:
                return "E";
            default:
                return "D";
        }
    }

    private static class FileLastModifiedComparator implements Comparator<File> {
        public int compare(File file1, File file2) {

            long lastModified1 = file1.lastModified();
            long lastModified2 = file2.lastModified();

            if (lastModified1 > lastModified2) {
                return 1;
            } else if (lastModified1 < lastModified2) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
