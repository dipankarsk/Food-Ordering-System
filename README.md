# Food Ordering Application 

An online food ordering system accepts orders from customers and delivers at customer’s
address. Items are selected from a menu and the total bill amount is calculated. Selected items
may be put in a cart and addition, deletion of items in the cart is possible. Once the order is
confirmed, the time taken for delivery is intimated and the order can be tracked. There are
several payment modes, cash/wallet/bank transfer/credit/debit card on delivery or pre-paid.
Orders placed may be cancelled if delivery time exceeds 10% of specified time.

The following features of the Application

1. Authentication
   - Login
   - SignUp
2. Orders Based on Location of User
   - Resturant View
   - Food Menu
   - Food Cart
   - Whishlist (Additional)
3. Payments
   - Base bill of atleast 100 rs ensured
   - Offer Discounts SAVE20 and SAVE50 only one time per user
   - Deliver charges based on distance calculated
   - Various Method of payment used like Upi ,Debit/Credit Card and Net Banking.
4. Tracking and Rating
   - Tracking the order time with the original estimated time and if order time exceeds than cancelation available.
   - Rating the food and the application as a whole.

# Pre-Requisites

  - java 17.0.1
  - Java(TM) SE Runtime Environment (build 17.0.1+12-LTS-39)
  - Java HotSpot(TM) 64-Bit Server VM (build 17.0.1+12-LTS-39, mixed mode, sharing)
  - IDE Visual Studio Code Version: 1.62.3
  - Maveen v0.34.1



 
# How to Install the Project

 1. Add Maven Dependency to Pom.xml
 ```
  <dependency>
   <groupId>org.xerial</groupId>
   <artifactId>sqlite-jdbc</artifactId>
   <version>3.15.1
   </version>
  </dependency>
  ```
  ``` 
  <dependency>
	<groupId>com.googlecode.json-simple</groupId>
	<artifactId>json-simple</artifactId>
	<version>1.1.1</version>
  </dependency>
  ```
 2. Create and Change Directory to foodordering
 ```
 mkdir foodordering
 cd foodordering
 ```
 3. Clone the git Repository
 ```
 git clone https://github.com/dipankarsk/oopd_project_2021.git
```
# Running the Project

- Using the jar file
- Directly using the console with main method