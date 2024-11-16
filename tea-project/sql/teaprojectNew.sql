/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : teaproject

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 16/11/2024 20:19:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `uid` bigint NULL DEFAULT NULL COMMENT '关联用户id',
  `pid` bigint NULL DEFAULT NULL COMMENT '关联产品id',
  `number` int NULL DEFAULT NULL COMMENT '产品数量',
  `tprice` double(4, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `cart` VALUES (107, 2, 6, 1, 14.00);
INSERT INTO `cart` VALUES (108, 2, 4, 1, 10.00);

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `ititle` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片标题',
  `iurl` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片路径(图片)',
  `itype` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片类型 0：注册页面轮播图  1：产品轮播图图片等等 ',
  `imsg` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '描述  如：注册页面轮播图',
  `istate` int NULL DEFAULT NULL COMMENT '1:可用    0：禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES (1, '欢迎界面轮播图', 'http://localhost:1000/file/tea-project/5158f636-edd9-4989-87e0-ae48b666c30e.png', '1', '欢迎界面轮播图', 3);
INSERT INTO `image` VALUES (2, '欢迎界面轮播图', 'http://localhost:1000/file/tea-project/4ba71edb-5971-4bcf-813f-a9b9dc44afad.png', '1', '欢迎界面轮播图', 3);
INSERT INTO `image` VALUES (3, '欢迎界面轮播图', 'http://localhost:1000/file/tea-project/5fd1e724-a996-486b-b7a9-ab9645628fc5.png', '1', '欢迎界面轮播图', 3);
INSERT INTO `image` VALUES (4, '欢迎界面轮播图', 'http://localhost:1000/file/tea-project/f9e8681e-80c9-4d5f-a580-7a6698343590.png', '1', '欢迎界面轮播图', 3);
INSERT INTO `image` VALUES (5, '欢迎界面轮播图', 'http://localhost:1000/file/tea-project/979993c0-bfde-434d-b682-68e2b43ae0f2.png', '1', '欢迎界面轮播图', 1);
INSERT INTO `image` VALUES (6, '首页轮播图1', 'http://localhost:1000/file/tea-project/a81ac3ab-0f27-44e7-9cba-2f019eefd8f4.jpg', '2', '首页轮播图', 1);
INSERT INTO `image` VALUES (7, '首页轮播图', 'http://localhost:1000/file/tea-project/08b553f1-f6f6-4d17-84b8-ec19293eac83.png', '2', '首页轮播图', 1);
INSERT INTO `image` VALUES (8, '首页轮播图', 'http://localhost:1000/file/tea-project/d4a98062-b87d-4f7f-80c1-39a9c94523ca.png', '2', '首页轮播图', 2);
INSERT INTO `image` VALUES (9, '首页轮播图', 'http://localhost:1000/file/tea-project/f2964dd4-e5b9-4edd-ba5a-0d903879e54d.png', '2', '首页轮播图', 2);
INSERT INTO `image` VALUES (10, '产品界面轮播图', 'http://localhost:1000/file/tea-project/83974732-5da3-4127-b581-ab034d81482a.png', '3', '产品界面轮播图', 2);
INSERT INTO `image` VALUES (11, '产品界面轮播图', 'http://localhost:1000/file/tea-project/c4f34aa5-666b-4076-a248-f599bbbd5d28.png', '3', '产品界面轮播图', 2);
INSERT INTO `image` VALUES (12, '产品界面轮播图', 'http://localhost:1000/file/tea-project/c9062c53-418f-459c-a94d-9f275c398304.png', '3', '产品界面轮播图', 2);
INSERT INTO `image` VALUES (13, '首页轮播图', 'http://localhost:1000/file/tea-project/1250337b-9e3f-411d-b957-cc0f63996420.jpg', '2', NULL, NULL);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户ID' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (9, '写代码让人头疼', '( ఠൠఠ )ﾉ', '2024-11-07 11:57:20', 1);
INSERT INTO `notice` VALUES (10, '难受哇', NULL, '2024-11-16 00:41:10', 1);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `pname` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `typeid` bigint NULL DEFAULT NULL COMMENT '关联类型表id',
  `tprice` double(4, 2) NULL DEFAULT NULL COMMENT '价格',
  `tintroduce` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '产品介绍',
  `pimageurl` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片路径(图片)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '万里木兰', 1, 8.80, '好喝', 'http://localhost:1000/file/tea-project/6a169cf8-c5a8-48ec-bf5d-84a06001eb06.jpg');
