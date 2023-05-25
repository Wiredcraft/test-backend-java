package me.solution.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.solution.model.domain.Following;
import org.springframework.stereotype.Repository;

/**
 * mapper to access follower-following
 *
 * @author davincix
 * @since 2023/5/20 18:19
 */
@Repository
public interface FollowingMapper extends BaseMapper<Following> {
}
