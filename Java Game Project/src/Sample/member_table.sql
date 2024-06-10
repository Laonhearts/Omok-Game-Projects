CREATE TABLE member (
                        name VARCHAR(20) NOT NULL,
                        nickname VARCHAR(20) NOT NULL,
                        id VARCHAR(20) NOT NULL,
                        password VARCHAR(20) NOT NULL,
                        email VARCHAR(40) NOT NULL,
                        win INT NOT NULL,
                        lose INT NOT NULL,
                        PRIMARY KEY (nickname, id)
);

