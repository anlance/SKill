package club.anlan.sKill.service.Impl;

import club.anlan.sKill.domain.SKillGoods;
import club.anlan.sKill.mapper.GoodsMapper;
import club.anlan.sKill.service.GoodsService;
import club.anlan.sKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<GoodsVo> getGoodsVo() {
        return goodsMapper.getGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        SKillGoods sg = new SKillGoods();
        sg.setGoodsId(goods.getId());
        sg.setStockCount(goods.getStockCount()-1);
        int ret = goodsMapper.reduceStock(sg);
        return ret > 0;
    }
}
