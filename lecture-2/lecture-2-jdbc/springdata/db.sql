-- spring_data.`user` definition

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(500) DEFAULT NULL,
  `balance` int DEFAULT '0',
  `version` int DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_IDX` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;