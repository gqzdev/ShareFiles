/*
Navicat MySQL Data Transfer

Source Server         : MYSQL5
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : sharefiles

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2018-04-19 11:21:40
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `sf_record`
-- ----------------------------
DROP TABLE IF EXISTS `sf_record`;
CREATE TABLE `sf_record` (
  `id` int(11) NOT NULL auto_increment,
  `sid` int(11) default NULL,
  `uid` int(11) default NULL,
  `dtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
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
  `id` int(11) NOT NULL auto_increment,
  `sname` varchar(128) NOT NULL,
  `spath` varchar(512) NOT NULL,
  `size` int(11) default NULL,
  `uploadtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `tag` varchar(256) default NULL,
  `integral` int(11) default NULL,
  `dcount` int(11) default '0',
  `summary` varchar(512) default NULL,
  `uid` int(11) default NULL,
  `isDel` int(2) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_uid` (`uid`),
  CONSTRAINT `fk_uid` FOREIGN KEY (`uid`) REFERENCES `sf_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sf_source
-- ----------------------------

-- ----------------------------
-- Table structure for `sf_user`
-- ----------------------------
DROP TABLE IF EXISTS `sf_user`;
CREATE TABLE `sf_user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(32) NOT NULL,
  `password` varchar(64) NOT NULL,
  `phone` varchar(16) default NULL,
  `integral` int(11) default '20',
  `regtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sf_user
-- ----------------------------
