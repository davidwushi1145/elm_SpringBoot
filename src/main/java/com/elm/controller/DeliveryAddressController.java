package com.elm.controller;

import com.elm.common.BaseResponse;
import com.elm.common.ErrorCode;
import com.elm.common.ResultUtils;
import com.elm.common.UserSupport;
import com.elm.model.bo.DeliveryAddress;
import com.elm.model.vo.DeliveryAddressVo;
import com.elm.service.DeliveryAddressService;
import com.elm.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController {
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private UserSupport userSupport;

    @GetMapping("/lists")
    public BaseResponse<List<DeliveryAddressVo>> listDeliveryAddressByUserId() {
        String userId = userSupport.getCurrentUserId();
        List<DeliveryAddressVo> deliveryAddressVoList = deliveryAddressService.listDeliveryAddressByUserId(userId);
        if (deliveryAddressVoList != null) {
            return ResultUtils.success(deliveryAddressVoList);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，获取当前用户配送地址列表失败");
        }

    }

    @GetMapping("/{daId}")
    public BaseResponse<DeliveryAddressVo> getDeliveryAddressById(@PathVariable(value = "daId") Integer daId) {
        if (daId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        DeliveryAddressVo deliveryAddressVo = deliveryAddressService.getDeliveryAddressById(daId);
        if (deliveryAddressVo != null) {
            return ResultUtils.success(deliveryAddressVo);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，获取当前用户的某个配送地址失败");
        }
    }

    @PostMapping("/newDA")
    public BaseResponse<Integer> saveDeliveryAddress(@RequestParam("contactName") String contactName,
                                                     @RequestParam("contactSex") Integer contactSex,
                                                     @RequestParam("contactTel") String contactTel,
                                                     @RequestParam("address") String address) {
        String userId = userSupport.getCurrentUserId();
        if (userId == null || contactName == null || contactSex == null || contactTel == null || address == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        if (contactSex != 1 && contactSex != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "性别参数应该为1 or 0");
        }
        if (contactTel.length() != 11) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "联系电话应该为11位");
        }
        if (address.charAt(0) != '0' && contactTel.charAt(0) != '1') {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "联系电话应该以1 或 0开头");
        }
        Integer result = deliveryAddressService.saveDeliveryAddress(userId, contactName, contactSex, contactTel, address);
        if (result.equals(1)) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，新建配送地址失败");
        }
    }

    @PostMapping("/updated-DA")
    public BaseResponse<Integer> updateDeliveryAddress(@RequestParam("daId") Integer daId,
                                                       @RequestParam("contactName") String contactName,
                                                       @RequestParam("contactSex") Integer contactSex,
                                                       @RequestParam("contactTel") String contactTel,
                                                       @RequestParam("address") String address) {
        if (daId == null || contactName == null || contactSex == null || contactTel == null || address == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        if (contactSex != 1 && contactSex != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "性别参数应该为1 or 0");
        }
        if (contactTel.length() != 11) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "联系电话应该为11位");
        }
        if (contactTel.charAt(0) != '0' && contactTel.charAt(0) != '1') {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "联系电话应该以1 或 0开头");
        }
        Integer result = deliveryAddressService.updateDeliveryAddress(daId, contactName, contactSex, contactTel, address);
        if (result.equals(1)) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新配送地址失败");
        }
    }

    @DeleteMapping("/removed-DA")
    public BaseResponse<Integer> removeDeliveryAddress(@RequestParam("daId") Integer daId) {
        if (daId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        Integer result = deliveryAddressService.removeDeliveryAddress(daId);
        if (result.equals(1)) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，移除配送地址失败");
        }
    }
}