INSERT INTO `product` VALUES (2, '白雾红尘', 1, 16.00, '好喝', 'http://localhost:1000/file/tea-project/bd96de9c-8b55-4c83-9e34-5c3aa088c173.jpg');
INSERT INTO `product` VALUES (3, '招牌芋圆奶茶', 1, 20.00, '好喝', 'http://localhost:1000/file/tea-project/c800fe9a-3900-4875-a8b5-7dc4ac7fdd26.jpg');
INSERT INTO `product` VALUES (4, '黄金珍珠奶茶', 1, 10.00, '好喝', 'http://localhost:1000/file/tea-project/bdd76f2d-b92a-42b0-8060-3d6e04263645.jpg');
INSERT INTO `product` VALUES (5, '书亦烧仙草', 1, 14.00, '好喝', 'http://localhost:1000/file/tea-project/ad8fecf4-d208-43de-bf8c-34ae2ac4d0db.jpg');
INSERT INTO `product` VALUES (6, '葡萄芋圆冻冻', 6, 14.00, '好喝', 'http://localhost:1000/file/tea-project/d238cc8e-3bd6-4f4b-96a1-39766d1f86e1.jpg');
INSERT INTO `product` VALUES (7, '伯牙绝弦', 3, 16.00, '好喝', 'http://localhost:1000/file/tea-project/0a44e779-544f-461f-9984-e8e8e9aa1ac5.jpg');
INSERT INTO `product` VALUES (8, '有奶没牛', 1, 17.00, '好喝', 'http://localhost:1000/file/tea-project/c33d84ca-52e8-41e7-9848-b03bedcf4e14.jpg');
INSERT INTO `product` VALUES (9, '焦糖拿铁', 1, 15.00, '好喝', 'http://localhost:1000/file/tea-project/6f23f0e2-e1a7-4653-89ce-7ea5fcdbe584.jpg');
INSERT INTO `product` VALUES (10, '玫瑰石榴冰茶', 3, 14.00, '好喝', 'http://localhost:1000/file/tea-project/2244678b-755c-431e-b7b5-fb837f8397eb.jpg');
INSERT INTO `product` VALUES (11, '抹茶星冰乐', 3, 19.00, '好喝', 'http://localhost:1000/file/tea-project/0f04fb05-917f-4178-a0fc-42f4d76657c7.jpg');
INSERT INTO `product` VALUES (12, '黄油啤酒', 4, 8.00, '好喝', 'http://localhost:1000/file/tea-project/8a091fe9-d629-4805-9805-78a517be4b0e.jpg');
INSERT INTO `product` VALUES (13, '黑糖珍珠大圣代', 4, 12.00, '好喝', 'http://localhost:1000/file/tea-project/c88eb2e6-b98e-4b6d-8394-ea233c936a1b.jpg');
INSERT INTO `product` VALUES (14, '芝士奶盖绿茶', 2, 22.00, '好喝', 'http://localhost:1000/file/tea-project/3a594229-fb5f-48fa-9492-6bc57c6b485a.jpg');
INSERT INTO `product` VALUES (15, '小猫给你奶茶里倒点猫砂', 5, 0.50, '豆腐砂（水溶版）可冲厕所', 'http://localhost:1000/file/tea-project/b3c889bd-2e0c-4d64-9bdb-35929684b361.jpg');

