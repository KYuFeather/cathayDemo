package com.cathay.demo.beans.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CurrInfoTransferDto {

    public CurrInfoTransferDto() {
        currInfos = new ArrayList<CurrInfoDto>();
    }

    private String updTime;

    private List<CurrInfoDto> currInfos;
}
