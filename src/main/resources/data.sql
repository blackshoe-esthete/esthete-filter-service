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


-- 유저 2 추가
INSERT INTO users (user_uuid, nickname, created_at, updated_at, user_id, profile_img_url) VALUES
                                                                                              (UNHEX(REPLACE('65b87d26-9482-4984-843a-bee6efb3d9cd', '-', '')), '이수빈', NOW(), NOW(), 1, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png'),
                                                                                              (UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440000', '-', '')), '홍보영', NOW(), NOW(), 2, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png'),
                                                                                              (UNHEX(REPLACE('4b55df30-7a87-49b2-bd56-e0f5210a9a5d', '-', '')), '한상호', NOW(), NOW(), 3, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png'),
                                                                                              (UNHEX(REPLACE('86a93e29-0f46-4a65-9c49-7fbf7c13e9f2', '-', '')), '초록나무', NOW(), NOW(), 4, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png'),
                                                                                              (UNHEX(REPLACE('4d4be043-5d57-45eb-a3fb-dc48e5e452b0', '-', '')), '행복한미소', NOW(), NOW(), 5, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png'),
                                                                                              (UNHEX(REPLACE('d843ab0d-3b90-4d78-9812-7a5f3c11d312', '-', '')), '작은별', NOW(), NOW(), 6, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png'),
                                                                                              (UNHEX(REPLACE('f1a92b4d-8d08-4d02-9a9b-07c9979e9b24', '-', '')), '푸른하늘', NOW(), NOW(), 7, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png'),
                                                                                              (UNHEX(REPLACE('b21b5a4c-96f8-4a3b-af6e-7bf50b065365', '-', '')), '노을빛', NOW(), NOW(), 8, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png'),
                                                                                              (UNHEX(REPLACE('6b6590ac-003f-4c8b-82c3-0e2e24ce7cc8', '-', '')), '햇살가득', NOW(), NOW(), 9, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png'),
                                                                                              (UNHEX(REPLACE('fda6f365-0a2d-4b48-ae6c-935f1f6b2f2a', '-', '')), '별빛소리', NOW(), NOW(), 10, 'https://d1g6qszf7cmafu.cloudfront.net/default/profile.png')
ON DUPLICATE KEY UPDATE user_uuid = user_uuid;

-- 유저 2의 새로운 필터 추가
INSERT INTO filters (created_at, filter_id, updated_at, user_id, filter_uuid, description, name)
VALUES
    (NOW(), 1, NOW(), 1, UNHEX(REPLACE('0e917e2a-620d-48e5-8881-3195d91b6a3d', '-', '')), '한여름 밤의 꿈같은 필터', '한여름 밤의 꿈'),
    (NOW(), 2, NOW(), 1, UNHEX(REPLACE('ddaa616c-a841-483f-82be-e2567aed6da2', '-', '')), '맑은 하늘 감성 필터', '하늘'),
    (NOW(), 3, NOW(), 1, UNHEX(REPLACE('903cddde-7dc2-4b03-b41e-ee53dd32f144', '-', '')), 'MZ 감성 느낌의 필터', '힙'),
    (NOW(), 4, NOW(), 10, UNHEX(REPLACE('0b96d89e-9741-4ce4-8a27-879f2219e7bb', '-', '')), '겨울 느낌의 필터', '몽글몽글'),
    (NOW(), 5, NOW(), 10, UNHEX(REPLACE('15bff8d7-8b0f-4762-8f6b-5b88fc0aed66', '-', '')), '따뜻한 느낌의 필터', '따뜻한 해'),
    (NOW(), 6, NOW(), 3, UNHEX(REPLACE('7aa6efdb-cae8-47de-ba9b-d0bc7b47ab8f', '-', '')), '부드러운 느낌의 필터', '부드러운 바람'),
    (NOW(), 7, NOW(), 3, UNHEX(REPLACE('1c7f678b-6647-4d0f-b5cd-687cd887d947', '-', '')), '평화로운 느낌의 필터', '평화로운 세계'),
    (NOW(), 8, NOW(), 4, UNHEX(REPLACE('5930d24f-526f-4138-9fc9-1c9cb5977f5b', '-', '')), '차가운 느낌의 필터', '차가운 향기'),
    (NOW(), 9, NOW(), 4, UNHEX(REPLACE('8102cdc3-3a2b-40fa-b816-9e3e8a08033f', '-', '')), '세련된 느낌의 필터', '세련된 냄세'),
    (NOW(), 10, NOW(), 4, UNHEX(REPLACE('9b3b7a31-addb-43b2-bfa7-6bde8c1a0834', '-', '')), '빈티지한 느낌의 필터', '빈티지 세계'),
    (NOW(), 11, NOW(), 7, UNHEX(REPLACE('38e35624-8d6d-442f-8a9f-50ab3a5441a8', '-', '')), '클래식한 느낌의 필터', '클래식 노래'),
    (NOW(), 12, NOW(), 5, UNHEX(REPLACE('ed5603ca-9f4d-4f14-a750-4d9117a46253', '-', '')), '화사한 느낌의 필터', '화사한 화장'),
    (NOW(), 13, NOW(), 5, UNHEX(REPLACE('0c42493a-5063-4ee3-88c4-148e603bd994', '-', '')), '자연스러운 느낌의 필터', '자연스러운 메이크업'),
    (NOW(), 14, NOW(), 8, UNHEX(REPLACE('92b37a68-b13e-4b18-8333-ed0ea3a3b32e', '-', '')), '쾌활한 느낌의 필터', '쾌활한 분위기'),
    (NOW(), 15, NOW(), 6, UNHEX(REPLACE('2646b676-d990-4823-bcf5-d4137e3404ae', '-', '')), '잔잔한 느낌의 필터', '잔잔한 분위기')
ON DUPLICATE KEY UPDATE filter_uuid = filter_uuid;

INSERT INTO temporary_filters (created_at, temporary_filter_id, updated_at, user_id, temporary_filter_uuid, description, name)
VALUES
    (NOW(), 1, NOW(), 1, UNHEX(REPLACE('a720245d-d592-424c-b874-8033cd1b3b2a', '-', '')), 'testTemporaryFilter1description', 'testTemporaryFilter1'),
    (NOW(), 2, NOW(), 1, UNHEX(REPLACE('7e9e3ad3-d328-2157-8cbf-813d4c69ceaa', '-', '')), 'testTemporaryFilter2description', 'testTemporaryFilter2')
ON DUPLICATE KEY UPDATE temporary_filter_uuid = temporary_filter_uuid;

INSERT INTO attributes (brightness, contrast, exposure, hue, saturation, temperature, sharpness, gray_scale, attribute_id, created_at, filter_id, temporary_filter_id, updated_at)
VALUES
    (5, 2, 5, 2, 2, 2, 5, 10, 1, NOW(), 1, NULL, NOW()),
    (1, 4, 3, 6, 5, 7, 2, 30, 2, NOW(), 2, NULL, NOW()),
    (5, 2, 5, 3, 6, 5, 4, 10, 3, NOW(), 3, NULL, NOW()),
    (4, 3, 4, 2, 5, 6, 3, 20, 4, NOW(), 4, NULL, NOW()),
    (3, 4, 3, 1, 4, 7, 2, 30, 5, NOW(), 5, NULL, NOW()),
    (6, 5, 6, 4, 7, 6, 5, 40, 6, NOW(), 6, NULL, NOW()),
    (7, 6, 7, 5, 8, 7, 6, 50, 7, NOW(), 7, NULL, NOW()),
    (8, 7, 8, 6, 9, 8, 7, 60, 8, NOW(), 8, NULL, NOW()),
    (2, 8, 2, 7, 2, 9, 8, 70, 9, NOW(), 9, NULL, NOW()),
    (9, 9, 9, 8, 10, 10, 9, 80, 10, NOW(), 10, NULL, NOW()),
    (10, 10, 10, 9, 11, 11, 10, 90, 11, NOW(), 11, NULL, NOW()),
    (11, 11, 11, 10, 12, 12, 11, 100, 12, NOW(), 12, NULL, NOW()),
    (12, 12, 12, 11, 13, 13, 12, 110, 13, NOW(), 13, NULL, NOW()),
    (13, 13, 13, 12, 14, 14, 13, 120, 14, NOW(), 14, NULL, NOW()),
    (14, 14, 14, 13, 15, 15, 14, 130, 15, NOW(), 15, NULL, NOW())
ON DUPLICATE KEY UPDATE attribute_id = attribute_id;





-- 새 필터의 대표 이미지 추가 (기존 URL 사용)
INSERT INTO representation_img_urls (filter_id, representation_img_url_id, temporary_filter_id, representation_img_url_uuid, cloudfront_url, s3_url)
VALUES
    (1, 1, NULL, UNHEX(REPLACE('62cc8b3f-4ddf-4518-beaa-63651df8403d', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/vintage/vintage1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/vintage/vintage1.jpg'),
    (2, 2, NULL, UNHEX(REPLACE('4db99727-e273-47dd-9dde-d06563e5c53e', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/vintage/vintage2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/vintage/vintage2.jpg'),
    (3, 3, NULL, UNHEX(REPLACE('838f5b54-3134-4b54-9b41-857c74df7857', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/vintage/vintage3.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/vintage/vintage3.jpg'),
    (4, 4, NULL, UNHEX(REPLACE('8fe63e8c-153a-4ed7-b661-744d0a7c4bdc', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/sophisticated/sophisticated1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/sophisticated/sophisticated1.jpg'),
    (5, 5, NULL, UNHEX(REPLACE('72cc8b3f-4ddf-4518-beaa-63651df8403e', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/sophisticated/sophisticated2.jpg  ', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/sophisticated/sophisticated2.jpg'),
    (6, 6, NULL, UNHEX(REPLACE('9fe63e8c-153a-4ed7-b661-744d0a7c4bdd', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/sophisticated/sophisticated3.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/sophisticated/sophisticated3.jpg'),
    (7, 7, NULL, UNHEX(REPLACE('f74cfc40-898c-465e-a8a7-258170663beb', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/elegant/elegant1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/elegant/elegant1.jpg'),
    (8, 8, NULL, UNHEX(REPLACE('f74cfc40-898c-465e-a8a7-258170663beb', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/elegant/elegant2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/elegant/elegant2.jpg'),
    (9, 9, NULL, UNHEX(REPLACE('f6f62d00-08ae-46d6-97d7-b5b5202a1148', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/elegant/elegant3.jpg ', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/elegant/elegant3.jpg'),
    (10, 10, NULL, UNHEX(REPLACE('86ce7907-f5c7-4a7e-8353-f3335ba1d506', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/calm/calm1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/calm/calm1.jpg '),
    (11, 11, NULL, UNHEX(REPLACE('ac1ea6fb-fffd-474f-a892-31f8c2484e7e', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/calm/calm2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/calm/calm2.jpg'),
    (12, 12, NULL, UNHEX(REPLACE('847a7e20-e6f1-4e14-ac2b-29d656c7a34d', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/calm/calm3.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/calm/calm3.jpg'),
    (13, 13, NULL, UNHEX(REPLACE('f533ba08-32c2-42b3-8296-911450abfd89', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/abundant/abundant1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/abundant/abundant1.jpg'),
    (14, 14, NULL, UNHEX(REPLACE('68d6c6eb-b046-4fce-8984-f7835a5becd8', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/abundant/abundant2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/abundant/abundant2.jpg'),
    (15, 15, NULL, UNHEX(REPLACE('ec8c8a07-6d1c-47a1-8f2a-0e5e002c15c5', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/abundant/abundant3.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/abundant/abundant3.jpg')
    ON DUPLICATE KEY UPDATE representation_img_url_uuid = representation_img_url_uuid;

-- 새 필터의 썸네일 추가 (기존 URL 사용)
INSERT INTO thumbnail_urls (filter_id, temporary_filter_id, thumbnail_url_id, thumbnail_url_uuid, cloudfront_url, s3_url)
VALUES
    (1, NULL, 1, UNHEX(REPLACE('c1a9fbe6-a3d3-4134-be35-d11127f4e70f', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/cold/cold1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/cold/cold1.jpg'),
    (2, NULL, 2, UNHEX(REPLACE('c5081f94-d300-4c6c-b46d-6fc25d1d74ae', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/cold/cold2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/cold/cold2.jpg'),
    (NULL, 1, 3, UNHEX(REPLACE('c1a9fbe6-aa36-4134-be35-d11127f4e70f', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/cold/cold3.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/cold/cold3.jpg'),
    (NULL, 2, 4, UNHEX(REPLACE('c5081f94-db14-4c6c-b46d-6fc25d1d74ae', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/peaceful/peaceful1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/peaceful/peaceful1.jpg'),
    (3, NULL, 5, UNHEX(REPLACE('d1a9fbe6-a3d3-4134-be35-d11127f4e70f', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/peaceful/peaceful2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/peaceful/peaceful2.jpg'),
    (4, NULL, 6, UNHEX(REPLACE('d5081f94-d300-4c6c-b46d-6fc25d1d74af', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/peaceful/peaceful2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/peaceful/peaceful3.jpg'),
    (5, NULL, 7, UNHEX(REPLACE('6fa90f8f-abe7-4c7d-99f3-d4e04ce0dac6', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/deep/deep1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/deep/deep1.jpg'),
    (6, NULL, 8, UNHEX(REPLACE('0d0700ce-bab5-48b9-a128-b6e7905d6f9e', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/deep/deep2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/deep/deep2.jpg'),
    (7, NULL, 9, UNHEX(REPLACE('72da5b22-e6f0-4a14-9e1f-bdb440e0beec', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/deep/deep3.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/deep/deep3.jpg'),
    (8, NULL, 10, UNHEX(REPLACE('5da6c8ce-9de2-4ec3-be6b-b5e6e4002871', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/bright/bright1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/bright/bright1.jpg'),
    (9, NULL, 11, UNHEX(REPLACE('7762b3db-2a42-4c27-9c1c-f99741efd8ee', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/bright/bright2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/bright/bright2.jpg'),
    (10, NULL, 12, UNHEX(REPLACE('46c434e7-b2ef-403c-902a-c9c25af3ff95', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/bright/bright3.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/bright/bright3.jpg'),
    (11, NULL, 13, UNHEX(REPLACE('520dc26c-cc2d-4fcd-8aff-d6b68b7ef5e1', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/modern/modern1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/modern/modern1.jpg'),
    (12, NULL, 14, UNHEX(REPLACE('da9af6ed-fc22-469f-b3c5-bdc4de5a0123', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/modern/modern2.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/modern/modern2.jpg'),
    (13, NULL, 15, UNHEX(REPLACE('9b6ce540-87ad-4f5a-b2b3-3f790a57a899', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/modern/modern3.jpg', ' https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/modern/modern3.jpg'),
    (14, NULL, 16, UNHEX(REPLACE('5fb7a8c9-3cbf-4f7d-b94c-8c7a23da1f69', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/simple/simple1.jpg', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/simple/simple1.jpg'),
    (15, NULL, 17, UNHEX(REPLACE('93fcee0e-0d6d-4570-ad97-74d4f3ad75f1', '-', '')), 'https://d1g6qszf7cmafu.cloudfront.net/filter/test/simple/simple2.jpg ', 'https://esthete-bucket.s3.ap-northeast-2.amazonaws.com/filter/test/simple/simple2.jpg')
    ON DUPLICATE KEY UPDATE thumbnail_url_uuid = thumbnail_url_uuid;


-- 새 필터의 태그 추가
INSERT INTO filter_tags (created_at, filter_id, filter_tag_id, tag_id, temporary_filter_id, updated_at)
VALUES
    (NOW(), 1, 1, 1, NULL, NOW()),
    (NOW(), 1, 2, 2, NULL, NOW()),
    (NOW(), 1, 3, 5, NULL, NOW()),
    (NOW(), 2, 4, 1, NULL, NOW()),
    (NOW(), 2, 5, 2, NULL, NOW()),
    (NOW(), 2, 6, 5, NULL, NOW()),
    (NOW(), 3, 7, 3, NULL, NOW()),
    (NOW(), 3, 8, 4, NULL, NOW()),
    (NOW(), 3, 18, 5, NULL, NOW()),
    (NOW(), 4, 9, 6, NULL, NOW()),
    (NOW(), 4, 10, 7, NULL, NOW()),
    (NOW(), 4, 19, 1, NULL, NOW()),
    (NOW(), 5, 9, 1, NULL, NOW()),
    (NOW(), 5, 20, 2, NULL, NOW()),
    (NOW(), 6, 10, 2, NULL, NOW()),
    (NOW(), 6, 21, 3, NULL, NOW()),
    (NOW(), 7, 11, 3, NULL, NOW()),
    (NOW(), 7, 22, 4, NULL, NOW()),
    (NOW(), 8, 12, 4, NULL, NOW()),
    (NOW(), 8, 23, 5, NULL, NOW()),
    (NOW(), 9, 13, 5, NULL, NOW()),
    (NOW(), 9, 24, 6, NULL, NOW()),
    (NOW(), 10, 14, 6, NULL, NOW()),
    (NOW(), 10, 24, 7, NULL, NOW()),
    (NOW(), 11, 15, 7, NULL, NOW()),
    (NOW(), 11, 17, 1, NULL, NOW()),
    (NOW(), 12, 16, 8, NULL, NOW()),
    (NOW(), 12, 5, 2, NULL, NOW()),
    (NOW(), 13, 17, 9, NULL, NOW()),
    (NOW(), 13, 15, 3, NULL, NOW())
    ON DUPLICATE KEY UPDATE filter_tag_id = filter_tag_id;


-- 유저 1의 구매 정보 추가 (유저 2의 필터 구매)
INSERT INTO purchasings (purchasing_uuid, user_id, filter_id, created_at, updated_at)
VALUES
    (UNHEX(REPLACE('f47ac10b-58cc-4372-a567-0e02b2c3d479', '-', '')), 1, 3, NOW(), NOW()),
    (UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440000', '-', '')), 1, 4, NOW(), NOW())
    ON DUPLICATE KEY UPDATE purchasing_uuid = VALUES(purchasing_uuid);