/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.student;

import com.torch.domain.model.school.School;
import com.torch.domain.model.student.Student;
import com.torch.interfaces.school.AddSchoolCommand;
import com.torch.interfaces.school.UpdateSchoolCommand;
import com.torch.interfaces.student.AddStudentCommand;
import com.torch.interfaces.student.StudentDetail;
import com.torch.interfaces.student.StudentDetailDto;
import com.torch.interfaces.student.StudentSearchDto;
import com.torch.interfaces.student.UpdateStudentCommand;
import java.util.List;

/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
public interface StudentService {

  Student addStudent(AddStudentCommand command);

  Student updateStudent(UpdateStudentCommand command);

  StudentDetail getStudentDetail(Long id);

  List<StudentDetailDto> getAllStudents(Integer pageSize, Integer currentPage, String province,
      String city);

  List<StudentDetailDto> filterStudents(Integer pageSize, Integer currentPage,
      StudentSearchDto dto);

  void deleteStudent(Long id);

   void exportStudent(Long id, String email) throws Exception;

}