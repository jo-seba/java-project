-- seller
INSERT INTO `seller_company` (`id`, `name`)
VALUES (1, "회사1"),
       (2, "히사입니다"),
       (3, "여긴회사");

INSERT INTO `seller` (`id`, `email`, `company_id`, `role`, `name`, `phone_number`)
VALUES (1, "onetefst@testa.comadsfadf", 1, 21, "아이야", "00011112222"),
       (2, "onetest1234@testa.comadsfadf", 1, 11, "홍길동", "11012341234"),
       (3, "twotabcd@testb.comasdfadsf", 1, 1, "아아호", "11043214321"),
       (4, "threehiehi@testc.comasdfasdf", 3, 1, "사람임", "13212452352");


-- venue
INSERT INTO `venue` (`id`, `name`, `road_address`, `latitude`, `longitude`, `capacity`)
VALUES (1, 'KSPO DOME (올림픽 체조경기장)', '서울 송파구 올림픽로 424', 37.521074, 127.116112, 15000),
       (2, '부산 벡스코', '부산 해운대구 APEC로 55', 35.169076, 129.136028, 7000),
       (3, '일산 킨텍스', '경기 고양시 일산서구 킨텍스로 217-60', 37.670299, 126.744533, 5000),
       (4, '고척스카이돔', '서울 구로구 경인로 430', 37.498225, 126.867105, 16000);

INSERT INTO `venue_layout` (`venue_id`, `company_id`, `name`, `is_local`, `created_at`, `updated_at`)
VALUES (1, 1, '일반 뮤지컬 레이아웃', true, NOW(), NOW()),
       (1, 2, '다음 뮤지컬 레이아웃', true, NOW(), NOW()),
       (1, 1, '일반 레이아웃', true, NOW(), NOW()),
       (2, 1, 'D 레이아웃', false, NOW(), NOW()),
       (2, 3, 'E 레이아웃', false, NOW(), NOW()),
       (3, 1, 'F 레이아웃', true, NOW(), NOW()),
       (3, 2, 'G 레이아웃', true, NOW(), NOW()),
       (4, 1, 'H 레이아웃', true, NOW(), NOW()),
       (4, 3, 'I 레이아웃', true, NOW(), NOW());

INSERT INTO `venue_area` (`layout_id`, `name`, `price`)
VALUES (1, 'A1 구역', 10000),
       (1, 'A2 구역', 15000),
       (2, 'B1 구역', 12000),
       (2, 'B2 구역', 16000),
       (3, 'C1 구역', 11000),
       (3, 'C2 구역', 14000),
       (4, 'D1 구역', 13000),
       (4, 'D2 구역', 17000),
       (5, 'E1 구역', 10000),
       (5, 'E2 구역', 15000),
       (6, 'F1 구역', 12000),
       (6, 'F2 구역', 16000),
       (7, 'G1 구역', 11000),
       (7, 'G2 구역', 14000),
       (8, 'H1 구역', 10000),
       (8, 'H2 구역', 15000),
       (9, 'I1 구역', 13000),
       (9, 'I2 구역', 17000);


-- concert
INSERT INTO `concert` (`company_id`, `venue_id`, `venue_layout_id`, `title`, `duration`, `started_at`,
                       `ended_at`,
                       `booking_started_at`, `booking_ended_at`, `is_sellable`, `is_deleted`, `created_at`,
                       `updated_at`, `thumbnail`, `description`)
