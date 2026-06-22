-- Add slug column (nullable initially so existing rows can be populated first)
ALTER TABLE topics ADD COLUMN slug VARCHAR(255);

-- Generate base slugs from titles: lowercase, strip non-alphanumeric, collapse spaces to hyphens
UPDATE topics
SET slug = LOWER(
    TRIM(BOTH '-' FROM
        REGEXP_REPLACE(
            REGEXP_REPLACE(title, '[^a-zA-Z0-9 ]', '', 'g'),
            ' +', '-', 'g'
        )
    )
);

-- Fallback for any row that ended up with an empty slug
UPDATE topics
SET slug = 'topic-' || SUBSTRING(id::text, 1, 8)
WHERE slug IS NULL OR LENGTH(TRIM(slug)) = 0;

-- Resolve duplicates by appending first 6 chars of UUID (keeps slug human-readable)
WITH ranked AS (
    SELECT id,
           ROW_NUMBER() OVER (PARTITION BY slug ORDER BY created_at) AS rn
    FROM topics
)
UPDATE topics t
SET slug = t.slug || '-' || SUBSTRING(t.id::text, 1, 6)
FROM ranked r
WHERE t.id = r.id AND r.rn > 1;

-- Enforce NOT NULL and uniqueness
ALTER TABLE topics ALTER COLUMN slug SET NOT NULL;
CREATE UNIQUE INDEX uq_topics_slug ON topics(slug);
CREATE INDEX idx_topics_slug ON topics(slug);
