/*
Navicat MySQL Data Transfer

Source Server         : gqz_ECS
Source Server Version : 50718
Source Host           : 120.79.179.149:3306
Source Database       : sharefiles

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-04-05 17:41:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sf_admin`
-- ----------------------------
DROP TABLE IF EXISTS `sf_admin`;
CREATE TABLE `sf_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `date` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sf_admin
-- ----------------------------
INSERT INTO `sf_admin` VALUES ('1', 'gqz', 'admin', '2018-04-05 12:57:51', '超级管理员');

-- ----------------------------
-- Table structure for `sf_record`
-- ----------------------------
DROP TABLE IF EXISTS `sf_record`;
CREATE TABLE `sf_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `dtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_sid` (`sid`),
  KEY `fk_uid2` (`uid`),
  CONSTRAINT `fk_sid` FOREIGN KEY (`sid`) REFERENCES `sf_source` (`id`),
  CONSTRAINT `fk_uid2` FOREIGN KEY (`uid`) REFERENCES `sf_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sf_record
-- ----------------------------

-- ----------------------------
-- Table structure for `sf_source`
-- ----------------------------
DROP TABLE IF EXISTS `sf_source`;
CREATE TABLE `sf_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sname` varchar(128) NOT NULL,
  `spath` varchar(512) NOT NULL,
  `size` int(11) DEFAULT NULL,
  `uploadtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tag` varchar(256) DEFAULT NULL,
  `integral` int(11) DEFAULT NULL,
  `dcount` int(11) DEFAULT '0',
  `summary` varchar(512) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `isDel` int(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_uid` (`uid`),
  CONSTRAINT `fk_uid` FOREIGN KEY (`uid`) REFERENCES `sf_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sf_source
-- ----------------------------
INSERT INTO `sf_source` VALUES ('5', '电脑操作按键大全', 'upload/甘全中/2018031058534.txt', '15818', '2018-03-10 22:38:33', 'computer', '1', '1', '电脑上每个按键的作用', '7', '0');
INSERT INTO `sf_source` VALUES ('6', '2018_plan', 'upload/甘全中/2018031030012.txt', '2242', '2018-03-10 22:47:08', 'PLAN  PDCA', '1', '1', 'PDCA  plan do check action', '7', '0');
INSERT INTO `sf_source` VALUES ('7', 'English Porgram', 'upload/甘全中/2018031028737.docx', '78829', '2018-03-10 23:32:46', 'English Porgramming', '2', '0', 'English Porgramming', '7', '1');
INSERT INTO `sf_source` VALUES ('8', '计算机编程常用英语词汇', 'upload/gqzhong/2018031238345.docx', '78829', '2018-03-12 22:18:11', '计算机编程常用英语词汇', '3', '0', '计算机编程常用英语词汇', '12', '0');
INSERT INTO `sf_source` VALUES ('12', 'jQuery帮助文档', 'upload/gqzhong520/2018031697496.chm', '401443', '2018-03-16 22:11:01', '开发手册', '2', '0', 'jQuery帮助文档', '9', '0');
INSERT INTO `sf_source` VALUES ('15', '数据库表设计', 'upload/admin/2018032145464.doc', '177664', '2018-03-21 16:16:08', 'database design', '2', '2', '数据库设计(Database Design)是指对于一个给定的应用环境，构造最优的数据库模式，建立数据库及其应用系统，使之能够有效地存储数据，满足各种用户的应用需求（信息要求和处理要求）。在数据库领域内，常常把使用数据库的各类系统统称为数据库应用系统。', '5', '0');

-- ----------------------------
-- Table structure for `sf_user`
-- ----------------------------
DROP TABLE IF EXISTS `sf_user`;
CREATE TABLE `sf_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(64) NOT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `integral` int(11) DEFAULT '20',
  `regtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sf_user
-- ----------------------------
INSERT INTO `sf_user` VALUES ('5', 'admin', 'ganquanzhong', '12345678900', '2', '2018-03-07 18:31:16');
INSERT INTO `sf_user` VALUES ('7', '甘全中', 'ganquanzhong', '13995976182', '100', '2018-03-08 18:20:32');
INSERT INTO `sf_user` VALUES ('8', '幸好有你', 'ganquanzhong', '13670642106', '0', '2018-03-08 22:50:41');
INSERT INTO `sf_user` VALUES ('9', 'gqzhong520', 'ganquanzhong', '13995976182', '14', '2018-03-10 21:34:10');
INSERT INTO `sf_user` VALUES ('12', 'gqzhong', 'pwdGAN1996', '13995976182', '2', '2018-03-12 22:17:31');