VALUES (1, 1, 1, '아이유 전국투어 - 서울', 200, '2026-06-01 18:00:00', '2026-06-01 21:00:00',
        '2025-05-01 10:00:00', '2026-06-01 00:00:00', true, false, NOW(), NOW(), 'iu_seoul.jpg',
        '아이유의 2025년 전국투어 첫 공연'),
       (2, 2, 4, '방탄소년단 팬미팅 - 부산', 100, '2025-06-10 19:00:00', '2025-06-10 22:00:00',
        '2025-05-10 12:00:00', '2025-06-10 00:00:00', true, false, NOW(), NOW(), 'bts_busan.jpg', 'BTS 팬들과 함께하는 특별한 밤'),
       (1, 3, 6, '장범준 콘서트 - 대구', 120, '2025-06-15 17:00:00', '2025-06-15 20:00:00',
        '2025-05-15 09:00:00', '2025-06-15 00:00:00', true, false, NOW(), NOW(), 'jang_dg.jpg', '감성 가득한 여름밤의 콘서트'),
       (3, 1, 2, '임영웅 쇼케이스 - 서울', 200, '2025-06-20 19:00:00', '2025-06-20 21:30:00',
        '2025-05-20 10:00:00', '2025-06-20 00:00:00', true, false, NOW(), NOW(), 'lim_hero.jpg', '전 세대를 아우르는 트로트 열기'),
       (2, 4, 8, '에스파 미니콘 - 광주', 300, '2025-07-05 18:00:00', '2025-07-05 20:00:00',
        '2025-06-01 14:00:00', '2025-07-05 00:00:00', true, false, NOW(), NOW(), 'aespa_gj.jpg', '에스파와 광주 팬들의 특별한 만남'),
       (1, 2, 5, 'AKMU 어쿠스틱 나잇', 50, '2025-06-25 18:30:00', '2025-06-25 21:00:00',
        '2025-05-25 10:00:00', '2025-06-25 00:00:00', true, false, NOW(), NOW(), 'akmu_busan.jpg', '소박하고 따뜻한 음악 여행'),
       (3, 1, 3, '싸이 흠뻑쇼 - 서울', 240, '2025-07-15 19:30:00', '2025-07-15 22:30:00',
        '2025-06-01 12:00:00', '2025-07-15 00:00:00', true, false, NOW(), NOW(), 'psy_show.jpg', '물 뿌리는 여름 콘서트'),
       (1, 2, 7, '10CM 여름 콘서트', 30, '2025-07-20 18:00:00', '2025-07-20 20:00:00',
        '2025-06-15 11:00:00', '2025-07-20 00:00:00', true, false, NOW(), NOW(), '10cm_summer.jpg', '달콤한 감성 라이브'),
       (2, 3, 6, '이무진 콘서트 - 대구', 70, '2025-07-25 19:00:00', '2025-07-25 21:30:00',
        '2025-06-25 10:00:00', '2025-07-25 00:00:00', true, false, NOW(), NOW(), 'moo_dg.jpg', '이무진의 매력 넘치는 무대'),
       (3, 4, 9, '볼빨간사춘기 - 광주 소극장', 210, '2025-08-01 17:00:00', '2025-08-01 19:00:00',
        '2025-07-01 09:00:00', '2025-08-01 00:00:00', true, false, NOW(), NOW(), 'bb_spring.jpg', '소극장에서 만나는 볼사'),
       (2, 1, 3, '크러쉬 서울 공연', 140, '2025-08-10 18:30:00', '2025-08-10 20:30:00',
        '2025-07-10 10:00:00', '2025-08-10 00:00:00', true, false, NOW(), NOW(), 'crush_seoul.jpg', '감성 R&B의 정점'),
       (1, 2, 5, '선미 여름투어 - 부산', 130, '2025-08-15 19:00:00', '2025-08-15 21:00:00',
        '2025-07-15 12:00:00', '2025-08-15 00:00:00', true, false, NOW(), NOW(), 'sunmi_busan.jpg', '화려하고 파워풀한 무대'),
       (3, 3, 6, '적재 콘서트 - 대구', 220, '2025-08-20 18:00:00', '2025-08-20 20:00:00',
        '2025-07-20 09:00:00', '2025-08-20 00:00:00', true, false, NOW(), NOW(), 'jakjae_dg.jpg', '기타와 목소리로 채우는 밤'),
       (3, 4, 8, 'DAY6 밴드 라이브 - 광주', 120, '2025-08-25 19:30:00', '2025-08-25 22:00:00',
        '2025-07-25 11:00:00', '2025-08-25 00:00:00', true, false, NOW(), NOW(), 'day6_gj.jpg', '밴드 라이브의 진수'),
       (1, 1, 2, 'NCT 127 서울 팬콘', 140, '2025-09-01 18:00:00', '2025-09-01 21:00:00',
        '2025-08-01 10:00:00', '2025-09-01 00:00:00', true, false, NOW(), NOW(), 'nct127_fan.jpg', '서울에서 만나는 NCT 127'),
       (3, 2, 4, '폴킴 콘서트 - 부산', 30, '2025-09-05 18:00:00', '2025-09-05 20:30:00',
        '2025-08-05 13:00:00', '2025-09-05 00:00:00', true, false, NOW(), NOW(), 'pk_busan.jpg', '가을 감성 충전'),
       (2, 3, 7, '잔나비 대구 공연', 90, '2025-09-10 17:30:00', '2025-09-10 20:00:00',
        '2025-08-10 10:00:00', '2025-09-10 00:00:00', true, false, NOW(), NOW(), 'jannabi_dg.jpg', '추억을 소환하는 시간'),
       (1, 4, 9, '이하이 광주 콘서트', 40, '2025-09-15 19:00:00', '2025-09-15 21:00:00',
        '2025-08-15 11:00:00', '2025-09-15 00:00:00', true, false, NOW(), NOW(), 'leehi_gj.jpg', '이하이와 함께하는 가을 밤'),
       (3, 1, 3, 'ZICO - 서울 단독공연', 200, '2025-09-20 18:00:00', '2025-09-20 20:30:00',
        '2025-08-20 10:00:00', '2025-09-20 00:00:00', true, false, NOW(), NOW(), 'zico_seoul.jpg', '강렬한 무대, ZICO의 귀환'),
       (2, 2, 4, '라포엠 부산 리사이틀', 300, '2025-09-25 18:00:00', '2025-09-25 20:00:00',
        '2025-08-25 12:00:00', '2025-09-25 00:00:00', true, false, NOW(), NOW(), 'lapoem_busan.jpg', '클래식과 대중의 경계에서');