-- ----------------------------
-- Table structure for productorder
-- ----------------------------
DROP TABLE IF EXISTS `productorder`;
CREATE TABLE `productorder`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '产品、订单中间表',
  `oid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '关联订单流水号',
  `pid` bigint NULL DEFAULT NULL COMMENT '关联产品id',
  `number` int NULL DEFAULT NULL COMMENT '购买产品数量',
  `price` double(8, 2) NULL DEFAULT NULL COMMENT '产品单价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `productorder` VALUES (23, '70ed7dffa00848dbaffab7dec1dcf187', 1, 1, 8.80);
INSERT INTO `productorder` VALUES (24, '4a931300816549f0b7e04531785d9f74', 1, 2, 8.80);
INSERT INTO `productorder` VALUES (25, 'c7f10ca7680746c0b70fe2c56726d14c', 8, 1, 17.00);
INSERT INTO `productorder` VALUES (26, 'ea2011df29334b99b8d2aefe50a35fde', 5, 1, 14.00);
INSERT INTO `productorder` VALUES (27, '654d53bfeea64e35bb2f5d4da474774b', 1, 1, 8.80);
INSERT INTO `productorder` VALUES (28, 'c0a2ff4cdac24dc59aa0ef151b8fcccb', 1, 1, 8.80);
INSERT INTO `productorder` VALUES (29, '345c3a81966c48eba2b0bbae1e9e5ede', 3, 1, 20.00);
INSERT INTO `productorder` VALUES (30, '1ce57b21cac140e8aa155b4016f7e3ab', 2, 1, 16.00);
INSERT INTO `productorder` VALUES (31, '1ce57b21cac140e8aa155b4016f7e3ab', 3, 1, 20.00);
INSERT INTO `productorder` VALUES (32, '1ce57b21cac140e8aa155b4016f7e3ab', 4, 1, 10.00);
INSERT INTO `productorder` VALUES (33, 'd6beb1feabf54b1faa7192d2f4e5d974', 4, 1, 10.00);
INSERT INTO `productorder` VALUES (34, 'd6beb1feabf54b1faa7192d2f4e5d974', 3, 1, 20.00);
INSERT INTO `productorder` VALUES (35, 'd6beb1feabf54b1faa7192d2f4e5d974', 2, 1, 16.00);
INSERT INTO `productorder` VALUES (36, 'd6beb1feabf54b1faa7192d2f4e5d974', 5, 1, 14.00);
INSERT INTO `productorder` VALUES (37, 'c9865788f56b4331956ce1150f7feea3', 4, 1, 10.00);
INSERT INTO `productorder` VALUES (38, 'e1b7ab97b83a4df8bc773efbad40f0b8', 8, 1, 17.00);
INSERT INTO `productorder` VALUES (39, '7d9859ace8684d41b04fd30e2fc3505a', 2, 1, 16.00);
INSERT INTO `productorder` VALUES (40, '7d9859ace8684d41b04fd30e2fc3505a', 3, 1, 20.00);
INSERT INTO `productorder` VALUES (41, '3c36345e233a441896b2f8239e7970f0', 2, 1, 16.00);
INSERT INTO `productorder` VALUES (42, '3c36345e233a441896b2f8239e7970f0', 3, 1, 20.00);
INSERT INTO `productorder` VALUES (43, '3c36345e233a441896b2f8239e7970f0', 4, 1, 10.00);
INSERT INTO `productorder` VALUES (44, '83960429fc844ae7ba0676c78bd96f16', 6, 1, 14.00);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色名称',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色描述',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

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
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户密码',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '个人简介',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `salt` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '加盐',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `state` int NULL DEFAULT 1 COMMENT '状态（正常/禁用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'ba1a907429fd7580268ff64098d271f4', '', '12345@gmail.com', '15587878787', '', 'http://localhost:1000/file/tea-project/ed9c7c64-890e-41b0-93bf-cdfdc545d870.jpg', 'ADMIN', '85FBBC8C72165C58', '2023-06-19 11:24:56', '男', 1);
INSERT INTO `sys_user` VALUES (2, 'user', '15f9fafe5c3c7bc572e5f1f3a3d56549', '', '231@qq.com', '15566778899', '', 'http://localhost:1000/file/tea-project/61952ee3-1baf-43f3-b7bc-6404ea27428a.jpg', 'USER', '08F230786A4FFC98', '2023-06-19 19:24:56', '男', 1);
INSERT INTO `sys_user` VALUES (3, 'user1', '0cb9db4b39af0e15d9880bec3e1b3208', NULL, '12345@gmail.com', '15123', NULL, 'http://localhost:1000/file/tea-project/cb33d96b-10fb-4d98-baab-c9e87f24a1b3.jpg', 'USER', 'C73652722566A4BB', '2024-11-06 12:45:56', '男', 1);
INSERT INTO `sys_user` VALUES (4, 'user12', 'c38d2e7cb731db9de2ee963d9a5c430d', NULL, NULL, NULL, NULL, 'http://localhost:1000/file/tea-project/0d6a3dfa-7230-4a83-a17a-ab155c5de895.jpg', 'USER', '94CFCEEF63027E51', '2024-11-06 12:46:40', NULL, 1);
INSERT INTO `sys_user` VALUES (5, 'user123', '1182ff394c21d7fc6cb54908e2d08c6c', NULL, NULL, NULL, NULL, 'http://localhost:1000/file/tea-project/56616051-6e4a-4603-ab49-a5664a77e5b7.jpg', 'USER', '5C20D60D195C01C3', '2024-11-06 12:51:04', NULL, 1);
INSERT INTO `sys_user` VALUES (6, 'xdn', 'f2063e8518f2bd2d1a77cbca3374b69f', NULL, NULL, NULL, NULL, 'http://localhost:1000/file/tea-project/7ab58b0c-33bd-45af-a770-b827880e5aa7.jpg', 'ADMIN', '2C5563AAF700AA13', '2024-11-16 00:00:22', NULL, 1);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `typename` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型名称',
  `timageurl` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型图片路径',
  `tstate` int NULL DEFAULT NULL COMMENT '1:可用    0：禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '醇香奶茶', 'http://localhost:1000/file/tea-project/e5941086-aeb3-40ec-a7af-475ab7a648df.jpg', 1);
INSERT INTO `type` VALUES (2, '鲜牛乳', 'http://localhost:1000/file/tea-project/46be1fd2-84b7-42ed-aa4f-5d0bc689e998.jpg', 1);
INSERT INTO `type` VALUES (3, '四级鲜果茶', 'http://localhost:1000/file/tea-project/bf326b26-c0ac-4f8d-b828-9e15ef092256.jpg', 1);
INSERT INTO `type` VALUES (4, '乳酸菌', 'http://localhost:1000/file/tea-project/4057cb3b-84ad-4312-8a98-cd44b8389418.jpg', 1);
INSERT INTO `type` VALUES (5, '加料2', 'http://localhost:1000/file/tea-project/5ae99306-8747-4d21-9263-f0b76d7a8397.jpg', 0);
INSERT INTO `type` VALUES (6, '花香轻乳茶', 'http://localhost:1000/file/tea-project/78e058d8-18fc-422c-aaa3-d2b0823b7540.jpg', 1);

-- ----------------------------
-- Table structure for user_order
-- ----------------------------
DROP TABLE IF EXISTS `user_order`;
CREATE TABLE `user_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `otype` int NULL DEFAULT NULL COMMENT '1：外卖    0：自取',
  `onumber` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单流水号',
  `uid` bigint NULL DEFAULT NULL COMMENT '关联用户表id',
  `oprice` double(8, 2) NULL DEFAULT NULL COMMENT '订单价格',
  `paystate` int NULL DEFAULT NULL COMMENT '支付状态 0:未支付   1：已经支付',
  `otime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '支付时间',
  `ostate` int NULL DEFAULT NULL COMMENT '订单状态：0：预定中（未收获） 1：已收货 2：退订 3：禁用',
  `pid` bigint NULL DEFAULT NULL COMMENT '关联商品id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_order
-- ----------------------------
INSERT INTO `user_order` VALUES (7, 0, '4ea155a0d53f41d5b361d4ec2436661d', 7, 56.80, 0, '2022-03-07 15:04:17', 1, NULL);
INSERT INTO `user_order` VALUES (8, 0, '8623bc8a4c9a4a8394301ead12a26bad', 7, 66.90, 0, '2022-03-07 15:44:24', 1, NULL);
INSERT INTO `user_order` VALUES (9, 1, 'a15dc58a4c734be7997f5dc24a1fcc12', 7, 8.80, 0, '2022-03-07 15:54:08', 1, NULL);
INSERT INTO `user_order` VALUES (10, 0, '26c20dcede6f440485ef9f666f08c081', 7, 8.80, 1, '2022-03-07 16:01:57', 1, NULL);
INSERT INTO `user_order` VALUES (11, 0, 'a503d124866d40e89c9c441efe5ff8d9', 7, 20.00, 1, '2022-03-07 16:07:12', 1, NULL);
INSERT INTO `user_order` VALUES (12, 0, '94dbd90e21d943a79bc3fa0a5318555b', 9, 30.00, 1, '2022-03-07 17:15:48', 1, NULL);
INSERT INTO `user_order` VALUES (13, 0, '9c9090d4cab6417ab6954154e17b9a79', 9, 28.00, 1, '2022-03-07 17:15:59', 1, NULL);
INSERT INTO `user_order` VALUES (14, 0, '35d4903be96a4c04b00cabb6ae853b05', 1, 279.00, 1, '2024-10-11 01:00:24', 1, NULL);
INSERT INTO `user_order` VALUES (15, 0, '70ed7dffa00848dbaffab7dec1dcf187', 2, 8.80, 1, '2024-11-06 20:09:11', 1, NULL);
INSERT INTO `user_order` VALUES (16, 0, '4a931300816549f0b7e04531785d9f74', 2, 17.60, 1, '2024-11-07 20:49:24', 1, NULL);
INSERT INTO `user_order` VALUES (17, 0, 'c7f10ca7680746c0b70fe2c56726d14c', 2, 17.00, 1, '2024-11-07 20:51:50', 1, NULL);
INSERT INTO `user_order` VALUES (18, 0, 'ea2011df29334b99b8d2aefe50a35fde', 2, 14.00, 1, '2024-11-07 21:08:51', 1, NULL);
INSERT INTO `user_order` VALUES (19, 0, '654d53bfeea64e35bb2f5d4da474774b', 2, 8.80, 1, '2024-11-07 21:38:42', 1, NULL);
INSERT INTO `user_order` VALUES (20, 1, 'c0a2ff4cdac24dc59aa0ef151b8fcccb', 2, 8.80, 1, '2024-11-11 20:48:56', 1, NULL);
INSERT INTO `user_order` VALUES (21, 0, '345c3a81966c48eba2b0bbae1e9e5ede', 2, 20.00, 1, '2024-11-15 20:24:16', 1, NULL);
INSERT INTO `user_order` VALUES (22, 0, '1ce57b21cac140e8aa155b4016f7e3ab', 2, 46.00, 1, '2024-11-15 20:26:06', 1, NULL);
INSERT INTO `user_order` VALUES (23, 0, 'd6beb1feabf54b1faa7192d2f4e5d974', 2, 60.00, 1, '2024-11-15 20:26:29', 1, NULL);
INSERT INTO `user_order` VALUES (24, 0, 'c9865788f56b4331956ce1150f7feea3', 2, 10.00, 1, '2024-11-15 20:28:39', 0, NULL);
INSERT INTO `user_order` VALUES (25, 0, 'e1b7ab97b83a4df8bc773efbad40f0b8', 2, 17.00, 1, '2024-11-15 20:29:50', 0, NULL);
INSERT INTO `user_order` VALUES (26, 0, '7d9859ace8684d41b04fd30e2fc3505a', 2, 36.00, 1, '2024-11-15 21:25:46', 0, NULL);
INSERT INTO `user_order` VALUES (27, 0, '3c36345e233a441896b2f8239e7970f0', 2, 46.00, 1, '2024-11-15 22:11:54', 0, NULL);
INSERT INTO `user_order` VALUES (28, 0, '83960429fc844ae7ba0676c78bd96f16', 2, 14.00, 1, '2024-11-15 22:13:03', 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
