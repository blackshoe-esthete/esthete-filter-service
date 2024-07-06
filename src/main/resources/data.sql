-- "따뜻한", "부드러운", "평화로운", "차가운", "세련된" 태그 삽입
-- 태그 전체 삽입
INSERT INTO tags (tag_uuid, name) VALUES
                                       (UNHEX(REPLACE('d20e2654-3c4a-4ebe-b1c9-5695ac2a6207', '-', '')), '따뜻한'),
                                       (UNHEX(REPLACE('fe96c294-b5f3-425e-a6de-8cc1b13beb5a', '-', '')), '부드러운'),
                                       (UNHEX(REPLACE('118ccbfb-8caf-498b-913a-16a315b3a859', '-', '')), '평화로운'),
                                       (UNHEX(REPLACE('4a0db2eb-f4bc-4fa3-ae47-8381ed0da1ab', '-', '')), '차가운'),
                                       (UNHEX(REPLACE('ae4a3cee-f7e3-48a1-8b0a-eb4d177b2267', '-', '')), '세련된'),
                                       (UNHEX(REPLACE('1f479a8d-dab2-4d95-96c9-73d5f7382a01', '-', '')), '자연스러운'),
                                       (UNHEX(REPLACE('8969e7f1-2d1e-4a6d-b234-73c2aa7b24ff', '-', '')), '클래식한'),
                                       (UNHEX(REPLACE('9b11a16b-6786-4a28-8273-ff9e06b80318', '-', '')), '쾌활한'),
                                       (UNHEX(REPLACE('35009d25-65e1-48da-800e-44be42bf3b4e', '-', '')), '우아한'),
                                       (UNHEX(REPLACE('775f2020-070f-4ba1-b601-b456b4a8c165', '-', '')), '잔잔한'),
                                       (UNHEX(REPLACE('5b3a7d95-529d-42a4-a9eb-9e3fd3c42933', '-', '')), '풍요로운'),
                                       (UNHEX(REPLACE('e8e34cc1-27e1-4875-b474-90c3c1c2a7bb', '-', '')), '짙은'),
                                       (UNHEX(REPLACE('c27a3a02-134b-41de-a50e-27d722d2fbbd', '-', '')), '빈티지한'),
                                       (UNHEX(REPLACE('f9abf63a-bfd3-4960-840e-45841a1c50d3', '-', '')), '화사한'),
                                       (UNHEX(REPLACE('3c0c90ff-f775-474a-bc6d-83f1c8c30536', '-', '')), '모던한'),
                                       (UNHEX(REPLACE('8a3eb59d-263e-486a-aa9c-c672d2599a8b', '-', '')), '수수한'),
                                       (UNHEX(REPLACE('45633dc1-34d0-4a2c-8cc9-0bf7ecbb6e3c', '-', '')), '사랑스러운'),
                                       (UNHEX(REPLACE('8954a54d-2e9a-4a6e-b63e-081119c4a93c', '-', '')), '캐주얼한'),
                                       (UNHEX(REPLACE('89fde358-fd6b-4d6b-ba07-057e6c4e4b8b', '-', '')), '자유분방한'),
                                       (UNHEX(REPLACE('05e59ff6-7d1c-4497-850a-1683de7e7e59', '-', '')), '다채로운'),
                                       (UNHEX(REPLACE('1b2f7f85-5d71-4881-81ad-70a1b2d1c1a0', '-', '')), '차분한'),
                                       (UNHEX(REPLACE('61715019-1f05-45e6-91e2-13b50d818efb', '-', '')), '신비로운'),
                                       (UNHEX(REPLACE('b06da443-52c2-4398-9bdf-6a7f3f14f29f', '-', '')), '순수한'),
                                       (UNHEX(REPLACE('c5a5ff7b-0b40-4683-b796-5c295b1908a5', '-', '')), '고요한'),
                                       (UNHEX(REPLACE('ec2b0244-e37c-4fd0-8aee-c11c831124b3', '-', '')), '고급스러운')
    ON DUPLICATE KEY UPDATE tag_uuid = tag_uuid;

INSERT INTO users (user_uuid, nickname, created_at, updated_at, user_id, profile_img_url) VALUES
                                                                   (UNHEX(REPLACE('5a0db2eb-f4bc-4fa3-ae47-8381ed0da1ab', '-', '')), 'testuser', NOW(), NOW(), 1, 'default')
                                                                    ON DUPLICATE KEY UPDATE user_uuid = user_uuid;


INSERT INTO filters (created_at, filter_id, updated_at, user_id, filter_uuid, description, name)
VALUES
    (NOW(), 1, NOW(), 1, UNHEX(REPLACE('a720245d-d592-432c-b874-8033cd1b3b2a', '-', '')), 'testFilter1description', 'testFilter1'),
    (NOW(), 2, NOW(), 1, UNHEX(REPLACE('7e9e3ad3-d328-4377-8cbf-813d4c69ceaa', '-', '')), 'testFilter2description', 'testFilter2')
ON DUPLICATE KEY UPDATE filter_uuid = filter_uuid;

