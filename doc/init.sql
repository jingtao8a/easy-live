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
  `avatar` VARCHAR(255) COMMENT '头像',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `idx_email` (`email` ASC) VISIBLE,
  UNIQUE INDEX `idx_nick_name` (`nick_name` ASC) VISIBLE)
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

CREATE TABLE `easylive`.`video_info_post` (
  `video_id` VARCHAR(10) NOT NULL COMMENT '视频ID',
  `video_cover` VARCHAR(50) NOT NULL COMMENT '视频封面',
  `video_name` VARCHAR(100) NOT NULL COMMENT '视频名称',
  `user_id` VARCHAR(10) NOT NULL COMMENT '用户ID',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_update_time` DATETIME NOT NULL COMMENT '最后更新时间',
  `p_category_id` INT(11) NOT NULL COMMENT '父级分类ID',
  `category_id` INT(11) NULL COMMENT '分类ID',
  `status` TINYINT(1) NOT NULL COMMENT '0:转码中 1:转码失败 2:待审核 3:审核成功 4:审核失败',
  `post_type` TINYINT(4) NOT NULL COMMENT '0:自制作 1:转载',
  `origin_info` VARCHAR(200) NULL COMMENT '原资源说明',
  `tags` VARCHAR(300) NULL COMMENT '标签',
  `introduction` VARCHAR(2000) NULL COMMENT '简介',
  `interaction` VARCHAR(5) NULL COMMENT '互动设置',
  `duration` INT(11) NULL COMMENT '持续时间(seconds)',
  PRIMARY KEY (`video_id`),
  INDEX `idx_create_time` (`create_time` ASC) VISIBLE,
  INDEX `idx_user_id` (`user_id` ASC) VISIBLE,
  INDEX `idx_category_id` (`category_id` ASC) VISIBLE,
  INDEX `idx_pcategory_id` (`p_category_id` ASC) VISIBLE)
  COMMENT = '视频信息';
  
  CREATE TABLE `easylive`.`video_info_file_post` (
  `file_id` VARCHAR(20) NOT NULL COMMENT '文件唯一ID',
  `upload_id` VARCHAR(15) NOT NULL COMMENT '上传ID',
  `user_id` VARCHAR(10) NOT NULL COMMENT '用户ID',
  `video_id` VARCHAR(10) NOT NULL COMMENT '视频ID',
  `file_index` INT(11) NOT NULL COMMENT '文件索引',
  `file_name` VARCHAR(200) NULL COMMENT '文件名',
  `file_size` BIGINT(20) NULL COMMENT '文件大小',
  `file_path` VARCHAR(100) NULL COMMENT '文件路径',
  `update_type` TINYINT(4) NULL COMMENT '0:无更新 1:有更新',
  `transfer_result` TINYINT(4) NULL COMMENT '0:转码中 1:转码成功 2:转码失败',
  `duration` INT(11) NULL COMMENT '持续时间(seconds)',
  PRIMARY KEY (`file_id`),
  UNIQUE INDEX `idx_key_upload_id` (`upload_id` ASC) VISIBLE,
  INDEX `idx_video_id` (`video_id` ASC) VISIBLE)
COMMENT = '视频文件信息';


CREATE TABLE `easylive`.`video_info` (
  `video_id` VARCHAR(10) NOT NULL COMMENT '视频ID',
  `video_cover` VARCHAR(50) NOT NULL COMMENT '视频封面',
  `video_name` VARCHAR(100) NOT NULL COMMENT '视频名称',
  `user_id` VARCHAR(10) NOT NULL COMMENT '用户ID',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `last_update_time` DATETIME NOT NULL COMMENT '最后更新时间',
  `p_category_id` INT(11) NOT NULL COMMENT '父级分类ID',
  `category_id` INT(11) NULL COMMENT '分类ID',
  `post_type` TINYINT(4) NOT NULL COMMENT '0:自制作 1:转载',
  `tags` VARCHAR(300) NULL COMMENT '标签',
  `introduction` VARCHAR(2000) NULL COMMENT '简介',
  `interaction` VARCHAR(5) NULL COMMENT '互动设置',
  `duration` INT(11) NULL COMMENT '持续时间(seconds)',
  `play_count` INT(11) DEFAULT 0 COMMENT '播放数量',
  `like_count` INT(11) DEFAULT 0 COMMENT '点赞数量',
  `danmu_count` INT(11) DEFAULT 0 COMMENT '弹幕数量',
  `comment_count` INT(11) DEFAULT 0 COMMENT '评论数量',
  `coin_count` INT(11) DEFAULT 0 COMMENT '投币数量',
  `collect_count` INT(11) DEFAULT 0 COMMENT '收藏数量',
  `recommend_type` TINYINT(1) DEFAULT 0 COMMENT '是否推荐 0:未推荐 1:已推荐',
  `last_play_time` DATETIME COMMENT '最后播放时间',
  PRIMARY KEY (`video_id`),
  INDEX `idx_create_time` (`create_time` ASC) VISIBLE,
  INDEX `idx_user_id` (`user_id` ASC) VISIBLE,
  INDEX `idx_category_id` (`category_id` ASC) VISIBLE,
  INDEX `idx_pcategory_id` (`p_category_id` ASC) VISIBLE,
  INDEX `idx_recommend_type` (`recommend_type` ASC) VISIBLE,
  INDEX `idx_play_time` (`last_play_time` ASC) VISIBLE)
  COMMENT = '视频信息';
  
  CREATE TABLE `easylive`.`video_info_file` (
  `file_id` VARCHAR(20) NOT NULL COMMENT '文件唯一ID',
  `user_id` VARCHAR(10) NOT NULL COMMENT '用户ID',
  `video_id` VARCHAR(10) NOT NULL COMMENT '视频ID',
  `file_index` INT(11) NOT NULL COMMENT '文件索引',
  `file_name` VARCHAR(200) NULL COMMENT '文件名',
  `file_size` BIGINT(20) NULL COMMENT '文件大小',
  `file_path` VARCHAR(100) NULL COMMENT '文件路径',
  `duration` INT(11) NULL COMMENT '持续时间(seconds)',
  PRIMARY KEY (`file_id`),
  INDEX `idx_video_id` (`video_id` ASC) VISIBLE)
COMMENT = '视频文件信息';

CREATE TABLE `easylive`.`user_action` (
  `action_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `video_id` VARCHAR(10) NOT NULL COMMENT '视频ID',
  `video_user_id` VARCHAR(10) NOT NULL COMMENT '视频用户ID',
  `comment_id` INT(11) NOT NULL DEFAULT 0 COMMENT '评论ID',
  `action_type` TINYINT(1) NOT NULL COMMENT '0:喜欢点赞评论 1.讨厌评论 2.视频点赞 3.视频收藏 4.视频投币',
  `action_count` INT(11) NOT NULL COMMENT '数量',
  `user_id` VARCHAR(10) NOT NULL COMMENT '用户ID',
  `action_time` DATETIME NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`action_id`),
  UNIQUE INDEX `idx_key_video_comment_type_user` (`video_id` ASC, `comment_id` ASC, `action_type` ASC, `user_id` ASC) VISIBLE,
  INDEX `idx_video_id` (`video_id` ASC) VISIBLE,
  INDEX `idx_user_id` (`user_id` ASC) VISIBLE,
  INDEX `idx_type` (`action_type` ASC) VISIBLE,
  INDEX `idx_action_time` (`action_time` ASC) VISIBLE)
