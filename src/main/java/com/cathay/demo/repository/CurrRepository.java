package com.cathay.demo.repository;

import com.cathay.demo.beans.dto.CurrDto;
import com.cathay.demo.entity.Currency;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface CurrRepository extends CrudRepository<Currency, Long> {
    List<Currency> findByCurrEngIn(Collection<String> currEngs);
    List<Currency> findAll();

    @Transactional
    void deleteByCurrEngIn(List<String> currEngs);
}
