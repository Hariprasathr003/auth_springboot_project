CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Primary key, Auto generated for each user
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT(UUID()), --  Unique Identifier for each user
    name VARCHAR(50) NOT NULL, -- Name of the user, mandatory field
    phone_number VARCHAR(20) NOT NULL, -- User's phone number, mandatory field
    email VARCHAR(50) NOT NULL, -- User's email, mandatory field
    user_name VARCHAR(50) BINARY NOT NULL, -- Username, case-sensitive and must be unique, mandatory field
    password VARCHAR(255) NOT NULL, -- Encrypted password for the user, mandatory field
    role ENUM('ADMIN', 'TRAINEE', 'ATTENDEE') NOT NULL, -- User role, restricts values to ADMIN, TRAINEE, and ATTENDEE
    is_active BOOLEAN DEFAULT TRUE, -- Indicates if the user is active; defaults to TRUE (active)
    created_by VARCHAR(50) DEFAULT 'system', -- Who created the user record
    updated_by VARCHAR(50), -- Who last updated the user record
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- creation of timestamp
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Timestamp of last updated
    version BIGINT NOT NULL DEFAULT 0, '${my_placeholder}'-- optimistic locking to identify the count of records changes 
    serach VARCHAR(250) --  search field (likely for caching search results or indexing purposes)
);


-- Default Admin User
INSERT INTO users (id,uuid,name, phone_number, email, user_name, password, role) VALUES
(NULL,UUID(),'Admin User', '+6383804074', 'admin@gmail.com', 'Admin', '$2a$10$sRxo7sgi4elrPdboR7jucutOC6AR3yzcDG7z2zHZmvaXr1Dablr5K', 'ADMIN'),
(NULL,UUID(),'Admin User2', '+8300590074', 'admin2@gmail.com', 'Admin2', '$2a$10$.1MxjBqVSTvzg6yB8cnjdOyjOQoFiVM0/m25ZJnmTutPQCLFdGq4q', 'ADMIN');
