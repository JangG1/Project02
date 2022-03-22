package com.bcpr.backend.STT.Helper;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class SttHelper {

    public  String getSTThelper(String path ,String lang) {
        String clientId = "jdkthmlpk7";             // Application Client ID";
        String clientSecret = "JOvAP25012bTeQ7QQzgg4JLtd6MuEs1lSMXsHqbk";     // Application Client Secret";
        String result="";
        try {
            String imgFile = path;
            File voiceFile = new File(imgFile);

            String language = lang;        // 언어 코드 ( Kor, Jpn, Eng, Chn )
            String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
            URL url = new URL(apiURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            System.out.println("아이디 : "+conn.getRequestProperty("X-NCP-APIGW-API-KEY-ID"));
            System.out.println("비밀번호 : "+conn.getRequestProperty("X-NCP-APIGW-API-KEY"));
            System.out.println("Content-Type : "+conn.getRequestProperty("Content-Type"));
            System.out.println("voiceFile : "+voiceFile);




            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(voiceFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            BufferedReader br = null;
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String inputLine;

            if (br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                    result=response.toString();
                }
                br.close();
                System.out.println(result);
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}
