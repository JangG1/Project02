package com.bcpr.backend.STT.Controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bcpr.backend.STT.Helper.SttHelper;
import com.bcpr.backend.STT.Mapper.SttMapper;
import com.bcpr.backend.STT.domain.Document_Trans;
import com.bcpr.backend.utill.FileSaveHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Sttapicontroller {
    private SttMapper mapper;

    @Autowired
    public void setMapper(SttMapper mapper) {
        this.mapper = mapper;
    }
    @PostMapping("/Stt")
    public String getKorStt(@RequestParam(value="file", required=false) MultipartFile file,
                            @RequestParam(value="lang") String lang,
                            HttpServletRequest request) throws IOException{
        FileSaveHelper fsh=new FileSaveHelper(request.getServletContext().getRealPath("resources"));
        System.out.println("lang"+ lang);
        String url= fsh.saveTemp(file);
        SttHelper SH=new SttHelper();
        String out= SH.getSTThelper(url,lang);
        System.out.println("out :"+ out);
        fsh.deleteTemp(url);

        return out;
    }

    @PostMapping("/Stt/upload")
    public int upload(
            @RequestParam("email") String email,
            @RequestParam("trans_date") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime trans_date,
            @RequestParam("kind") String kind,
            @RequestParam(value="input", required=false) MultipartFile input,
            @RequestParam("output") String output,
            HttpServletRequest request)throws IOException {

        FileSaveHelper fsh = new FileSaveHelper(request.getServletContext().getRealPath("resources"));
        String url = fsh.makeMultiFile("document_trans",email, kind, trans_date, input);
        return mapper.insertDocument_TransContent(email,trans_date,kind,url,output);
    }
    @GetMapping("/Stt/list/{email}")
    public List<Document_Trans> getList(
            @PathVariable("email") String email){
        //log.info("test : {}",test.get(0));
        return mapper.getDocument_TransListByEmail(email);
    }

    @GetMapping("/Stt/download/{email}/{document_no}/{kind}")
    public void download(
            @PathVariable("email") String email,
            @PathVariable("document_no") int document_no,
            @PathVariable("kind") String kind,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Document_Trans dt = mapper.getDocument_Trans(email, document_no);

        FileSaveHelper fsh = new FileSaveHelper(request.getServletContext().getRealPath("resources"));
		String path = fsh.makePath("document_trans", email, dt.getInput(), dt.getTrans_date(), kind);

		File file = new File(path);
		if(!kind.equals("input")) {
	        fsh.saveFile(path, file, dt.getOutput());
		}
		
        byte[] fileByte = FileUtils.readFileToByteArray(file);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getName(), "UTF-8")+"\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
        System.out.println(response.getHeader("Content-Disposition"));
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();

        if(kind.equals("output")){ 
			fsh.deleteFile(file);
		}
    }
    @PostMapping("/Stt/remove")
    public int remove(
    		@RequestParam("email") String email,
            @RequestParam("document_no") int document_no,
        	HttpServletRequest request) throws IOException {
		Document_Trans dt = mapper.getDocument_Trans(email, document_no);
		FileSaveHelper fsh = new FileSaveHelper(request.getServletContext().getRealPath("resources"));
		String path = fsh.makePath("document_trans", email, dt.getInput(), dt.getTrans_date(), "input");
		File file = new File(path);
		fsh.deleteFile(file);
        return mapper.deleteMedia_TransContent(email,document_no);
    }
}
