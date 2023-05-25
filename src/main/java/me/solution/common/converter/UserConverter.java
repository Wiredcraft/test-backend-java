package me.solution.common.converter;

import me.solution.model.domain.User;
import me.solution.model.reqresp.FollowResp;
import me.solution.model.reqresp.SignUpReq;
import me.solution.model.reqresp.UserResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
* user model converter
*
* @author davincix
* @since 2023/5/23 01:58
*/
@Mapper(componentModel = "spring")
public interface UserConverter {

    UserResp model2Resp(User user);

    @Mapping(target = "passwd", ignore = true)
    User req2Model(SignUpReq req);

    FollowResp model2FollowResp(User user);
}
