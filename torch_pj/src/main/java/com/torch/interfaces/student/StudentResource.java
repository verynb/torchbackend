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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.torch.application.school.SchoolService;
import com.torch.application.student.StudentService;
import com.torch.application.student.impl.StudentExcelServiceImpl;
import com.torch.domain.model.contribute.QRemittance;
import com.torch.domain.model.contribute.Remittance;
import com.torch.domain.model.contribute.RemittanceRepository;
import com.torch.domain.model.release.CreditRepository;
import com.torch.domain.model.release.Creditcredit;
import com.torch.domain.model.school.QSchool;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ResultDTO;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.contribute.dto.RemittanceDetailDto;
import com.torch.interfaces.school.AddSchoolCommand;
import com.torch.interfaces.school.UpdateSchoolCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
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

    private final CreditRepository creditRepository;

    private final UserRepository userRepository;

    private final RemittanceRepository remittanceRepository;

    private final StudentExcelServiceImpl studentExcelService;

    @Autowired
    public StudentResource(final StudentService studentService,
                           final StudentRepository studentRepository,
                           final CreditRepository creditRepository,
                           final UserRepository userRepository,
                           final RemittanceRepository remittanceRepository,
                           final StudentExcelServiceImpl studentExcelService) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.creditRepository = creditRepository;
        this.userRepository = userRepository;
        this.remittanceRepository = remittanceRepository;
        this.studentExcelService = studentExcelService;
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
    @RoleCheck
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
            @ApiParam(value = "市") @RequestParam(required = false) String city
    ) {
        return StudentListDto.builder()
                .studentLists(studentService.getAllStudents(pageSize, currentPage, province, city))
                .codeMessage(new CodeMessage())
                .build();
    }

    @RoleCheck
    @ApiOperation(value = "根据条件赛选学生列表，条件不传查询全部", notes = "", response = StudentDetailDto.class, httpMethod = "POST")
    @RequestMapping(path = "/students/filter", method = POST)
    public StudentListDto filterStudents(
            @ApiParam(value = "查询条件") @RequestBody StudentSearchDto dto) {
        return StudentListDto.builder()
                .studentLists(studentService.filterStudents(dto.getPageSize(), dto.getCurrentPage(), dto))
                .codeMessage(new CodeMessage())
                .build();
    }

    @RoleCheck
    @ApiOperation(value = "根据学生ID查询受助记录", notes = "", response = CreditRecordListDto.class, httpMethod = "GET")
    @RequestMapping(path = "/students/credits/{studentId}", method = GET)
    public CreditRecordListDto getCredits(@PathVariable("studentId") long studentId) {
        CreditRecordListDto dto = CreditRecordListDto.builder()
                .codeMessage(new CodeMessage())
                .recordDtos(Lists.newArrayList())
                .build();
        List<Creditcredit> creditcredits = creditRepository.findByStudentId(studentId);
        if (CollectionUtils.isEmpty(creditcredits)) {
            return dto;
        }
        Map<Long, Creditcredit> map = Maps.newHashMap();
        creditcredits.forEach(creditcredit -> {
            if (creditcredit == null || creditcredit.getSponsorId() == null) {
                return;
            }
            if (!map.containsKey(creditcredit.getSponsorId())) {
                map.put(creditcredit.getSponsorId(), creditcredit);
            }
        });
        for (Map.Entry<Long, Creditcredit> entry : map.entrySet()) {
            CreditRecordDto recordDto = new CreditRecordDto();
            User user = userRepository.findOne(entry.getKey());
            recordDto.setSponsorName(user == null ? "" : user.getName());
            List<Creditcredit> cdList = creditcredits.stream()
                    .filter(c -> c.getSponsorId().equals(entry.getKey()))
                    .sorted(Comparator.comparing(Creditcredit::getCreditTime))
                    .collect(Collectors.toList());
            List<CreditRecord> recordList = Lists.newArrayList();
            cdList.forEach(cd -> {
                recordList.add(CreditRecord.builder()
                        .creditTime(
                                cd.getCreditTime() == null ? "" : cd.getCreditTime().toString("yyyy-MM-dd"))
                        .money(cd.getMoney())
                        .studentId(cd.getStudentId())
                        .build());
            });
            recordDto.setRecordList(recordList);
            dto.getRecordDtos().add(recordDto);
        }
        return dto;
    }


    @RoleCheck
    @ApiOperation(value = "根据学生ID查询受助,放款记录", notes = "", response = CreditAndRemanRecordDto.class, httpMethod = "GET")
    @RequestMapping(path = "/students/creditAndReman/{studentId}", method = GET)
    public CreditAndRemanRecordDto getCreditAndReman(@PathVariable("studentId") long studentId) {
        CreditAndRemanRecordDto dto = CreditAndRemanRecordDto.builder()
                .codeMessage(new CodeMessage())
                .recordDtos(Lists.newArrayList())
                .build();
        List<Creditcredit> creditcredits = creditRepository.findByStudentId(studentId);
        creditcredits.forEach(credit -> {
            User user = userRepository.findOne(credit.getSponsorId() == null ? 0l : credit.getSponsorId());
            CreditAndRemanRecordListDto dto1 = CreditAndRemanRecordListDto.builder()
                    .isCredit(true)
                    .money(credit.getMoney())
                    .sponsorName(user == null ? "" : user.getName())
                    .studentId(credit.getStudentId())
                    .time(credit.getCreditTime())
                    .build();
            dto.getRecordDtos().add(dto1);
        });
        List<Remittance> remittances = (List<Remittance>) remittanceRepository
                .findAll(QRemittance.remittance.studentId.eq(studentId));
        remittances.forEach(remittance -> {
            User user = userRepository.findOne(remittance.getContributeId() == null ? 0l : remittance.getContributeId());
            CreditAndRemanRecordListDto dto1 = CreditAndRemanRecordListDto.builder()
                    .isCredit(false)
                    .money(remittance.getRemittanceMoney())
                    .sponsorName(user == null ? "" : user.getName())
                    .studentId(remittance.getStudentId())
                    .time(remittance.getRemittanceTime())
                    .build();
            dto.getRecordDtos().add(dto1);
        });
        if (CollectionUtils.isNotEmpty(dto.getRecordDtos())) {
            List<CreditAndRemanRecordListDto> recordDtos = Lists.newArrayList();
            dto.getRecordDtos().sort(Comparator.comparing(CreditAndRemanRecordListDto::getTime).reversed());
            dto.getRecordDtos().forEach(recored -> {
                recored.setFormatedTime(recored.getTime() == null ? "" : recored.getTime().toString("yyyy-MM-dd"));
                recored.setTime(null);
                recordDtos.add(recored);
            });
            dto.setRecordDtos(recordDtos);
        }
        return dto;
    }


    @RoleCheck
    @ApiOperation(value = "导出学生信息", notes = "", httpMethod = "GET")
    @RequestMapping(path = "/students/import", method = GET)
    public ReturnDto imports() {
        try {
            studentExcelService.importStudent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnDto.builder()
            .codeMessage(new CodeMessage())
            .build();
    }

    @RoleCheck
    @ApiOperation(value = "导出学生信息", notes = "", httpMethod = "GET")
    @RequestMapping(path = "/students/export/{id}", method = GET)
    public ReturnDto export(
            @ApiParam(value = "邮件地址") @RequestParam String email, @PathVariable("id") Long id) {
        try {
            studentExcelService.exportStudent(id, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnDto.builder()
                .codeMessage(new CodeMessage())
                .build();
    }

    @RoleCheck
    @ApiOperation(value = "导出学生照片信息", notes = "", httpMethod = "GET")
    @RequestMapping(path = "/studentPhoto/export/{id}", method = GET)
    public ReturnDto exportPhoto(
            @ApiParam(value = "邮件地址") @RequestParam String email, @PathVariable("id") Long id,
            String url) {
        try {
            studentExcelService.exportStudentPhoto(id, email, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnDto.builder()
                .codeMessage(new CodeMessage())
                .build();
    }


    @RoleCheck
    @ApiOperation(value = "导出受助人承诺书", notes = "", httpMethod = "GET")
    @RequestMapping(path = "/undertaking/export/{id}", method = GET)
    public ReturnDto exportUndertaking(
            @ApiParam(value = "邮件地址") @RequestParam String email, @PathVariable("id") Long id) {
        try {
            studentExcelService.exportUndertaking(id, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnDto.builder()
                .codeMessage(new CodeMessage())
                .build();
    }


}
