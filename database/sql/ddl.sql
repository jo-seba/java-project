-- user
DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
    `id`	        bigint	    NOT NULL    auto_increment  PRIMARY KEY,
    `name`	        varchar(24)	NULL,
    `phone_number`	varchar(11)	NULL,
    `created_at`	timestamp	NULL        DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`	timestamp   NULL
);

DROP TABLE IF EXISTS user_alarm;
CREATE TABLE `user_alarm` (
    `id`	                bigint	        NOT NULL    auto_increment  PRIMARY KEY,
    `fcm_token`	            varchar(255)	NOT NULL    DEFAULT "",
    `marketing`	            boolean	        NOT NULL    DEFAULT false,
    `marketing_agreed_at`   timestamp       NULL,
    `concert`	            boolean	        NOT NULL    DEFAULT false,
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS user_agreement;
CREATE TABLE `user_agreement` (
    `id`	            bigint      NOT NULL    auto_increment  PRIMARY KEY,
    `private`   	    boolean     NOT NULL    DEFAULT false,
    `marketing`     	boolean     NOT NULL    DEFAULT false,
    `last_updated_at`	timestamp   NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- seller
DROP TABLE IF EXISTS seller_company;
CREATE TABLE `seller_company` (
    `id`	        int     	NOT NULL    auto_increment  PRIMARY KEY,
    `name`	        varchar(30)	NOT NULL,
    `created_at`	timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`	timestamp   NULL
);

DROP TABLE IF EXISTS seller;
CREATE TABLE `seller` (
    `id`	        int     	NOT NULL    auto_increment  PRIMARY KEY,
    `email`         varchar(50) NOT NULL    UNIQUE,
    `company_id`	int     	NOT NULL,
    `role`          tinyint     NOT NULL    DEFAULT 0   COMMENT "1: MEMBER, 11: MANAGER, 21: OWNER",
    `name`	        varchar(24)	NOT NULL,
    `phone_number`	varchar(20)	NOT NULL,
    `created_at`	timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`	timestamp   NULL,
    FOREIGN KEY (company_id) REFERENCES seller_company(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- venue
DROP TABLE IF EXISTS venue;
CREATE TABLE `venue` (
    `id`    	    int             NOT NULL    auto_increment  PRIMARY KEY,
    `name`	        varchar(50) 	NOT NULL,
    `capacity`  	mediumint	    NOT NULL,
    `road_address`  varchar(100)	NOT NULL,
    `latitude`      decimal(9, 6)   NOT NULL,
    `longitude`     decimal(9, 6)   NOT NULL,
    `created_at`    timestamp	    NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    timestamp	    NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`	timestamp       NULL
);

