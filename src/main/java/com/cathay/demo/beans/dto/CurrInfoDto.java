package com.cathay.demo.beans.dto;

import lombok.Data;

@Data
public class CurrInfoDto {
    // 幣別
    private String currEng;
    // 幣別中文名稱
    private String currChn;
    // 匯率
    private Double exRate;
}
