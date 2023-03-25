package com.example.MyBoxYoonho.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name="file_db")
public class File extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="real_name")
    private String realName;

    @Column(name="uuid_name")
    private String uuidName;

    @Column(name="extension")
    private String extension;

    @Column(name="file_path")
    private String filePath;

    @Column(name="file_size")
    private long fileSize;


}