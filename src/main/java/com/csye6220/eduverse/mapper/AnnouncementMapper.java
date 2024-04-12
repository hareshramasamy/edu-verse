package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.dao.CourseOfferingDAO;
import com.csye6220.eduverse.dao.InstructorDAO;
import com.csye6220.eduverse.entity.Announcement;
import com.csye6220.eduverse.entity.CourseOffering;
import com.csye6220.eduverse.entity.Instructor;
import com.csye6220.eduverse.pojo.AnnouncementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class AnnouncementMapper {

    CourseOfferingDAO courseOfferingDAO;
    InstructorDAO instructorDAO;

    @Autowired
    public AnnouncementMapper(CourseOfferingDAO courseOfferingDAO, InstructorDAO instructorDAO) {
        this.courseOfferingDAO = courseOfferingDAO;
        this.instructorDAO = instructorDAO;
    }

    public AnnouncementDTO mapAnnouncementsToDTO(Announcement announcement) {
        if(announcement == null) {
            return null;
        }
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setId(announcement.getId());
        announcementDTO.setInstructorId(Objects.nonNull(announcement.getInstructor()) ? announcementDTO.getInstructorId() : null);
        announcementDTO.setInstructorName(Objects.nonNull(announcement.getInstructor()) ? announcement.getInstructor().getUser().getFirstName() + " " + announcement.getInstructor().getUser().getLastName() : "");
        announcementDTO.setCourseOfferingId(Objects.nonNull(announcement.getCourseOffering()) ? announcementDTO.getCourseOfferingId() : null);
        announcementDTO.setContent(announcement.getContent());
        announcementDTO.setTitle(announcement.getTitle());
        announcementDTO.setDateTime(announcement.getDateTime());
        return announcementDTO;
    }

    public AnnouncementDTO mapAnnouncementsToDTOSinglePage(Announcement announcement) {
        if(announcement == null) {
            return null;
        }
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setId(announcement.getId());
        announcementDTO.setInstructorId(Objects.nonNull(announcement.getInstructor()) ? announcement.getInstructor().getId() : null);
        announcementDTO.setInstructorName(Objects.nonNull(announcement.getInstructor()) ? announcement.getInstructor().getUser().getFirstName() + " " + announcement.getInstructor().getUser().getLastName() : "");
        announcementDTO.setCourseOfferingId(Objects.nonNull(announcement.getCourseOffering()) ? announcement.getCourseOffering().getId() : null);
        String contentWithLineBreaks = announcement.getContent().replaceAll("\r\n", "<br>");
        announcementDTO.setContent(contentWithLineBreaks);
        announcementDTO.setTitle(announcement.getTitle());
        announcementDTO.setDateTime(announcement.getDateTime());
        return announcementDTO;
    }

    public Announcement mapDTOToAnnouncement(AnnouncementDTO announcementDTO, String name) {
        Announcement announcement = new Announcement();
        CourseOffering courseOffering = courseOfferingDAO.getCourseOfferingById(announcementDTO.getCourseOfferingId());
        Instructor instructor = instructorDAO.getInstructorByUsername(name);
        announcement.setCourseOffering(courseOffering);
        announcement.setInstructor(instructor);
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setDateTime(LocalDateTime.now());
        return announcement;
    }
}
