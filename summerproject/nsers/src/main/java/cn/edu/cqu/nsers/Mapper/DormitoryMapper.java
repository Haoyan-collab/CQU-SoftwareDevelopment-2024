package cn.edu.cqu.nsers.Mapper;

import cn.edu.cqu.nsers.pojo.Dormitory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DormitoryMapper extends BaseMapper<Dormitory> {
    @Select("SELECT SUM(dormitory_cnt) FROM dormitory WHERE dormitory_garden = #{dormitory_garden}")
    Integer getSumOfGarden(@Param("dormitory_garden") String dormitory_garden);
}
