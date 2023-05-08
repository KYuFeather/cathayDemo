package com.cathay.demo.assembler;


import com.cathay.demo.beans.dto.CurrDto;
import com.cathay.demo.beans.dto.CurrInfoDto;
import com.cathay.demo.beans.dto.CurrInfoTransferDto;
import com.cathay.demo.beans.dto.exRate.CurrInfo;
import com.cathay.demo.beans.dto.exRate.ExRateResponse;
import com.cathay.demo.entity.Currency;

import java.text.SimpleDateFormat;
import java.util.*;


public class CurrAssembler {
    public CurrInfoTransferDto toCurrInfoTransferDto(ExRateResponse exRateResponse, List<Currency> currInfos) {
        CurrInfoTransferDto currInfoTransferDto = new CurrInfoTransferDto();
        // 轉換更新時間
        Date date = new Date(exRateResponse.getTime().getUpdated());
        SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        currInfoTransferDto.setUpdTime(ft.format(date));

        Map<String, Currency> currInfosMap = new HashMap<>();
        for (Currency curr: currInfos) {
            currInfosMap.put(curr.getCurrEng(), curr);
        }

        // Mapping幣別內容
        for (String key: exRateResponse.getBpi().keySet()) {
            if (currInfosMap.containsKey(key)) {
                CurrInfo currInfo = exRateResponse.getBpi().get(key);
                CurrInfoDto currInfoDto = new CurrInfoDto();
                currInfoDto.setCurrEng(currInfo.getCode());
                currInfoDto.setExRate(Double.parseDouble(currInfo.getRate_float()));
                currInfoDto.setCurrChn(currInfosMap.get(currInfo.getCode()).getCurrChn());
                currInfoTransferDto.getCurrInfos().add(currInfoDto);
            }
        }
        return currInfoTransferDto;
    }

    public List<CurrDto> toCurrsDto(List<Currency> currInfos) {
        List<CurrDto> currsDto = new ArrayList<>();
        for (Currency currInfo: currInfos) {
            CurrDto currDto = new CurrDto();
            currDto.setCurrChn(currInfo.getCurrChn());
            currDto.setCurrEng(currInfo.getCurrEng());
            currsDto.add(currDto);
        }
        return currsDto;
    }

    public List<Currency> toCurrency(List<CurrDto> currs) {
        List<Currency> currencies = new ArrayList<>();
        for (CurrDto curr: currs) {
            Currency currency = new Currency();
            currency.setCurrChn(curr.getCurrChn());
            currency.setCurrEng(curr.getCurrEng());
            currencies.add(currency);
        }
        return currencies;
    }

}
