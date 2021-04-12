-- MySQL dump 10.13  Distrib 8.0.23, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: footware_db
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `b_id` int NOT NULL AUTO_INCREMENT,
  `b_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`b_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` (`b_id`, `b_name`) VALUES (1,'Balenciaga'),(2,'Gucci'),(3,'Marni'),(4,'Rick Owens'),(5,'Off-White'),(6,'Alexander McQueen');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `c_id` int NOT NULL AUTO_INCREMENT,
  `c_name_en` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `c_name_ru` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `categories_c_name_uindex` (`c_name_en`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` (`c_id`, `c_name_en`, `c_name_ru`) VALUES (1,'Boots','Ботинки'),(2,'Loafers','Лоферы'),(3,'Sneakers','Кроссовки'),(4,'Oxfords and Derby','Оксфорды и Дерби'),(5,'Sandals','Сандали');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colors` (
  `cl_id` int NOT NULL AUTO_INCREMENT,
  `cl_name_en` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `cl_name_ru` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`cl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors` DISABLE KEYS */;
INSERT INTO `colors` (`cl_id`, `cl_name_en`, `cl_name_ru`) VALUES (1,'Balck','Черный'),(2,'Red','Красный'),(3,'White','Белый'),(4,'Black/White','Черный/Белый'),(5,'Black/Pink','Черный/Розовый');
/*!40000 ALTER TABLE `colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `cu_firstname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cu_email` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `cu_password` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `cu_phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cu_country` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cu_city` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cu_address` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cu_role` enum('user','admin') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'user',
  PRIMARY KEY (`cu_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` (`cu_firstname`, `cu_email`, `cu_password`, `cu_phone`, `cu_country`, `cu_city`, `cu_address`, `cu_role`) VALUES ('Генрикас','12345@mail.ru','qwerty1234','+37164781170','Латвия','Рига','Ул Будрайдиса, 10','user'),('Аркадий','arkasha@rambler.com','qwerty123','+375337895612','Беларусь','Гродно','Ул. Леонида Беды 33, 12','user'),('Ivan','ivan@mail.ru','qwerty123','+375331678912','Беларусь','Гомель','Ул Петра Чайковского 23,11','user'),('Ivan Petrov','petrov@gmail.com','qwerty123','+375337865439','Беларусь','Минск','Ул Голодеда 15, 23','user'),('Victor Vyrostak','virostak@mail.ru','vitek1998','+375291802550',NULL,NULL,NULL,'admin'),('Victor','vite@gmail.com','qwerty123','+375291802551','Belarus','Minsk','st Mendeleev 17,32','user');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `footwear_items`
--

DROP TABLE IF EXISTS `footwear_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `footwear_items` (
  `fi_id` int NOT NULL AUTO_INCREMENT,
  `fi_art` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `fi_size` decimal(3,1) NOT NULL,
  `fi_status` enum('STOCK','SOLVED') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'STOCK',
  PRIMARY KEY (`fi_id`),
  KEY `footwear_items_footwears_f_art_fk` (`fi_art`),
  CONSTRAINT `footwear_items_footwears_f_art_fk` FOREIGN KEY (`fi_art`) REFERENCES `footwears` (`f_art`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `footwear_items`
--

LOCK TABLES `footwear_items` WRITE;
/*!40000 ALTER TABLE `footwear_items` DISABLE KEYS */;
INSERT INTO `footwear_items` (`fi_id`, `fi_art`, `fi_size`, `fi_status`) VALUES (1,'A10c',40.0,'STOCK'),(3,'A10c',42.0,'SOLVED'),(4,'A10c',45.0,'STOCK'),(5,'52N3',36.0,'SOLVED'),(8,'XC10',40.0,'STOCK'),(9,'XC10',42.0,'STOCK'),(10,'XC10',45.0,'STOCK'),(11,'A10c',42.0,'SOLVED'),(12,'A10c',42.0,'STOCK'),(13,'A10c',42.0,'STOCK');
/*!40000 ALTER TABLE `footwear_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `footwears`
--

DROP TABLE IF EXISTS `footwears`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `footwears` (
  `f_art` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `f_name` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `f_price` decimal(6,2) DEFAULT NULL,
  `f_category` int NOT NULL,
  `f_description_en` text COLLATE utf8_unicode_ci,
  `f_image_link` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `f_color` int DEFAULT NULL,
  `f_brand` int NOT NULL,
  `f_for` enum('HIM','HER') COLLATE utf8_unicode_ci NOT NULL,
  `f_description_ru` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`f_art`),
  KEY `footwears_categories_c_id_fk` (`f_category`),
  KEY `footwears_colors_fk` (`f_color`),
  KEY `footwears_brands_b_id_fk` (`f_brand`),
  KEY `footwears_f_for_index` (`f_for`),
  CONSTRAINT `footwears_brands_b_id_fk` FOREIGN KEY (`f_brand`) REFERENCES `brands` (`b_id`) ON UPDATE CASCADE,
  CONSTRAINT `footwears_categories_c_id_fk` FOREIGN KEY (`f_category`) REFERENCES `categories` (`c_id`) ON UPDATE CASCADE,
  CONSTRAINT `footwears_colors_fk` FOREIGN KEY (`f_color`) REFERENCES `colors` (`cl_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `footwears`
--

LOCK TABLES `footwears` WRITE;
/*!40000 ALTER TABLE `footwears` DISABLE KEYS */;
INSERT INTO `footwears` (`f_art`, `f_name`, `f_price`, `f_category`, `f_description_en`, `f_image_link`, `f_color`, `f_brand`, `f_for`, `f_description_ru`) VALUES ('52N3','Double G',520.00,5,'An emblem of the GG Marmont line, the Double G is an archival play on the original Running G, a belt buckle that first appeared in Gucci collections in the 70s. For Pre-Fall 2018, the antique gold-toned detail enhances a pair of flat black leather sandals. Black leather. Double G. Adjustable ankle strap with buckle closure. Made in Italy.\n\n','https://cdn-images.farfetch-contents.com/12/96/44/80/12964480_22092189_1000.jpg',5,2,'HER','Эмблема линейки \'GG Marmont\', культовый логотип Double G, украшает черные кожаные сандалии, представленные в коллекции Gucci Pre-Fall 2018. Фурнитура цвета антикварного золота, регулируемый ремешок на щиколотку с пряжкой. Сделано в Италии.'),('A10c','Track',975.00,3,'A new sports shoe silhouette released for the AW18 collection, Balenciaga\'s \'Track\' sneakers are the pair of the moment. Paying homage to chunky trainers of the 90s, these texturised black sneakers from Balenciaga feature a ridged rubber sole, a branded insole, a round toe, a lace-up front fastening and a logo patch at the tongue.','https://cdn-images.farfetch-contents.com/16/11/50/16/16115016_32008729_1000.jpg',1,1,'HIM','Кроссовки Track от Balenciaga (Баленсиага). Ребристая резиновая подошва, стелька с логотипом, закругленный носок, шнуровка спереди и нашивка-логотип на язычке. Цвет: черный.'),('AJ11','Mega Bozo',1180.00,1,'Grained texture, square toe, elasticated side panels, pull-tab at the heel, ankle-length, chunky rubber sole.','https://cdn-images.farfetch-contents.com/16/29/46/93/16294693_31215736_1000.jpg',1,4,'HIM','Зернистая текстура, квадратный носок, эластичные боковые панели, петля для подтягивания сзади, длина по щиколотку и массивная резиновая подошва.'),('ALM48P','Contrast Stitching Derby',854.00,4,'We\'re quite a square when it comes to choosing our shoes. We like Marni and this square-toe pair seems to be waiting for us to make a decision. 3, 2, 1… decision made!','https://cdn-images.farfetch-contents.com/15/46/69/93/15466993_29404660_1000.jpg',3,3,'HER','Контрастная строчка, стелька с логотипом, квадратный носок, шнуровка спереди и ребристая резиновая подошва. Цвет: белый.'),('W65k7','Thread Slick',672.00,3,'Alexander McQueen is here to boost your sense of comfort, your confidence and your style credentials. All of that is achieved through these Tread Slick sneakers and their unique two-tone design. A step towards style excellence.','https://cdn-images.farfetch-contents.com/16/43/75/45/16437545_31746805_1000.jpg',4,6,'HER','Тисненый логотип сбоку, ребристая резиновая подошва, закругленный носок и стелька с логотипом.'),('XC10','Tassel Marbled',965.00,2,'A lot of people wish they were in your shoes. And we have a feeling these tassel marbled sole loafers from Off-White are entirely to blame. Be prepared for some jealous glances whenever you take them out.','https://cdn-images.farfetch-contents.com/16/07/19/43/16071943_31851563_1000.jpg',1,5,'HIM','Лоферы на подошве с мраморным узором\nдекоративные кисточки\nзакругленный носок\nрезиновая подошва\nмраморный узор');
/*!40000 ALTER TABLE `footwears` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `ord_id` int NOT NULL AUTO_INCREMENT,
  `ord_customer_email` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `ord_status` enum('WAITING','APPROVED','DECLINE','COMPLETE') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'WAITING',
  `ord_price` decimal(7,2) NOT NULL DEFAULT '0.00',
  `ord_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ord_id`),
  KEY `orders_customers_cu_email_fk` (`ord_customer_email`),
  CONSTRAINT `orders_customers_cu_email_fk` FOREIGN KEY (`ord_customer_email`) REFERENCES `customers` (`cu_email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`ord_id`, `ord_customer_email`, `ord_status`, `ord_price`, `ord_date`) VALUES (16,'petrov@gmail.com','WAITING',2470.00,'2021-04-12 11:30:03');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_items`
--

DROP TABLE IF EXISTS `orders_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_items` (
  `oi_order_id` int NOT NULL,
  `oi_item_id` int NOT NULL,
  PRIMARY KEY (`oi_item_id`,`oi_order_id`),
  KEY `orders_items_orders_ord_id_fk` (`oi_order_id`),
  CONSTRAINT `orders_items_footwear_items_fi_id_fk` FOREIGN KEY (`oi_item_id`) REFERENCES `footwear_items` (`fi_id`),
  CONSTRAINT `orders_items_orders_ord_id_fk` FOREIGN KEY (`oi_order_id`) REFERENCES `orders` (`ord_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_items`
--

LOCK TABLES `orders_items` WRITE;
/*!40000 ALTER TABLE `orders_items` DISABLE KEYS */;
INSERT INTO `orders_items` (`oi_order_id`, `oi_item_id`) VALUES (16,3),(16,5),(16,11);
/*!40000 ALTER TABLE `orders_items` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-12 19:07:10
