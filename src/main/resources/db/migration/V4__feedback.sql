CREATE TABLE feedback (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID,
    trying_to TEXT NOT NULL,
    went_wrong TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
