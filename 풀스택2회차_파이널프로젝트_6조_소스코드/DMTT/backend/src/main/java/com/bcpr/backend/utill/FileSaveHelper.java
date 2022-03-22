package com.bcpr.backend.utill;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.bcpr.backend.ocr.domain.Media_Trans;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSaveHelper {
	String saveDir = "";
	String result = "";
	
	//resources 상대경로 설정
	//HttpServletRequest request 매개변수 필요
	//ex) new FileSaveHelper(request.getServletContext().getRealPath("resources"));
	public FileSaveHelper(String saveDir) {
		this.saveDir = saveDir;
	}
	
	//media_trans 상대 경로 설정
	//ex) ..\DMTT\backend\src\main\webapp\resources\media_trans\이메일\타입-날짜-파일명
	public String makeMultiFile(String parent, String email, String kind, LocalDateTime date, MultipartFile file) throws IOException {
		String basePath = File.separator+parent;
		String destPath = File.separator+email+File.separator+kind+"-"+formatDate(date)+"-"+file.getOriginalFilename();
		result = kind+"-"+formatDate(date)+"-"+file.getOriginalFilename();
		saveMultiFile(saveDir+basePath+destPath,file);
		return result;
	}
	
	//TTS
	public String copyFile(String parent, String email, String kind, LocalDateTime date, File file) throws IOException {
		String basePath = File.separator+parent;
		String destPath = File.separator+email+File.separator+kind+"-"+formatDate(date)+"-"+file.getName();
		result = kind+"-"+formatDate(date)+"-"+file.getName();
		File newFile = new File(saveDir+basePath+destPath);
		Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);//1원본 2매개변수
		
		saveFile(saveDir+basePath+destPath,newFile,null);
		return result;
	}
	
	public String makePath(String parent, String email, String fileName, LocalDateTime date, String kind) throws IOException {
		String basePath = File.separator+parent;
		String destPath = File.separator+email+File.separator;
		
		if(parent.equals("voice_trans")) {
			if(kind.equals("output")&&fileName != null) {
				destPath += fileName;
			}else {
				destPath += formatDate(date)+"-input.txt";
			}
		}
		else if(parent.equals("translation")) {
			destPath += formatDate(date)+"-"+kind+".txt";
		}else {
			if(kind.equals("input")&&fileName != null) {
				destPath += fileName;
			}else {
				destPath += formatDate(date)+"-output.txt";
			}
		}
		result = saveDir+basePath+destPath;
		return result;
	}
	
	//ocr api를 위한 임시파일 생성
	public String saveTemp(MultipartFile file) throws IOException{
		String basePath = File.separator+"temp";
		String destPath = File.separator+file.getOriginalFilename();
		saveMultiFile(saveDir+basePath+destPath,file);
		return saveDir+basePath+destPath;
	}
	
	//ocr api를 위한 임시파일 삭제
	public void deleteTemp(String path) {
		File file = new File(path); 
		if( file.exists() ){ 
			if(file.delete()){ 
				log.info("임시파일삭제 성공"); 
			}else{
				log.info("임시파일삭제 실패"); 
			} 
		}else{ 
			log.info("임시파일이 존재하지 않습니다."); 
		}
	}
	public void saveFile(String path, File file, String str) throws IOException {
		if(!file.exists()) {
			if(file.getParentFile().mkdirs()) {
			}
			file.createNewFile();
		}
		if(str!=null && !str.equals("")) {
			try{ 
	            // BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
	            BufferedWriter fw = new BufferedWriter(new FileWriter(path, true));
	            // 파일안에 문자열 쓰기
	            fw.write(str);
	            fw.flush();
	            // 객체 닫기
	            fw.close();        
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		}
	}
	public void deleteFile(File file) throws IOException{
		if(file.exists()) {
			if(file.delete()){ 
			}
		}
	}
	
	//경로를 받아서 파일생성
	public void saveMultiFile(String path,MultipartFile file) throws IOException {
		File newFileName = new File(path);
		if(!newFileName.exists()) {
			if(newFileName.getParentFile().mkdirs()) {
				newFileName.createNewFile();
			}
		}
		
		try {
			file.transferTo(newFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//업로드시간을 첨부이미지파일 식별을 위한 문자열로 변환하는 함수.
	public String formatDate(LocalDateTime sDate) {
		
		String year = ""+sDate.getYear();
		String month = (sDate.getMonthValue() < 10) ? "0"+sDate.getMonthValue() : ""+sDate.getMonthValue();
		String day = (sDate.getDayOfMonth() < 10) ? "0"+sDate.getDayOfMonth() : ""+sDate.getDayOfMonth();
		String hour = (sDate.getHour() < 10) ? "0"+sDate.getHour() : ""+sDate.getHour();
		String minute = (sDate.getMinute() < 10) ? "0"+sDate.getMinute() : ""+sDate.getMinute();
		String second = (sDate.getSecond() < 10) ? "0"+sDate.getSecond() : ""+sDate.getSecond();
		
		return year+month+day+hour+minute+second;
		
	}
}