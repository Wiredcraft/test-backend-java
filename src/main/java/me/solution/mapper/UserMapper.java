package me.solution.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.solution.model.domain.User;
import org.springframework.stereotype.Repository;

/**
 * mapper to access user
 *
 * @author davincix
 * @since 2023/5/20 18:19
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
