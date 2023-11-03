/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql8
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : make_flow

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 13/07/2023 10:06:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for flow_template
-- ----------------------------
DROP TABLE IF EXISTS `makeflow_template`;
CREATE TABLE `makeflow_template`  (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `creator` varchar(64) NULL DEFAULT NULL COMMENT '创建人',
  `updator` varchar(64) NULL DEFAULT NULL COMMENT '更新人',
  `delete` tinyint NOT NULL COMMENT '是否删除',
  `name` varchar(128) NOT NULL COMMENT '模板名字',
  `flow_process_code_id` varchar(64) NOT NULL COMMENT '模板的codeId',
  `state` varchar(32) NULL DEFAULT NULL COMMENT '状态（1启用',
  `max` tinyint NOT NULL COMMENT '是否最新版本（1，最新，0，不是最新）',
  `version` int NOT NULL COMMENT '版本',
  `data_type` varchar(64) NOT NULL COMMENT '数据类型',
  `template_data` text NOT NULL COMMENT '模板数据',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_flow_process_code_id`(`flow_process_code_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `makeflow_activity`;
CREATE TABLE `makeflow_activity`  (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `creator` varchar(64) NULL DEFAULT NULL COMMENT '创建人',
  `updator` varchar(64) NULL DEFAULT NULL COMMENT '更新人',
  `delete` tinyint NOT NULL COMMENT '是否删除',
  `name` varchar(128) NOT NULL COMMENT '模板名字',
  `activity_type` varchar(64) NOT NULL COMMENT '活动类型',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime  NULL DEFAULT NULL COMMENT '结束时间',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `flow_inst_id` varchar(64) NOT NULL COMMENT '流程实列id',
  `definition_id` varchar(64) NOT NULL COMMENT '流程定义id',
  `parent_id` varchar(64) NULL DEFAULT NULL COMMENT '父活动',
  `pre_activity_id` varchar(64) NULL DEFAULT NULL COMMENT '前一个节点id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_flow_inst_id`(`flow_inst_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `makeflow_execute`;
CREATE TABLE `makeflow_execute`  (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `creator` varchar(64) NULL DEFAULT NULL COMMENT '创建人',
  `updator` varchar(64) NULL DEFAULT NULL COMMENT '更新人',
  `delete` tinyint NOT NULL COMMENT '是否删除',
  `root_flow_inst_id` varchar(64)  NULL DEFAULT NULL COMMENT '根流程实列id',
  `activity_id` varchar(64) NOT NULL COMMENT '当前活动id',
  `activity_code_id` varchar(64) NOT NULL COMMENT '当前活动codeId',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime  NULL DEFAULT NULL COMMENT '结束时间',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `flow_inst_id` varchar(64) NOT NULL COMMENT '流程实列id',
  `definition_id` varchar(64) NOT NULL COMMENT '流程定义id',
  `parent_id` varchar(64) NULL DEFAULT NULL COMMENT '父活动',
  `variables` text NULL DEFAULT NULL COMMENT '动态参数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_flow_inst_id`(`flow_inst_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `makeflow_flow_inst`;
CREATE TABLE `makeflow_flow_inst`  (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `creator` varchar(64) NULL DEFAULT NULL COMMENT '创建人',
  `updator` varchar(64) NULL DEFAULT NULL COMMENT '更新人',
  `delete` tinyint NOT NULL COMMENT '是否删除',
  `defintion_code_id` varchar(64)  NOT NULL COMMENT '根流程实列id',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime  NULL DEFAULT NULL COMMENT '结束时间',
  `apply_time` datetime  NULL DEFAULT NULL COMMENT '结束时间',
  `status` varchar(32) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_defintion_code_id`(`defintion_code_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;



DROP TABLE IF EXISTS `makeflow_task`;
CREATE TABLE `makeflow_task`  (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `creator` varchar(64) NULL DEFAULT NULL COMMENT '创建人',
  `updator` varchar(64) NULL DEFAULT NULL COMMENT '更新人',
  `delete` tinyint NOT NULL COMMENT '是否删除',
  `name` varchar(128)  NOT NULL COMMENT '任务名',
  `hanlder` varchar(64) NOT NULL COMMENT '处理人',
  `flow_inst_id` varchar(64) NOT NULL COMMENT '流程id',
  `activity_id` varchar(64) NOT NULL COMMENT '节点id',
  `activity_code_id` varchar(64) NOT NULL COMMENT '节点codeId',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `complte_time` datetime null COMMENT '完成时间',
  `task_type` varchar(32) NOT NULL COMMENT '任务类型',
  `opinion` text NULL DEFAULT NULL COMMENT '意见',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_flow_inst_id`(`flow_inst_id`) USING BTREE,
  INDEX `idx_activity_id`(`activity_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


