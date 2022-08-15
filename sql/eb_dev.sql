/*
Navicat MySQL Data Transfer

Source Server         : test000
Source Server Version : 50733
Source Host           : localhost:3306
Source Database       : eb_dev

Target Server Type    : MYSQL
Target Server Version : 50733
File Encoding         : 65001

Date: 2022-08-15 16:07:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_address`
-- ----------------------------
DROP TABLE IF EXISTS `t_address`;
CREATE TABLE `t_address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `address_info` varchar(255) DEFAULT NULL,
  `consignee_name` varchar(255) DEFAULT NULL COMMENT '收货人姓名',
  `phone_num` varchar(255) DEFAULT NULL,
  `is_default` int(11) DEFAULT '0',
  PRIMARY KEY (`address_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `t_address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_address
-- ----------------------------
INSERT INTO `t_address` VALUES ('19', '7', '华联', 'ui Y', '15975230881', '0');
INSERT INTO `t_address` VALUES ('20', '7', '华联', 'name', '15975230111', '1');

-- ----------------------------
-- Table structure for `t_adminuser`
-- ----------------------------
DROP TABLE IF EXISTS `t_adminuser`;
CREATE TABLE `t_adminuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `is_super` int(11) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_adminuser
-- ----------------------------
INSERT INTO `t_adminuser` VALUES ('1', 'admin', '123', '1', '1', '10000');
INSERT INTO `t_adminuser` VALUES ('2', 'admin1', '123', '0', '0', '10000');
INSERT INTO `t_adminuser` VALUES ('3', 'admin2', '123', '0', '1', '8002');
INSERT INTO `t_adminuser` VALUES ('4', 'xr', '123', '1', '1', '200000');
INSERT INTO `t_adminuser` VALUES ('5', 'zw', '123', '1', '1', '200000');
INSERT INTO `t_adminuser` VALUES ('8', 'admin3', '123', '1', '0', '11000');

-- ----------------------------
-- Table structure for `t_cart`
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart` (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL,
  `nums` int(11) DEFAULT '0' COMMENT '商品数量',
  PRIMARY KEY (`cart_id`),
  KEY `user_cast_uid` (`user_id`),
  KEY `goods_cast_gid` (`goods_id`),
  CONSTRAINT `goods_cast_gid` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`id`),
  CONSTRAINT `user_cast_uid` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cart
-- ----------------------------

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES ('39', '65', '35', '好吃', '2022-07-11 17:11:17');

-- ----------------------------
-- Table structure for `t_focus`
-- ----------------------------
DROP TABLE IF EXISTS `t_focus`;
CREATE TABLE `t_focus` (
  `focus_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `focus_time` datetime DEFAULT NULL,
  PRIMARY KEY (`focus_id`),
  KEY `gid` (`goods_id`),
  KEY `uid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_focus
-- ----------------------------

-- ----------------------------
-- Table structure for `t_goods`
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `goods_or_price` double DEFAULT NULL,
  `goods_ru_price` double DEFAULT NULL,
  `goods_store` int(11) DEFAULT NULL,
  `goods_picture` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_recommend` tinyint(2) DEFAULT NULL,
  `is_advertisement` tinyint(2) DEFAULT NULL,
  `goods_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `typeid` (`goods_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('26', '巧克力布朗尼', '100', '85', '1000', '201910274135059473.jpg', '0', '1', '14');
INSERT INTO `t_goods` VALUES ('27', '热带水果蛋糕', '340', '310', '122', '201910274135126352.jpg', '0', '1', '14');
INSERT INTO `t_goods` VALUES ('32', '草莓冰激凌', '120', '90', '38', '201910280135330014.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('33', '草莓松饼', '150', '100', '6', '201910280135503341.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('34', '鸳鸯马卡龙', '150', '150', '756', '201910280135404619.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('35', '奶盖香菜拿铁', '100', '85', '800', '201910280135559856.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('36', '雪顶甜甜圈酸奶', '100', '85', '588', '雪顶甜甜圈酸奶.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('37', '爆米花可可冰激凌球', '100', '85', '800', '爆米花可可冰激凌球.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('38', '彩虹冰激凌', '100', '85', '778', '彩虹冰激凌.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('39', '草莓香草双拼巧克力', '100', '86', '780', '草莓香草双拼巧克力.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('40', '脆皮雪糕', '100', '85', '740', '脆皮雪糕.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('41', '瓜子仁冰淇淋球', '100', '85', '800', '瓜子仁冰淇淋球.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('42', '坚果巧克力冰激凌', '100', '85', '799', '坚果巧克力冰激凌.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('43', '老友记招牌分享冰激凌', '100', '85', '799', '老友记招牌分享冰激凌.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('44', '桑葚冰激凌球', '100', '87', '800', '桑葚冰激凌球.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('45', '石榴巧克力冰激凌', '100', '85', '800', '石榴巧克力冰激凌.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('46', '树莓冰激凌', '100', '85', '800', '树莓冰激凌.jpg', '1', '0', '1');
INSERT INTO `t_goods` VALUES ('47', '可可马卡龙', '100', '85', '777', '可可马卡龙.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('48', '马卡龙拼盘', '100', '85', '800', '马卡龙拼盘.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('49', '抹茶马卡龙', '100', '85', '600', '抹茶马卡龙.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('50', '柠檬马卡龙', '100', '85', '700', '柠檬马卡龙.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('51', '巧克力豆曲奇', '100', '85', '700', '巧克力豆曲奇.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('52', '圣诞曲奇', '100', '85', '700', '圣诞曲奇.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('53', '糖果圣诞树曲奇', '100', '85', '675', '糖果圣诞树曲奇.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('54', '鲜花饼干', '100', '85', '700', '鲜花饼干.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('55', '杏仁马卡龙', '100', '85', '700', '杏仁马卡龙.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('56', '椰丝马卡龙', '100', '85', '700', '椰丝马卡龙.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('57', '原味马卡龙', '100', '85', '700', '原味马卡龙.jpg', '1', '0', '3');
INSERT INTO `t_goods` VALUES ('58', '半裸水果蛋糕', '100', '85', '700', '半裸水果蛋糕.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('59', '草莓慕斯', '100', '85', '700', '草莓慕斯.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('60', '哥特风蛋糕', '100', '85', '700', '哥特风蛋糕.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('61', '红丝绒蛋糕', '100', '85', '700', '红丝绒蛋糕.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('62', '坚果可可蛋糕', '100', '85', '700', '坚果可可蛋糕.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('63', '芒果千层', '100', '88', '888', '芒果千层.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('64', '奶油巧克力布朗尼蛋糕', '100', '88', '888', '奶油巧克力布朗尼蛋糕.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('65', '巧克力千层', '100', '88', '888', '巧克力千层.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('66', '轻芝士蛋糕', '100', '88', '888', '轻芝士蛋糕.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('67', '小熊翻糖蛋糕', '100', '99', '888', '小熊翻糖蛋糕.jpg', '1', '0', '14');
INSERT INTO `t_goods` VALUES ('68', '草莓果酱三明治', '111', '99', '999', '草莓果酱三明治.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('69', '草莓吐司', '111', '99', '997', '草莓吐司.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('70', '法棍', '111', '99', '975', '法棍.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('71', '果酱华夫饼', '111', '99', '999', '果酱华夫饼.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('72', '海鲜披萨', '111', '99', '999', '海鲜披萨.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('73', '鸡蛋牛油吐司', '111', '99', '999', '鸡蛋牛油吐司.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('74', '牛角包', '111', '99', '999', '牛角包.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('75', '牛油果吐司', '111', '99', '999', '牛油果吐司.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('76', '巧克力甜甜圈', '111', '99', '999', '巧克力甜甜圈.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('77', '水果甜甜圈', '111', '99', '999', '水果甜甜圈.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('78', '鲜花吐司', '111', '99', '999', '鲜花吐司.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('79', '香蕉薄脆', '111', '111', '999', '香蕉薄脆.jpg', '1', '0', '16');
INSERT INTO `t_goods` VALUES ('80', '草莓挞', '222', '111', '999', '草莓挞.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('81', '蓝莓挞', '222', '111', '977', '蓝莓挞.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('82', '莓莓拼盘挞', '222', '111', '997', '莓莓拼盘挞.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('83', '莓莓挞', '222', '111', '999', '莓莓挞.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('84', '南瓜挞', '222', '111', '999', '南瓜挞.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('85', '巧克力松饼', '222', '111', '999', '巧克力松饼.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('86', '山荆子挞', '222', '111', '999', '山荆子挞.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('87', '香蕉抹茶松饼', '222', '111', '997', '香蕉抹茶松饼.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('88', '香蕉松饼', '222', '111', '999', '香蕉松饼.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('89', '樱桃奶油挞', '222', '99', '998', '樱桃奶油挞.jpg', '1', '0', '15');
INSERT INTO `t_goods` VALUES ('90', '白兰地', '212', '99', '999', '白兰地.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('91', '草莓气泡酒', '222', '99', '999', '草莓气泡酒.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('92', '黑咖啡', '222', '99', '999', '黑咖啡.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('93', '花酒', '222', '99', '999', '花酒.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('94', '卡布奇诺', '222', '99', '999', '卡布奇诺.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('95', '可可冰激凌', '222', '99', '999', '可可冰激凌.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('96', '蓝莓奶昔', '222', '99', '999', '蓝莓奶昔.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('97', '热可可', '221', '99', '999', '热可可.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('98', '三人套餐', '221', '99', '999', '三人套餐.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('99', '蔬菜汁', '221', '99', '998', '蔬菜汁.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('100', '水果气泡水', '221', '111', '999', '水果气泡水.jpg', '1', '0', '17');
INSERT INTO `t_goods` VALUES ('101', '咖啡', '80', '70', '200', '1641948612750.jpg', '0', '0', '17');

-- ----------------------------
-- Table structure for `t_goodstype`
-- ----------------------------
DROP TABLE IF EXISTS `t_goodstype`;
CREATE TABLE `t_goodstype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_goodstype
-- ----------------------------
INSERT INTO `t_goodstype` VALUES ('1', '冰激凌');
INSERT INTO `t_goodstype` VALUES ('3', '法式饼干');
INSERT INTO `t_goodstype` VALUES ('14', '蛋糕');
INSERT INTO `t_goodstype` VALUES ('15', '水果挞&松饼');
INSERT INTO `t_goodstype` VALUES ('16', '其他类');
INSERT INTO `t_goodstype` VALUES ('17', '饮品');
INSERT INTO `t_goodstype` VALUES ('19', 'test');
INSERT INTO `t_goodstype` VALUES ('20', 'test1');

-- ----------------------------
-- Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `order_name` varchar(255) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `is_comment` int(11) DEFAULT NULL,
  `receipt_info` varchar(255) DEFAULT NULL COMMENT '收货信息',
  PRIMARY KEY (`order_id`),
  KEY `order_user_uid` (`user_id`),
  CONSTRAINT `order_user_uid` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('64', '7', '奶盖香菜拿铁,樱桃奶油挞,', '2253', '1', null, '2022-07-11 16:31:02', '2022-07-11 16:31:06', '0', '2', 'ui Y  15975230881 待定');
INSERT INTO `t_order` VALUES ('65', '7', '奶盖香菜拿铁,', '170', '1', null, '2022-07-11 17:10:42', '2022-07-11 17:10:45', '0', '1', 'ui Y  15975230881 华联');
INSERT INTO `t_order` VALUES ('66', '7', '草莓冰激凌,', '180', '0', null, '2022-08-11 17:47:13', '2022-08-11 17:47:13', '0', '0', '华联 name 15975230111');
INSERT INTO `t_order` VALUES ('67', '7', '草莓松饼,', '200', '0', null, '2022-08-11 22:47:09', '2022-08-11 22:47:09', '0', '0', '华联 name 15975230111');
INSERT INTO `t_order` VALUES ('68', '7', '彩虹冰激凌,鸳鸯马卡龙,', '470', '1', null, '2022-08-12 08:58:16', '2022-08-12 08:58:17', '0', '0', '华联 name 15975230111');

-- ----------------------------
-- Table structure for `t_orderdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_orderdetail`;
CREATE TABLE `t_orderdetail` (
  `orderDetail_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL,
  `nums` int(11) DEFAULT NULL COMMENT '购买数量',
  PRIMARY KEY (`orderDetail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_orderdetail
-- ----------------------------
INSERT INTO `t_orderdetail` VALUES ('68', '64', '89', '2');
INSERT INTO `t_orderdetail` VALUES ('69', '64', '35', '3');
INSERT INTO `t_orderdetail` VALUES ('70', '65', '35', '2');
INSERT INTO `t_orderdetail` VALUES ('71', '66', '32', '2');
INSERT INTO `t_orderdetail` VALUES ('72', '67', '33', '2');
INSERT INTO `t_orderdetail` VALUES ('73', '68', '38', '2');
INSERT INTO `t_orderdetail` VALUES ('74', '68', '34', '2');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_email` varchar(255) NOT NULL DEFAULT '' COMMENT '登录邮箱',
  `password` varchar(255) NOT NULL COMMENT '加密密码',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改人',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL COMMENT '0表示已被删除，1表示未被删除',
  `salt` varchar(36) DEFAULT NULL COMMENT '盐',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('7', '493838759@qq.com', 'ad8b98bcf7e7786fe73e13b3237614de', null, null, null, '2022-01-02 00:46:58', null, '1', 'de8d7601-942f-417e-a95e-4c7aa069dc1d');
INSERT INTO `t_user` VALUES ('8', '1069705000@qq.com', 'a8fce4786bfddc3c1cbe4a18e75d639b', null, null, null, '2022-01-05 08:21:15', null, '1', 'fb1002eb-2720-4817-9488-c68d96541005');
INSERT INTO `t_user` VALUES ('11', '3061014433@qq.com', '0d80afb10397280c393d7a65773fd9d1', null, null, null, '2022-01-12 09:06:31', null, '1', '78000226-346e-41b8-bc5e-661eead47413');
