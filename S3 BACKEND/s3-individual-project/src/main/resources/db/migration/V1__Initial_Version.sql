-- User table
CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       username VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Video Game table
CREATE TABLE videogame (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           publisher VARCHAR(255),
                           name VARCHAR(255) NOT NULL,
                           released DATE NOT NULL,
                           genre VARCHAR(25) NOT NULL
);

-- Forum table
CREATE TABLE forum (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       videoGame BIGINT,
                       CONSTRAINT fk_forum_videogame FOREIGN KEY (videoGame) REFERENCES videogame(id)
);

-- Forum Post table
CREATE TABLE forum_post (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           forum BIGINT,
                           posted_by BIGINT,
                           post_content VARCHAR(255),
                           creation_date DATE,
                           CONSTRAINT fk_forum_post_forum FOREIGN KEY (forum) REFERENCES forum(id),
                           CONSTRAINT fk_forum_post_user FOREIGN KEY (posted_by) REFERENCES users(id)
);

-- Forum Post Rating table
CREATE TABLE forum_post_rating (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   rated_by BIGINT,
                                   forum_post BIGINT,
                                   rate_value BOOLEAN NOT NULL,
                                   CONSTRAINT fk_forum_post_rating_user FOREIGN KEY (rated_by) REFERENCES users(id),
                                   CONSTRAINT fk_forum_post_rating_post FOREIGN KEY (forum_post) REFERENCES forum_post(id), -- This line should reference 'forum_post'
                                   UNIQUE (rated_by, forum_post)
);
CREATE TABLE videogame_review (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `videogame` BIGINT NOT NULL,
                                  `reviewed_by` BIGINT NOT NULL,
                                  `review_content`VARCHAR(255),
                                  `creation_date` DATE,
                                  `review_value` BOOLEAN NOT NULL,
                                  PRIMARY KEY (`id`),
                                  CONSTRAINT `fk_videogame_review_videogame` FOREIGN KEY (`videogame`) REFERENCES `videogame` (`id`),
                                  CONSTRAINT `fk_videogame_review_user` FOREIGN KEY (`reviewed_by`) REFERENCES `users` (`id`)
);
CREATE TABLE videogame_review_rating (
                                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                                         `videogame_review_id` BIGINT NOT NULL,
                                         `rate_value` BOOLEAN NOT NULL,
                                         `rated_by` BIGINT NOT NULL,
                                         PRIMARY KEY (`id`),
                                         CONSTRAINT `fk_videogame_review_rating_review` FOREIGN KEY (`videogame_review_id`) REFERENCES `videogame_review` (`id`)
);

CREATE TABLE `friendship_requests` (
                                                     `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `requester_id` BIGINT NOT NULL,
                                                     `requested_id` BIGINT NOT NULL,
                                                     PRIMARY KEY (`id`),
    CONSTRAINT `fk_friendship_requests_requester`
    FOREIGN KEY (`requester_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_friendship_requests_requested`
    FOREIGN KEY (`requested_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
    );

-- Create 'friendships' table
CREATE TABLE `friendships` (
                                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                                             `requested_by_id` BIGINT NOT NULL,
                                             `accepted_by_id` BIGINT NOT NULL,
                                             PRIMARY KEY (`id`),
    CONSTRAINT `fk_friendships_requested_by`
    FOREIGN KEY (`requested_by_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_friendships_accepted_by`
    FOREIGN KEY (`accepted_by_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE `forum_post_comments` (
                                                     `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `forum_post_id` BIGINT NOT NULL,
                                                     `submitted_by_id` BIGINT NOT NULL,
                                                     `comment_content` TEXT NOT NULL,
                                                     `published_date` DATE NOT NULL,
                                                     PRIMARY KEY (`id`),
    CONSTRAINT `fk_forum_post_comments_post`
    FOREIGN KEY (`forum_post_id`)
    REFERENCES `forum_post` (`id`)
    ON DELETE CASCADE,
    CONSTRAINT `fk_forum_post_comments_user`
    FOREIGN KEY (`submitted_by_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    );