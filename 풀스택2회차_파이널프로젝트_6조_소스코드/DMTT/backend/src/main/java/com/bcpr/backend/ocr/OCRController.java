package com.bcpr.backend.ocr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bcpr.backend.ocr.domain.Media_Trans;
import com.bcpr.backend.utill.FileSaveHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
public class OCRController {
	private OCRMapper mapper;
	
	@Autowired
	public void setMapper(OCRMapper mapper) {
		this.mapper = mapper;
	}
	
	//ocr api 사용
	@PostMapping("/ocr")
	 public String getOCR(	
		@RequestParam(value="file", required=false) MultipartFile file,
		HttpServletRequest request) throws IOException{
		FileSaveHelper fsh = new FileSaveHelper(request.getServletContext().getRealPath("resources"));
		String url = fsh.saveTemp(file);
		System.out.println(url);
		OCRHelper oh = new OCRHelper();
		String out = oh.forFile(url);
		fsh.deleteTemp(url);
		
	    return out;
	}
	
	//media_trans 보관함에 저장
	@PostMapping("/ocr/upload")
	public int upload(
			@RequestParam("email") String email,
			@RequestParam("trans_date") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime trans_date,
			@RequestParam("kind") String kind,
			@RequestParam(value="input", required=false) MultipartFile input,
			@RequestParam("output") String output,
			HttpServletRequest request)throws IOException {

		FileSaveHelper fsh = new FileSaveHelper(request.getServletContext().getRealPath("resources"));
		String url = fsh.makeMultiFile("media_trans",email, kind, trans_date, input);
		return mapper.insertMedia_TransContent(email,trans_date,kind,url,output);
	}
	
	//media_trans 보관함 아이템 불러오기 email 기준 전부
	@GetMapping("/ocr/list/{email}")
	public List<Media_Trans> getList(
			@PathVariable("email") String email){
		//log.info("test : {}",test.get(0));
		return mapper.getMedia_TransListByEmail(email);
	}
	
	@GetMapping("/ocr/download/{email}/{media_no}/{kind}")
	public void download(
			@PathVariable("email") String email,
			@PathVariable("media_no") int media_no,
			@PathVariable("kind") String kind,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Media_Trans mt = mapper.getMedia_Trans(email, media_no);
		
		FileSaveHelper fsh = new FileSaveHelper(request.getServletContext().getRealPath("resources"));
		String path = fsh.makePath("media_trans", email, mt.getInput(), mt.getTrans_date(), kind);
		
		File file = new File(path);
		if(!kind.equals("input")) {
	        fsh.saveFile(path, file, mt.getOutput());
		}
		
		byte[] fileByte = FileUtils.readFileToByteArray(file);
		
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getName(), "UTF-8")+"\"");
	    response.setHeader("Content-Transfer-Encoding", "binary");

	    response.getOutputStream().write(fileByte);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	    
	    if(kind.equals("output")){ 
			fsh.deleteFile(file);
		}
    }
	
	//media_trans 보관함 아이템 삭제 email, no 기준(협의필요)
	@PostMapping("/ocr/remove")
	public int remove(
			@RequestParam("email") String email,
			@RequestParam("media_no") int media_no,
			HttpServletRequest request) throws IOException {
		Media_Trans mt = mapper.getMedia_Trans(email, media_no);
		FileSaveHelper fsh = new FileSaveHelper(request.getServletContext().getRealPath("resources"));
		String path = fsh.makePath("media_trans", email, mt.getInput(), mt.getTrans_date(), "input");
		File file = new File(path);
		fsh.deleteFile(file);
		return mapper.deleteMedia_TransContent(email,media_no);
	}
}
