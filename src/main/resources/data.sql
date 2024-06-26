-- Inserting categories
INSERT INTO categories (name) VALUES ('Laptops'), ('Desktops'), ('Tablets'), ('Monitors'), ('Keyboards'), ('Mice'), ('Accessories');

-- Inserting producers
INSERT INTO producers (name) VALUES ('Lenovo'), ('Dell'), ('HP'), ('Asus'), ('Acer'), ('Apple'), ('Samsung'), ('LG'), ('Microsoft'), ('Razer');

-- Inserting products
INSERT INTO products (name, description, price, category_id, producer_id, amount_in_magazine)
VALUES ('ThinkPad X1 Carbon', 'Ultra-light Ultrabook', 1599.99, 1, 1, 20),
       ('Dell XPS 15', 'Powerful laptop for professionals', 1799.99, 1, 2, 15),
       ('HP Spectre x360', 'Convertible laptop with stunning design', 1399.99, 1, 3, 18),
       ('Asus ROG Zephyrus G14', 'High-performance gaming laptop', 1499.99, 1, 4, 12),
       ('Acer Predator Helios 300', 'Gaming laptop with excellent performance', 1299.99, 1, 5, 10),
       ('Apple MacBook Pro', 'Sleek and powerful macOS laptop', 1999.99, 1, 6, 22),
       ('Samsung Galaxy Book Flex', 'Premium 2-in-1 laptop with S Pen', 1299.99, 1, 7, 17),
       ('LG Gram', 'Lightweight laptop with long battery life', 1299.99, 1, 8, 19),
       ('Microsoft Surface Laptop 4', 'Elegant and powerful Windows laptop', 1399.99, 1, 9, 21),
       ('Razer Blade 15', 'Gaming laptop with premium build quality', 1799.99, 1, 10, 16),
       ('Dell Inspiron 15', 'Affordable everyday laptop', 899.99, 1, 2, 25),
       ('HP Pavilion Gaming Laptop', 'Budget-friendly gaming laptop', 1099.99, 1, 3, 20),
       ('Asus VivoBook 15', 'Slim and lightweight laptop', 699.99, 1, 4, 30),
       ('Acer Aspire 5', 'Versatile and affordable laptop', 799.99, 1, 5, 28),
       ('Apple MacBook Air', 'Thin and light macOS laptop', 1199.99, 1, 6, 23),
       ('Samsung Chromebook 4', 'Simple and reliable Chromebook', 299.99, 1, 7, 35),
       ('LG Ultra PC', 'Powerful ultrabook for professionals', 1299.99, 1, 8, 27),
       ('Microsoft Surface Pro 8', 'Versatile 2-in-1 tablet/laptop', 1299.99, 3, 9, 24),
       ('Razer Book 13', 'Ultra-portable laptop for creators', 1599.99, 1, 10, 26),
       ('Lenovo IdeaPad 3', 'Budget-friendly laptop for everyday use', 599.99, 1, 1, 32),
       ('Dell OptiPlex 7090', 'Powerful desktop for business', 1299.99, 2, 2, 15),
       ('HP EliteDesk 800 G6', 'Premium business desktop PC', 1499.99, 2, 3, 18),
       ('Asus ROG Strix GA15', 'High-performance gaming desktop', 1699.99, 2, 4, 12),
       ('Acer Aspire TC', 'Affordable home desktop PC', 799.99, 2, 5, 10),
       ('Apple Mac Mini', 'Compact desktop with macOS', 699.99, 2, 6, 22),
       ('Samsung Galaxy Tab S7', 'Premium Android tablet', 699.99, 3, 7, 17),
       ('LG G Pad 5', 'Versatile Android tablet', 399.99, 3, 8, 19),
       ('Microsoft Surface Go 3', 'Portable Windows tablet', 499.99, 3, 9, 21),
       ('Razer Edge', 'Gaming tablet for on-the-go', 999.99, 3, 10, 16),
       ('Logitech G Pro X', 'High-performance mechanical gaming keyboard', 129.99, 5, 4, 50),
       ('Razer BlackWidow Elite', 'Premium mechanical gaming keyboard', 169.99, 5, 7, 45),
       ('Corsair K95 RGB Platinum', 'Feature-rich gaming keyboard', 199.99, 5, 5, 40),
       ('SteelSeries Apex Pro', 'Adjustable mechanical gaming keyboard', 199.99, 5, 5, 35),
       ('Apple Magic Keyboard', 'Sleek wireless keyboard for Mac', 99.99, 5, 6, 60),
       ('Samsung Galaxy SmartTag', 'Bluetooth item tracker', 29.99, 7, 7, 70),
       ('LG UltraGear 27GN950-B', '27" 4K Gaming Monitor', 999.99, 4, 8, 25),
       ('Microsoft Surface Studio 2', 'All-in-one desktop for creatives', 3499.99, 2, 9, 8),
       ('Razer Raptor 27', '27" QHD gaming monitor with high refresh rate', 799.99, 4, 10, 14),
       ('Logitech G Pro Wireless', 'Wireless gaming mouse with HERO sensor', 149.99, 6, 2, 55),
       ('Razer DeathAdder V2', 'Ergonomic gaming mouse with optical sensor', 69.99, 6, 7, 50),
       ('Corsair Dark Core RGB Pro', 'Wireless gaming mouse with customizable lighting', 89.99, 6, 2, 48);

