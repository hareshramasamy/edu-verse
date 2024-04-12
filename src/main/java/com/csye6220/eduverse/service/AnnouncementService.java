package com.csye6220.eduverse.service;

import com.csye6220.eduverse.pojo.AnnouncementDTO;
import java.util.List;

public interface AnnouncementService {
    List<AnnouncementDTO> getAnnouncementsByCourseOffering(Long courseOfferingId);

    void createAnnouncement(AnnouncementDTO announcementDTO, String name);

    AnnouncementDTO getAnnouncementById(Long announcementId);
}
