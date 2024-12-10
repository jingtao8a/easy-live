create schema easylive;
use easylive;

CREATE TABLE `easylive`.`user_info` (
  `user_id` VARCHAR(10) NOT NULL COMMENT '用户id',
  `nick_name` VARCHAR(20) NOT NULL COMMENT '昵称',
  `email` VARCHAR(150) NOT NULL COMMENT '邮箱',
  `password` VARCHAR(50) NOT NULL COMMENT '密码',
  `sex` TINYINT(1) NULL COMMENT '0：女 1：男 2：未知',
  `birthday` VARCHAR(10) NULL COMMENT '出生日期',
  `school` VARCHAR(150) NULL COMMENT '学校',
  `person_introduction` VARCHAR(200) NULL COMMENT '个人简介',
  `join_time` DATETIME NOT NULL COMMENT '加入时间',
  `last_login_ip` VARCHAR(15) NULL COMMENT '最后登入ip',
  `last_login_time` DATETIME NULL COMMENT '最后登入时间',
  `status` TINYINT(1) NOT NULL COMMENT '0：禁用 1：正常',
  `notice_info` VARCHAR(300) NULL COMMENT '空间公告',
  `total_coin_count` INT(11) NOT NULL COMMENT '硬币总数量',
  `current_coin_count` INT(11) NOT NULL COMMENT '当前硬币数',
  `theme` TINYINT(1) NOT NULL COMMENT '主题',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `idx_email` (`email` ASC) INVISIBLE,
  UNIQUE INDEX `idx_nick_name` (`nick_name` ASC) INVISIBLE)
COMMENT = '用户信息';

CREATE TABLE `easylive`.`category_info` (
  `category_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增分类id',
  `category_code` VARCHAR(30) NOT NULL COMMENT '分类编码',
  `category_name` VARCHAR(30) NOT NULL COMMENT '分类名称',
  `p_category_id` INT(11) NOT NULL COMMENT '父级分类id',
  `icon` VARCHAR(50) NULL COMMENT '图标',
  `background` VARCHAR(50) NULL COMMENT '背景图',
  `sort` TINYINT(4) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `idx_key_category_code` (`category_code` ASC) VISIBLE)
COMMENT = '分类信息';

