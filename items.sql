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
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `items` (
  `itemID` int(11) NOT NULL AUTO_INCREMENT,
  `itemCategory` varchar(25) NOT NULL,
  `itemName` varchar(50) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`itemID`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (26,'Coffee','Greek coffee',2.2),(27,'Coffee','Greek coffee double',2.3),(28,'Coffee','Nescafe',2.5),(29,'Coffee','Nescafe frappe',2.5),(30,'Coffee','Espresso',2.5),(31,'Coffee','Espresso Double',3.5),(32,'Coffee','Espresso Freddo',3.5),(33,'Coffee','Capuccino',3.8),(34,'Coffee','Capuccino Freddo',3.8),(35,'Coffee','Chocolate Hot',3.5),(36,'Coffee','Chocolate Cold',3.5),(37,'Coffee','Nescafe frappe with ice cream',3.8),(38,'Coffee','Filter Coffee',2.5),(39,'Beer','Fix 500ml',3.5),(40,'Beer','Mythos 500ml',3.5),(41,'Beer','Alfa 500ml',3.5),(42,'Beer','Vergina 330ml',3.3),(43,'Beer','Amstel Radler 330ml',3),(44,'Beer','Fix Dark 330ml',4),(45,'Beer','Strongbow 330ml',3),(46,'Wine','Dry White 750ml',13),(47,'Wine','Dry Red 750ml',13),(48,'Wine','Dry Rose 750ml',13),(49,'Wine','Glass of Wine ',3.5),(50,'Wine','Moscato d\'asti',5),(51,'Soft Drink','Fresh Juice',4),(52,'Soft Drink','Juice',3),(53,'Soft Drink','Coca Cola',2.5),(54,'Soft Drink','Coca Cola Zero',2.5),(55,'Soft Drink','Sprite',2.5),(56,'Soft Drink','Fanta Orange',2.5),(57,'Soft Drink','Fanta Lemon',2.5),(58,'Soft Drink','Ice Tea Lemon',2.5),(59,'Soft Drink','Ice Tea Peach',2.5),(60,'Soft Drink','Ice Green Tea',2.5),(61,'Soft Drink','Soda Water',2.5),(62,'Soft Drink','Ginger ale',2.5),(63,'Soft Drink','Tonic Water',2.5),(64,'Snack','Toast',2.6),(65,'Snack','Fried potatoes',3.5),(66,'Snack','Omelet',4),(67,'Snack','Onions Rings',5),(68,'Snack','Chicken Wings',6.5),(69,'Snack','Chicken Bytes',5.5),(70,'Snack','Club Sandwich',7),(71,'Snack','Beef Burger',7),(72,'Snack','Gyros',6.5),(73,'Snack','Local Sausage',6.5),(74,'Snack','Schnitzel',7),(75,'Salad','Greek Salad',5.5),(76,'Salad','Caesar Salad',7.5),(77,'Salad','Chef Salad',7),(78,'Salad','Tuna Salad',7.5),(79,'Salad','Arugula-Parmesan Salad',7),(80,'Salad','Fruit Salad',6),(81,'Salad','Fruit salad with yogurt',7),(82,'Liquer','Triple Sec',4.5),(83,'Liquer','Grand Marnier',4.5),(84,'Liquer','Drambuine',4.5),(85,'Liquer','Kahlua',4.5),(86,'Liquer','Tia Maria',4.5),(87,'Liquer','Baileys',4.5),(88,'Liquer','Amareto',4.5),(89,'Liquer','Malibu',4.5),(90,'Liquer','Apple ',4.5),(91,'Liquer','Strawberry',4.5),(92,'Liquer','Passoa',4.5),(93,'Liquer','Banana',4.5),(94,'Liquer','Galliano',4.5),(95,'Aperitifs','Ouzo Glass',4),(96,'Aperitifs','Campari',4.5),(97,'Aperitifs','Martini',4.6),(98,'Spirits','Gin',5),(99,'Spirits','Rum',5),(100,'Spirits','Rum Dark',6),(101,'Spirits','Tequila',5),(102,'Spirits','Tequila Gold',5),(103,'Spirits','Whiskey',5),(104,'Spirits','Vodka',5),(105,'Spirits','Brandy***',4.5),(106,'Spirits','Brandy*****',6),(107,'Cocktail','Mojito',7),(108,'Cocktail','Mojito Strawberry',7),(109,'Cocktail','Pina Colada',7),(110,'Cocktail','Caipirinha',7),(111,'Cocktail','Frozen Daiquiri',7),(112,'Cocktail','Mambo',7),(113,'Cocktail','Long Island',7),(114,'Cocktail','Mai Tai',7),(115,'Cocktail','Cosmopolitan',7),(116,'Cocktail','Apple Martini',7),(117,'Cocktail','Sex On The Pool',7),(118,'Cocktail','Frozen Pool',7),(119,'Cocktail','Pornstar Martini',7),(120,'Cocktail','Jameson Ginger',7),(121,'Cocktail','Margarita',7),(122,'Soft Drink','Water 500ml',0.5),(123,'Soft Drink','Water 1L',1),(124,'Giagiamas','Lemon - strawberry',3.5),(125,'Giagiamas','Lemon',3.5),(126,'Giagiamas','Green apple - Pomegranate',3.5),(127,'Giagiamas','Peach',3.5),(128,'Giagiamas','My Yogo Shake',4.5),(129,'Giagiamas','My Milk Shake',4.5),(130,'Giagiamas','Chillino Oreo',4.5),(131,'Giagiamas','Caramel Coffechino',5),(132,'Giagiamas','Snikerato',5),(133,'Giagiamas','Coco Choco',4),(134,'Giagiamas','My Fruities Slush',3.5),(135,'Extra','Air Condition',6),(136,'Extra','Safety Box',3),(137,'Extra','Laundry',13),(138,'Extra','Towel',5),(139,'Extra','Jacuzzi',3),(140,'Extra','Transfer',55),(141,'Ice Cream','Vanillia Secret',3.2),(142,'Ice Cream','Grand Vanillia',3.2),(143,'Ice Cream','Chocolate Orgy',3.2),(144,'Ice Cream','Karabola',2.6),(145,'Coffee','Hot Tea',2.5),(146,'Ice Cream','Dodo Roll',1.3),(147,'Ice Cream','Cookies',2),(148,'Ice Cream','X-Pop',0.7),(149,'Spirits','Baileys',5);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
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
