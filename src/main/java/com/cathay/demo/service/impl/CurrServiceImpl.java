package com.cathay.demo.service.impl;

import com.cathay.demo.assembler.CurrAssembler;
import com.cathay.demo.beans.dto.CurrDto;
import com.cathay.demo.beans.dto.CurrInfoTransferDto;
import com.cathay.demo.beans.dto.exRate.ExRateResponse;
import com.cathay.demo.entity.Currency;
import com.cathay.demo.repository.CurrRepository;
import com.cathay.demo.service.CurrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrServiceImpl implements CurrService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrRepository currRepository;

    private CurrAssembler currAssembler = new CurrAssembler();

    @Override
    public CurrInfoTransferDto getCurrInfoTransfer() throws Exception {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        ResponseEntity<ExRateResponse> response = restTemplate.getForEntity(url, ExRateResponse.class);
        Collection<String> bpi = response.getBody().getBpi().keySet();
        List<Currency> currInfos = currRepository.findByCurrEngIn(bpi);
        return currAssembler.toCurrInfoTransferDto(response.getBody(), currInfos);
    }

    @Override
    public List<CurrDto> getCurrency() throws Exception {
        List<Currency> currInfos = currRepository.findAll();
        return currAssembler.toCurrsDto(currInfos);
    }

    @Override
    public void addCurrency(List<CurrDto> currsDto) throws Exception {
        currRepository.saveAll(currAssembler.toCurrency(currsDto));
    }

    @Override
    public void deleteCurrency(List<String> currs) throws Exception {
        currRepository.deleteByCurrEngIn(currs);
    }

    @Override
    public List<CurrDto> updateCurrency(List<CurrDto> currsDto) throws Exception {
        List<Currency> currencies = currAssembler.toCurrency(currsDto);
        currRepository.saveAll(currencies);
        List<String> currEngs = currencies.stream().map(Currency::getCurrEng).collect(Collectors.toList());
        return currAssembler.toCurrsDto(currRepository.findByCurrEngIn(currEngs));
    }


}
