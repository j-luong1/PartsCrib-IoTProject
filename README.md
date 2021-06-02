# Parts Crib Solution Project for CENG 355 0NC
## Jonathan Luong, Robert Dinh, Colin LeDonne
## Computer Engineering Technology

## Declaration of Joint Authorship

We Jonathan, Robert, and Colin, confirm that this work submitted is the joint work of our group and is expressed our own words. Any uses made within it of the works of any other author, in any form (ideas, equations, figures, texts, tables, programs, code), are properly acknowledged at the point of use. A list of the references used is included. The work breakdown is as follows: Each of us provided functioning, documented hardware for a sensor or effector. Jonathan provided the HD44780 16x2 LCD Display, Robert provided the PN532 NFC Sensor, and Colin provided the VCNL 4010 Proximity sensor, all the products are from Adafruit Industries. In the integration effort Jonathan is the lead for further development of our mobile application, Colin is the lead for the Hardware, and Robert is the lead for connecting the two via the Database.

## Proposal

Our team has created a mobile application, developed databases, completed a software engineering course, and individually prototyped small embedded systems with a custom PCB as well as enclosure (via 3D print/laser cut). Our Internet of Things (Iot) capstone project utilizes a distributed computing model over smart phone application as well as devices with web browser functionality, a database accessible over the internet, a wireless connected embedded system prototype with a custom PCB with an enclosure, and are documented via this technical report targeting OACETT certification guidelines.

The final product to be created will be a NFC reader that interacts with the database tables we have created to update account and inventory information. A mobile device with NFC enabled will initiate the transaction when hovered over the product. This will complete the transaction process for checking out or returning equipment. There will be a web interface that the administrators have access to so the database information can be interacted with from their end with functionality to keep track of orders and update information to the application. All the interactions with the database are handled by PHP script for account and order related actions, these scripts are designed to be easily reused in any other form of item management system where an application only needs to submit parameters to initiate the functions within.


Intended project key component descriptions and part numbers
Development platform:

Sensor 1: Adafruit PN532 NFC Sensor

Sensor 2: Adafruit VCNL 4010 Proximity Sensor

Effector 1: Adafruit HD44780 16x2 LCD Display


We will further develop skills to configure the operating system, embedded systems, database, and web browser interface using these key components to complete our goal of developing a new system for the Parts Crib to use regarding equipment provisioning. Our project description/specifications will be reviewed by, Vlad Porcila, a staff member at the Parts Crib whom we have been collaborating with during our software engineering course. They will also ideally attend the ICT Capstone Expo to see the outcome of our project and possibly implement our product to use in their system. The goal is to create a product and system that students and staff will both look forward to using during their journey at Humber College.

The small physical prototypes that we build are to be made small and safe enough to be brought to class every week as well as be worked on at home. In alignment with the space below the tray in the Humber North Campus Electronics Parts kit the overall project maximum dimensions are 12 13/16” x 2 7/8” = 32.5cm x 15.25cm x 7.25cm.

Keeping safety and Z462 in mind, the highest DC voltage that will be used is 5.1V from a wall adapter operating a Raspberry Pi 3 microprocessor. Maximum power consumption will not exceed 15.3 watts. We are working with prototypes and that these prototypes are not to be left powered on or unattended despite the connectivity that we develop.

The project is estimated to be finished within the Winter Semester of 2019 in time to be presented at the ICT Capstone Expo in April.

In conclusion our aim is to create a product and the system that has better functionality for both client and staff use while utilizing both web browser use and smart device interaction.


## Executive Summary

Our team is working towards creating an inventory management system that can be implemented at the Humber College Institute of Technology & Advanced Learning North Campus Technology Parts Crib. We will be developing a product to ensure the main process of equipment provisioning at the campus is simple and efficient for both the clients (Students and Faculty) as well as the administration (employees of the Parts Crib). 

Throughout the day there is often congestion at the Parts Crib as multiple clients will make requests before a lab for their course. The current implementation for equipment provisioning is also not intuitive for clients to use. Our project aims to improve on these two points as well as implementing the ideas from our collaborator to create a system with more functionality than the current system.

Technology is increasing the quality of life of humanity every day. We want to advance the current system at the Parts Crib so that future clients can easily use the service and create a pleasurable experience. This product will be used to present that our team can develop an inventory and account database that is easily maintained and interacted with.
