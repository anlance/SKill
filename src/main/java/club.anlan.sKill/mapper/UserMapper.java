package club.anlan.sKill.mapper;

import club.anlan.sKill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getById(@Param("id") long id);

    @Insert("insert into user(id,name) values (#{id},#{name})")
    int insert(User user);
}
