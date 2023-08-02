# SilkRoad

<p align="center">
  <img src = "SilkRoadClient/src/main/resources/logo.png" width="700" height = "400">
</p>

**_<h4 align="center"> A place where you can sell/buy anything you want! </h4>_**

<p align="center">
  <a href="#contributors">Contributors</a> •
  <a href="#introduction">Introduction</a> •
  <a href="#how-to-use">How to Use</a> •
  <a href="#key-features">Key Features</a>
</p>


:star: Star us on GitHub — it motivates us a lot!

## Contributors

* [**Farbod Khorramvatan**](https://github.com/Farbodkhm)
* [**Pooyan Oskouhi**](https://github.com/pooyanosk1382)
* [**Ali Bakhshesh**](https://github.com/alib2014)


## Introduction

`Silk Road` is a digital market place on windows desktop where usres can post their advertisements, see other's advertisements, communicate/negotiate with them through chat option, save their favorites, search among all items and so on.


## How to Use
First you need to clone the project
````
git clone https://github.com/Farbodkhm/SilkRoad.git
````
Then you need to run the Server.java in a specific port

> **Note**
> Clients & server must be in the same local network

After the server is up, put the IP & Port of the sever in CreateSocket.java and then, run Main.java!


## Key Features

<details>
<summary>Accounts</summary>
  
  >You can either enter with your personal account or enter as a guest to view the ads

<p align="center">
  <img src = "https://github.com/Farbodkhm/SilkRoad/assets/68291080/a32a8a7e-3f2b-46f2-8138-38e4769f1b8e" width="600" height = "500">
</p>


The city name you enter must be valid(One of cities of Iran)

Also, the strength of your password will be checked simultaneously as you enter it.
</details>

<details>
<summary>Captcha System</summary>
  
A simple captcha is required for entering the app

<p align="center">
  <img src = "https://github.com/Farbodkhm/SilkRoad/assets/68291080/6033afc9-46df-4e69-8305-df577a15ba3b" width="500" height = "400">
</p>
</details>

<details>
<summary>Email Verification</summary>
  
Your email will be validated with an OTP

<p align="center">
    <img width="49%" src="https://github.com/Farbodkhm/SilkRoad/assets/68291080/586e1316-c3b3-4b84-a054-88a13eede843"/>
&nbsp;
    <img width="49%" height="400" src="https://github.com/Farbodkhm/SilkRoad/assets/68291080/f37907f1-af65-4892-80ee-bf4a667003a4"/>
</p>
</details>

<details>
<summary>Password Recovery</summary>
  
In case of forgetting your password, you can recover it easily!

<p align="center">
  <img src = "https://github.com/Farbodkhm/SilkRoad/assets/68291080/aeef3466-296e-4052-b7a8-4213c9b4fca6" width="500" height = "400">
</p>
</details>

<details>
<summary>Chat & Notification System</summary>
  
You can send direct messages to the owners of the advertisements and communitace with them. Also, notification of your unread messages will pop up as soon as you login to your account
</details>

<details>
<summary>Switch between themes(Dark & Light)</summary>
  
In any page you are, you can switch to the another theme
</details>

<details>
<summary>Security</summary>
  
All communications between client & server will be ecrypted using AED encryption. Also, all passwords will be hashed and then will be stored in database
</details>

<details>
<summary>Multi-thread Programming</summary>
  
In order to handle all users cuncurrently, multi-thread concepts are used and they are syncronized appropriately to avoid any multi-threading problems
</details>



