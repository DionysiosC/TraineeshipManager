CREATE DATABASE IF NOT EXISTS `project_db`;
USE `project_db`;

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) DEFAULT NULL,
    `password` VARCHAR(255) DEFAULT NULL,
    `role` VARCHAR(10) DEFAULT NULL, 
    `fullName` VARCHAR(255) DEFAULT NULL,
    `AM` VARCHAR(20) DEFAULT NULL,  -- Only for Students
    `avgGrade` DOUBLE DEFAULT NULL,        -- Only for Students
    `preferredLocation` VARCHAR(255) DEFAULT NULL, -- Student/Company
    `interests` TEXT DEFAULT NULL,         -- Student/Professor
    `skills` TEXT DEFAULT NULL,            -- Only for Students
    `lookingForTraineeship` BOOLEAN DEFAULT FALSE, -- Only for Students
    `companyLocation` VARCHAR(255) DEFAULT NULL, -- Only for Companies
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `TraineeshipPosition`;

CREATE TABLE `TraineeshipPosition` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) DEFAULT NULL,
    `description` TEXT DEFAULT NULL,
    `fromDate` DATE DEFAULT NULL,
    `toDate` DATE DEFAULT NULL,
    `topics` TEXT DEFAULT NULL,
    `skills` TEXT DEFAULT NULL,
    `isAssigned` BOOLEAN DEFAULT FALSE,
    `studentLogbook` TEXT DEFAULT NULL,
    `passFailGrade` BOOLEAN DEFAULT FALSE,
    `student_id` INT DEFAULT NULL, 
    `supervisor_id` INT DEFAULT NULL,
    `company_id` INT DEFAULT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (`company_id`) REFERENCES `User`(`id`) ON DELETE SET NULL,
    FOREIGN KEY (`supervisor_id`) REFERENCES `User`(`id`) ON DELETE SET NULL,
    FOREIGN KEY (`student_id`) REFERENCES `User`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Evaluation`;

CREATE TABLE `Evaluation` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `evaluation_type` VARCHAR(15) DEFAULT NULL,
    `motivation` INT DEFAULT NULL,
    `efficiency` INT DEFAULT NULL,
    `effectiveness` INT DEFAULT NULL,
    `company_score` INT DEFAULT NULL,
    `traineeship_id` INT DEFAULT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (`traineeship_id`) REFERENCES `TraineeshipPosition`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;