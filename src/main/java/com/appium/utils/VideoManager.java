package com.appium.utils;

import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import static java.sql.DriverManager.getDriver;

public class VideoManager {
    TestUtils utils = new TestUtils();

    public void startRecording(){


        ((CanRecordScreen) new DriverManager().getDriver()).startRecordingScreen();


        utils.log().info(" Start recording the video" );
    }

    public void stopRecording(String scenarioName) throws IOException {
        GlobalParams params = new GlobalParams();
        String media = ((CanRecordScreen) new DriverManager().getDriver()).stopRecordingScreen();
        String dirPath = params.getPlatformName() + "_"
                + params.getDeviceName() + File.separator +"Videos";

        File videoDir = new File(dirPath);

        synchronized(videoDir){
            if(!videoDir.exists()) {
                videoDir.mkdirs();
            }
        }
        FileOutputStream stream = null;
        try {

            stream = new FileOutputStream(videoDir + File.separator + scenarioName + System.currentTimeMillis() + ".mp4");
            stream.write(Base64.decodeBase64(media));
            stream.close();
            utils.log().info("video path: " + videoDir + File.separator + scenarioName + System.currentTimeMillis()+ ".mp4");
        } catch (Exception e) {
            utils.log().error("error during video capture" + e.toString());
        } finally {
            if(stream != null) {
                stream.close();
            }
        }
    }
}
