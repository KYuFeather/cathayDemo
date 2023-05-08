package com.cathay.demo.beans.dto.exRate;

import lombok.Data;

import java.util.Map;

@Data
public class ExRateResponse {

    private UpdTime time;

    private String disclaimer;

    private String chartName;

    private Map<String, CurrInfo> bpi;
}
