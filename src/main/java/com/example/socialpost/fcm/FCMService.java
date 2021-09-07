package com.example.socialpost.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Service
public class FCMService {
    @Autowired
    Environment env;
    FirebaseMessaging instance;

    @PostConstruct
    private void init() {
        try {
            FileInputStream refreshToken = new FileInputStream(env.getProperty("fcm.keypath"));
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .build();
            this.instance = FirebaseMessaging.getInstance(FirebaseApp.initializeApp(options));
            log.info("FCM BEAN Set Connect..");
        }catch (IOException e){
            log.info("Error : FCM 설정 오류");
            e.printStackTrace();
        }
    }
}