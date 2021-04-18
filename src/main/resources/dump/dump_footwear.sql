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
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `cu_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
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


--
-- Table structure for table `footwear_images`
--

DROP TABLE IF EXISTS `footwear_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `footwear_images` (
  `img_name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `img_art` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`img_name`),
  KEY `footwear_images_footwears_f_art_fk` (`img_art`),
  CONSTRAINT `footwear_images_footwears_f_art_fk` FOREIGN KEY (`img_art`) REFERENCES `footwears` (`f_art`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


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
  `f_for` enum('HIM','HER') COLLATE utf8_unicode_ci NOT NULL,
  `f_color` int DEFAULT NULL,
  `f_brand` int NOT NULL,
  `f_description_en` text COLLATE utf8_unicode_ci,
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-19  0:53:45
