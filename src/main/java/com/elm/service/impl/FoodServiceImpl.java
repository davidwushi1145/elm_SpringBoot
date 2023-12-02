package com.elm.service.impl;

import com.elm.mapper.FoodMapper;
import com.elm.model.bo.Food;
import com.elm.model.vo.FoodVo;
import com.elm.service.FoodService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public List<FoodVo> listFoodByBusinessId(Integer businessId) {
        try {
            List<Food> foodList = foodMapper.listFoodByBusinessId(businessId);
            return getFoodVo(foodList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public FoodVo getFoodVo(Food food) {
        if (food == null) {
            return null;
        }
        FoodVo foodVo = new FoodVo();
        BeanUtils.copyProperties(food, foodVo);
        return foodVo;
    }


    public List<FoodVo> getFoodVo(List<Food> foodList) {
        if (CollectionUtils.isEmpty(foodList)) {
            return new ArrayList<>();
        }
        return foodList.stream().map(this::getFoodVo).collect(Collectors.toList());
    }
}
