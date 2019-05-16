package club.anlan.sKill.mapper;

import club.anlan.sKill.domain.SKillGoods;
import club.anlan.sKill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Select("select g.*,sg.skill_price,sg.stock_count,sg.start_date,sg.end_date from skill_goods sg left join goods g on sg.goods_id = g.id")
    List<GoodsVo> getGoodsVo();

    @Select("select g.*,sg.skill_price,sg.stock_count,sg.start_date,sg.end_date from skill_goods sg left join goods g on sg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update skill_goods set stock_count = stock_count-1 where goods_id = #{goodsId} ")
    void reduceStock(SKillGoods goods);
}
