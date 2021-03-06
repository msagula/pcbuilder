# pcbuilder

## Description
   - A simple program that helps user build a PC
   ![PC1](https://user-images.githubusercontent.com/55845904/71956212-2c921e00-31b0-11ea-8d99-387450096652.PNG)

## Functionality

### Sign in/Registration
   - A user can sign in to a program using a username and password or register if he/she does not have an account yet. User’s credentials are saved or retrieved using database. It is required for a user to sign in to access the rest of the program

### Research(Not yet implemented)
   - A component that displays information about each PC part. It helps users decide what part they need.  
### Looking up part
   - The program contains a table of all available parts and their information (name, price, type, best use). A user can look up a part by name or sort the table by name, price, type, and best use. 
   ![PC3](https://user-images.githubusercontent.com/55845904/71956684-6ca5d080-31b1-11ea-881c-a94849e91b80.PNG)

### User's Cart
   - A user can select a desired part and add it to a cart. A cart contains a table with user’s selected items. There are four buttons. 'Remove Part' removes selected part from the user's cart. 'Empty cart' removes all the parts from the user's cart. 'Save Cart' saves user's cart to database. 'Checkout' displays message that the transaction has been completed successfully and clears the cart. 

### Interactive test
   - A user can test their knowledge about PC building. The component contains an image list with PC parts and an image of the motherboard. A user clicks on one of the part in the image list and drops it
on motherboard image. If the user places the part in the correct location, the part
gets placed on the motherboard image. Otherwise a message gets displayed to try again
![PC2](https://user-images.githubusercontent.com/55845904/71956292-5c412600-31b0-11ea-9ce9-3408a2c0d619.PNG)
   
### Video tutorial(Not yet implemented)
   - A component that contains embedded video tutorials on how to build a PC

### Chat(Not yet implemented)
   - Allows user and the admin to send and receive a message. 

### Database connection
   - The program is connected to database using JDBC. It contains all of the user's credentials, all the parts, and each user's shopping cart

### Admin option(Not yet implemented)
   - A user with admin credentials can add, remove, or change the price of a part in the database. He/she can also send and receive messages from the users. An admin can also remove other users form the database. There is also an 'orders' tab, where the admin can see all the orders that the users have placed

## Future releases
   - Implement AdminUI, Chat, Research, and Tutorial classes
   - Create 'orders' table in the database. When the user presses 'save' button in their shopping cart, the table gets updated with patrts that the user has selected
   - Change the Interactive Test so that the user can see the image being dragged from the image list to the motherboard
