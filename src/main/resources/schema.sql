-- `tags` 테이블 생성
CREATE TABLE IF NOT EXISTS `tags` (
  `tag_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `tag_uuid` BINARY(16) NOT NULL UNIQUE,
    `name` VARCHAR(50) NOT NULL,
    `created_at` DATETIME(6) NULL,
    `updated_at` DATETIME(6) NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `users` 테이블 생성
CREATE TABLE IF NOT EXISTS `users` (
   `user_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   `user_uuid` BINARY(16) NOT NULL UNIQUE,
    `profile_img_url` VARCHAR(100) NULL,
    `nickname` VARCHAR(50) NOT NULL,
    `created_at` DATETIME(6) NULL,
    `updated_at` DATETIME(6) NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `temporary_filters` 테이블 생성
CREATE TABLE IF NOT EXISTS `temporary_filters` (
   `temporary_filter_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   `temporary_filter_uuid` BINARY(16) NOT NULL UNIQUE,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(100) NOT NULL,
    `created_at` DATETIME(6) NULL,
    `updated_at` DATETIME(6) NULL,
    `user_id` BIGINT NOT NULL,
    CONSTRAINT `temporary_filter_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `filters` 테이블 생성
CREATE TABLE IF NOT EXISTS `filters` (
 `filter_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 `created_at` DATETIME(6) NULL,
    `updated_at` DATETIME(6) NULL,
    `user_id` BIGINT NULL,
    CONSTRAINT `filters_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `attributes` 테이블 생성
CREATE TABLE IF NOT EXISTS `attributes` (
    `attribute_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `brightness` FLOAT NULL,
    `sharpness` FLOAT NULL,
    `exposure` FLOAT NULL,
    `contrast` FLOAT NULL,
    `saturation` FLOAT NULL,
    `highlights` FLOAT NULL,
    `shadows` FLOAT NULL,
    `created_at` DATETIME(6) NULL,
    `updated_at` DATETIME(6) NULL,
    `filter_id` BIGINT NULL,
    CONSTRAINT `attribute_fk_filter_id` FOREIGN KEY (`filter_id`) REFERENCES `filters` (`filter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `filter_tags` 테이블 생성
CREATE TABLE IF NOT EXISTS `filter_tags` (
 `filter_tag_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 `filter_id` BIGINT NOT NULL,
 `tag_id` BIGINT NOT NULL,
 `created_at` DATETIME(6) NULL,
    `updated_at` DATETIME(6) NULL,
    CONSTRAINT `filter_tag_fk_filter_id` FOREIGN KEY (`filter_id`) REFERENCES `filters` (`filter_id`),
    CONSTRAINT `filter_tag_fk_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`tag_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `likes` 테이블 생성
CREATE TABLE IF NOT EXISTS `likes` (
   `like_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   `like_uuid` BINARY(16) NOT NULL UNIQUE,
    `user_id` BIGINT NOT NULL,
    `created_at` DATETIME(6) NULL,
    `updated_at` DATETIME(6) NULL,
    `filter_id` BIGINT NOT NULL,
    CONSTRAINT `likes_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `likes_fk_filter_id` FOREIGN KEY (`filter_id`) REFERENCES `filters` (`filter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `photos` 테이블 생성
CREATE TABLE IF NOT EXISTS `photos` (
    `photo_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `photo_uuid` BINARY(16) NOT NULL UNIQUE,
    `img_url` VARCHAR(100) NOT NULL,
    `created_at` DATETIME(6) NULL,
    `updated_at` DATETIME(6) NULL,
    `filter_id` BIGINT NULL,
    `temporary_filter_id` BIGINT NULL,
    CONSTRAINT `photo_fk_filter_id` FOREIGN KEY (`filter_id`) REFERENCES `filters` (`filter_id`),
    CONSTRAINT `photo_fk_temporary_filter_id` FOREIGN KEY (`temporary_filter_id`) REFERENCES `temporary_filters` (`temporary_filter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `purchasings` 테이블 생성
CREATE TABLE IF NOT EXISTS `purchasings` (
 `purchasing_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 `purchasing_uuid` BINARY(16) NOT NULL UNIQUE,
    `user_id` BIGINT NOT NULL,
    `created_at` DATETIME(6) NULL,
    `updated_at` DATETIME(6) NULL,
    `filter_id` BIGINT NULL,
    CONSTRAINT `purchasing_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `purchasing_fk_filter_id` FOREIGN KEY (`filter_id`) REFERENCES `filters` (`filter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `representation_img_urls` 테이블 생성
CREATE TABLE IF NOT EXISTS `representation_img_urls` (
 `representation_img_url_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 `representation_img_url_uuid` BINARY(16) NOT NULL UNIQUE,
    `cloudfront_url` VARCHAR(100) NOT NULL,
    `s3_url` VARCHAR(100) NOT NULL,
    `filter_id` BIGINT NOT NULL,
    CONSTRAINT `representation_fk_filter_id` FOREIGN KEY (`filter_id`) REFERENCES `filters` (`filter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `thumbnail_urls` 테이블 생성
CREATE TABLE IF NOT EXISTS `thumbnail_urls` (
    `thumbnail_url_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `thumbnail_url_uuid` BINARY(16) NOT NULL UNIQUE,
    `cloudfront_url` VARCHAR(100) NOT NULL,
    `s3_url` VARCHAR(100) NOT NULL,
    `filter_id` BIGINT NULL,
    `temporary_filter_id` BIGINT NULL,
    CONSTRAINT `thumbnail_url_fk_filter_id` FOREIGN KEY (`filter_id`) REFERENCES `filters` (`filter_id`),
    CONSTRAINT `thumbnail_url_fk_temporary_filter_id` FOREIGN KEY (`temporary_filter_id`) REFERENCES `temporary_filters` (`temporary_filter_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- `user_tags` 테이블 생성
CREATE TABLE IF NOT EXISTS `user_tags` (
   `user_tag_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   `user_id` BIGINT NOT NULL,
   `tag_id` BIGINT NOT NULL,
   CONSTRAINT `user_tag_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `user_tag_fk_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`tag_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
