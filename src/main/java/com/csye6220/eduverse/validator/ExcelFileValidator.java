package com.csye6220.eduverse.validator;

import com.csye6220.eduverse.dao.EnrollmentDAO;
import com.csye6220.eduverse.dao.StudentDAO;
import com.csye6220.eduverse.pojo.ExcelUploadValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class ExcelFileValidator implements Validator {

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    EnrollmentDAO enrollmentDAO;
    @Override
    public boolean supports(Class<?> clazz) {
        return ExcelUploadValues.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExcelUploadValues excelUploadValues = (ExcelUploadValues) target;
        validateContentType(excelUploadValues, errors);
        if(!errors.hasErrors()) {
            validateMoreThanOneColumn(excelUploadValues, errors);
            validateStudentExists(excelUploadValues, errors);
            validateStudentAlreadyEnrolled(excelUploadValues, errors);
        }
    }

    private void validateMoreThanOneColumn(ExcelUploadValues excelUploadValues, Errors errors) {
        if(excelUploadValues.isMoreThanOneColumn() && !errors.hasErrors()) {
            errors.rejectValue("data", "Invalid file","The excel file uploaded should contain only one Email Column!");
        }
    }

    private void validateStudentAlreadyEnrolled(ExcelUploadValues excelUploadValues, Errors errors) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Students with Email IDs: ");
        boolean isEnrolled = false;
        for(String str : excelUploadValues.getEmailList()) {
            if(enrollmentDAO.checkCurrentCourseEnrollmentByEmail(excelUploadValues.getCourseOfferingId(), str)) {
                isEnrolled = true;
                stringBuilder.append(str).append(", ");
            }
        }
        stringBuilder.append("are already enrolled in this course!");
        if(isEnrolled) {
            errors.rejectValue("data", "Invalid file", stringBuilder.toString());
        }
    }

    private void validateStudentExists(ExcelUploadValues excelUploadValues, Errors errors) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Students with Email IDs: ");
        boolean isNull = false;
        for(String str : excelUploadValues.getEmailList()) {
            if(Objects.isNull(studentDAO.getStudentByEmail(str))) {
                isNull = true;
                stringBuilder.append(str).append(", ");
            }
        }
        stringBuilder.append("do not exist, please remove them and try again!");
        if(isNull) {
            errors.rejectValue("data", "Invalid file", stringBuilder.toString());
        }
    }

    private void validateContentType(ExcelUploadValues excelUploadValues, Errors errors) {
        if(!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(excelUploadValues.getData().getContentType())) {
            errors.rejectValue("data", "Invalid file", "Invalid file format, upload only .xlsx file");
        }
    }
}
