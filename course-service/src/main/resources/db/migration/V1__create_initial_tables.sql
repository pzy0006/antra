-- ----------------------------------------------------
-- V1__create_initial_tables.sql
-- 创建初始的课程 (Course) 和教师 (Instructor) 表
-- ----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS course_schema;

-- 2. 设置当前 Schema 为 course_schema
SET search_path TO course_schema;

-- 2. 教师表
CREATE TABLE instructor (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    department VARCHAR(100)
);

-- 3. 课程表
CREATE TABLE course (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL, 
    credit_hours INTEGER NOT NULL,
    instructor_id UUID REFERENCES instructor(id), 
    description TEXT
);