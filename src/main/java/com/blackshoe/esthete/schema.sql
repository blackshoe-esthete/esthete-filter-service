CREATE TABLE tag (
                     tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     tag_uuid BINARY(16) NOT NULL UNIQUE,
                     name VARCHAR(255),
                     created_at TIMESTAMP NOT NULL,
                     updated_at TIMESTAMP,
                     CONSTRAINT unique_uuid UNIQUE (tag_uuid)
);
