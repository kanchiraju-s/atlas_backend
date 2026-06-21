CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(100) NOT NULL,
    avatar_url TEXT,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_users_username ON users(username);

CREATE TABLE topics (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    created_by UUID,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_topics_title
ON topics(title);

CREATE INDEX idx_topics_created_by
ON topics(created_by);

CREATE TABLE topic_tags (
    topic_id UUID NOT NULL,
    tag VARCHAR(100) NOT NULL
);


CREATE TABLE drops (
    id UUID PRIMARY KEY,
    topic_id UUID NOT NULL,
    author_id UUID NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE drop_tags (
    drop_id UUID NOT NULL,
    tag VARCHAR(100) NOT NULL
);


CREATE TABLE discussions (
    id UUID PRIMARY KEY,
    drop_id UUID NOT NULL,
    author_id UUID NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);


CREATE TABLE notifications (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_discussions_drop_id
ON discussions(drop_id);

CREATE INDEX idx_discussions_author_id
ON discussions(author_id);

CREATE INDEX idx_notifications_user_id
ON notifications(user_id);