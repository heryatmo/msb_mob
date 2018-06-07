package com.example.heryatmo.msb_mob.response;

import com.example.heryatmo.msb_mob.model.CalonVolunteer;
import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.model.User;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaftarCalonVolunteerResponse {

    private Boolean isSuccess;
    private Integer codeStatus;
    private String message;
    private List<CalonVolunteer> data;
}
