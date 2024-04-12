package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Announcement;
import com.csye6220.eduverse.pojo.AnnouncementDTO;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnnouncementDAO extends DAO {

    public List<Announcement> getAnnouncementsByCourseOfferingId(Long courseOfferingId) {
        begin();

        String hql = "FROM Announcement a WHERE a.courseOffering.id = :courseOfferingId";
        Query<Announcement> query = getSession().createQuery(hql, Announcement.class);
        query.setParameter("courseOfferingId", courseOfferingId);

        List<Announcement> announcements = query.list();
        System.out.println(announcements);
        commit();
        return announcements;
    }

    public void createAnnouncement(Announcement announcement) {
        begin();
        getSession().persist(announcement);
        commit();
        close();
    }

    public Announcement getAnnouncementById(Long announcementId) {
        begin();

        String hql = "FROM Announcement a WHERE a.id = :announcementId";
        Query<Announcement> query = getSession().createQuery(hql, Announcement.class);
        query.setParameter("announcementId", announcementId);

        Announcement announcement = query.uniqueResult();
        System.out.println(announcement);
        commit();
        return announcement;
    }
}
