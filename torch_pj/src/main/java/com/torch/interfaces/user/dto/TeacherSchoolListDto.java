package com.torch.interfaces.user.dto;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSchoolListDto {
    private CodeMessage codeMessage;
    private List<TeacherSchoolDto> tschoolds;
}
