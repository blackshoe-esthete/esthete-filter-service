-- "따뜻한", "부드러운", "평화로운", "차가운", "세련된" 태그 삽입
INSERT INTO tag (tag_uuid, name, created_at, updated_at) VALUES
                                                             (UNHEX(REPLACE('d20e2654-3c4a-4ebe-b1c9-5695ac2a6207', '-', '')), '따뜻한', NOW(), NOW()),
                                                             (UNHEX(REPLACE('fe96c294-b5f3-425e-a6de-8cc1b13beb5a', '-', '')), '부드러운', NOW(), NOW()),
                                                             (UNHEX(REPLACE('118ccbfb-8caf-498b-913a-16a315b3a859', '-', '')), '평화로운', NOW(), NOW()),
                                                             (UNHEX(REPLACE('4a0db2eb-f4bc-4fa3-ae47-8381ed0da1ab', '-', '')), '차가운', NOW(), NOW()),
                                                             (UNHEX(REPLACE('ae4a3cee-f7e3-48a1-8b0a-eb4d177b2267', '-', '')), '세련된', NOW(), NOW());
