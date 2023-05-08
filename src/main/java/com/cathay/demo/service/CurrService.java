package com.cathay.demo.service;

import com.cathay.demo.beans.dto.CurrDto;
import com.cathay.demo.beans.dto.CurrInfoDto;
import com.cathay.demo.beans.dto.CurrInfoTransferDto;

import java.util.List;

public interface CurrService {
    public CurrInfoTransferDto getCurrInfoTransfer() throws Exception;

    public List<CurrDto> getCurrency() throws Exception;

    public void addCurrency(List<CurrDto> currsDto) throws Exception;

    public void deleteCurrency(List<String> currs) throws Exception;

    public List<CurrDto> updateCurrency(List<CurrDto> currsDto) throws Exception;
}
