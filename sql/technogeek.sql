CREATE DATABASE technogeek;
use technogeek;

-- Create User Table
CREATE TABLE User (
    UserId INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Gender VARCHAR(50),
    Address VARCHAR(255),
    City VARCHAR(100),
    State VARCHAR(100),
    Country VARCHAR(100),
    Pin INT
);

-- Create UserMetaData Table
CREATE TABLE UserMetaData (
    UserId INT PRIMARY KEY,
    UniqueId VARCHAR(255) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    CONSTRAINT FK_User FOREIGN KEY (UserId) REFERENCES User(UserId) ON DELETE CASCADE
);

-- Create Role Table
CREATE TABLE Role (
    RoleId INT AUTO_INCREMENT PRIMARY KEY,
    RoleName VARCHAR(10) UNIQUE NOT NULL
);

INSERT INTO Role (Name) VALUES ('USER'), ('ADMIN');

-- Create UserRoleMapping Table
CREATE TABLE UserRoleMapping (
    UserId INT NOT NULL,
    RoleId INT NOT NULL,
    PRIMARY KEY (UserId, RoleId),
    FOREIGN KEY (UserId) REFERENCES User(UserId) ON DELETE CASCADE,
    FOREIGN KEY (RoleId) REFERENCES Role(RoleId) ON DELETE CASCADE
);