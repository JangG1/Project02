package com.bcpr.backend.ocr;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OCRHelper {
	public final String apiURL = "https://dakj3dl6zh.apigw.ntruss.com/custom/v1/14192/76e38e6cfacf0c4bdc5f9ca5d9b1b36d09f196b345d859b568020c6729d2c972/general";
	public final String secretKey = "TklYTGpycXh2d2lXVGRFR2JQZ1ppcURXdFhDeHNiYkc=";
	
	public String forFile(String path) {
		//List<String result = new ArrayList<>();
		String result = "";
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setReadTimeout(30000);
			con.setRequestMethod("POST");
			String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			con.setRequestProperty("X-OCR-SECRET", secretKey);

			JSONObject json = new JSONObject();
			json.put("version", "V2");
			json.put("requestId", UUID.randomUUID().toString());
			json.put("timestamp", System.currentTimeMillis());
			JSONObject image = new JSONObject();
			
			String extension = path.substring(path.lastIndexOf(".")+1);
			
			image.put("format", extension);
			image.put("name", "demo");
			JSONArray images = new JSONArray();
			images.add(image);
			json.put("images", images);
			String postParams = json.toString();

			con.connect();
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			long start = System.currentTimeMillis();
			File file = new File(path);
			writeMultiPart(wr, postParams, file, boundary);
			wr.close();

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
				
			JSONParser jsonParser= new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
			JSONArray imageInfoArray = (JSONArray) jsonObject.get("images");
			
			response = new StringBuffer();
			for(int i=0; i<imageInfoArray.size(); i++)
			{
				JSONObject fields = (JSONObject) imageInfoArray.get(i);
				JSONArray fieldInfoArray = (JSONArray) fields.get("fields");
				String temp = "";
				for(int j=0; j<fieldInfoArray.size(); j++) {
					JSONObject fieldObject = (JSONObject) fieldInfoArray.get(j);
					//temp+=(String)fieldObject.get("inferText");
					response.append((String)fieldObject.get("inferText"));
					if((boolean)fieldObject.get("lineBreak")) {
						//result.add(temp);
						temp = "";
						response.append(System.getProperty("line.separator"));
					}
				}
			}
			result = response.toString();
		} catch (Exception e) {
			log.error(e.toString());
		} finally {
			return result;
		}
	}

	private void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
		IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("--").append(boundary).append("\r\n");
		sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
		sb.append(jsonMessage);
		sb.append("\r\n");

		out.write(sb.toString().getBytes("UTF-8"));
		out.flush();

		if (file != null && file.isFile()) {
			out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
			StringBuilder fileString = new StringBuilder();
			fileString
				.append("Content-Disposition:form-data; name=\"file\"; filename=");
			fileString.append("\"" + file.getName() + "\"\r\n");
			fileString.append("Content-Type: application/octet-stream\r\n\r\n");
			out.write(fileString.toString().getBytes("UTF-8"));
			out.flush();

			try (FileInputStream fis = new FileInputStream(file)) {
				byte[] buffer = new byte[8192];
				int count;
				while ((count = fis.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}
				out.write("\r\n".getBytes());
			}

			out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
		}
		out.flush();
	}
}
