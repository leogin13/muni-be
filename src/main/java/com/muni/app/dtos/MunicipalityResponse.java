package com.muni.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MunicipalityResponse {
    private Long id;
    private String name;
    private String province;
}
