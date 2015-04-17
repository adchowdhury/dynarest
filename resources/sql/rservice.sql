SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `lcs_tbl_rservice_entity`;
DROP TABLE IF EXISTS `lcs_tbl_rservice_entity_conversion_config`;
DROP TABLE IF EXISTS `lcs_tbl_rservice_entity_conversion_config_type_lookup`;
DROP TABLE IF EXISTS `lcs_tbl_rservice_http_request_type`;
DROP TABLE IF EXISTS `lcs_tbl_rservice_method`;
DROP TABLE IF EXISTS `lcs_tbl_rservice_method_exception`;
DROP TABLE IF EXISTS `lcs_tbl_rservice_method_input_parameters`;
DROP TABLE IF EXISTS `lcs_tbl_rservice_method_parameters`;
DROP TABLE IF EXISTS `lcs_tbl_rservice_module`;
DROP TABLE IF EXISTS `lcs_tbl_rservice_validation_rules`;




CREATE TABLE `lcs_tbl_rservice_http_request_type` (
  `http_request_type_id` int(11) NOT NULL,
  `request_type_name` varchar(10) NOT NULL,
  PRIMARY KEY (`http_request_type_id`)
)

CREATE TABLE `lcs_tbl_rservice_entity_conversion_config_type_lookup` (
  `lookup_id` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`lookup_id`)
) 

CREATE TABLE `lcs_tbl_rservice_entity` (
  `entity_id` varchar(50) NOT NULL,
  `entity_class_name` varchar(255) NOT NULL,
  PRIMARY KEY (`entity_id`)
)

CREATE TABLE `lcs_tbl_rservice_entity_conversion_config` (
  `config_id` varchar(50) NOT NULL,
  `entity_id` varchar(50) NOT NULL,
  `config_type` int(11) NOT NULL,
  `property_name` varchar(255) NOT NULL,
  `transformer_class` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`config_id`),
  KEY `config_type` (`config_type`),
  KEY `entity_id` (`entity_id`),
  CONSTRAINT `lcs_tbl_rservice_entity_conversion_config_ibfk_1` FOREIGN KEY (`config_type`) REFERENCES `lcs_tbl_rservice_entity_conversion_config_type_lookup` (`lookup_id`),
  CONSTRAINT `lcs_tbl_rservice_entity_conversion_config_ibfk_3` FOREIGN KEY (`entity_id`) REFERENCES `lcs_tbl_rservice_entity` (`entity_id`)
)

CREATE TABLE `lcs_tbl_rservice_module` (
  `module_id` int(11) NOT NULL,
  `module_name` varchar(255) NOT NULL,
  `module_description` text NOT NULL,
  `module_version` decimal(10,2) NOT NULL,
  `parent_module` int(11) DEFAULT NULL,
  `module_url` varchar(255) NOT NULL,
  `api_class` varchar(1024) NOT NULL,
  PRIMARY KEY (`module_id`),
  UNIQUE KEY `module_url` (`module_url`),
  KEY `parent_module` (`parent_module`),
  CONSTRAINT `lcs_tbl_rservice_module_ibfk_1` FOREIGN KEY (`parent_module`) REFERENCES `lcs_tbl_rservice_module` (`module_id`)
) 

CREATE TABLE `lcs_tbl_rservice_method` (
  `method_id` varchar(50) NOT NULL,
  `method_name` varchar(255) NOT NULL,
  `method_url` varchar(1024) NOT NULL,
  `module_id` int(11) NOT NULL,
  `method_display_name` varchar(255) NOT NULL,
  `method_description` varchar(255) NOT NULL,
  `http_request_type_id` int(11) NOT NULL,
  `rbac_module_operation_id` int(11) NOT NULL,
  `output_parameter` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`method_id`),
  KEY `module_id` (`module_id`),
  KEY `http_request_type_id` (`http_request_type_id`),
  KEY `output_parameter` (`output_parameter`),
  CONSTRAINT `lcs_tbl_rservice_method_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `lcs_tbl_rservice_module` (`module_id`),
  CONSTRAINT `lcs_tbl_rservice_method_ibfk_2` FOREIGN KEY (`http_request_type_id`) REFERENCES `lcs_tbl_rservice_http_request_type` (`http_request_type_id`),
  CONSTRAINT `lcs_tbl_rservice_method_ibfk_3` FOREIGN KEY (`output_parameter`) REFERENCES `lcs_tbl_rservice_entity` (`entity_id`)
) 

CREATE TABLE `lcs_tbl_rservice_method_input_parameters` (
  `parameter_id` varchar(50) NOT NULL,
  `method_id` varchar(50) NOT NULL,
  `entity_id` varchar(50) NOT NULL,
  `sequence` int(11) NOT NULL DEFAULT '0',
  `parameter_name` varchar(255) NOT NULL,
  PRIMARY KEY (`parameter_id`),
  KEY `method_id` (`method_id`),
  KEY `entity_id` (`entity_id`),
  CONSTRAINT `lcs_tbl_rservice_method_input_parameters_ibfk_1` FOREIGN KEY (`method_id`) REFERENCES `lcs_tbl_rservice_method` (`method_id`),
  CONSTRAINT `lcs_tbl_rservice_method_input_parameters_ibfk_2` FOREIGN KEY (`entity_id`) REFERENCES `lcs_tbl_rservice_entity` (`entity_id`)
)

CREATE TABLE `lcs_tbl_rservice_method_exception` (
  `exception_id` varchar(50) NOT NULL,
  `method_id` varchar(50) NOT NULL,
  `exception_class_name` varchar(255) NOT NULL,
  `return_code` int(11) NOT NULL,
  PRIMARY KEY (`exception_id`),
  KEY `method_id` (`method_id`),
  CONSTRAINT `lcs_tbl_rservice_method_exception_ibfk_1` FOREIGN KEY (`method_id`) REFERENCES `lcs_tbl_rservice_method` (`method_id`)
) 