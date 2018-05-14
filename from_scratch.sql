-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Mer 09 Mai 2018 à 19:39
-- Version du serveur :  5.7.21
-- Version de PHP :  5.6.33-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `from_scratch`
--

-- --------------------------------------------------------

--
-- Structure de la table `blog_post`
--

CREATE TABLE IF NOT EXISTS `blog_post` (
`id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` longtext NOT NULL,
  `user_id` int(11) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `image_path` varchar(255) DEFAULT NULL,
  `online` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `blog_post`
--

INSERT INTO `blog_post` (`id`, `title`, `content`, `user_id`, `creation_date`, `image_path`, `online`) VALUES
(69, 'Ideas & inspiration For Building Your Portfolio', '<html dir="ltr"><head></head><body contenteditable="true"><p>On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain.</p>\n\n<p>But who has any right to find fault:</p>\n\n<ul>\n	<li>With a man who chooses to enjoy</li>\n	<li>That has no annoying consequences</li>\n	<li>Avoids a pain that produces</li>\n	<li>Duty or the obligations of business</li>\n	<li>Dures pains to avoid worse pains</li>\n	<li>Occasionally circumstances occur in which</li>\n</ul>\n\n<p>Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it</p>\n\n<blockquote>\n<p>Our five-year program helps villagers identify most vulnerable children and develop care plans for them, establishes agricultural co-ops to produce income for these children’s families, and changes the economy of the entire village</p>\n</blockquote>\n\n<p>But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful.</p>\n</body></html>', 1, '2018-05-07 00:00:00', 'uploads/blog/1525710148_blog_13.jpg', 1),
(70, 'Getting Started Designing Apps for the Apple Watch', '<p>On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain.</p>\r\n\r\n<p>But who has any right to find fault:</p>\r\n\r\n<ul>\r\n	<li>With a man who chooses to enjoy</li>\r\n	<li>That has no annoying consequences</li>\r\n	<li>Avoids a pain that produces</li>\r\n	<li>Duty or the obligations of business</li>\r\n	<li>Dures pains to avoid worse pains</li>\r\n	<li>Occasionally circumstances occur in which</li>\r\n</ul>\r\n\r\n<p>Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it</p>\r\n\r\n<blockquote>\r\n<p>Our five-year program helps villagers identify most vulnerable children and develop care plans for them, establishes agricultural co-ops to produce income for these children&rsquo;s families, and changes the economy of the entire village</p>\r\n</blockquote>\r\n\r\n<p>But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful.</p>\r\n', 1, '2018-05-07 00:00:00', 'uploads/blog/1525710205_blog_14 (1).jpg', 1),
(71, 'LETâ€™S BUILD A TRADITIONAL CITY AND MAKE A PROFIT', '<p>On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain.</p>\r\n\r\n<p>But who has any right to find fault:</p>\r\n\r\n<ul>\r\n	<li>With a man who chooses to enjoy</li>\r\n	<li>That has no annoying consequences</li>\r\n	<li>Avoids a pain that produces</li>\r\n	<li>Duty or the obligations of business</li>\r\n	<li>Dures pains to avoid worse pains</li>\r\n	<li>Occasionally circumstances occur in which</li>\r\n</ul>\r\n\r\n<p>Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it</p>\r\n\r\n<blockquote>\r\n<p>Our five-year program helps villagers identify most vulnerable children and develop care plans for them, establishes agricultural co-ops to produce income for these children&rsquo;s families, and changes the economy of the entire village</p>\r\n</blockquote>\r\n', 44, '2018-05-07 00:00:00', 'uploads/blog/1525710431_blog_13.jpg', 1);

-- --------------------------------------------------------

--
-- Structure de la table `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
`id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `content` longtext NOT NULL,
  `user_id` int(11) NOT NULL,
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `comment`
--

INSERT INTO `comment` (`id`, `post_id`, `content`, `user_id`, `creation_date`) VALUES
(34, 69, 'This is very usefull Post', 1, '2018-05-07 17:15:57'),
(35, 71, 'This has nothing to do with Kids !!', 1, '2018-05-07 17:34:50'),
(36, 69, 'new COmment', 1, '2018-05-07 19:36:09'),
(37, 69, 'comment body', 1, '2018-05-07 20:06:11');

-- --------------------------------------------------------

--
-- Structure de la table `line_item`
--

CREATE TABLE IF NOT EXISTS `line_item` (
`id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT '1',
  `vat` double NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `line_item`
--

INSERT INTO `line_item` (`id`, `order_id`, `product_id`, `quantity`, `vat`, `total`) VALUES
(75, 30, 8, 1, 22.8, 120),
(76, 31, 12, 1, 19, 34),
(78, 30, 15, 5, 59.85, 315),
(79, 30, 9, 1, 91.2, 480),
(80, 29, 9, 1, 22.8, 120),
(81, 34, 13, 1, 16.91, 89),
(82, 33, 8, 1, 22.8, 120),
(83, 35, 9, 1, 22.8, 120),
(84, 36, 8, 1, 22.8, 120),
(85, 37, 11, 1, 19, 68),
(86, 38, 9, 1, 19, 120),
(87, 37, 8, 1, 19, 120),
(89, 39, 11, 4, 51.68000000000001, 272),
(90, 40, 13, 1, 19, 89),
(91, 40, 11, 1, 19, 68);

-- --------------------------------------------------------

--
-- Structure de la table `order`
--

CREATE TABLE IF NOT EXISTS `order` (
`id` int(11) NOT NULL,
  `reference` varchar(255) NOT NULL,
  `order_status` int(11) NOT NULL,
  `shipping_method_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delivery_address` varchar(100) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `order`
--

INSERT INTO `order` (`id`, `reference`, `order_status`, `shipping_method_id`, `customer_id`, `creation_date`, `delivery_address`, `payment_method`) VALUES
(29, '572e1d4eadb3462ab3272fece926dd1a', 1, 1, 1, '2018-05-07 00:00:00', 'NULL', 'PayPal'),
(30, 'a8c1f2c6c09f40a796c9d1333e1b96ce', 1, 0, 52, '2018-05-07 00:00:00', 'NULL', 'Credit Card'),
(31, 's8zouF4sWW0T7kQ0t1VF', 1, 3, 44, '2018-05-07 18:04:33', 'dazdazd', 'bacs'),
(33, '857827a4458d4700afe5e48ff275fc60', 1, 0, 1, '2018-05-07 00:00:00', 'NULL', 'PayPal'),
(34, '6c3530f574ab4ce8989e14bcbecc2b58', 1, 0, 52, '2018-05-07 00:00:00', 'NULL', 'Credit Card'),
(35, 'c803d5d112f04f6694f1ee09d4ebc469', 1, 2, 52, '2018-05-07 00:00:00', 'new address', 'NULL'),
(36, '5af3b07ce5e545f4b4d4a23109f70929', 0, NULL, 1, '2018-05-07 19:51:52', NULL, NULL),
(37, 'GIb6b8x6kajC19VilNvt', 0, NULL, 44, '2018-05-07 18:55:01', NULL, NULL),
(38, 'Bk8Isjk8J3ByDqWzUKPh', 1, 1, 52, '2018-05-07 19:16:30', '', 'cheque'),
(39, '6c802258ef5843ac97e33bec72453e3d', 1, 0, 54, '2018-05-07 00:00:00', 'NULL', 'BitCoin'),
(40, 'rCuc8CN90h7s7Pj36ZIr', 1, 1, 54, '2018-05-07 20:01:43', '', 'bacs');

-- --------------------------------------------------------

--
-- Structure de la table `post`
--

CREATE TABLE IF NOT EXISTS `post` (
`id` int(11) NOT NULL,
  `content` longtext NOT NULL,
  `user_id` int(11) NOT NULL,
  `thread_id` int(11) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reported` int(11) DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `post`
--

INSERT INTO `post` (`id`, `content`, `user_id`, `thread_id`, `creation_date`, `reported`) VALUES
(101, 'My 2-months child is sick and I need a doctor right now !', 1, 66, '2018-05-07 17:31:02', 0),
(102, 'My child is 3 years old, and I think a special care is needed for him.', 1, 67, '2018-05-07 17:32:21', 0),
(103, 'There''s many .. let''s see mmm  .. check out the hospital center in the south east area', 52, 66, '2018-05-07 18:32:49', 0),
(104, 'Where I can find the biggest barbie doll for my daughter', 1, 68, '2018-05-07 17:32:55', 0),
(105, 'i have no clue, but there''s one well known : the little prince by the the north urbain zone !! \nhope you appreciate it :)', 52, 67, '2018-05-07 18:35:43', 0),
(106, 'Content of new Thread', 1, 69, '2018-05-07 19:37:22', NULL),
(107, 'Content', 1, 70, '2018-05-07 20:10:44', 0);

-- --------------------------------------------------------

--
-- Structure de la table `posttag`
--

CREATE TABLE IF NOT EXISTS `posttag` (
`id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `posttag`
--

INSERT INTO `posttag` (`id`, `post_id`, `tag_id`) VALUES
(28, 69, 30),
(29, 69, 31);

-- --------------------------------------------------------

--
-- Structure de la table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
`id` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `unit_price` double NOT NULL,
  `description` longtext NOT NULL,
  `reference` varchar(255) NOT NULL,
  `vat_rate` float DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `short_description` longtext,
  `active` int(1) NOT NULL DEFAULT '1',
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `products`
--

INSERT INTO `products` (`id`, `image`, `quantity`, `category_id`, `unit_price`, `description`, `reference`, `vat_rate`, `name`, `short_description`, `active`, `creation_date`) VALUES
(8, 'uploads/products/1525710670_product_41.jpg', 97, 4, 120, '<p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo.</p>\r\n', 'REFT1546', 19, 'Long-sleeved T-shirt', 'Long-sleeved T-shirt', 1, NULL),
(9, 'uploads/products/1525710780_product_13.jpg', 100, 4, 120, '<p>&nbsp;</p>\r\n\r\n<p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo.</p>\r\n', 'REF12458', 19, 'Happy Ninja', '\r\nPellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo.', 1, NULL),
(11, 'uploads/products/1525711941_2025660_HE.jpg', 58, 4, 68, '<p>Super soft, comfy and made for a night of sweet dreams!</p>\r\n\r\n<ul>\r\n	<li>Part of our babyPLACE(R) collection</li>\r\n	<li>Made of 100% polyester fleece; specially treated for a soft feel and to reduce pilling</li>\r\n	<li>Contrast trim made of 100% spun rib-knit polyester</li>\r\n	<li>Please note: for child&#39;s safety, garment should fit snugly or be flame resistant. This garment is flame resistant.</li>\r\n	<li>Allover plaid pattern</li>\r\n	<li>Zipper closure from snap-tab neck to ankle</li>\r\n	<li>Zipper guard protects skin</li>\r\n	<li>Attached footies feature elasticized back ankles; no-slip soles made of 65% polyester/35% cotton twill on sizes 6-9 months and up</li>\r\n	<li>Tagless label</li>\r\n	<li>Imported</li>\r\n</ul>\r\n\r\n<p>Big Fashion, Little Prices</p>\r\n', 'RFF534354', 19, 'Blanket Sleeper', 'Super soft, comfy and made for a night of sweet dreams!\r\n\r\nPart of our babyPLACE(R) collection\r\nMade of 100% polyester fleece; specially treated for a soft feel...', 1, NULL),
(12, 'uploads/products/1525712121_2025660_HE.jpg', 58, 4, 34, '<p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo.</p>\r\n', '24357202', 19, ' Babyhug Singlet Jumpsuit Rose', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet...', 1, NULL),
(13, 'uploads/products/1525712175_2062428_82.jpg', 46, 6, 89, '<p>Step up his style with this retro shoes!</p>\r\n\r\n<ul>\r\n	<li>Part of our shoePLACE (R) collection</li>\r\n	<li>Made of faux leather</li>\r\n	<li>Outsole made of rubber</li>\r\n	<li>Lining made of cotton canvas</li>\r\n	<li>Lace-up style</li>\r\n	<li>Imported</li>\r\n</ul>\r\n\r\n<p>Big Fashion, Little Prices</p>\r\n', 'FREFgsd585', 19, 'Boys Desert Boot', 'Step up his style with this retro shoes!\r\n\r\nPart of our shoePLACE (R) collection\r\nMade of faux leather\r\nOutsole made of rubber\r\nLining made of cotton canvas', 1, NULL),
(14, 'uploads/products/1525712223_2072410_QP.jpg', 78, 5, 85, '<p>A smart set he&#39;ll learn a lot in while he sleeps.</p>\r\n\r\n<ul>\r\n	<li>Made of 100% rib-knit cotton</li>\r\n	<li>Note: For child&#39;s safety, garment should fit snugly. This garment is not flame resistant. Loose fitting garment is more likely to catch fire.</li>\r\n	<li>Contrast crew neck and banded sleeve and leg cuffs</li>\r\n	<li>&#39;&#39;Boy genius&#39; and atom graphic at front</li>\r\n	<li>Science printed pants have elasticized waist</li>\r\n	<li>Tagless label</li>\r\n	<li>Imported</li>\r\n</ul>\r\n\r\n<p>Big Fashion, Little Prices</p>\r\n', 'RFE', 19, 'Boys Long Sleeve ''Boy Genius'' Top', 'A smart set he''ll learn a lot in while he sleeps.\r\n\r\nMade of 100% rib-knit cotton\r\nNote: For child''s safety, garment should fit snugly. This garment is...', 1, NULL),
(15, 'uploads/products/1525712267_2071723_01.jpg', 85, 7, 63, '<p>This durable boot is a cold-weather must-have!</p>\r\n\r\n<ul>\r\n	<li>Part of our shoePLACE( R) collection</li>\r\n	<li>Upper made of 100% nylon canvas; allover chinle print with contrast trim</li>\r\n	<li>Hook-and-loop closure strap made of 100% soft faux leather</li>\r\n	<li>Outsole made of 100% rubber</li>\r\n	<li>Upper and tongue lining made of 100% polyester faux fur</li>\r\n	<li>Sock lining made of 100% polyester tricot; brushed for softness</li>\r\n	<li>Pull-on style</li>\r\n	<li>Strap has rubber snowflake patch</li>\r\n	<li>Imported</li>\r\n</ul>\r\n\r\n<p>Big Fashion, Little Prices</p>\r\n', 'GR865', 19, 'Girls Chinle Print Snowboot', 'This durable boot is a cold-weather must-have!\r\n\r\nPart of our shoePLACE( R) collection\r\nUpper made of 100% nylon canvas; allover chinle print with contrast trim\r\nHook-and-loop closure...', 1, NULL),
(16, 'uploads/products/1525712350_2071634_1264-1.jpg', 78, 7, 76, '<p>With a cute cutout, this dress is twirl-ready!</p>\r\n\r\n<ul>\r\n	<li>Made of 100% cotton jersey</li>\r\n	<li>Pullover style</li>\r\n	<li>Cutout with non-functional bow at back</li>\r\n	<li>Allover doodle print</li>\r\n	<li>Designed in a below-the-knee length</li>\r\n	<li>Pre-washed for an extra-gentle feel and to reduce shrinkage</li>\r\n	<li>Tagless label</li>\r\n	<li>Imported</li>\r\n</ul>\r\n\r\n<p>Big Fashion, Little Prices</p>\r\n', 'Rfe5858', 19, 'Skater Dress', 'With a cute cutout, this dress is twirl-ready!\r\n\r\nMade of 100% cotton jersey\r\nPullover style\r\nCutout with non-functional bow at back\r\nAllover doodle print\r\nDesigned in a...', 1, NULL),
(17, 'uploads/products/1525712402_2065338_1012.jpg', 78, 5, 98, '<p>Wear just the liner, just the shell or combine the two for three looks in one!</p>\r\n\r\n<ul>\r\n	<li>Outer shell made of 100% polyester ripstop; specially coated for water resistance</li>\r\n	<li>Snap tabs to connect to liner</li>\r\n	<li>Body and sleeve lining made of 100% polyester taffeta</li>\r\n	<li>Hood lining made of 100% polyester fleece; brushed for softness and specially treated to prevent pilling</li>\r\n	<li>Inner liner of polyester fleece zips out to become its own jacket; brushed for softness and specially treated to prevent pilling</li>\r\n	<li>Zip-front style with an easy-pull tab has a zipper guard and chin guard</li>\r\n	<li>Self fabric storm hood</li>\r\n	<li>Storm flap</li>\r\n	<li>Colorblock styling</li>\r\n	<li>Adjustable storm cuffs with hook-and-loop closures</li>\r\n	<li>Easy-access zip-close pockets</li>\r\n	<li>Imported</li>\r\n</ul>\r\n\r\n<p>Big Fashion, Little Prices</p>\r\n', 'REF587686', 19, ' Boys Long Sleeve Colorblock 3-In-1 Jacket', 'Wear just the liner, just the shell or combine the two for three looks in one!\r\n\r\nOuter shell made of 100% polyester ripstop; specially coated for water...', 1, NULL),
(18, 'uploads/products/1525712464_2024082_DJ.jpg', 86, 4, 78, '<p>He can dress up in comfort, in slip-ons just like dad&#39;s favorites!</p>\r\n\r\n<ul>\r\n	<li>Part of our shoePLACE (R) collection</li>\r\n	<li>Textured 100% faux leather, durably stitched</li>\r\n	<li>Polyester elastic inserts at tongue</li>\r\n	<li>Lined in soft faux suede</li>\r\n	<li>Cushioned footbed</li>\r\n	<li>Extra padding at heel</li>\r\n	<li>Textured, non-marking resin rubber outsole</li>\r\n	<li>Imported</li>\r\n</ul>\r\n\r\n<p>Big Fashion, Little Prices</p>\r\n', 'REF57858', 19, 'Boys Slip-On Dress Shoe', 'He can dress up in comfort, in slip-ons just like dad''s favorites!\r\n\r\nPart of our shoePLACE (R) collection\r\nTextured 100% faux leather, durably stitched\r\nPolyester elastic inserts...\r\n', 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `product_category`
--

CREATE TABLE IF NOT EXISTS `product_category` (
`id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `active` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `product_category`
--

INSERT INTO `product_category` (`id`, `name`, `active`) VALUES
(4, 'Hoodies', 1),
(5, 'Fashion', 1),
(6, 'Bootsf', 1),
(7, 'Girl', 1);

-- --------------------------------------------------------

--
-- Structure de la table `report`
--

CREATE TABLE IF NOT EXISTS `report` (
`id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `treated` int(11) DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `report`
--

INSERT INTO `report` (`id`, `user_id`, `post_id`, `treated`) VALUES
(24, 1, 103, 0);

-- --------------------------------------------------------

--
-- Structure de la table `shipping_method`
--

CREATE TABLE IF NOT EXISTS `shipping_method` (
`id` int(11) NOT NULL,
  `name` varchar(111) NOT NULL,
  `extra_fee` float NOT NULL,
  `active` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `shipping_method`
--

INSERT INTO `shipping_method` (`id`, `name`, `extra_fee`, `active`) VALUES
(1, 'La poste', 10, 0),
(2, 'Chrono Post ', 25, 1),
(3, 'Pick up at Store', 0, 1);

-- --------------------------------------------------------

--
-- Structure de la table `stores`
--

CREATE TABLE IF NOT EXISTS `stores` (
`id` int(11) NOT NULL,
  `lon` double DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `active` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `stores`
--

INSERT INTO `stores` (`id`, `lon`, `lat`, `name`, `active`) VALUES
(1, 10.14948, 36.82327, 'AllFOrKIDS', 1),
(3, 10.15567, 36.850875, 'Our Store', 1);

-- --------------------------------------------------------

--
-- Structure de la table `tag`
--

CREATE TABLE IF NOT EXISTS `tag` (
`id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `tag`
--

INSERT INTO `tag` (`id`, `name`) VALUES
(30, 'Babies'),
(31, 'Kids');

-- --------------------------------------------------------

--
-- Structure de la table `thread`
--

CREATE TABLE IF NOT EXISTS `thread` (
`id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `topic_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `thread`
--

INSERT INTO `thread` (`id`, `title`, `topic_id`) VALUES
(66, 'Who''s the best doctor near Tunis', 12),
(67, 'Is "Rawdhet El Yasamine" good ?', 16),
(68, 'Barbie dolls', 17),
(69, 'new Thread', 12),
(70, 'New Thread', 12);

-- --------------------------------------------------------

--
-- Structure de la table `topic`
--

CREATE TABLE IF NOT EXISTS `topic` (
`id` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `online` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `topic`
--

INSERT INTO `topic` (`id`, `name`, `online`) VALUES
(12, 'Babies 0-6 Months', 1),
(13, 'Babies 6-12 Months', 1),
(14, 'Babies 1-3 Years', 1),
(15, 'Kids 3-6 Years', 1),
(16, 'Kinder Gardens', 1),
(17, 'Toys', 1);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
`id` int(11) NOT NULL,
  `username` varchar(40) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT '',
  `password` varchar(255) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `role` int(11) DEFAULT '0',
  `active` int(1) DEFAULT '1',
  `avatar_path` varchar(255) DEFAULT NULL,
  `is_customer` int(1) DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `username`, `first_name`, `last_name`, `address`, `phone`, `password`, `email`, `role`, `active`, `avatar_path`, `is_customer`) VALUES
(1, 'wassim', 'wassim', 'kallel', '', '24544708', '$2y$10$3CWbYIvehPmYS2NjWPOiFutStdlnhn8SAf9jL75Y5TO0GDtwcTetW', 'wassim.kallel@hotmail.com', 3, 1, 'uploads/avatars/wassim', 1),
(44, 'amine', 'Amine', 'Troudi', 'NULL', '55048903', '$2y$10$CObRY7ef.mvgIBTL3skUueiqI.JflNuU.e.4GUma7sLLqpzKSgbHy', 'atroudi605@gmail.com', 3, 1, 'uploads/avatars/amine', 1),
(52, 'kbach', 'khoubeib', 'bach', NULL, '55110222', '$2y$10$iuZ1yimPQjbZH1mwcoBGg.xmZ6bFQNTAhG0ysbGqJsvCodMST115G', 'khoubeib.bach@gmail.com', 3, 1, NULL, 1),
(53, 'johndoe', 'John', 'Doe', NULL, '29268988', '$2y$10$P/dJnej7gASPEUKpQPzgmetcu2Q3ZSG2qv0EFkzsdXR.RQXAxaC4i', 'john@doe.con', 0, 1, NULL, 0),
(54, 'mm', 'mm', 'mm', NULL, '55110222', '$2y$10$ENptYgLo8zd7ar9OL70oBO4YEzbQcx9V1T2OkvkAjlfkxWH8NnxqW', 'kk@hh.j', 3, 1, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `user_sessions`
--

CREATE TABLE IF NOT EXISTS `user_sessions` (
`id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `session_token` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `user_sessions`
--

INSERT INTO `user_sessions` (`id`, `user_id`, `session_token`) VALUES
(35, 46, 'tTTyivERmZJl41Pia6Qp'),
(111, 1, 'LofCRfqNCmETR70892Qq'),
(114, 44, 'Y3VxmlmigiqIEMPY8tXk'),
(115, 54, 'eYiysVwBICicnxGyhDjo');

-- --------------------------------------------------------

--
-- Structure de la table `vote`
--

CREATE TABLE IF NOT EXISTS `vote` (
`id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `vote` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `vote`
--

INSERT INTO `vote` (`id`, `user_id`, `post_id`, `vote`) VALUES
(73, 1, 103, 1),
(74, 1, 101, 1),
(75, 1, 106, 1);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `blog_post`
--
ALTER TABLE `blog_post`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `comment`
--
ALTER TABLE `comment`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `line_item`
--
ALTER TABLE `line_item`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `order`
--
ALTER TABLE `order`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `post`
--
ALTER TABLE `post`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `posttag`
--
ALTER TABLE `posttag`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `products`
--
ALTER TABLE `products`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `product_category`
--
ALTER TABLE `product_category`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `report`
--
ALTER TABLE `report`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `shipping_method`
--
ALTER TABLE `shipping_method`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `stores`
--
ALTER TABLE `stores`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `tag`
--
ALTER TABLE `tag`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `thread`
--
ALTER TABLE `thread`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `topic`
--
ALTER TABLE `topic`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user_sessions`
--
ALTER TABLE `user_sessions`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `vote`
--
ALTER TABLE `vote`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `blog_post`
--
ALTER TABLE `blog_post`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=72;
--
-- AUTO_INCREMENT pour la table `comment`
--
ALTER TABLE `comment`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=38;
--
-- AUTO_INCREMENT pour la table `line_item`
--
ALTER TABLE `line_item`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=92;
--
-- AUTO_INCREMENT pour la table `order`
--
ALTER TABLE `order`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT pour la table `post`
--
ALTER TABLE `post`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=108;
--
-- AUTO_INCREMENT pour la table `posttag`
--
ALTER TABLE `posttag`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT pour la table `products`
--
ALTER TABLE `products`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT pour la table `product_category`
--
ALTER TABLE `product_category`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `report`
--
ALTER TABLE `report`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT pour la table `shipping_method`
--
ALTER TABLE `shipping_method`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `stores`
--
ALTER TABLE `stores`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `tag`
--
ALTER TABLE `tag`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT pour la table `thread`
--
ALTER TABLE `thread`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=71;
--
-- AUTO_INCREMENT pour la table `topic`
--
ALTER TABLE `topic`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=55;
--
-- AUTO_INCREMENT pour la table `user_sessions`
--
ALTER TABLE `user_sessions`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=116;
--
-- AUTO_INCREMENT pour la table `vote`
--
ALTER TABLE `vote`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=76;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
