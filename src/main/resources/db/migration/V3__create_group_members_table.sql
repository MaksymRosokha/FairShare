CREATE TABLE IF NOT EXISTS group_members
(
    id         SERIAL PRIMARY KEY,
    user_id    INT       NOT NULL,
    group_id   INT       NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk__group_members__user_id__users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk__group_members__group_id__expense_groups FOREIGN KEY (group_id) REFERENCES expense_groups (id) ON DELETE CASCADE,
    CONSTRAINT uk__group_members__user_group UNIQUE (user_id, group_id)
);