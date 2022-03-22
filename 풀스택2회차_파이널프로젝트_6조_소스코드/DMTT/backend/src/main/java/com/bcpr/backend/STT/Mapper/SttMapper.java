package com.bcpr.backend.STT.Mapper;


import com.bcpr.backend.ocr.domain.Media_Trans;
import org.apache.ibatis.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.bcpr.backend.STT.domain.Document_Trans;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SttMapper{
    @Select("select count(*) from document_trans")
    int getCountDocument_Trans();

    @Select("select * from document_trans")
    List<Document_Trans> getDocument_TransList();

    @Select("select * from document_trans where email = #{email} order by document_no desc")
    List<Document_Trans> getDocument_TransListByEmail(@Param("email") String email);

    @Select("select * from document_trans where email = #{email} and document_no = #{document_no}")
    Document_Trans getDocument_Trans(
            @Param("email") String email,
            @Param("document_no") int document_no);


    @Insert("insert into document_Trans(email,trans_date,kind,input,output) "
            +"values(#{email},#{trans_date},#{kind},#{input,jdbcType=VARCHAR},#{output})")
    int insertDocument_TransContent(
            @Param("email") String email,
            @Param("trans_date") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime trans_date,
            @Param("kind") String kind,
            @Param("input") String input,
            @Param("output") String output);

    @Delete("delete from document_Trans where email = #{email} and document_no = #{document_no}")
    int deleteMedia_TransContent(
            @Param("email") String email,
            @Param("document_no") int document_no);
}
