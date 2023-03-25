package com.example.MyBoxYoonho.repository;

import com.example.MyBoxYoonho.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
    File findByUuidName(String uuid);
}
