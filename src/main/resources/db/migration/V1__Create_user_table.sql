create table users (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(14) NOT NULL UNIQUE, -- CPF com formato xxx.xxx.xxx-xx
    phone VARCHAR(20),
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00
    
)