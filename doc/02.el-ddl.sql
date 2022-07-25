/*
 Navicat Premium Data Transfer

 Source Server         : 172.20.27.53-3307
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 172.20.27.53:3307
 Source Schema         : el-devops

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 25/07/2022 09:59:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for el_params_define
-- ----------------------------
DROP TABLE IF EXISTS `el_params_define`;
CREATE TABLE `el_params_define`  (
  `param_def_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `param_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `param_label` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `data_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'String' COMMENT '数据类型String:Integer:Boolean',
  `param_default_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数默认值',
  `param_seq` int(11) NULL DEFAULT 1 COMMENT '参数顺序',
  `param_type` int(11) NULL DEFAULT 2 COMMENT '1出参，2入参',
  `param_of` int(11) NULL DEFAULT NULL COMMENT '1全局参数(剧本参数)，2环节参数(服务参数)',
  `service_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务ID',
  `book_def_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剧本ID',
  PRIMARY KEY (`param_def_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for el_params_inst
-- ----------------------------
DROP TABLE IF EXISTS `el_params_inst`;
CREATE TABLE `el_params_inst`  (
  `param_inst_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `param_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名',
  `param_label` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数标签',
  `data_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据类型String:Integer:Boolean',
  `param_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `value_type` int(11) NULL DEFAULT NULL COMMENT '值类型1常量2变量',
  `param_type` int(11) NULL DEFAULT 2 COMMENT '参数类型，1出参，2入参',
  `param_of` int(11) NULL DEFAULT NULL COMMENT '1全局参数(剧本参数)，2环节参数(服务参数)',
  `book_def_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剧本id，param_of为1时有值',
  `service_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务id，param_of为2时有值',
  `activity_def_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动定义ID',
  `param_seq` int(11) NULL DEFAULT 1,
  PRIMARY KEY (`param_inst_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for el_playbook_define
-- ----------------------------
DROP TABLE IF EXISTS `el_playbook_define`;
CREATE TABLE `el_playbook_define`  (
  `book_def_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `book_def_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剧本标识',
  `book_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剧本名称',
  `book_desc` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剧本描述',
  `book_status` int(11) NULL DEFAULT NULL COMMENT '状态:0新建,1已创建，2已发布(上线)，3已挂起(下线)，4草稿',
  `process_def_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程定义id',
  `process_def_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程定义key',
  `book_xml` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `book_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '流程定义json',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`book_def_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for el_playbook_instance
-- ----------------------------
DROP TABLE IF EXISTS `el_playbook_instance`;
CREATE TABLE `el_playbook_instance`  (
  `book_inst_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `process_inst_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程实例id',
  `root_process_inst_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '根流程实例id',
  `book_def_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剧本定义id',
  `book_def_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `book_inst_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剧本实例名称',
  `book_inst_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剧本实例说明',
  `status` int(11) NULL DEFAULT 1 COMMENT '1运行中,2暂停,3异常终止,4已完结',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `related_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '相关数据活动-服务-参数',
  PRIMARY KEY (`book_inst_id`) USING BTREE,
  INDEX `playbookinst_on_procinst_id_index`(`process_inst_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for el_service
-- ----------------------------
DROP TABLE IF EXISTS `el_service`;
CREATE TABLE `el_service`  (
  `service_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务名称',
  `service_ch_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务显示名称',
  `service_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务描述',
  `protocol` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'local' COMMENT 'local;http;python;gradle',
  `service_type` int(11) NULL DEFAULT NULL COMMENT '服务类型',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `method` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '脚本',
  PRIMARY KEY (`service_id`) USING BTREE,
  INDEX `service_on_app_id_index`(`app_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for el_service_on_activity
-- ----------------------------
DROP TABLE IF EXISTS `el_service_on_activity`;
CREATE TABLE `el_service_on_activity`  (
  `sid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `activity_def_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `book_def_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
