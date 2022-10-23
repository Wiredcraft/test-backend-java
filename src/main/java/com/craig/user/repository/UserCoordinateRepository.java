package com.craig.user.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.craig.user.entity.UserCoordinate;
import com.craig.user.entity.dto.NearbyUserDto;
import com.craig.user.mapper.UserCoordinateMapper;

@Repository
public class UserCoordinateRepository extends ServiceImpl<UserCoordinateMapper, UserCoordinate> {
    
    public List<NearbyUserDto> getNearbyUsers(Long userId, int topN) {
        return this.baseMapper.getNearbyUsers(userId, topN);
    }

    public void addCoord(Long userId, BigDecimal lng, BigDecimal lat) {
        UserCoordinate coordinate = new UserCoordinate();
        coordinate.setUserId(userId);
        coordinate.setLastCoord(getPointString(lng, lat));
        this.save(coordinate);
    }

    public void addCoordOrUpdate(Long userId, BigDecimal lng, BigDecimal lat) {
        Wrapper<UserCoordinate> query = new QueryWrapper<UserCoordinate>().lambda().eq(UserCoordinate::getUserId,
                userId);
        UserCoordinate existCoord = this.getOne(query);
        if (existCoord == null) {
            addCoord(userId, lng, lat);
            return;
        }

        existCoord.setLastCoord(getPointString(lng, lat));
        existCoord.setLogTime(new Date());
        this.updateById(existCoord);
    }
    
    private String getPointString(BigDecimal lng, BigDecimal lat) {
        return "POINT(" + lng + " " + lat + ")";
    }
}
