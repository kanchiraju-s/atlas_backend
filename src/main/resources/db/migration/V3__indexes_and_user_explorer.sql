-- Explicit indexes for foreign key lookups
CREATE INDEX IF NOT EXISTS idx_drops_topic_id ON drops(topic_id);
CREATE INDEX IF NOT EXISTS idx_drops_author_id ON drops(author_id);
CREATE INDEX IF NOT EXISTS idx_discussions_parent_discussion_id ON discussions(parent_discussion_id);

-- Alpha user fields
ALTER TABLE users
    ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    ADD COLUMN IF NOT EXISTS explorer_number INTEGER,
    ADD COLUMN IF NOT EXISTS email VARCHAR(255),
    ADD COLUMN IF NOT EXISTS google_id VARCHAR(255);

CREATE UNIQUE INDEX IF NOT EXISTS idx_users_google_id ON users(google_id) WHERE google_id IS NOT NULL;
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_email ON users(email) WHERE email IS NOT NULL;

-- Explorer number sequence
CREATE SEQUENCE IF NOT EXISTS explorer_number_seq START 1 INCREMENT 1;
