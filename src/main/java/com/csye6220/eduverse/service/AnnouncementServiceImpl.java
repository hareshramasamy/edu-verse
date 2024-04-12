package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.AnnouncementDAO;
import com.csye6220.eduverse.entity.Announcement;
import com.csye6220.eduverse.mapper.AnnouncementMapper;
import com.csye6220.eduverse.pojo.AnnouncementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    AnnouncementDAO announcementDAO;

    AnnouncementMapper announcementMapper;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementDAO announcementDAO, AnnouncementMapper announcementMapper) {
        this.announcementDAO = announcementDAO;
        this.announcementMapper = announcementMapper;
    }

    @Override
    public List<AnnouncementDTO> getAnnouncementsByCourseOffering(Long courseOfferingId) {
        return announcementDAO.getAnnouncementsByCourseOfferingId(courseOfferingId)
                .stream()
                .map(announcement -> announcementMapper.mapAnnouncementsToDTO(announcement))
                .toList();
    }

    @Override
    public void createAnnouncement(AnnouncementDTO announcementDTO, String name) {
        Announcement announcement = announcementMapper.mapDTOToAnnouncement(announcementDTO, name);
        announcementDAO.createAnnouncement(announcement);
    }

    @Override
    public AnnouncementDTO getAnnouncementById(Long announcementId) {
        return announcementMapper.mapAnnouncementsToDTOSinglePage(announcementDAO.getAnnouncementById(announcementId));
    }

    @Override
    public AnnouncementDTO getAnnouncementForEditPage(Long announcementId) {
        return announcementMapper.mapAnnouncementsToDTO(announcementDAO.getAnnouncementById(announcementId));
    }

    @Override
    public AnnouncementDTO editAnnouncementById(AnnouncementDTO announcementDTO, Long announcementId, String name) {
        Announcement announcement = announcementDAO.getAnnouncementById(announcementId);
        announcement.setContent(announcementDTO.getContent());
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setDateTime(LocalDateTime.now());
        return announcementMapper.mapAnnouncementsToDTOSinglePage(announcementDAO.editAnnouncement(announcement));
    }

    @Override
    public void deleteAnnouncementById(Long announcementId) {
        Announcement announcement = announcementDAO.getAnnouncementById(announcementId);
        announcementDAO.deleteAnnouncement(announcement);
    }
}
