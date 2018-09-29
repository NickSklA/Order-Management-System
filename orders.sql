-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: barmanagementsystem
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `orderID` int(11) NOT NULL AUTO_INCREMENT,
  `roomID` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` time(6) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`orderID`),
  KEY `room_ID_FK` (`roomID`),
  CONSTRAINT `room_ID_FK` FOREIGN KEY (`roomID`) REFERENCES `booked_rooms` (`roomid`)
) ENGINE=InnoDB AUTO_INCREMENT=342 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,13,'2018-07-21','19:16:51.000000',1),(4,13,'2018-07-21','13:00:22.000000',1),(5,13,'2018-07-21','13:22:47.000000',1),(6,19,'2018-07-21','14:03:23.000000',1),(7,19,'2018-07-21','14:08:12.000000',1),(8,20,'2018-07-21','14:08:29.000000',1),(9,13,'2018-07-21','14:08:56.000000',1),(10,13,'2018-07-21','14:16:44.000000',1),(11,20,'2018-07-21','14:29:38.000000',1),(12,19,'2018-07-21','15:08:56.000000',1),(13,20,'2018-07-21','15:33:56.000000',1),(14,21,'2018-07-21','15:38:08.000000',1),(15,22,'2018-07-21','15:39:30.000000',1),(16,19,'2018-07-21','15:44:52.000000',1),(17,23,'2018-07-21','15:47:46.000000',1),(18,13,'2018-07-21','16:13:44.000000',1),(19,22,'2018-07-21','16:20:32.000000',1),(20,21,'2018-07-21','16:36:34.000000',1),(21,22,'2018-07-21','16:54:15.000000',1),(22,19,'2018-07-21','20:19:40.000000',1),(23,24,'2018-07-21','22:41:18.000000',1),(25,25,'2018-07-22','10:35:21.000000',1),(27,19,'2018-07-22','10:49:06.000000',1),(28,24,'2018-07-22','10:52:26.000000',1),(29,22,'2018-07-22','11:07:17.000000',1),(31,24,'2018-07-22','11:19:01.000000',1),(33,22,'2018-07-22','11:42:13.000000',1),(34,19,'2018-07-22','12:11:59.000000',1),(35,20,'2018-07-22','12:12:33.000000',1),(36,21,'2018-07-22','12:17:48.000000',1),(37,22,'2018-07-22','12:57:05.000000',1),(40,22,'2018-07-22','13:24:32.000000',1),(41,25,'2018-07-22','13:31:34.000000',1),(42,19,'2018-07-22','13:32:54.000000',1),(43,25,'2018-07-22','13:56:00.000000',1),(46,11,'2018-07-22','14:46:02.000000',1),(47,21,'2018-07-22','14:52:50.000000',1),(49,20,'2018-07-22','15:39:53.000000',1),(52,27,'2018-07-22','16:32:44.000000',1),(54,13,'2018-07-22','17:30:56.000000',1),(58,13,'2018-07-21','19:16:57.000000',1),(66,19,'2018-07-22','23:34:37.000000',1),(67,22,'2018-07-23','11:11:14.000000',1),(68,19,'2018-07-23','12:12:16.000000',1),(69,14,'2018-07-23','12:30:11.000000',1),(70,22,'2018-07-23','12:40:56.000000',1),(71,21,'2018-07-23','12:41:36.000000',1),(73,20,'2018-07-23','12:52:10.000000',1),(76,14,'2018-07-23','14:18:31.000000',1),(77,24,'2018-07-23','14:26:30.000000',1),(79,14,'2018-07-23','14:31:42.000000',1),(80,21,'2018-07-23','15:01:27.000000',1),(81,19,'2018-07-23','15:01:50.000000',1),(82,14,'2018-07-23','15:59:32.000000',1),(84,19,'2018-07-23','16:10:46.000000',1),(85,25,'2018-07-23','00:16:49.000000',1),(86,14,'2018-07-23','16:17:11.000000',1),(87,17,'2018-07-23','16:39:50.000000',1),(89,13,'2018-07-23','16:47:07.000000',1),(90,22,'2018-07-23','17:13:31.000000',1),(91,22,'2018-07-23','18:02:30.000000',1),(92,22,'2018-07-24','12:10:00.000000',1),(93,21,'2018-07-24','13:32:57.000000',1),(94,24,'2018-07-24','13:24:20.000000',1),(95,20,'2018-07-24','14:45:11.000000',1),(96,24,'2018-07-24','16:45:41.000000',1),(97,21,'2018-07-24','15:24:45.000000',1),(98,13,'2018-07-24','21:00:29.000000',1),(99,22,'2018-07-24','16:02:09.000000',1),(100,25,'2018-07-24','23:13:14.000000',1),(101,25,'2018-07-24','23:13:20.000000',1),(102,21,'2018-07-24','21:02:24.000000',1),(103,25,'2018-07-24','23:13:28.000000',1),(104,13,'2018-07-24','23:09:12.000000',1),(105,24,'2018-07-24','23:13:36.000000',1),(106,11,'2018-07-25','22:09:26.000000',1),(107,22,'2018-07-25','12:33:09.000000',1),(108,13,'2018-07-25','18:03:58.000000',1),(109,20,'2018-07-25','13:56:22.000000',1),(110,21,'2018-07-25','13:58:06.000000',1),(111,14,'2018-07-25','15:24:27.000000',1),(112,21,'2018-07-25','15:32:24.000000',1),(113,19,'2018-07-25','16:15:54.000000',1),(116,19,'2018-07-25','23:57:45.000000',1),(118,13,'2018-07-25','23:57:50.000000',1),(119,19,'2018-07-26','12:04:44.000000',1),(120,21,'2018-07-26','15:34:01.000000',1),(121,20,'2018-07-26','13:39:41.000000',1),(122,13,'2018-07-26','17:11:03.000000',1),(123,24,'2018-07-26','13:47:43.000000',1),(124,22,'2018-07-26','13:11:05.000000',1),(126,19,'2018-07-26','21:56:22.000000',1),(127,19,'2018-07-26','22:49:27.000000',1),(128,20,'2018-07-26','00:46:47.000000',1),(129,25,'2018-07-26','23:30:22.000000',1),(130,20,'2018-07-27','00:46:54.000000',1),(131,24,'2018-07-27','12:56:56.000000',1),(132,20,'2018-07-27','13:25:37.000000',1),(133,22,'2018-07-27','14:06:31.000000',1),(134,13,'2018-07-27','20:57:21.000000',1),(135,19,'2018-07-27','13:51:30.000000',1),(136,29,'2018-07-27','14:09:50.000000',0),(137,13,'2018-07-28','14:28:25.000000',1),(138,22,'2018-07-28','17:05:09.000000',1),(139,33,'2018-07-28','15:47:38.000000',0),(140,33,'2018-07-28','15:52:13.000000',0),(141,24,'2018-07-28','16:06:41.000000',1),(142,21,'2018-07-28','16:08:46.000000',1),(143,34,'2018-07-28','16:21:26.000000',1),(144,25,'2018-07-28','00:24:55.000000',1),(145,20,'2018-07-28','00:23:47.000000',1),(147,40,'2018-07-29','14:31:56.000000',1),(148,36,'2018-07-29','00:42:42.000000',1),(149,22,'2018-07-29','17:12:13.000000',1),(150,13,'2018-07-29','17:27:59.000000',1),(151,34,'2018-07-29','13:04:05.000000',1),(152,14,'2018-07-29','15:06:04.000000',1),(153,40,'2018-07-29','14:32:05.000000',1),(154,20,'2018-07-29','13:22:25.000000',1),(155,37,'2018-07-29','17:10:41.000000',1),(157,25,'2018-07-29','17:40:28.000000',1),(158,34,'2018-07-29','15:29:41.000000',1),(159,36,'2018-07-29','15:44:14.000000',1),(160,24,'2018-07-29','15:55:41.000000',1),(161,34,'2018-07-29','16:52:40.000000',1),(162,22,'2018-07-30','16:04:55.000000',1),(163,24,'2018-07-30','12:52:55.000000',1),(164,14,'2018-07-30','14:48:46.000000',1),(165,13,'2018-07-30','17:18:22.000000',1),(166,34,'2018-07-30','13:55:31.000000',1),(167,24,'2018-07-30','14:52:41.000000',1),(168,22,'2018-07-30','14:54:18.000000',1),(169,40,'2018-07-30','14:32:17.000000',1),(170,34,'2018-07-30','15:44:19.000000',1),(171,40,'2018-07-30','14:32:24.000000',1),(172,37,'2018-07-30','17:20:31.000000',1),(175,24,'2018-07-30','22:23:46.000000',1),(176,24,'2018-07-30','23:12:26.000000',1),(178,20,'2018-07-30','00:03:03.000000',1),(179,25,'2018-07-30','00:04:07.000000',1),(180,34,'2018-07-30','23:26:15.000000',1),(181,29,'2018-07-31','11:51:22.000000',0),(182,14,'2018-07-31','12:45:49.000000',1),(183,13,'2018-07-31','18:13:27.000000',1),(184,36,'2018-07-31','13:25:24.000000',1),(185,25,'2018-07-31','21:21:49.000000',1),(186,14,'2018-07-31','14:40:30.000000',1),(187,36,'2018-07-31','14:41:03.000000',1),(188,14,'2018-07-31','14:50:48.000000',1),(189,24,'2018-07-31','15:32:41.000000',1),(190,14,'2018-07-31','16:12:07.000000',1),(191,36,'2018-07-31','16:29:32.000000',1),(192,25,'2018-07-31','23:58:14.000000',1),(193,20,'2018-07-31','00:05:39.000000',1),(194,21,'2018-07-31','23:43:40.000000',1),(195,40,'2018-08-01','10:35:53.000000',1),(196,41,'2018-08-01','10:36:32.000000',1),(197,39,'2018-08-01','10:37:06.000000',1),(198,13,'2018-08-01','17:06:19.000000',1),(199,25,'2018-08-01','19:06:43.000000',1),(200,20,'2018-08-01','13:21:45.000000',1),(201,41,'2018-08-01','14:01:04.000000',1),(202,39,'2018-08-01','15:08:19.000000',1),(203,40,'2018-08-01','15:01:33.000000',1),(204,34,'2018-08-01','15:42:51.000000',0),(205,22,'2018-08-01','18:59:22.000000',1),(206,34,'2018-08-01','17:15:07.000000',1),(207,33,'2018-08-01','23:06:36.000000',0),(208,24,'2018-08-01','22:31:27.000000',1),(209,13,'2018-08-01','23:26:44.000000',1),(210,34,'2018-08-01','23:48:45.000000',1),(211,24,'2018-08-01','23:52:05.000000',0),(212,25,'2018-08-01','23:52:32.000000',1),(213,20,'2018-08-01','23:53:15.000000',0),(214,21,'2018-08-02','17:09:10.000000',1),(215,13,'2018-08-02','23:44:23.000000',1),(216,24,'2018-08-02','14:07:54.000000',1),(217,20,'2018-08-02','17:09:22.000000',1),(218,41,'2018-08-02','16:37:34.000000',1),(219,22,'2018-08-02','17:08:35.000000',1),(220,39,'2018-08-02','16:11:40.000000',1),(221,40,'2018-08-02','16:12:38.000000',1),(222,37,'2018-08-02','18:32:09.000000',1),(223,25,'2018-08-02','23:01:54.000000',1),(224,24,'2018-08-02','22:51:11.000000',1),(225,33,'2018-08-02','22:53:16.000000',0),(226,29,'2018-08-03','12:57:14.000000',0),(227,34,'2018-08-03','15:55:04.000000',1),(228,22,'2018-08-03','02:06:08.000000',1),(229,13,'2018-08-03','18:43:26.000000',1),(231,25,'2018-08-03','18:42:59.000000',1),(232,20,'2018-08-03','14:08:44.000000',1),(233,24,'2018-08-03','14:12:17.000000',1),(234,42,'2018-08-03','18:42:39.000000',1),(235,42,'2018-08-04','23:16:38.000000',1),(236,37,'2018-08-04','13:41:58.000000',1),(237,25,'2018-08-04','16:03:44.000000',1),(238,24,'2018-08-04','16:35:54.000000',1),(239,13,'2018-08-04','16:55:35.000000',1),(240,39,'2018-08-04','17:03:16.000000',1),(241,40,'2018-08-04','17:04:06.000000',1),(242,41,'2018-08-04','17:05:29.000000',1),(243,29,'2018-08-05','12:09:40.000000',0),(244,43,'2018-08-05','12:10:16.000000',1),(245,45,'2018-08-05','12:11:29.000000',1),(246,42,'2018-08-05','12:12:25.000000',1),(247,52,'2018-08-05','12:13:38.000000',1),(248,42,'2018-08-05','17:58:43.000000',1),(249,13,'2018-08-05','17:58:30.000000',1),(250,41,'2018-08-05','17:57:33.000000',1),(251,53,'2018-08-05','22:41:38.000000',1),(252,53,'2018-08-05','22:41:59.000000',1),(253,53,'2018-08-06','13:13:33.000000',1),(254,40,'2018-08-06','15:44:22.000000',1),(255,13,'2018-08-06','18:01:54.000000',1),(256,39,'2018-08-06','15:44:45.000000',1),(257,41,'2018-08-06','15:45:18.000000',1),(258,42,'2018-08-06','18:02:56.000000',1),(259,29,'2018-08-07','10:50:18.000000',0),(260,13,'2018-08-07','18:19:01.000000',1),(261,42,'2018-08-07','18:19:10.000000',1),(262,29,'2018-08-07','15:20:10.000000',0),(263,53,'2018-08-07','17:16:27.000000',1),(264,13,'2018-08-09','17:58:58.000000',1),(265,42,'2018-08-09','17:59:03.000000',1),(266,53,'2018-08-09','15:39:23.000000',1),(267,54,'2018-08-09','18:18:29.000000',1),(269,56,'2018-08-09','23:40:57.000000',1),(270,13,'2018-08-10','17:55:27.000000',1),(271,53,'2018-08-10','16:38:05.000000',1),(272,42,'2018-08-10','17:55:42.000000',1),(273,58,'2018-08-10','15:36:40.000000',0),(274,55,'2018-08-10','22:49:42.000000',1),(276,13,'2018-08-11','17:31:29.000000',1),(278,58,'2018-08-11','16:35:01.000000',0),(279,50,'2018-08-11','16:25:45.000000',1),(280,50,'2018-08-11','21:10:56.000000',0),(281,54,'2018-08-11','20:08:48.000000',1),(282,59,'2018-08-11','21:42:52.000000',1),(283,60,'2018-08-11','21:45:58.000000',1),(284,13,'2018-08-12','20:56:04.000000',1),(285,60,'2018-08-12','16:00:07.000000',1),(286,62,'2018-08-12','16:02:59.000000',1),(287,58,'2018-08-12','14:54:35.000000',0),(288,53,'2018-08-12','15:43:04.000000',1),(289,64,'2018-08-12','15:52:04.000000',0),(290,62,'2018-08-12','01:34:01.000000',1),(291,62,'2018-08-13','19:13:39.000000',1),(292,60,'2018-08-13','17:22:01.000000',1),(293,42,'2018-08-13','17:05:19.000000',1),(294,13,'2018-08-13','17:19:29.000000',1),(295,58,'2018-08-13','14:32:02.000000',0),(296,13,'2018-08-13','17:19:43.000000',1),(297,54,'2018-08-13','19:54:49.000000',1),(298,62,'2018-08-13','18:17:40.000000',1),(299,63,'2018-08-13','18:18:49.000000',0),(300,62,'2018-08-13','01:39:38.000000',1),(302,66,'2018-08-13','21:02:15.000000',1),(303,54,'2018-08-14','12:05:14.000000',1),(304,62,'2018-08-14','19:06:11.000000',1),(305,65,'2018-08-14','15:34:43.000000',1),(306,64,'2018-08-14','12:15:35.000000',0),(307,58,'2018-08-14','14:13:51.000000',0),(308,60,'2018-08-14','18:16:03.000000',1),(309,57,'2018-08-14','18:16:05.000000',0),(310,62,'2018-08-14','01:03:16.000000',1),(311,60,'2018-08-15','20:46:37.000000',1),(312,13,'2018-08-15','17:46:51.000000',1),(313,58,'2018-08-15','13:30:49.000000',0),(314,62,'2018-08-15','19:05:10.000000',1),(315,62,'2018-08-15','20:47:16.000000',0),(316,13,'2018-08-15','22:39:50.000000',1),(317,60,'2018-08-16','16:52:16.000000',1),(318,62,'2018-08-16','18:25:31.000000',1),(319,13,'2018-08-16','18:32:07.000000',1),(320,67,'2018-08-16','13:11:50.000000',1),(321,58,'2018-08-16','14:05:43.000000',0),(322,62,'2018-08-16','02:08:47.000000',1),(323,67,'2018-08-16','21:12:09.000000',1),(324,69,'2018-08-16','21:37:56.000000',1),(325,66,'2018-08-16','21:38:42.000000',1),(326,67,'2018-08-16','21:51:37.000000',1),(327,66,'2018-08-16','00:08:17.000000',1),(328,66,'2018-08-17','00:24:31.000000',1),(329,66,'2018-08-17','00:25:09.000000',1),(330,62,'2018-08-17','19:43:16.000000',1),(331,72,'2018-08-17','17:24:24.000000',0),(332,66,'2018-08-17','21:32:07.000000',1),(333,62,'2018-08-18','18:56:21.000000',1),(334,58,'2018-08-18','17:05:12.000000',0),(335,79,'2018-08-18','15:32:33.000000',1),(336,78,'2018-08-18','18:09:32.000000',1),(337,77,'2018-08-18','18:10:33.000000',1),(338,62,'2018-08-18','20:00:29.000000',0),(339,62,'2018-08-19','13:02:54.000000',0),(340,60,'2018-08-19','13:03:04.000000',0),(341,58,'2018-08-19','13:03:42.000000',0);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-29 22:24:45