package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.dao.CourseOfferingDAO;
import com.csye6220.eduverse.entity.CourseOffering;
import com.csye6220.eduverse.entity.FileData;
import com.csye6220.eduverse.pojo.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
@Component
public class FileMapper {

    CourseOfferingDAO courseOfferingDAO;

    @Autowired
    public FileMapper(CourseOfferingDAO courseOfferingDAO) {
        this.courseOfferingDAO = courseOfferingDAO;
    }

    public FileDTO mapFilesToDTO(FileData fileData) {
        if(fileData == null) {
            return null;
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(fileData.getId());
        fileDTO.setFileName(fileData.getFileName());
        fileDTO.setFileType(fileData.getFileType());
        fileDTO.setDateTime(fileData.getDateTime());
        fileDTO.setCourseOfferingId(Objects.nonNull(fileData.getCourseOffering()) ? fileData.getCourseOffering().getId() : null);
        return fileDTO;
    }

    public FileData mapDTOtoFile(FileDTO fileDTO) throws IOException {
        FileData fileData = new FileData();
        CourseOffering courseOffering = courseOfferingDAO.getCourseOfferingById(fileDTO.getCourseOfferingId());
        fileData.setData(fileDTO.getData().getBytes());
        fileData.setFileName(fileDTO.getData().getOriginalFilename());
        fileData.setFileType(fileDTO.getData().getContentType());
        fileData.setDateTime(LocalDateTime.now());
        fileData.setCourseOffering(courseOffering);
        return fileData;
    }
}
