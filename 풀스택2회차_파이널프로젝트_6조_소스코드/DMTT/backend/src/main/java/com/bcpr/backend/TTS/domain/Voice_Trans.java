package com.bcpr.backend.TTS.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voice_Trans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long voice_no;
    private String email;
    private LocalDateTime trans_date;
    private String kind;
    @Lob
    private String input;
    private String output;
}
