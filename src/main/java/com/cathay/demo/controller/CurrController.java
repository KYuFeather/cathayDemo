package com.cathay.demo.controller;

import com.cathay.demo.beans.dto.CurrDto;
import com.cathay.demo.beans.dto.CurrInfoTransferDto;
import com.cathay.demo.beans.dto.DeleteCurrsDto;
import com.cathay.demo.service.CurrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cathaybk")
public class CurrController {

    @Autowired
    private CurrService currService;

    // take currentPrice
    @GetMapping("/getCurrentPrice")
    public CurrInfoTransferDto getCurrentPrice() throws Exception {
        return currService.getCurrInfoTransfer();
    }

    @GetMapping("/getCurrency")
    public List<CurrDto> getCurrency() throws Exception {
        return currService.getCurrency();
    }

    @PostMapping("/addCurrency")
    public void addCurrency(@RequestBody List<CurrDto> currsDto) throws Exception {
        currService.addCurrency(currsDto);
    }

    @PostMapping("/deleteCurrency")
    public void deleteCurrency(@RequestBody DeleteCurrsDto deleteCurrsDto) throws Exception {
        currService.deleteCurrency(deleteCurrsDto.getCurrs());
    }

    @PostMapping("/updateCurrency")
    public @ResponseBody List<CurrDto> updateCurrency(@RequestBody List<CurrDto> currsDto) throws Exception {
        return currService.updateCurrency(currsDto);
    }
}
