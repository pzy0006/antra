CREATE SCHEMA IF NOT EXISTS review_schema;

-- 2. 设置当前 Schema 为 review_schema
SET search_path TO review_schema;
-- V2__create_review_table.sql
CREATE TABLE review (
    id SERIAL PRIMARY KEY,
    course_id INT NOT NULL,  -- 外键，关联到课程服务
    user_id INT NOT NULL,    
    rating INT NOT NULL,
    comment VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

