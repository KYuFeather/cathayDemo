package com.cathay.demo.beans.dto.exRate;

import lombok.Data;

@Data
public class CurrInfo {
    private String code;
    private String symbol;
    private String rate;
    private String description;
    private String rate_float;
}
