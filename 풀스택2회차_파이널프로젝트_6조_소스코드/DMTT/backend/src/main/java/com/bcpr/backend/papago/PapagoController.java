package com.bcpr.backend.papago;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bcpr.backend.ocr.OCRMapper;
import com.bcpr.backend.ocr.domain.Media_Trans;
import com.bcpr.backend.security.api.UserResource;
import com.bcpr.backend.security.domain.User;
import com.bcpr.backend.security.service.UserService;
import com.bcpr.backend.utill.FileSaveHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j

//papago api 사용
public class PapagoController {
	
	private PapagoMapper mapper;
	
	@Autowired
	public void setMapper(PapagoMapper mapper) {
		this.mapper = mapper;
	}
	
	//Papago api 사용
	@PostMapping("/papago/array")
	 public String papaForArray(	
			 @RequestParam(value = "text[]") List<String> text,
			 @RequestParam(value = "from_language") String from_language,
			 @RequestParam(value = "to_language") String to_language,
				HttpServletRequest request) throws IOException{
			
		PapagoRepo papagorepo = new PapagoRepo();
		String t = papagorepo.translationForArray(text, from_language, to_language);
		papagorepo.setText(t);
		log.info("test : {}",papagorepo);
	    return papagorepo.getText();
	}
	
	@PostMapping("/papago/json")
	 public String papaForJson(	
			 @RequestParam(value = "text") String text,
			 @RequestParam(value = "from_language") String from_language,
			 @RequestParam(value = "to_language") String to_language,
				HttpServletRequest request) throws IOException{
			
		PapagoRepo papagorepo = new PapagoRepo();
		String t = papagorepo.translation(text, from_language, to_language);
		papagorepo.setText(t);
		log.info("test : {}",papagorepo);

	    return papagorepo.getText();

	}

	//TRANSLATION 보관함에 저장
		@PostMapping("/papago/upload")
		public int upload(
				@RequestParam("email") String email,
				@RequestParam("trans_date") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime trans_date,
				@RequestParam("input") String input,
				@RequestParam("output") String output,
				HttpServletRequest request)throws IOException {

			
			return mapper.insertTranslation_TranslaterContent(email, trans_date, input, output);
		}
		
		//media_trans 보관함 아이템 불러오기 email 기준 전부
		@GetMapping("/papago/list/{email}")
		public List<Translation> getList(
				@PathVariable("email") String email){
			//log.info("test : {}",test.get(0));
			return mapper.getTranslationListByEmail(email);
		}
		
		@GetMapping("/papago/download/{email}/{translation_no}/{kind}")
		public void download(
				@PathVariable("email") String email,
				@PathVariable("translation_no") int translation_no,
				@PathVariable("kind") String kind,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			Translation t = mapper.getTranslation(email, translation_no);
			FileSaveHelper fsh = new FileSaveHelper(request.getServletContext().getRealPath("resources"));
			String path = fsh.makePath("translation", email, null, t.getTrans_date(), kind);
			File file = new File(path);
			String text = "";
            text = t.getOutput();
			if(kind.equals("input"))
				text = t.getInput();
	        fsh.saveFile(path, file, text);
			
			byte[] fileByte = FileUtils.readFileToByteArray(file);
			
			response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getName(), "UTF-8")+"\"");
		    response.setHeader("Content-Transfer-Encoding", "binary");

		    response.getOutputStream().write(fileByte);
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		    
		    fsh.deleteFile(file);
	    }
		
		//media_trans 보관함 아이템 삭제 email, no 기준(협의필요)
		@PostMapping("/papago/remove")
		public int delete(
				@RequestParam("email") String email,
				@RequestParam("translation_no") int translation_no) {
			return mapper.deleteTranslation_TranslaterContent(email, translation_no);
		}
}
