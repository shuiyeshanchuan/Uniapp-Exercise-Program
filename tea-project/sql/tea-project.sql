/*
 Navicat Premium Data Transfer

 Source Server         : root@localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : tea-project

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 24/10/2024 21:07:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `uid` bigint(11) NULL DEFAULT NULL COMMENT '关联用户id',
  `pid` bigint(11) NULL DEFAULT NULL COMMENT '关联产品id',
  `number` int(11) NULL DEFAULT NULL COMMENT '产品数量',
  `tprice` double(4, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (61, 7, 1, 1, 8.80);
INSERT INTO `cart` VALUES (62, 7, 2, 1, 16.00);
INSERT INTO `cart` VALUES (63, 7, 3, 1, 20.00);
INSERT INTO `cart` VALUES (64, 7, 4, 1, 10.00);
INSERT INTO `cart` VALUES (65, 1, 1, 5, 8.80);
INSERT INTO `cart` VALUES (66, 1, 5, 5, 14.00);
INSERT INTO `cart` VALUES (67, 1, 8, 5, 17.00);
INSERT INTO `cart` VALUES (68, 1, 2, 5, 16.00);

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `ititle` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片标题',
  `iurl` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径(图片)',
  `itype` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片类型 0：注册页面轮播图  1：产品轮播图图片等等 ',
  `imsg` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述  如：注册页面轮播图',
  `istate` int(2) NULL DEFAULT NULL COMMENT '1:可用    0：禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES (1, '欢迎界面轮播图', 'http://localhost:1000/file/img/w1.png', '1', '欢迎界面轮播图', 3);
INSERT INTO `image` VALUES (2, '欢迎界面轮播图', 'http://localhost:1000/file/img/w2.png', '1', '欢迎界面轮播图', 3);
INSERT INTO `image` VALUES (3, '欢迎界面轮播图', 'http://localhost:1000/file/img/w3.png', '1', '欢迎界面轮播图', 3);
INSERT INTO `image` VALUES (4, '欢迎界面轮播图', 'http://localhost:1000/file/img/w4.png', '1', '欢迎界面轮播图', 3);
INSERT INTO `image` VALUES (5, '欢迎界面轮播图', 'http://localhost:1000/file/img/w5.png', '1', '欢迎界面轮播图', 1);
INSERT INTO `image` VALUES (6, '首页轮播图1', 'http://localhost:1000/file/img/i1.png', '2', '首页轮播图', 1);
INSERT INTO `image` VALUES (7, '首页轮播图', 'http://localhost:1000/file/img/i2.png', '2', '首页轮播图', 1);
INSERT INTO `image` VALUES (8, '首页轮播图', 'http://localhost:1000/file/img/i3.png', '2', '首页轮播图', 2);
INSERT INTO `image` VALUES (9, '首页轮播图', 'http://localhost:1000/file/img/i4.png', '2', '首页轮播图', 2);
INSERT INTO `image` VALUES (10, '产品界面轮播图', 'http://localhost:1000/file/img/pb1.png', '3', '产品界面轮播图', 2);
INSERT INTO `image` VALUES (11, '产品界面轮播图', 'http://localhost:1000/file/img/pb2.png', '3', '产品界面轮播图', 2);
INSERT INTO `image` VALUES (12, '产品界面轮播图', 'http://localhost:1000/file/img/pb3.png', '3', '产品界面轮播图', 2);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户ID' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `pname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `typeid` bigint(20) NULL DEFAULT NULL COMMENT '关联类型表id',
  `tprice` double(4, 2) NULL DEFAULT NULL COMMENT '价格',
  `tintroduce` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品介绍',
  `pimageurl` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径(图片)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '金桔柠檬1杯', 1, 8.80, '金桔配柠檬，清香与清新的碰撞，经典CP，酸甜清爽。\r\n· ⚠非外卖平台，门店不配送，购买后请到店使用', 'http://localhost:1000/file/img/p1.png');
INSERT INTO `product` VALUES (2, '红豆麻薯双拼', 1, 16.00, '红豆麻薯双拼（大杯）1份，包间免费', 'http://localhost:1000/file/img/p2.png');
INSERT INTO `product` VALUES (3, '招牌芋圆奶茶1份', 1, 20.00, '招牌芋圆奶茶', 'http://localhost:1000/file/img/p3.png');
INSERT INTO `product` VALUES (4, '黄金珍珠奶茶1杯', 1, 10.00, '黄金珍珠奶茶1杯', 'http://localhost:1000/file/img/p4.png');
INSERT INTO `product` VALUES (5, '【小春哥同款】书亦烧仙草(大杯)1杯', 1, 14.00, '【小春哥同款】书亦烧仙草(大杯)1杯', 'http://localhost:1000/file/img/p5.png');
INSERT INTO `product` VALUES (6, '葡萄芋圆冻冻1杯', 6, 14.00, '葡萄芋圆冻冻1杯', 'http://localhost:1000/file/img/p6.png');
INSERT INTO `product` VALUES (7, '鸭屎香大橘茶（大杯）1杯', 3, 16.00, '鸭屎香大橘茶（大杯）1杯', 'http://localhost:1000/file/img/p7.png');
INSERT INTO `product` VALUES (8, '黑糖小芋圆奶茶1杯', 1, 17.00, '黑糖小芋圆奶茶1杯', 'http://localhost:1000/file/img/p8.png');
INSERT INTO `product` VALUES (9, '牛魔王黑砖奶茶1杯', 1, 15.00, '牛魔王黑砖奶茶1杯', 'http://localhost:1000/file/img/p9.png');
INSERT INTO `product` VALUES (10, '多肉西瓜冰茶（大杯）1份', 3, 14.00, '多肉西瓜冰茶（大杯）1份', 'http://localhost:1000/file/img/p10.png');
INSERT INTO `product` VALUES (11, '芝士多肉葡萄（大杯）1杯', 3, 19.00, '芝士多肉葡萄（大杯）1杯', 'http://localhost:1000/file/img/p11.png');
INSERT INTO `product` VALUES (12, '草莓摇摇奶昔1杯', 4, 8.00, '草莓摇摇奶昔1杯', 'http://localhost:1000/file/img/p12.png');
INSERT INTO `product` VALUES (13, '黑糖珍珠大圣代1杯', 4, 12.00, '黑糖珍珠大圣代1杯', 'http://localhost:1000/file/img/p13.png');
INSERT INTO `product` VALUES (14, '芝士奶盖绿茶1杯', 2, 22.00, '芝士奶盖绿茶1杯', 'http://localhost:1000/file/img/p14.png');

-- ----------------------------
-- Table structure for productorder
-- ----------------------------
DROP TABLE IF EXISTS `productorder`;
CREATE TABLE `productorder`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '产品、订单中间表',
  `oid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联订单流水号',
  `pid` bigint(11) NULL DEFAULT NULL COMMENT '关联产品id',
  `number` int(11) NULL DEFAULT NULL COMMENT '购买产品数量',
  `price` double(8, 2) NULL DEFAULT NULL COMMENT '产品单价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of productorder
-- ----------------------------
INSERT INTO `productorder` VALUES (5, 'cd518471fdf04f999c5cc81827dbadae', 1, 1, 8.80);
INSERT INTO `productorder` VALUES (6, '4ea155a0d53f41d5b361d4ec2436661d', 1, 1, 8.80);
INSERT INTO `productorder` VALUES (7, '4ea155a0d53f41d5b361d4ec2436661d', 2, 3, 16.00);
INSERT INTO `productorder` VALUES (8, '8623bc8a4c9a4a8394301ead12a26bad', 14, 1, 22.00);
INSERT INTO `productorder` VALUES (9, '8623bc8a4c9a4a8394301ead12a26bad', 1, 1, 8.80);
INSERT INTO `productorder` VALUES (10, '8623bc8a4c9a4a8394301ead12a26bad', 2, 1, 16.00);
INSERT INTO `productorder` VALUES (11, '8623bc8a4c9a4a8394301ead12a26bad', 3, 1, 20.00);
INSERT INTO `productorder` VALUES (12, 'a15dc58a4c734be7997f5dc24a1fcc12', 1, 1, 8.80);
INSERT INTO `productorder` VALUES (13, '26c20dcede6f440485ef9f666f08c081', 1, 1, 8.80);
INSERT INTO `productorder` VALUES (14, 'a503d124866d40e89c9c441efe5ff8d9', 3, 1, 20.00);
INSERT INTO `productorder` VALUES (15, '94dbd90e21d943a79bc3fa0a5318555b', 7, 1, 16.00);
INSERT INTO `productorder` VALUES (16, '94dbd90e21d943a79bc3fa0a5318555b', 10, 1, 14.00);
INSERT INTO `productorder` VALUES (17, '9c9090d4cab6417ab6954154e17b9a79', 12, 2, 8.00);
INSERT INTO `productorder` VALUES (18, '9c9090d4cab6417ab6954154e17b9a79', 13, 1, 12.00);
INSERT INTO `productorder` VALUES (19, '35d4903be96a4c04b00cabb6ae853b05', 1, 5, 8.80);
INSERT INTO `productorder` VALUES (20, '35d4903be96a4c04b00cabb6ae853b05', 5, 5, 14.00);
INSERT INTO `productorder` VALUES (21, '35d4903be96a4c04b00cabb6ae853b05', 8, 5, 17.00);
INSERT INTO `productorder` VALUES (22, '35d4903be96a4c04b00cabb6ae853b05', 2, 5, 16.00);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色名称',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色描述',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '超级管理员', 'ADMIN');
INSERT INTO `sys_role` VALUES (3, '普通用户', '普通用户', 'USER');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户密码',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '个人简介',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `salt` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '加盐',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '开始时间',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `state` int(11) NULL DEFAULT 1 COMMENT '状态（正常/禁用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'ba1a907429fd7580268ff64098d271f4', '关注b站：程序员瑞哥', '12345@gmail.com', '15587878787', '关注b站：程序员瑞哥', 'http://localhost:1000/file/exam/0908c950-6c36-49eb-9739-5e0dda5565a3.jpg', 'ADMIN', '85FBBC8C72165C58', '2023-06-19 11:24:56', '男', 1);
INSERT INTO `sys_user` VALUES (2, 'user', 'd12f572c1af6293b9a5eb615bd1388e3', '关注b站：程序员瑞哥', '231@qq.com', '15566778899', '关注b站：程序员瑞哥', 'http://localhost:1000/file/exam/c84f3335-5d92-42f6-9532-dee2af2a5b69.png', 'USER', '985B8D7143218F39', '2023-06-19 19:24:56', '男', 1);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `typename` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型名称',
  `timageurl` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型图片路径',
  `tstate` int(2) NULL DEFAULT NULL COMMENT '1:可用    0：禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '醇香奶茶', 'http://localhost:1000/file/img/t1.png', 1);
INSERT INTO `type` VALUES (2, '鲜牛乳', 'http://localhost:1000/file/img/t2.png', 1);
INSERT INTO `type` VALUES (3, '四级鲜果茶', 'http://localhost:1000/file/img/t3.png', 1);
INSERT INTO `type` VALUES (4, '乳酸菌', 'http://localhost:1000/file/img/t4.png', 1);
INSERT INTO `type` VALUES (5, '加料2', 'http://localhost:1000/file/img/t5.png', 0);
INSERT INTO `type` VALUES (6, '花香轻乳茶', 'http://localhost:1000/file/img/t6.png', 1);

-- ----------------------------
-- Table structure for user_order
-- ----------------------------
DROP TABLE IF EXISTS `user_order`;
CREATE TABLE `user_order`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `otype` int(2) NULL DEFAULT NULL COMMENT '1：外卖    0：自取',
  `onumber` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单流水号',
  `uid` bigint(11) NULL DEFAULT NULL COMMENT '关联用户表id',
  `oprice` double(8, 2) NULL DEFAULT NULL COMMENT '订单价格',
  `paystate` int(2) NULL DEFAULT NULL COMMENT '支付状态 0:未支付   1：已经支付',
  `otime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付时间',
  `ostate` int(2) NULL DEFAULT NULL COMMENT '订单状态：0：预定中（未收获） 1：已收货 2：退订 3：禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_order
-- ----------------------------
INSERT INTO `user_order` VALUES (7, 0, '4ea155a0d53f41d5b361d4ec2436661d', 7, 56.80, 0, '2022-03-07 15:04:17', 1);
INSERT INTO `user_order` VALUES (8, 0, '8623bc8a4c9a4a8394301ead12a26bad', 7, 66.90, 0, '2022-03-07 15:44:24', 1);
INSERT INTO `user_order` VALUES (9, 1, 'a15dc58a4c734be7997f5dc24a1fcc12', 7, 8.80, 0, '2022-03-07 15:54:08', 1);
INSERT INTO `user_order` VALUES (10, 0, '26c20dcede6f440485ef9f666f08c081', 7, 8.80, 1, '2022-03-07 16:01:57', 1);
INSERT INTO `user_order` VALUES (11, 0, 'a503d124866d40e89c9c441efe5ff8d9', 7, 20.00, 1, '2022-03-07 16:07:12', 1);
INSERT INTO `user_order` VALUES (12, 0, '94dbd90e21d943a79bc3fa0a5318555b', 9, 30.00, 1, '2022-03-07 17:15:48', 1);
INSERT INTO `user_order` VALUES (13, 0, '9c9090d4cab6417ab6954154e17b9a79', 9, 28.00, 1, '2022-03-07 17:15:59', 1);
INSERT INTO `user_order` VALUES (14, 0, '35d4903be96a4c04b00cabb6ae853b05', 1, 279.00, 1, '2024-10-11 01:00:24', 1);

SET FOREIGN_KEY_CHECKS = 1;
