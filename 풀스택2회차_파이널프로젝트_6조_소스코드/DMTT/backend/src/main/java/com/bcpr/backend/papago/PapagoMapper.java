package com.bcpr.backend.papago;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bcpr.backend.ocr.domain.Media_Trans;

@Mapper
public interface PapagoMapper {
	@Select("select count(*) from translation")
	int getCountTranslation();


	@Select("select * from translation")
	List<Translation> getTranslations();

	
	@Select("select * from translation where email = #{email} order by translation_no desc")
	List<Translation> getTranslationListByEmail(@Param("email") String email);
	
	@Select("select * from translation where email = #{email} and translation_no = #{translation_no}")
	Translation getTranslation(
			@Param("email") String email,
			@Param("translation_no") int media_no);
	
	@Insert("insert into translation(email,trans_date,input,output) "
			+"values(#{email},#{trans_date},#{input},#{output})")
	int insertTranslation_TranslaterContent(
			@Param("email") String email,
			@Param("trans_date") LocalDateTime trans_date,
			@Param("input") String input,
			@Param("output") String output);
		
	@Delete("delete from translation where email = #{email} and translation_no = #{translation_no}")
	int deleteTranslation_TranslaterContent(
			@Param("email") String email,
	@Param("translation_no") int translation_no);


	



}
