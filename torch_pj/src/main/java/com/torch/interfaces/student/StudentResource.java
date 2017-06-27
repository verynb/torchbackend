/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.student;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.torch.application.school.SchoolService;
import com.torch.application.student.StudentService;
import com.torch.domain.model.school.QSchool;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ResultDTO;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.school.AddSchoolCommand;
import com.torch.interfaces.school.UpdateSchoolCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanj 2017-01-12 16:41:05
 * @version 1.0
 */
@RestController
@RequestMapping(path = API_CONTEXT_PATH, produces = APPLICATION_JSON_VALUE)
@Api(value = "StudentResource", description = "学生相关api")
public class StudentResource {

  private final StudentService studentService;

  private final StudentRepository studentRepository;

  @Autowired
  public StudentResource(final StudentService studentService,
      final StudentRepository studentRepository) {
    this.studentService = studentService;
    this.studentRepository = studentRepository;
  }

  @RoleCheck
  @ApiOperation(value = "新增学生", notes = "", response = Student.class, httpMethod = "POST")
  @RequestMapping(path = "/student", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public StudentDto addStudent(@Valid @RequestBody AddStudentCommand command) {
    return StudentDto.builder()
        .student(studentService.addStudent(command))
        .codeMessage(new CodeMessage())
        .build();
  }

  @ApiOperation(value = "更新学生", notes = "", httpMethod = "PUT")
  @RequestMapping(path = "/student", method = PUT)
  @ResponseStatus(HttpStatus.OK)
  public StudentDto updateStudent(@Valid @RequestBody UpdateStudentCommand command) {
    return StudentDto.builder()
        .student(studentService.updateStudent(command))
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "删除学生", notes = "", httpMethod = "DELETE")
  @RequestMapping(path = "/student/{id}", method = DELETE)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto deleteStudent(@PathVariable("id") Long id) {
    studentService.deleteStudent(id);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "获取学生详细", notes = "", response = StudentDetail.class, httpMethod = "GET")
  @RequestMapping(path = "/student/{id}", method = GET)
  public StudentDetail getStudentDetail(@PathVariable("id") Long id) {
    StudentDetail dto = studentService.getStudentDetail(id);
    dto.setCodeMessage(new CodeMessage());
    return dto;
  }

  @RoleCheck
  @ApiOperation(value = "获取学生列表", notes = "", response = StudentDetailDto.class, httpMethod = "GET")
  @RequestMapping(path = "/students", method = GET)
  public StudentListDto getAllStudents(
      @ApiParam(value = "分页条数") @RequestParam(required = false) Integer pageSize,
      @ApiParam(value = "当前页") @RequestParam(required = false) Integer currentPage,
      @ApiParam(value = "省") @RequestParam(required = false) String province,
      @ApiParam(value = "省") @RequestParam(required = false) String city
  ) {
    return StudentListDto.builder()
        .studentLists(studentService.getAllStudents(pageSize, currentPage, province, city))
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "根据条件赛选学生列表，条件不传查询全部", notes = "", response = StudentDetailDto.class, httpMethod = "GET")
  @RequestMapping(path = "/students/filter", method = GET)
  public StudentListDto filterStudents(
      @ApiParam(value = "分页条数") @RequestParam(required = false, defaultValue = "15") Integer pageSize,
      @ApiParam(value = "当前页") @RequestParam(required = false, defaultValue = "0") Integer currentPage,
      @ApiParam(value = "查询条件") @RequestBody StudentSearchDto dto) {
    return StudentListDto.builder()
        .studentLists(studentService.filterStudents(pageSize, currentPage, dto))
        .codeMessage(new CodeMessage())
        .build();
  }
}
