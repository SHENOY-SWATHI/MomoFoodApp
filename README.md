# MomoFoodApp
MomoFoodApp
A restaurant that sells a variety of momos. And accepts orders from 11 am to 9 pm. 
The order can be canceled within a minute. You can also schedule the order for the same day or the coming days.

MileStone - 1	

Project Setup with connection to DB and appropriate entities creation. 

MileStone - 2	

1. Create functions to fetch, create, update and delete (soft delete) a user.
2. Users should have a unique identifier like email or phone no. apart from an integral primary key. 

MileStone - 3	

1. Create functions to fetch, create, update and delete (soft delete) dishes.
2. Dish should have a unique identifier. 
3. Dish availability should be tracked appropriately.

MileStone - 4	

1. Implement the cart functionality (user should be able to add/remove/update dishes with their required quantities).
2. Carts can only be processed if all the items are available.

MileStone - 5	

1. Orders placed within working hours will be processed with active status which can be canceled within 1 minute.
2. Users can schedule orders for working hours of any day in slots of 1 hour. Eg 12 pm - 1 pm, 3pm - 4pm.

MileStone - 6	

1. Scheduled orders will be marked as “Scheduled” and will be marked as active at the start of their slot (Implement using Scheduler).
2. Scheduled orders cannot be cancelled once they are active.
3. Orders can be placed/canceled/fetched but cannot be updated.