INSERT INTO representation_img_urls (filter_id, representation_img_url_id, temporary_filter_id, representation_img_url_uuid, cloudfront_url, s3_url)
VALUES
    (1, 1, NULL, UNHEX(REPLACE('62cc8b3f-4ddf-4518-beaa-63651df8403d', '-', '')), 'https://d30asln0ue7bf5.cloudfront.net/filter/a720245d-d592-432c-b874-8033cd1b3b2a/representation/6857bd1f-fef5-48e0-8ecb-1d4ee6fe96da.jpg', 'https://blackshoe-esthete-s3.s3.amazonaws.com/filter/a720245d-d592-432c-b874-8033cd1b3b2a/representation/6857bd1f-fef5-48e0-8ecb-1d4ee6fe96da.jpg'),
    (1, 2, NULL, UNHEX(REPLACE('4db99727-e273-47dd-9dde-d06563e5c53e', '-', '')), 'https://d30asln0ue7bf5.cloudfront.net/filter/a720245d-d592-432c-b874-8033cd1b3b2a/representation/cf755b9d-74c1-4994-9fa1-0d45c5fe3d7b.webp', 'https://blackshoe-esthete-s3.s3.amazonaws.com/filter/a720245d-d592-432c-b874-8033cd1b3b2a/representation/cf755b9d-74c1-4994-9fa1-0d45c5fe3d7b.webp'),
    (1, 3, NULL, UNHEX(REPLACE('838f5b54-3134-4b54-9b41-857c74df7857', '-', '')), 'https://d30asln0ue7bf5.cloudfront.net/filter/a720245d-d592-432c-b874-8033cd1b3b2a/representation/8619115d-d03d-4e1d-afc0-89fd4c72e0ea.jpg', 'https://blackshoe-esthete-s3.s3.amazonaws.com/filter/a720245d-d592-432c-b874-8033cd1b3b2a/representation/8619115d-d03d-4e1d-afc0-89fd4c72e0ea.jpg'),
    (2, 4, NULL, UNHEX(REPLACE('8fe63e8c-153a-4ed7-b661-744d0a7c4bdc', '-', '')), 'https://d30asln0ue7bf5.cloudfront.net/filter/7e9e3ad3-d328-4377-8cbf-813d4c69ceaa/representation/0437f09a-0713-4213-9183-9d32a7653d81.jpg', 'https://blackshoe-esthete-s3.s3.amazonaws.com/filter/7e9e3ad3-d328-4377-8cbf-813d4c69ceaa/representation/0437f09a-0713-4213-9183-9d32a7653d81.jpg')
ON DUPLICATE KEY UPDATE representation_img_url_uuid = representation_img_url_uuid;

INSERT INTO thumbnail_urls (filter_id, temporary_filter_id, thumbnail_url_id, thumbnail_url_uuid, cloudfront_url, s3_url)
VALUES
    (1, NULL, 1, UNHEX(REPLACE('c1a9fbe6-a3d3-4134-be35-d11127f4e70f', '-', '')), 'https://d30asln0ue7bf5.cloudfront.net/filter/a720245d-d592-432c-b874-8033cd1b3b2a/thumbnail/13675aac-5091-4804-b2c0-9b7597aafbdc.jpg', 'https://blackshoe-esthete-s3.s3.amazonaws.com/filter/a720245d-d592-432c-b874-8033cd1b3b2a/thumbnail/13675aac-5091-4804-b2c0-9b7597aafbdc.jpg'),
    (2, NULL, 2, UNHEX(REPLACE('c5081f94-d300-4c6c-b46d-6fc25d1d74ae', '-', '')), 'https://d30asln0ue7bf5.cloudfront.net/filter/7e9e3ad3-d328-4377-8cbf-813d4c69ceaa/thumbnail/304d6328-d13f-4705-a9cd-ff5422c5a571.jpg', 'https://blackshoe-esthete-s3.s3.amazonaws.com/filter/7e9e3ad3-d328-4377-8cbf-813d4c69ceaa/thumbnail/304d6328-d13f-4705-a9cd-ff5422c5a571.jpg')
ON DUPLICATE KEY UPDATE thumbnail_url_uuid = thumbnail_url_uuid;

INSERT INTO attributes (brightness, contrast, exposure, hue, saturation, temperature, sharpness, gray_scale, attribute_id, created_at, filter_id, temporary_filter_id, updated_at)
VALUES
    (5, 2, 5, 2, 2, 2, 5, 10, 4, NOW(), 1, NULL, NOW()),
    (1, 4, 3, 6, 5, 7, 2, 30, 5, NOW(), 2, NULL, NOW())
ON DUPLICATE KEY UPDATE attribute_id = attribute_id;

INSERT INTO filter_tags (created_at, filter_id, filter_tag_id, tag_id, temporary_filter_id, updated_at)
VALUES
    (NOW(), 1, 1, 1, NULL, NOW()),
    (NOW(), 1, 2, 2, NULL, NOW()),
    (NOW(), 1, 3, 5, NULL, NOW()),
    (NOW(), 2, 4, 1, NULL, NOW()),
    (NOW(), 2, 5, 2, NULL, NOW()),
    (NOW(), 2, 6, 5, NULL, NOW())
ON DUPLICATE KEY UPDATE filter_tag_id = filter_tag_id;


INSERT INTO temporary_filters (created_at, temporary_filter_id, updated_at, user_id, temporary_filter_uuid, description, name)
VALUES
    (NOW(), 1, NOW(), 1, UNHEX(REPLACE('a720245d-d592-424c-b874-8033cd1b3b2a', '-', '')), 'testTemporaryFilter1description', 'testTemporaryFilter1'),
    (NOW(), 2, NOW(), 1, UNHEX(REPLACE('7e9e3ad3-d328-2157-8cbf-813d4c69ceaa', '-', '')), 'testTemporaryFilter2description', 'testTemporaryFilter2')
ON DUPLICATE KEY UPDATE temporary_filter_uuid = temporary_filter_uuid;

