package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.*;
import com.csye6220.eduverse.entity.CourseOffering;
import com.csye6220.eduverse.entity.Enrollment;
import com.csye6220.eduverse.entity.Instructor;
import com.csye6220.eduverse.entity.Student;
import com.csye6220.eduverse.mapper.CourseMapper;
import com.csye6220.eduverse.mapper.CourseOfferingMapper;
import com.csye6220.eduverse.pojo.CourseDTO;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.pojo.ExcelUploadValues;
import com.csye6220.eduverse.validator.ExcelFileValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseOfferingServiceImpl implements CourseOfferingService {

    private final CourseDAO courseDAO;

    private final CourseMapper courseMapper;

    private final InstructorDAO instructorDAO;

    private final CourseOfferingMapper courseOfferingMapper;

    private final CourseOfferingDAO courseOfferingDAO;
    private final EnrollmentDAO enrollmentDAO;
    private final StudentDAO studentDAO;
    private final ExcelFileValidator excelFileValidator;

    @Autowired
    public CourseOfferingServiceImpl(CourseDAO courseDAO, CourseMapper courseMapper, InstructorDAO instructorDAO, CourseOfferingMapper courseOfferingMapper, CourseOfferingDAO courseOfferingDAO, EnrollmentDAO enrollmentDAO, StudentDAO studentDAO, ExcelFileValidator excelFileValidator) {
        this.courseDAO = courseDAO;
        this.courseMapper = courseMapper;
        this.instructorDAO = instructorDAO;
        this.courseOfferingMapper = courseOfferingMapper;
        this.courseOfferingDAO = courseOfferingDAO;
        this.enrollmentDAO = enrollmentDAO;
        this.studentDAO = studentDAO;
        this.excelFileValidator = excelFileValidator;
    }

    @Override
    public List<CourseDTO> getAllCourses(String username) throws Exception {
        Instructor instructor = instructorDAO.getInstructorByUsername(username);
        if(Objects.isNull(instructor) || Objects.isNull(instructor.getDepartment())) {
            throw new Exception("Couldn't find Instructor's department");
        }
        return courseDAO.getCoursesByDepartment(instructor.getDepartment().getId())
                .stream()
                .map(courseMapper::mapCoursesToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createCourseOffering(CourseOfferingDTO courseOfferingDTO, String username) {
        CourseOffering courseOffering = courseOfferingMapper.mapDTOToCourseOffering(courseOfferingDTO, username);
        courseOfferingDAO.createCourseOffering(courseOffering);
    }

    @Override
    public boolean checkCurrentUserEnrollment(Long courseOfferingId, String name) {
        return enrollmentDAO.checkCurrentCourseEnrollment(courseOfferingId, name);
    }

    @Override
    public CourseOffering getCourseOfferingById(Long courseOfferingId) {
        return courseOfferingDAO.getCourseOfferingById(courseOfferingId);
    }

    @Override
    public boolean checkCurrentUserIsCourseInstructor(Long instructorId, String username) {
        Instructor instructor =  instructorDAO.getInstructorByUsername(username);
        return Objects.equals(instructor.getId(), instructorId);
    }

    @Override
    public CourseOfferingDTO mapCourseOfferingToDTO(CourseOffering courseOffering) {
        return courseOfferingMapper.mapCourseOfferingToDTO(courseOffering);
    }

    @Override
    public List<Student> getEnrolledStudents(Long courseOfferingId, int offset) {
        return enrollmentDAO.getEnrollmentsByCourseOfferingId(courseOfferingId, offset)
                .stream()
                .map(Enrollment::getStudent)
                .toList();
    }

    @Override
    public boolean checkCourseOfferingExists(Long courseOfferingId) {
        CourseOffering courseOffering = getCourseOfferingById(courseOfferingId);
        return Objects.nonNull(courseOffering);
    }

    @Override
    public CourseOfferingDTO getCourseOfferingDTOById(Long courseOfferingId) {
        return mapCourseOfferingToDTO(getCourseOfferingById(courseOfferingId));
    }

    @Override
    public void enrollStudentsToCourse(Long courseOfferingId, List<String> emailList) {
        CourseOffering courseOffering = courseOfferingDAO.getCourseOfferingById(courseOfferingId);
        for(String email : emailList) {
            Enrollment enrollment = new Enrollment();
            enrollment.setCourseOffering(courseOffering);
            Student student = studentDAO.getStudentByEmail(email);
            if(Objects.nonNull(student)) {
                enrollment.setStudent(student);
                enrollmentDAO.createEnrollment(enrollment);
            }
        }
    }

    @Override
    public void uploadExcelFile(ExcelUploadValues excelUploadValues, Long courseOfferingId, BindingResult result) {
        if("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(excelUploadValues.getData().getContentType())) {
            try (InputStream inputStream = excelUploadValues.getData().getInputStream()) {
                Workbook workbook = new XSSFWorkbook(inputStream);
                List<String> emailList = new ArrayList<>();

                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();

                if (rowIterator.hasNext()) {
                    rowIterator.next();
                }

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Cell cell = row.getCell(0);

                    if (cell != null) {
                        String email = cell.getStringCellValue();
                        emailList.add(email.trim());
                    }
                }

                excelUploadValues.setEmailList(emailList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        excelFileValidator.validate(excelUploadValues, result);
        if(!result.hasErrors()) {
            this.enrollStudentsToCourse(courseOfferingId, excelUploadValues.getEmailList());
        }
    }
}