-- Inserting images for products
INSERT INTO images (product_id, images)
VALUES (1, 'path/to/image1.jpg'), (1, 'path/to/image2.jpg'), (1, 'path/to/image3.jpg'),
       (2, 'path/to/image4.jpg'), (2, 'path/to/image5.jpg'), (2, 'path/to/image6.jpg'),
       (3, 'path/to/image7.jpg'), (3, 'path/to/image8.jpg'), (3, 'path/to/image9.jpg'),
       (4, 'path/to/image10.jpg'), (4, 'path/to/image11.jpg'), (4, 'path/to/image12.jpg'),
       (5, 'path/to/image13.jpg'), (5, 'path/to/image14.jpg'), (5, 'path/to/image15.jpg'),
       (6, 'path/to/image16.jpg'), (6, 'path/to/image17.jpg'), (6, 'path/to/image18.jpg'),
       (7, 'path/to/image19.jpg'), (7, 'path/to/image20.jpg'), (7, 'path/to/image21.jpg'),
       (8, 'path/to/image22.jpg'), (8, 'path/to/image23.jpg'), (8, 'path/to/image24.jpg'),
       (9, 'path/to/image25.jpg'), (9, 'path/to/image26.jpg'), (9, 'path/to/image27.jpg'),
       (10, 'path/to/image28.jpg'), (10, 'path/to/image29.jpg'), (10, 'path/to/image30.jpg'),
       (11, 'path/to/image31.jpg'), (11, 'path/to/image32.jpg'), (11, 'path/to/image33.jpg'),
       (12, 'path/to/image34.jpg'), (12, 'path/to/image35.jpg'), (12, 'path/to/image36.jpg'),
       (13, 'path/to/image37.jpg'), (13, 'path/to/image38.jpg'), (13, 'path/to/image39.jpg'),
       (14, 'path/to/image40.jpg'), (14, 'path/to/image41.jpg'), (14, 'path/to/image42.jpg'),
       (15, 'path/to/image43.jpg'), (15, 'path/to/image44.jpg'), (15, 'path/to/image45.jpg'),
       (16, 'path/to/image46.jpg'), (16, 'path/to/image47.jpg'), (16, 'path/to/image48.jpg'),
       (17, 'path/to/image49.jpg'), (17, 'path/to/image50.jpg'), (17, 'path/to/image51.jpg'),
       (18, 'path/to/image52.jpg'), (18, 'path/to/image53.jpg'), (18, 'path/to/image54.jpg'),
       (19, 'path/to/image55.jpg'), (19, 'path/to/image56.jpg'), (19, 'path/to/image57.jpg'),
       (20, 'path/to/image58.jpg'), (20, 'path/to/image59.jpg'), (20, 'path/to/image60.jpg'),
       (21, 'path/to/image61.jpg'), (21, 'path/to/image62.jpg'), (21, 'path/to/image63.jpg'),
       (22, 'path/to/image64.jpg'), (22, 'path/to/image65.jpg'), (22, 'path/to/image66.jpg'),
       (23, 'path/to/image67.jpg'), (23, 'path/to/image68.jpg'), (23, 'path/to/image69.jpg'),
       (24, 'path/to/image70.jpg'), (24, 'path/to/image71.jpg'), (24, 'path/to/image72.jpg'),
       (25, 'path/to/image73.jpg'), (25, 'path/to/image74.jpg'), (25, 'path/to/image75.jpg'),
       (26, 'path/to/image76.jpg'), (26, 'path/to/image77.jpg'), (26, 'path/to/image78.jpg'),
       (27, 'path/to/image79.jpg'), (27, 'path/to/image80.jpg'), (27, 'path/to/image81.jpg'),
       (28, 'path/to/image82.jpg'), (28, 'path/to/image83.jpg'), (28, 'path/to/image84.jpg'),
       (29, 'path/to/image85.jpg'), (29, 'path/to/image86.jpg'), (29, 'path/to/image87.jpg'),
       (30, 'path/to/image88.jpg'), (30, 'path/to/image89.jpg'), (30, 'path/to/image90.jpg'),
       (31, 'path/to/image91.jpg'), (31, 'path/to/image92.jpg'), (31, 'path/to/image93.jpg'),
       (32, 'path/to/image94.jpg'), (32, 'path/to/image95.jpg'), (32, 'path/to/image96.jpg'),
       (33, 'path/to/image97.jpg'), (33, 'path/to/image98.jpg'), (33, 'path/to/image99.jpg'),
       (34, 'path/to/image100.jpg'), (34, 'path/to/image101.jpg'), (34, 'path/to/image102.jpg'),
       (35, 'path/to/image103.jpg'), (35, 'path/to/image104.jpg'), (35, 'path/to/image105.jpg'),
       (36, 'path/to/image106.jpg'), (36, 'path/to/image107.jpg'), (36, 'path/to/image108.jpg'),
       (37, 'path/to/image109.jpg'), (37, 'path/to/image110.jpg'), (37, 'path/to/image111.jpg'),
       (38, 'path/to/image112.jpg'), (38, 'path/to/image113.jpg'), (38, 'path/to/image114.jpg'),
       (39, 'path/to/image115.jpg'), (39, 'path/to/image116.jpg'), (39, 'path/to/image117.jpg'),
       (40, 'path/to/image118.jpg'), (40, 'path/to/image119.jpg'), (40, 'path/to/image120.jpg');


INSERT INTO users (first_name, last_name, login, password, email, address, role)
VALUES ('Jan', 'Kowalski', 'jan_kowalski', MD5('password1'), 'jan@example.com', 'ul. Testowa 123, Warszawa', "ROLE_USER");
INSERT INTO users (first_name, last_name, login, password, email, address, role)
VALUES ('Anna', 'Admin', 'admin', "$2a$10$nJhW1uZ8NRdPXWD.i6141u0kF9FZVl6./JuqyeG6m9Px1/JoqVWeq", 'anna@example.com', 'ul. Admina 1, Kraków', "ROLE_ADMIN");
INSERT INTO users (first_name, last_name, login,password, email, address, role)
VALUES ('Adam', 'Nowak', 'adam_nowak',MD5('password3'), 'adam@example.com', 'ul. Przykładowa 456, Poznań', "ROLE_USER");
INSERT INTO users (first_name, last_name, login,password, email, address, role)
VALUES ('Adam', 'Nowak', 'dejton',"$2a$10$nJhW1uZ8NRdPXWD.i6141u0kF9FZVl6./JuqyeG6m9Px1/JoqVWeq", 'dejton@gmail.com', 'ul. Przykładowa 456, Poznań', "ROLE_USER");
