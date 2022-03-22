package com.bcpr.backend.TTS.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.bcpr.backend.TTS.domain.Voice_Trans;

@Mapper
public interface ttsMapper {
	@Select("select count(*) from voice_trans")
	int getCountVoice_Trans();

	@Select("select * from voice_trans")
	List<Voice_Trans> getVoice_TransList();
	
	@Select("select * from voice_trans where email = #{email} order by voice_no desc")
	List<Voice_Trans> getVoice_TransListByEmail(@Param("email") String email);
	
	@Select("select * from voice_trans where email = #{email} and voice_no = #{voice_no}")
	Voice_Trans getVoice_Trans(
			@Param("email") String email,
			@Param("voice_no") int voice_no);
	
	@Insert("insert into voice_trans(email,trans_date,kind,input,output) "
			+"values(#{email},#{trans_date},#{kind},#{input},#{output,jdbcType=VARCHAR})")
	int insertVoice_TransContent(
			@Param("email") String email,
			@Param("trans_date") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime trans_date,
			@Param("kind") String kind,
			@Param("input") String input,
			@Param("output") String output);
	
	@Delete("delete from voice_trans where email = #{email} and voice_no = #{voice_no}")
	int deleteVoice_TransContent(
			@Param("email") String email,
			@Param("voice_no") int voice_no);
	
}
