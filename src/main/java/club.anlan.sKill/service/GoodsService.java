package club.anlan.sKill.service;

import club.anlan.sKill.vo.GoodsVo;

import java.util.List;

public interface GoodsService {

    List<GoodsVo> getGoodsVo();

    GoodsVo getGoodsVoByGoodsId(long goodsId);

    void reduceStock(GoodsVo goods);
}
