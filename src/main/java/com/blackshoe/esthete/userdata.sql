INSERT INTO users (user_uuid, nickname, created_at, updated_at) VALUES
                                            (UNHEX(REPLACE('23e7b2b4-c1ac-4591-bb7f-c6706daf22aa', '-', '')), '홍보영', NOW(), NOW());