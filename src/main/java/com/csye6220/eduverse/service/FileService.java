package com.csye6220.eduverse.service;

import com.csye6220.eduverse.entity.FileData;
import com.csye6220.eduverse.pojo.FileDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    List<FileDTO> getFilesByCourseOffering(Long courseOfferingId);

    void uploadFile(FileDTO fileDTO) throws IOException;

    FileData getFileById(Long fileId);
}
