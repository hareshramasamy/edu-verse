package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.FileData;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileDAO extends DAO {
    public List<FileData> getFilesByCourseOfferingId(Long courseOfferingId) {
        begin();

        String hql = "FROM FileData a WHERE a.courseOffering.id = :courseOfferingId";
        Query<FileData> query = getSession().createQuery(hql, FileData.class);
        query.setParameter("courseOfferingId", courseOfferingId);

        List<FileData> files = query.list();
        System.out.println(files);
        commit();
        return files;
    }

    public void saveFile(FileData fileData) {
        begin();
        getSession().persist(fileData);
        commit();
        close();
    }

    public FileData getFileById(Long fileId) {
        begin();

        String hql = "FROM FileData a WHERE a.id = :fileId";
        Query<FileData> query = getSession().createQuery(hql, FileData.class);
        query.setParameter("fileId", fileId);

        FileData file = query.uniqueResult();
        System.out.println(file);
        commit();
        return file;
    }
}
