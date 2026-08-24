CREATE TABLE circles (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contribution_amount NUMERIC(19,2) NOT NULL,
    cycle_frequency VARCHAR(255) NOT NULL,
    terminology_profile VARCHAR(255) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE members (
    id UUID PRIMARY KEY,
    circle_id UUID NOT NULL REFERENCES circles(id),
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    payout_position INTEGER,
    joined_at TIMESTAMP NOT NULL
);

CREATE TABLE cycles (
    id UUID PRIMARY KEY,
    circle_id UUID NOT NULL REFERENCES circles(id),
    cycle_number INTEGER NOT NULL,
    collector_member_id UUID NOT NULL REFERENCES members(id),
    status VARCHAR(255) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP
);

CREATE TABLE contributions (
    id UUID PRIMARY KEY,
    cycle_id UUID NOT NULL REFERENCES cycles(id),
    member_id UUID NOT NULL REFERENCES members(id),
    amount NUMERIC(19,2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    paid_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);