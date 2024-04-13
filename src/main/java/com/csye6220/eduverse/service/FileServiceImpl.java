package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.FileDAO;
import com.csye6220.eduverse.entity.FileData;
import com.csye6220.eduverse.mapper.FileMapper;
import com.csye6220.eduverse.pojo.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{

    FileDAO fileDAO;

    FileMapper fileMapper;

    @Autowired
    public FileServiceImpl(FileDAO fileDAO, FileMapper fileMapper) {
        this.fileDAO = fileDAO;
        this.fileMapper = fileMapper;
    }

    @Override
    public List<FileDTO> getFilesByCourseOffering(Long courseOfferingId) {
        return fileDAO.getFilesByCourseOfferingId(courseOfferingId)
                .stream()
                .map(fileMapper::mapFilesToDTO)
                .toList();
    }
    @Override
    public void uploadFile(FileDTO fileDTO) throws IOException {
        FileData fileData = fileMapper.mapDTOtoFile(fileDTO);
        fileDAO.saveFile(fileData);
    }

    @Override
    public FileData getFileById(Long fileId) {
        return fileDAO.getFileById(fileId);
    }
}
