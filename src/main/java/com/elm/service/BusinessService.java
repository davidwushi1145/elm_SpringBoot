package com.elm.service;

import java.util.List;

import com.elm.model.vo.BusinessVo;

public interface BusinessService {
    public List<BusinessVo> listBusinessByOrderTypeId(Integer orderTypeId);

    public BusinessVo getBusinessById(Integer businessId);

    public List<BusinessVo> listBusinessByBusinessName(String businessName);
}