DROP TABLE IF EXISTS venue_layout;
CREATE TABLE `venue_layout` (
    `id`	        bigint	    NOT NULL    auto_increment  PRIMARY KEY,
    `venue_id`	    int	        NOT NULL,
    `company_id`    int         NOT NULL,
    `name`  	    varchar(20)	NOT NULL,
    `is_local`      boolean     NOT NULL,
    `created_at`    timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`	timestamp   NULL,
    FOREIGN KEY (venue_id) REFERENCES venue(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (company_id) REFERENCES seller_company(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS venue_area;
CREATE TABLE `venue_area` (
    `id`	    bigint          NOT NULL auto_increment  PRIMARY KEY,
    `layout_id`	bigint          NOT NULL,
    `name`  	varchar(40)	    NOT NULL,
    `price`	    decimal(9, 0)   NOT NULL,
    FOREIGN KEY (layout_id) REFERENCES venue_layout(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS venue_row;
CREATE TABLE `venue_row` (
    `id`	        bigint	    NOT NULL    auto_increment  PRIMARY KEY,
    `area_id`    	bigint	    NOT NULL,
    `name`	        varchar(2)	NULL,
    `start_column`	smallint	NULL,
    `end_column`	smallint	NULL,
    FOREIGN KEY (area_id) REFERENCES venue_area(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- concert
DROP TABLE IF EXISTS concert;
CREATE TABLE `concert` (
    `id`    	            bigint	        NOT NULL    auto_increment  PRIMARY KEY,
    `company_id`	        int	            NOT NULL,
    `venue_id`          	int	            NOT NULL,
    `venue_layout_id`       int	            NOT NULL,
    `title`             	varchar(40)	    NOT NULL,
    `duration`             	smallint   	    NOT NULL,
    `view_count`            bigint   	    NOT NULL    DEFAULT 0,
    `started_at`	        timestamp	    NOT NULL,
    `ended_at`	            timestamp	    NOT NULL,
    `booking_started_at`	timestamp	    NOT NULL,
    `booking_ended_at`	    timestamp	    NOT NULL,
    `is_sellable`       	boolean 	    NOT NULL	DEFAULT true,
    `is_deleted`	        boolean	        NOT NULL	DEFAULT false,
    `created_at`        	timestamp	    NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        	timestamp	    NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`	        timestamp       NULL,
    `thumbnail`	            varchar(255)	NOT NULL,
    `description`	        varchar(255)	NOT NULL    DEFAULT '',
    FOREIGN KEY (company_id) REFERENCES seller_company(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (venue_id) REFERENCES venue(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS concert_ticketing_config;
CREATE TABLE `concert_ticketing_config` (
    `id`            bigint      NOT NULL    PRIMARY KEY,
    `capacity`      int         NOT NULL    DEFAULT 500,
    `started_at`	timestamp	NOT NULL,
    `ended_at`	    timestamp	NOT NULL,
    FOREIGN KEY (id) REFERENCES concert(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS concert_category;
CREATE TABLE `concert_category` (
    `id`    int         NOT NULL    auto_increment PRIMARY KEY,
    `name`  varchar(20) NOT NULL
);

DROP TABLE IF EXISTS concert_concert_category;
CREATE TABLE `concert_concert_category` (
    `id`            bigint  NOT NULL    auto_increment PRIMARY KEY,
    `concert_id`    bigint  NOT NULL,
    `category_id`   int     NOT NULL,
    FOREIGN KEY (concert_id) REFERENCES concert(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (category_id) REFERENCES concert_category(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS concert_detail_image;
CREATE TABLE `concert_detail_image` (
    `id`	        bigint	        NOT NULL	auto_increment  PRIMARY KEY,
    `concert_id`	bigint	        NOT NULL,
    `image_url`	    varchar(255)	NOT NULL,
    FOREIGN KEY (concert_id) REFERENCES concert(id) ON DELETE CASCADE ON UPDATE CASCADE
);


DROP TABLE IF EXISTS concert_schedule;
CREATE TABLE `concert_schedule` (
    `id`	        bigint	    NOT NULL    auto_increment  PRIMARY KEY,
    `concert_id`	bigint	    NOT NULL,
    `concert_date`	timestamp	NOT NULL,
    `started_at`	timestamp	NOT NULL,
    `ended_at`  	timestamp	NOT NULL,
    `created_at`	timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    `updated_at`	timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`	timestamp   NULL,
    FOREIGN KEY (concert_id) REFERENCES concert(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS concert_seat;
CREATE TABLE `concert_seat` (
    `id`        	    bigint	    NOT NULL    auto_increment  PRIMARY KEY,
    `concert_id`        bigint	    NOT NULL,
    `schedule_id`       bigint	    NOT NULL,
    `area_id`	        bigint	    NOT NULL,
    `seat_row`	        smallint    NOT NULL,
    `seat_column`	    smallint	NOT NULL,
    `status`    	    tinyint	    NOT NULL    DEFAULT 0   COMMENT "0: available, 1: hold, 2: sold",
    `hold_user_id`  	bigint	    NULL,
    `hold_expired_at`	timestamp	NULL,
    FOREIGN KEY (concert_id) REFERENCES concert(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES concert_schedule(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (area_id) REFERENCES venue_area(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (hold_user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (schedule_id, area_id, seat_row, seat_column)
);

DROP TABLE IF EXISTS concert_order;
CREATE TABLE `concert_order` (
    `id`	        bigint	        NOT NULL    auto_increment  PRIMARY KEY,
    `user_id`	    bigint	        NOT NULL,
    `concert_id`	bigint	        NOT NULL,
    `schedule_id`	bigint	        NOT NULL,
    `seat_id`	    bigint	        NOT NULL,
    `price`	        decimal(9, 0)   NULL,
    `created_at`	timestamp	    NULL,
    `updated_at`    timestamp	    NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (concert_id) REFERENCES concert(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES concert_schedule(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (seat_id) REFERENCES concert_seat(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (user_id, schedule_id)
);
