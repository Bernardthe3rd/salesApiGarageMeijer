INSERT INTO public.vehicles (id, vin_number, brand, model, type, vehicle_year, license_plate, mileage, color, fuel_type,
                             engine_capacity, first_registration_date, amount_in_stock, vehicle_type)
VALUES (1, '2HGCM82633A123457', 'Toyota', 'Corolla', 'Sedan', 2021, 'Z-123-KL', 20000, 'White', 'Gasoline', 1.8,
        '2021-02-10', 5, 'Car'),
       (2, 'JH4KA8260MC123458', 'Volkswagen', 'Golf', 'Hatchback', 2020, 'Z789-NO', 18000, 'Silver', 'Diesel', 2.0,
        '2020-06-30', 1, 'Car');

INSERT INTO public.cars (id, number_of_doors, trunk_capacity, transmission, seating_capacity)
VALUES (1, 4, 450.0, 'Automatic', 5),
       (2, 5, 380.0, 'Manual', 5);

-- Business Vehicles
INSERT INTO public.vehicles (id, vin_number, brand, model, type, vehicle_year, license_plate, mileage, color, fuel_type,
                             engine_capacity, first_registration_date, amount_in_stock, vehicle_type)
VALUES (3, '1HGCM82633A654321', 'Mercedes', 'Sprinter', 'Van', 2019, 'V-543-YZ', 30000, 'White', 'Diesel', 3.0,
        '2019-07-15', 4, 'BusinessVehicle'),
       (4, 'JH4KA8260MC765432', 'Ford', 'Transit', 'Van', 2020, 'V-876-EF', 25000, 'Blue', 'Diesel', 2.5, '2020-03-20',
        6, 'BusinessVehicle');

INSERT INTO public.business_vehicles (id, company_owned, cargo_capacity, business_usage)
VALUES (3, TRUE, 1200.0, 'GOVERNMENT'),
       (4, TRUE, 1000.0, 'BUILDER');

-- Motorcycles
INSERT INTO public.vehicles (id, vin_number, brand, model, type, vehicle_year, license_plate, mileage, color, fuel_type,
                             engine_capacity, first_registration_date, amount_in_stock, vehicle_type)
VALUES (5, '1HD1KRM13FB123456', 'Harley-Davidson', 'Road King', 'Cruiser', 2021, 'MT-12-HI', 12000, 'Black', 'Gasoline',
        1.7, '2021-05-15', 2, 'Motor'),
       (6, 'JYAVP27E9AA123456', 'Yamaha', 'MT-07', 'Naked', 2020, 'MT-78-KL', 8000, 'Red', 'Gasoline', 0.7,
        '2020-09-30', 3, 'Motor');

INSERT INTO public.motors (id, type_motorcycle, wheelbase, handlebar_type)
VALUES (5, 'Cruiser', 1625, 'Ape Hanger'),
       (6, 'Naked', 1400, 'Clip-ons');

-- Customers
INSERT INTO public.customers (first_name, last_name, date_of_birth, street, postal_code, city, country,
                              phone_number, email, preffered_contact_method, name_last_sales_person, creation_date)
VALUES ('John', 'Doing', '1985-06-15', 'Kalverstraat 10', '1234AB', 'Amsterdam', 'Netherlands', '+31612345678',
        'john.doing@example.com', 'email', 'Emily Smith', '2024-01-01'),
       ('Anna', 'Johnson', '1990-09-25', 'Schoolstraat 22', '5678CD', 'Rotterdam', 'Netherlands', '+31687654321',
        'anna.johnson@example.com', 'phone', NULL, '2024-01-15');

-- Users
INSERT INTO public.users (id, username, password, last_login, is_active, creation_date)
VALUES (1, 'markjohnson', '$2a$10$CLwWDPy2bR52uTKx7ZIvqOGmA6TMby64U10zKn0Zk4IxibTRBn4eG', '2024-01-01', TRUE,
        '2024-01-01'),
       (2, 'emilysmith', '$2a$10$kjXWp5bhlPGLaNnrMOe.b.lBzucZMsvXIlxWeFyzgDeW.imGe7Y9m', '2024-01-15', TRUE,
        '2024-01-15'),
       (3, 'pattylover', '$2a$10$kjXWp5bhlPGLaNnrMOe.b.lBzucZMsvXIlxWeFyzgDeW.imGe7Y9m', '2024-01-15', TRUE,
        '2024-01-15');
-- password voor user1 = password123
-- password voor user2 = password456

-- Profiles
INSERT INTO public.profiles (id, creation_date, role, first_name, last_name, date_of_birth, street, postal_code, city,
                             country, email, phone_number, user_id)
VALUES (1, '2024-01-01', 'ADMIN', 'Mark', 'Johnson', '1982-05-12', 'Birch Lane 5', '1234AB', 'The Hague', 'Netherlands',
        'mark.johnson@example.com', '+31612345678', 1),
       (2, '2024-01-15', 'SELLER', 'Emily', 'Smith', '1995-11-20', 'Pine Street 12', '5678CD', 'Utrecht', 'Netherlands',
        'emily.smith@example.com', '+31687654321', 2);

-- Purchases
INSERT INTO public.purchases (order_date, supplier, purchase_price_ex, tax_price, bpm_price, purchase_price_incl,
                              expected_delivery_date, quantity, status, order_number, business_or_private, admin_id,
                              vehicle_id)
VALUES ('2024-01-01', 'ABC Supplier', 1500.00, 315.00, 0.00, 1815.00, '2024-01-10', 1, 'CLOSED', 1, 'BUSINESS',
        1, 3),
       ('2024-01-05', 'XYZ Supplier', 2000.00, 420.00, 2000.00, 4420.00, '2024-01-15', 1, 'CLOSED', 2,
        'PRIVATE', 1, 1);

-- Sales
INSERT INTO public.sales (sale_date, quantity, sale_price_ex, bpm_price, tax_price, sale_price_incl, type_order,
                          status, order_number, comment, discount, warranty, payment_method, business_or_private,
                          addition, seller_id, customer_id, vehicle_id)
VALUES ('2024-01-05', 1, 1200.00, 0.00, 252.00, 1452.00, 'Online', 'CLOSED', 1, 'First sale of the year', 0.0,
        '2 years', 'Credit Card', 'BUSINESS', 'TOWBAR', 2, 1, 4),
       ('2024-01-10', 1, 2500.00, 1000.00, 525.00, 4025.00, 'In-Store', 'CLOSED', 2,
        'Customer requested a discount', 0.0, '3 years', 'Cash', 'PRIVATE', 'NAVIGATION', 2, 2, 1);