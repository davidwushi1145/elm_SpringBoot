package com.elm.controller;

import com.elm.common.BaseResponse;
import com.elm.common.ErrorCode;
import com.elm.common.ResultUtils;
import com.elm.model.vo.PointTurnoverVo;
import com.elm.service.PointService;
import com.elm.exception.BusinessException;
import com.elm.model.bo.Point;
import com.elm.model.bo.PointTurnover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    /**
     * 获取用户积分表
     *
     * @param userId
     * @return
     */
    @GetMapping("/points/{userId}")
    private BaseResponse<Point> getPoint(@PathVariable(value = "userId") String userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        Point point = pointService.getPoint(userId);
        if (point == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，获取用户积分详情失败");
        } else {
            return ResultUtils.success(point);
        }
    }

    /**
     * 获取用户积分余额——对应业务1
     *
     * @param userId
     * @return
     */
    @GetMapping("/balances/{userId}")
    public BaseResponse<Integer> getPointBalance(@PathVariable(value = "userId") String userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        List<PointTurnover> pointTurnoverList = pointService.getUsefulPointTurnover(userId);
        pointTurnoverList = pointService.checkDate(pointTurnoverList);
        int pointAll = 0;
        for (PointTurnover pointTurnover : pointTurnoverList) {
            pointAll += pointTurnover.getBalance();
        }
        if (pointAll >= 0) {
            return ResultUtils.success(pointAll);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，查询用户积分余额失败");
        }
    }

    /**
     * 获取用户的积分明细
     *
     * @param userId
     * @return
     */
    @GetMapping("/pointTurnoverLists/{userId}")
    public BaseResponse<List<PointTurnoverVo>> getPointTurnoverVo(@PathVariable(value = "userId") String userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        List<PointTurnoverVo> pointTurnoverVoList = pointService.getPointTurnoverVoList(userId);
        if (pointTurnoverVoList != null) {
            return ResultUtils.success(pointTurnoverVoList);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，获取积分使用明细失败");
        }
    }

    /**
     * 获取积分
     *
     * @param userId
     * @return
     */
    @PostMapping("/newPoints")
    public BaseResponse<Integer> savePointTurnover(@RequestParam("userId") String userId, @RequestParam("amount") Integer amount) {
        if (userId == null || amount == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "获得的积分不可以为负数");
        }
        Integer result = pointService.savePointTurnover(userId, amount);
        if (result.equals(1)) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，查询用户积分余额失败");
        }
    }

    @PostMapping("/usedPoints")
    public BaseResponse<Integer> usePoint(@RequestParam("userId") String userId, @RequestParam("amount") Integer amount) {
        if (userId == null || amount == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "使用的积分数不可以为负数");
        }
        //获取积分总数
        List<PointTurnover> pointTurnoverList = pointService.getUsefulPointTurnover(userId);
        pointTurnoverList = pointService.checkDate(pointTurnoverList);
        int pointAll = 0;
        for (PointTurnover pointTurnover : pointTurnoverList) {
            pointAll += pointTurnover.getBalance();
        }

        if (amount > pointAll) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "您所拥有的积分不足以满足您的使用");
        }

        //然后开始使用积分
        List<PointTurnover> pointTurnoverListAfterUse = pointService.usePoint(pointTurnoverList, amount);
        if (pointTurnoverListAfterUse == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，使用用户积分失败失败");
        }
        int result = pointService.LogUsePointTurnover(userId, amount);
        if (result == 1) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，记录用户使用积分明细失败");
        }
    }
}
