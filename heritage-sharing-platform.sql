CREATE TABLE categories
(
    category_id   bigint AUTO_INCREMENT
        PRIMARY KEY,
    category_name varchar(100) NOT NULL,
    CONSTRAINT uk_categories_name
        UNIQUE (category_name)
)
    COLLATE = utf8mb4_unicode_ci;

CREATE TABLE tags
(
    tag_id   bigint AUTO_INCREMENT
        PRIMARY KEY,
    tag_name varchar(100) NOT NULL,
    CONSTRAINT uk_tags_name
        UNIQUE (tag_name)
)
    COLLATE = utf8mb4_unicode_ci;

CREATE TABLE users
(
    user_id              bigint AUTO_INCREMENT
        PRIMARY KEY,
    username             varchar(50)                NOT NULL,
    email                varchar(255)               NOT NULL,
    password_hash        varchar(255)               NOT NULL,
    personal_description text                       NULL,
    profile_picture_path varchar(500)               NULL,
    role                 varchar(20) DEFAULT 'user' NOT NULL,
    gender               varchar(10)                NULL,
    role_request_status  varchar(30) DEFAULT 'none' NOT NULL,
    CONSTRAINT uk_users_email
        UNIQUE (email)
)
    COLLATE = utf8mb4_unicode_ci;

CREATE TABLE resources
(
    resource_id           bigint AUTO_INCREMENT
        PRIMARY KEY,
    contributor_id        bigint       NOT NULL,
    title                 varchar(255) NOT NULL,
    description           text         NULL,
    category_id           bigint       NOT NULL,
    location              varchar(255) NULL,
    picture_path          varchar(500) NULL,
    video_path            varchar(500) NULL,
    copyright_declaration text         NULL,
    status                varchar(255) NOT NULL,
    submitted_at          datetime     NULL,
    approved_at           datetime     NULL,
    CONSTRAINT fk_resources_category
        FOREIGN KEY (category_id) REFERENCES categories (category_id),
    CONSTRAINT fk_resources_contributor
        FOREIGN KEY (contributor_id) REFERENCES users (user_id)
)
    COLLATE = utf8mb4_unicode_ci;

CREATE TABLE resource_actions
(
    action_id         bigint AUTO_INCREMENT
        PRIMARY KEY,
    resource_id       bigint       NOT NULL,
    action_type       varchar(255) NOT NULL,
    action_by_user_id bigint       NOT NULL,
    action_at         datetime     NOT NULL,
    feedback_text     text         NULL,
    CONSTRAINT fk_resource_actions_resource
        FOREIGN KEY (resource_id) REFERENCES resources (resource_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_resource_actions_user
        FOREIGN KEY (action_by_user_id) REFERENCES users (user_id)
)
    COLLATE = utf8mb4_unicode_ci;

CREATE TABLE resource_comments
(
    comment_id   bigint AUTO_INCREMENT
        PRIMARY KEY,
    resource_id  bigint   NOT NULL,
    user_id      bigint   NOT NULL,
    comment_text text     NOT NULL,
    commented_at datetime NOT NULL,
    CONSTRAINT fk_resource_comments_resource
        FOREIGN KEY (resource_id) REFERENCES resources (resource_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_resource_comments_user
        FOREIGN KEY (user_id) REFERENCES users (user_id)
            ON DELETE CASCADE
)
    COLLATE = utf8mb4_unicode_ci;

CREATE TABLE resources_tags
(
    resource_id bigint NOT NULL,
    tag_id      bigint NOT NULL,
    PRIMARY KEY (resource_id, tag_id),
    CONSTRAINT fk_resources_tags_resource
        FOREIGN KEY (resource_id) REFERENCES resources (resource_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_resources_tags_tag
        FOREIGN KEY (tag_id) REFERENCES tags (tag_id)
            ON DELETE CASCADE
)
    COLLATE = utf8mb4_unicode_ci;

