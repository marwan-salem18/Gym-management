<a id="readme-top"></a>
<!-- PROJECT LOGO -->
<div align="center">
  <a href="https://github.com/marwan-salem18/Gym-management">
  </a>
<h1 align="center">Gym Management System</h1>
  <p align="center">
    The Gym Management System is designed to simplify gym operations by providing separate modules for administrators, coaches, and members. It includes features for managing user accounts, schedules, and subscriptions, with all data stored in CSV files. The system eliminates the need for complex databases, offering a straightforward and reliable solution for managing gym activities.
    <br />
    <a href="https://github.com/marwan-salem18/Gym-management"><strong>Explore the docs »</strong></a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#modules">Modules</a>
      <ul>
        <li><a href="#administrative-module">Administrative Module</a></li>
        <li><a href="#coach-module">Coach Module</a></li>
        <li><a href="#member-module">Member Module</a></li>
        <li><a href="#user-module">User Module</a></li>
      </ul>
    </li>
    <li><a href="#file-based-system">File-Based System</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
<a id="about-the-project"></a>
## About The Project

The Gym Management System streamlines gym operations with dedicated modules for administrators, coaches, and members. It uses a file-based data storage system, storing all information in CSV files. This simple approach ensures easy data management without requiring complex databases. The system keeps track of user accounts, coach schedules, and billing details, all dynamically updated as users interact with it.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* [![Java][Java]][Java-url]
* [![JFrame][JFrame]][JFrame-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Modules

### Administrative Module
* Admin has username and password and can alter them.
* Admin manages coaches and members (Add, Delete, Update, List, Search).
* Admin manages Billing.
* Admin can generate reports about members.
* Admin assigns members to their coaches.
* System sends notifications when the subscription of a member ends.

### Coach Module
* Coach can set a plan and timeline schedule for members.
* Coach can send messages to all their members.

### Member Module
* Member can see the end date of their subscription.
* Member can view their Coach and Schedule.
* System sends notifications when the subscription of a member ends.

### User Module
* All users can log in and log out.
* Users can update their information except their ID.

## File-Based System

### Overview

At the heart of the Gym Management System lies a **custom-built file-based storage system**, which is implemented from scratch. Instead of relying on traditional databases, this system uses CSV (Comma-Separated Values) files to store all user and transaction data. Here's how it works:

1. **CSV Files**: All information (members, coaches, billing data, etc.) is stored in separate CSV files. These files allow for easy reading, writing, and manipulation of data without the complexity of a full database setup.

2. **Data Relations**: The system establishes relationships between different types of data using simple CSV structure rules. For example, the relationship between members and their coaches is stored in a `members.csv` file, where each member is linked to a coach via their unique IDs.

3. **Data Integrity**: The system ensures data integrity by performing operations (add, delete, update) on the CSV files whenever changes are made in the UI. These operations are handled by the program's core logic, which reads and writes the necessary files to maintain consistent state across all modules.

4. **Efficiency**: The CSV-based approach provides a straightforward, efficient way to handle gym data, especially for small to medium-sized applications. There’s no need for heavy database management, making it ideal for smaller systems like this Gym Management Program.

### Example of Files Used

* `members.csv`: Stores information about gym members, including their subscription end dates, assigned coach, and other personal details.
* `coaches.csv`: Contains details of coaches and their assigned members.
* `admin.csv`: Stores administrative data such as reports and user management details.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
<a id="usage"></a>
## Usage

* The program will open the UI with different frames for each module (Admin, Coach, Member).
* Use the Admin frame to manage users, coaches, billing, and generate reports.
* Coaches can manage their members' schedules.
* Members can check their subscription status and coach details.
* All data is stored and manipulated through CSV files in the system.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->
<a id="license"></a>
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


[Java]: https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://www.java.com
[JFrame]: https://img.shields.io/badge/JFrame-007396?style=for-the-badge&logo=java&logoColor=white
[JFrame-url]: https://docs.oracle.com/javase/tutorial/uiswing/