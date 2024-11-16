CREATE TABLE IF NOT EXISTS topics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Auto-generated primary key
    uuid VARCHAR(36) NOT NULL UNIQUE, -- Unique identifier for each topic
    name VARCHAR(255), -- Topic's name
    description TEXT, -- Topic's description
    category_uuid VARCHAR(36), -- Foreign key reference to Category's uuid
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Creation time
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Update time
    created_by VARCHAR(255), -- Who created the topic
    updated_by VARCHAR(255), -- Who updated the topic
    version BIGINT, -- Version for optimistic locking
    is_active BOOLEAN NOT NULL -- Active status  
);