COMMENT = '用户行为 点赞、评论';

CREATE TABLE `easylive`.`video_danmu` (
  `danmu_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `video_id` VARCHAR(10) NOT NULL COMMENT '视频ID',
  `file_id` VARCHAR(20) NOT NULL COMMENT '视频文件ID',
  `user_id` VARCHAR(45) NOT NULL COMMENT '用户ID',
  `post_time` DATETIME NULL DEFAULT NULL COMMENT '发布时间',
  `text` VARCHAR(300) NULL DEFAULT NULL COMMENT '内容',
  `mode` TINYINT(1) NULL DEFAULT NULL COMMENT '展示位置',
  `color` VARCHAR(10) NULL DEFAULT NULL COMMENT '颜色',
  `time` INT(11) NULL DEFAULT NULL COMMENT '展示时间',
  PRIMARY KEY (`danmu_id`),
  INDEX `idx_file_id` (`file_id` ASC) VISIBLE)
COMMENT = '视频弹幕';

CREATE TABLE `easylive`.`video_comment` (
  `comment_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `p_comment_id` INT NOT NULL COMMENT '父级评论ID',
  `video_id` VARCHAR(10) NOT NULL COMMENT '视频ID',
  `video_user_id` VARCHAR(10) NOT NULL COMMENT '视频用户ID',
  `content` VARCHAR(500) NULL DEFAULT NULL COMMENT '回复内容',
  `img_path` VARCHAR(150) NULL DEFAULT NULL COMMENT '图片',
  `user_id` VARCHAR(15) NOT NULL COMMENT '用户ID',
  `reply_user_id` VARCHAR(15) NULL DEFAULT NULL COMMENT '回复人ID',
  `top_type` TINYINT(4) NULL DEFAULT 0 COMMENT '0:未置顶 1:置顶',
  `post_time` DATETIME NOT NULL COMMENT '发布时间',
  `like_count` INT(11) NULL DEFAULT 0 COMMENT '喜欢数量',
  `hate_count` INT(11) NULL DEFAULT 0 COMMENT '讨厌数量',
  PRIMARY KEY (`comment_id`),
  INDEX `idx_post_time` (`post_time` ASC) VISIBLE,
  INDEX `idx_top` (`top_type` ASC) VISIBLE,
  INDEX `idx_p_id` (`p_comment_id` ASC) VISIBLE,
  INDEX `idx_user_id` (`user_id` ASC) VISIBLE,
  INDEX `idx_video_id` (`video_id` ASC) VISIBLE)
COMMENT = '评论';


-- drop table `easylive`.`user_info`; 
-- drop table `easylive`.`category_info`;
-- drop table `easylive`.`video_info_post`;
-- drop table `easylive`.`video_info_file_post`;
-- drop table `easylive`.`video_info`;
-- drop table `easylive`.`video_info_file`;
