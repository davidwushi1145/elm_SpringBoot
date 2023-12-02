package com.elm.service.impl;

import com.elm.common.ErrorCode;
import com.elm.mapper.PointMapper;
import com.elm.mapper.PointTurnoverMapper;
import com.elm.model.vo.PointTurnoverVo;
import com.elm.service.PointService;
import com.elm.exception.BusinessException;
import com.elm.model.bo.Point;
import com.elm.model.bo.PointTurnover;
import com.elm.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PointServiceImpl implements PointService {
    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private PointTurnoverMapper pointTurnoverMapper;

    /**
     * 检查积分是否过期
     *
     * @param pointTurnoverList
     * @return
     */
    @Override
    public List<PointTurnover> checkDate(List<PointTurnover> pointTurnoverList) {
        String DateNow = DateUtil.getTodayString();
        DateNow = DateUtil.roundUpToDay(DateNow);
        Iterator<PointTurnover> iterator = pointTurnoverList.iterator();
        while (iterator.hasNext()) {
            PointTurnover pointTurnover = iterator.next();
            String createTime = pointTurnover.getCreateTime();
            createTime = DateUtil.roundUpToDay(createTime);

            if (!DateUtil.compareDate(createTime, DateNow)) {
                //如果超过一年，过期了，就更新pointTurnover表
                try {
                    int result = pointTurnoverMapper.updateState(pointTurnover.getId(), pointTurnover.getPointId(), pointTurnover.getUserId(), "C");
                    if (result != 1) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新积分明细状态失败");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                //并且将这个元素从List中删除
                iterator.remove();
            }
        }
        return pointTurnoverList;
    }

    /**
     * 获取state以A开头的PT
     *
     * @param userId
     * @return
     */
    @Override
    public List<PointTurnover> getUsefulPointTurnover(String userId) {
        try {
            Point point = this.getPoint(userId);
            if (point == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，查询用户积分账户失败");
            }
            return pointTurnoverMapper.getUsefulPointTurnover(point.getId(), userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 消费时更新PT，按时间顺序更新
     *
     * @param pointTurnoverList
     * @param amount
     * @return
     */
    @Override
    public List<PointTurnover> usePoint(List<PointTurnover> pointTurnoverList, Integer amount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //使用比较函数，先将pointTurnoverList按照从小到大排序——从早到晚排序
        pointTurnoverList.sort(new Comparator<PointTurnover>() {
            @Override
            public int compare(PointTurnover p1, PointTurnover p2) {
                try {
                    Date d1 = dateFormat.parse(p1.getCreateTime());
                    Date d2 = dateFormat.parse(p2.getCreateTime());
                    return d1.compareTo(d2);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid date format", e);
                }
            }
        });

        try {
            Iterator<PointTurnover> iterator = pointTurnoverList.iterator();
            int remainingAmount = amount;
            while (iterator.hasNext()) {
                PointTurnover pointTurnover = iterator.next();
                int canUsePoint = pointTurnover.getBalance();
                if (canUsePoint > remainingAmount) {
                    //表明可直接满足，不需要继续遍历了,
                    int lastPoint = canUsePoint - remainingAmount;
                    remainingAmount = 0;
                    int result1 = pointTurnoverMapper.updateBalance(pointTurnover.getId(), pointTurnover.getPointId(), pointTurnover.getUserId(), lastPoint);
                    int result2 = pointTurnoverMapper.updateState(pointTurnover.getId(), pointTurnover.getPointId(), pointTurnover.getUserId(), "A2");
                    if (result1 != 1) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新积分Balance失败");
                    }
                    if (result2 != 1) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新积分state失败");
                    }
                    break;
                } else if (canUsePoint == remainingAmount) {
                    remainingAmount = 0;
                    int result1 = pointTurnoverMapper.updateBalance(pointTurnover.getId(), pointTurnover.getPointId(), pointTurnover.getUserId(), 0);
                    int result2 = pointTurnoverMapper.updateState(pointTurnover.getId(), pointTurnover.getPointId(), pointTurnover.getUserId(), "B");
                    if (result1 != 1) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新积分Balance失败");
                    }
                    if (result2 != 1) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新积分state失败");
                    }
                    iterator.remove();
                    break;
                } else {
                    remainingAmount -= canUsePoint;
                    int result1 = pointTurnoverMapper.updateBalance(pointTurnover.getId(), pointTurnover.getPointId(), pointTurnover.getUserId(), 0);
                    int result2 = pointTurnoverMapper.updateState(pointTurnover.getId(), pointTurnover.getPointId(), pointTurnover.getUserId(), "B");
                    if (result1 != 1) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新积分Balance失败");
                    }
                    if (result2 != 1) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新积分state失败");
                    }
                    iterator.remove();
                }
            }
            if (remainingAmount > 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "操作失败，当前积分不够使用");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pointTurnoverList;
    }

    /**
     * 创建新的消费PT记录
     *
     * @param userId
     * @param amount
     * @return
     */
    @Override
    public Integer LogUsePointTurnover(String userId, Integer amount) {
        try {
            Point point = this.getPoint(userId);
            return pointTurnoverMapper.saveUsePointTurnover(point.getId(), userId, "D", amount, DateUtil.getTodayString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PointTurnoverVo> getPointTurnoverVoList(String userId) {
        try {
            Point point = this.getPoint(userId);
            if (point == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，查询用户积分账户失败");
            }
            List<PointTurnover> pointTurnoverList = pointTurnoverMapper.getPointTurnover(point.getId(), userId);
            return getPointTurnoverVo(pointTurnoverList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer savePointTurnover(String userId, Integer amount) {
        try {
            Point point = this.getPoint(userId);
            if (point == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，查询用户积分账户失败");
            }
            return pointTurnoverMapper.savePointTurnover(point.getId(), userId, "A1", amount, DateUtil.getTodayString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Point getPoint(String userId) {
        try {
            return pointMapper.getPoint(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PointTurnoverVo getPointTurnoverVo(PointTurnover pointTurnover) {
        if (pointTurnover == null) {
            return null;
        }
        PointTurnoverVo pointTurnoverVo = new PointTurnoverVo();
        BeanUtils.copyProperties(pointTurnover, pointTurnoverVo);
        return pointTurnoverVo;
    }


    public List<PointTurnoverVo> getPointTurnoverVo(List<PointTurnover> pointTurnoverList) {
        if (CollectionUtils.isEmpty(pointTurnoverList)) {
            return new ArrayList<>();
        }
        return pointTurnoverList.stream().map(this::getPointTurnoverVo).collect(Collectors.toList());
    }
}
