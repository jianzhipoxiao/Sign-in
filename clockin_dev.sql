/*
 Navicat Premium Data Transfer

 Source Server         : lwx
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : clockin_dev

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 20/10/2023 18:26:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nickName` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `department` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE,
  INDEX `department`(`department`) USING BTREE,
  CONSTRAINT `t_admin_ibfk_1` FOREIGN KEY (`department`) REFERENCES `t_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '技术中心',
  `dleader` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`dname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_department
-- ----------------------------

-- ----------------------------
-- Table structure for t_recodr_from
-- ----------------------------
DROP TABLE IF EXISTS `t_recodr_from`;
CREATE TABLE `t_recodr_from`  (
  `rId` bigint(20) NOT NULL,
  `user` int(11) NULL DEFAULT NULL,
  `in_time` datetime(0) NULL DEFAULT NULL,
  `type` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `out_time` datetime(0) NULL DEFAULT NULL,
  `carry_key` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`rId`) USING BTREE,
  INDEX `user`(`user`) USING BTREE,
  CONSTRAINT `t_recodr_from_ibfk_1` FOREIGN KEY (`user`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_recodr_from
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `department` int(11) NULL DEFAULT NULL,
  `grade` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sno` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `major` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT 1,
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `image` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `department`(`department`) USING BTREE,
  CONSTRAINT `t_user_ibfk_1` FOREIGN KEY (`department`) REFERENCES `t_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
