# Library Repository APP
This is a Library Repository App that runs on the Android platform. 
The app allows users to perform various operations related to a library, 
including creating, reading, updating, and deleting books from the library collection.

<h1 align="center">
  <br>
  <img src="https://github.com/Davnn1/Library_App_AS/blob/master/app/src/main/res/drawable/logo.png" alt="Foco" width="160">
</h1>

# Key Feature
Key features of the app:
- Book Management: Admin can add new books to the library by providing details such as title, author, publication date, genre, etc. They can also view the existing books in the library, update their information, and delete books if needed.
- Borrowing Transactions: The app enables users to create borrowing transactions when a library member borrows a book. Admin can record the borrower's information, the borrowed book, and the due date for return.
- Return Book: Admins can update the status of a borrowed book when it is returned to the library. This action includes the ability to adjust whether the borrower needs to pay a fine or any charges for late returns.
- Borrowing History: The app maintains a history of borrowing transactions, allowing admin to track the borrowing and return activities. admin can view past transactions and access details such as the borrowing date, return date, and member information.
- User Authentication:
  - Login: Implement a login functionality where users can authenticate themselves by providing their credentials, such as username and password.
  - Register: Allow new users to register for an account by providing necessary details, such as username, password, email, etc.
  - Logout: Provide an option for logged-in users to securely log out from their account, ensuring their session is terminated.
- Account Management: Allow users to update their account information after logging in.
- Session Management: Implement session management to maintain user authentication throughout their interaction with the app. Automatically log users out after a certain period of inactivity to ensure security.

By using this Library Repository App, library staff can efficiently manage the library's book collection, track borrowing activities, and maintain a comprehensive record of book transactions. It enhances the overall organization and functionality of the library.

# Installation
Android Studio:
- Download or clone from this repository.
- Unzip the folder.
- Open the folder in Android Studio.
- Cut UAS_LIBRARY folder to your htdocs diretory.
- Insert volley_uas.sql inside the folder to http://localhost/phpmyadmin/.
- Check your IPv4 address on CMD with ipconfig
- Change the IP in app\src\main\java\com\example\projectuas_kelompok6\AppConfig.java with your IPv4 address to acces your localhost database.
- Run the APP on the Pixel 6 API 26 for the best experience.

# License
MIT  © Davin

## Contributing

1. Fork it (<https://github.com/yourname/yourproject/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request
