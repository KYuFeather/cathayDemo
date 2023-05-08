package com.cathay.demo;

import com.cathay.demo.beans.dto.CurrDto;
import com.cathay.demo.beans.dto.CurrInfoTransferDto;
import com.cathay.demo.beans.dto.DeleteCurrsDto;
import com.cathay.demo.entity.Currency;
import com.cathay.demo.repository.CurrRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private CurrRepository currRepository;

    private MockHttpSession session;

    @BeforeEach
    public void setupMockMvc() {
        session = new MockHttpSession();
    }

    @Test
    void getCurrentPrice() throws Exception{
        MvcResult mvcResult = (MvcResult) mvc.perform(MockMvcRequestBuilders.get("/cathaybk/getCurrentPrice")
                        .accept(MediaType.ALL)
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        CurrInfoTransferDto currInfoTransferDto = new Gson().fromJson(content, CurrInfoTransferDto.class);
        assertEquals(status, 200);
        assertNotNull(currInfoTransferDto.getCurrInfos());
        assertNotNull(currInfoTransferDto.getUpdTime());
    }

    @Test
    void getCurrency() throws Exception{
        MvcResult mvcResult = (MvcResult) mvc.perform(MockMvcRequestBuilders.get("/cathaybk/getCurrency")
                        .accept(MediaType.ALL)
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Type listType = new TypeToken<ArrayList<CurrDto>>(){}.getType();
        List<CurrDto> currsDtoList = new Gson().fromJson(content, listType);
        assertEquals(status, 200);
        assertNotNull(currsDtoList);
        assertNotEquals(currsDtoList.size(), 0);
    }

    @Test
    void addCurrency() throws Exception{
        CurrDto currDto01 = new CurrDto();
        currDto01.setCurrEng("JPY");
        currDto01.setCurrChn("日圓");
        CurrDto currDto02 = new CurrDto();
        currDto02.setCurrEng("TWD");
        currDto02.setCurrChn("新臺幣");
        List<CurrDto> currs = new ArrayList<>();
        currs.add(currDto01);
        currs.add(currDto02);
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = (MvcResult) mvc.perform(MockMvcRequestBuilders.post("/cathaybk/addCurrency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currs))
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        List<Currency> currInfos = currRepository.findAll();
        List<String> currencyList = currInfos.stream().map(Currency::getCurrEng).collect(Collectors.toList());
        assertTrue(currencyList.contains("JPY") && currencyList.contains("TWD"));
        System.out.println(currencyList.toString());
    }

    @Test
    void deleteCurrency() throws Exception{
        DeleteCurrsDto deleteCurrsDto = new DeleteCurrsDto();
        List<String> currs = new ArrayList<>();
        deleteCurrsDto.setCurrs(currs);
        currs.add("USD");
        currs.add("EUR");
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = (MvcResult) mvc.perform(MockMvcRequestBuilders.post("/cathaybk/deleteCurrency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteCurrsDto))
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        List<Currency> currInfos = currRepository.findAll();
        List<String> currencyList = currInfos.stream().map(Currency::getCurrEng).collect(Collectors.toList());
        assertTrue(currencyList.contains("GBP"));
        System.out.println(currencyList.toString());
    }

    @Test
    void updateCurrency() throws Exception{
        CurrDto currDto01 = new CurrDto();
        currDto01.setCurrEng("JPY");
        currDto01.setCurrChn("日元");
        CurrDto currDto02 = new CurrDto();
        currDto02.setCurrEng("TWD");
        currDto02.setCurrChn("新台幣");
        List<CurrDto> currs = new ArrayList<>();
        currs.add(currDto01);
        currs.add(currDto02);
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = (MvcResult) mvc.perform(MockMvcRequestBuilders.post("/cathaybk/updateCurrency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currs))
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Type listType = new TypeToken<ArrayList<CurrDto>>(){}.getType();
        List<CurrDto> currsUpd = new Gson().fromJson(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), listType);
        List<String> currencyChnList = currsUpd.stream().map(CurrDto::getCurrChn).collect(Collectors.toList());
        assertTrue(currencyChnList.contains("日元") && currencyChnList.contains("新台幣"));
    }
}
