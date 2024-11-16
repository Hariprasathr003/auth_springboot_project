-- V2__Create_Category_Table.sql

-- Create the Category table
CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Auto-generated primary key
    uuid VARCHAR(36) NOT NULL UNIQUE, -- Unique identifier for each category
    name VARCHAR(255), -- Category's name
    created_by VARCHAR(255), -- Who created the category
    updated_by VARCHAR(255), -- Who updated the category
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Creation time
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Update time
    version BIGINT, -- Version for optimistic locking
    is_active BOOLEAN NOT NULL -- Active status
);
