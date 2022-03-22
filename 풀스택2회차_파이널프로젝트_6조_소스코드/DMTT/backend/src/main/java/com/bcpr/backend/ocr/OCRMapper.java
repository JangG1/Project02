package com.bcpr.backend.ocr;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestParam;

import com.bcpr.backend.ocr.domain.Media_Trans;

@Mapper
public interface OCRMapper {
	@Select("select count(*) from media_trans")
	int getCountMedia_Trans();

	@Select("select * from media_trans")
	List<Media_Trans> getMedia_TransList();
	
	@Select("select * from media_trans where email = #{email} order by media_no desc")
	List<Media_Trans> getMedia_TransListByEmail(@Param("email") String email);
	
	@Select("select * from media_trans where email = #{email} and media_no = #{media_no}")
	Media_Trans getMedia_Trans(
			@Param("email") String email,
			@Param("media_no") int media_no);
	
	@Insert("insert into media_trans(email,trans_date,kind,input,output) "
			+"values(#{email},#{trans_date},#{kind},#{input,jdbcType=VARCHAR},#{output})")
	int insertMedia_TransContent(
			@Param("email") String email,
			@Param("trans_date") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime trans_date,
			@Param("kind") String kind,
			@Param("input") String input,
			@Param("output") String output);
	
	@Delete("delete from media_trans where email = #{email} and media_no = #{media_no}")
	int deleteMedia_TransContent(
			@Param("email") String email,
			@Param("media_no") int media_no);
	
}
