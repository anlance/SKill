package club.anlan.sKill.service.Impl;

import club.anlan.sKill.domain.OrderInfo;
import club.anlan.sKill.domain.SKillOrder;
import club.anlan.sKill.domain.User;
import club.anlan.sKill.redis.SkillKey;
import club.anlan.sKill.service.GoodsService;
import club.anlan.sKill.service.OrderService;
import club.anlan.sKill.service.RedisService;
import club.anlan.sKill.service.SkillService;
import club.anlan.sKill.util.MD5Util;
import club.anlan.sKill.util.UUIDUtil;
import club.anlan.sKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Transactional
    public OrderInfo doSkill(User user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if(success) {
            //order_info maiosha_order
            return orderService.createOrder(user, goods);
        }else {
            return null;
        }
    }

    public long getSkillResult(Long userId, long goodsId) {
        SKillOrder order = orderService.getSkillOrderByUserIdGoodsId(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    public boolean checkPath(User user, long goodsId, String path) {
        System.out.println(user+"-------------------------------"+path);
        if(user == null || path == null) {
            return false;
        }
        String pathOld = redisService.get(SkillKey.getSkillPath, ""+user.getId() + "_"+ goodsId, String.class);
        System.out.println(pathOld);
        return path.equals(pathOld);
    }

    public String createSkillPath(User user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisService.set(SkillKey.getSkillPath, ""+user.getId() + "_"+ goodsId, str);
        return str;
    }

    public BufferedImage createVerifyCode(User user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(SkillKey.getSkillVerifyCode, user.getId()+","+goodsId, rnd);
        //输出图片
        return image;
    }

    @Override
    public boolean checkVerifyCode(User user, long goodsId, int verifyCode) {
        if(user == null || goodsId <=0) {
            return false;
        }
        Integer codeOld = redisService.get(SkillKey.getSkillVerifyCode, user.getId()+","+goodsId, Integer.class);
        if(codeOld == null || codeOld - verifyCode != 0 ) {
            return false;
        }
        redisService.delete(SkillKey.getSkillVerifyCode, user.getId()+","+goodsId);
        return true;
    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};

    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SkillKey.isGoodsOver, ""+goodsId);
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(SkillKey.isGoodsOver, ""+goodsId, true);
    }


}
