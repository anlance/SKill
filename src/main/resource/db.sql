CREATE TABLE user(
	id  BIGINT(20) NOT NULL COMMENT '用户id，收集号码',
	nickname VARCHAR(255) NOT NULL,
	PASSWORD VARCHAR(32) DEFAULT NULL COMMENT 'md5(md5(pass明文+固定salt)+salt)',
	salt VARCHAR(10) DEFAULT NULL,
	head VARCHAR(126) DEFAULT NULL COMMENT '头像，云存储得id',
	register_date DATETIME DEFAULT NULL COMMENT '注册时间',
	last_login_date DATETIME DEFAULT NULL COMMENT '上次登录时间',
	login_count INT(11) DEFAULT '0' COMMENT '登录次数',
	PRIMARY KEY(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4

CREATE TABLE goods(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
	goods_name VARCHAR(16) DEFAULT NULL COMMENT '商品名称',
	goods_title VARCHAR(64) DEFAULT NULL COMMENT '商品标题',
	goods_img VARCHAR(64) DEFAULT NULL COMMENT '商品图片',
	goods_detail LONGTEXT COMMENT '商品的详情介绍',
	goods_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '商品单价',
	goods_stock INT(11) DEFAULT 0 COMMENT '商品库存，-1表示没有限制',
	PRIMARY KEY (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4

INSERT INTO goods VALUES(1, 'iPhoneX', 'Apple iPhoneX(A1865) 64GB 银色 移动联通电信4G手机', '/img/iPhoneX.png', 'Apple iPhoneX(A1865) 64GB银色 移动联通电信4G手机',8765.00, 10000),
(2,'华为Meta9','华为Meta9 4G+32G版 月光银 移动联通电信4G手机 双卡双待','/img/meta9.png','华为Meta9 4G+32G版 月光银 移动联通电信4G手机 双卡双待',3212.00,-1);


CREATE TABLE `skill_goods`(
	`id` BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表',
	`goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品ID',
	`miaosha_price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '秒杀价',
	`stock_count` INT(11) DEFAULT NULL COMMENT '库存数量',
	`start_date` DATETIME DEFAULT NULL COMMENT '秒杀开始时间',
	`end_date` DATETIME DEFAULT NULL COMMENT '秒杀结束时间'
) ENGINE = INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

INSERT INTO `skill_goods` VALUES(1,1,0.01,4,'2017-11-05 15:18:00', '2017-11-13 14:00:18'),(2,2,0.01,9,'2017-11-12 14:00','2017-11-13 14:00:24');

CREATE TABLE `order_info`(
	`id` BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	`user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
	`goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品id',
	`delivery_addr_id` BIGINT(20) DEFAULT NULL COMMENT '收货地址ID',
	`goods_name` VARCHAR(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
	`goods_count` INT(11) DEFAULT '0' COMMENT '商品数量',
	`goods_price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '商品单价',
	`order_channel` TINYINT(4) DEFAULT '0' COMMENT '1pc, 2android, 3ios',
	`status` TINYINT(4) DEFAULT '0' COMMENT '订单状态， 0新建未支付， 1已支付， 2已发货， 3已收货， 4已退款， 5已完成，',
	`create_date` DATETIME DEFAULT NULL COMMENT '订单的创建时间',
	`pay_date` DATETIME DEFAULT NULL COMMENT '支付时间'

)ENGINE = INNODB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `skill_order`(
	`id` BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	`user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
	`order_id` BIGINT(20) DEFAULT NULL COMMENT '订单ID',
	`goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品ID'

)ENGINE = INNODB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;