INSERT INTO `concert_ticketing_config` (`id`, `capacity`, `started_at`, `ended_at`)
VALUES (1, 100, '2025-05-01 10:00:00', '2026-06-01 00:00:00');

INSERT INTO `concert_schedule` (`id`, `concert_id`, `concert_date`, `started_at`, `ended_at`)
VALUES (1, 1, '2026-05-01 18:00:00', '2026-05-01 18:00:00', '2026-05-01 21:00:00');

INSERT INTO `concert_seat` (`id`, `concert_id`, `schedule_id`, `area_id`, `seat_row`, `seat_column`, `status`,
                            `hold_user_id`,
                            `hold_expired_at`)
VALUES (1, 1, 1, 1, 1, 1, 0, NULL, NULL),
       (2, 1, 1, 1, 1, 2, 0, NULL, NULL),
       (3, 1, 1, 1, 1, 3, 0, NULL, NULL),
       (4, 1, 1, 1, 2, 1, 0, NULL, NULL),
       (5, 1, 1, 1, 2, 2, 0, NULL, NULL),
       (6, 1, 1, 1, 2, 3, 0, NULL, NULL);

INSERT INTO `concert_category` (name)
VALUES ('발라드'),
       ('힙합'),
       ('락');

INSERT INTO `concert_concert_category` (`concert_id`, `category_id`)
VALUES (1, 1),
       (1, 2);

INSERT INTO `concert_detail_image` (concert_id, image_url)
VALUES (1, 'iu_seoul_1.jpg'),
       (1, 'iu_seoul_2.jpg'),
       (1, 'iu_seoul_3.jpg'),

       (2, 'bts_busan_1.jpg'),
       (2, 'bts_busan_2.jpg'),

       (3, 'jang_dg_1.jpg'),
       (3, 'jang_dg_2.jpg'),
       (3, 'jang_dg_3.jpg'),
       (3, 'jang_dg_4.jpg'),

       (4, 'lim_hero_1.jpg'),
       (5, 'aespa_gj_1.jpg'),
       (6, 'akmu_busan_1.jpg'),
       (7, 'psy_show_1.jpg'),
       (8, '10cm_summer_1.jpg'),
       (9, 'moo_dg_1.jpg'),
       (10, 'bb_spring_1.jpg'),
       (11, 'crush_seoul_1.jpg'),
       (12, 'sunmi_busan_1.jpg'),
       (13, 'jakjae_dg_1.jpg'),
       (14, 'day6_gj_1.jpg'),
       (15, 'nct127_fan_1.jpg'),
       (16, 'pk_busan_1.jpg'),
       (17, 'jannabi_dg_1.jpg'),
       (18, 'leehi_gj_1.jpg'),
       (19, 'zico_seoul_1.jpg'),
       (20, 'lapoem_busan_1.jpg');

