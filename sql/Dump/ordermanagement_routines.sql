-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: ordermanagement
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary view structure for view `vw_orderitemviewmodel`
--

DROP TABLE IF EXISTS `vw_orderitemviewmodel`;
/*!50001 DROP VIEW IF EXISTS `vw_orderitemviewmodel`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_orderitemviewmodel` AS SELECT 
 1 AS `id`,
 1 AS `orderId`,
 1 AS `productId`,
 1 AS `quantity`,
 1 AS `productName`,
 1 AS `productPrice`,
 1 AS `totalPrice`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_orderviewmodel`
--

DROP TABLE IF EXISTS `vw_orderviewmodel`;
/*!50001 DROP VIEW IF EXISTS `vw_orderviewmodel`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_orderviewmodel` AS SELECT 
 1 AS `id`,
 1 AS `customerId`,
 1 AS `date`,
 1 AS `customerName`,
 1 AS `price`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_productselectable`
--

DROP TABLE IF EXISTS `vw_productselectable`;
/*!50001 DROP VIEW IF EXISTS `vw_productselectable`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_productselectable` AS SELECT 
 1 AS `id`,
 1 AS `name`,
 1 AS `price`,
 1 AS `quantity`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vw_orderitemviewmodel`
--

/*!50001 DROP VIEW IF EXISTS `vw_orderitemviewmodel`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_orderitemviewmodel` AS select `oi`.`id` AS `id`,`oi`.`orderId` AS `orderId`,`oi`.`productId` AS `productId`,`oi`.`quantity` AS `quantity`,`p`.`name` AS `productName`,`p`.`price` AS `productPrice`,(`oi`.`quantity` * `p`.`price`) AS `totalPrice` from (`orderitem` `oi` join `product` `p` on((`oi`.`productId` = `p`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_orderviewmodel`
--

/*!50001 DROP VIEW IF EXISTS `vw_orderviewmodel`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_orderviewmodel` AS select `o`.`id` AS `id`,`o`.`customerId` AS `customerId`,`o`.`date` AS `date`,`c`.`name` AS `customerName`,cast(ifnull(sum((`p`.`price` * `oi`.`quantity`)),'-') as char charset utf8mb4) AS `price` from (((`order` `o` left join `customer` `c` on((`o`.`customerId` = `c`.`id`))) left join `orderitem` `oi` on((`o`.`id` = `oi`.`orderId`))) left join `product` `p` on((`oi`.`productId` = `p`.`id`))) group by `o`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_productselectable`
--

/*!50001 DROP VIEW IF EXISTS `vw_productselectable`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_productselectable` AS select `p`.`id` AS `id`,`p`.`name` AS `name`,`p`.`price` AS `price`,`p`.`quantity` AS `quantity` from `product` `p` where (`p`.`quantity` > 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-21  4:06:49
