# Change log

## v.1.0.1 30.01.2019
**Changed:**
- Migrate from MySql to MongoDB

## v0.7.4 23.12.2018
**Changed:**
- isDeleted flag field in hire model instead remove row from db when dont needed

**Fix:**
- restoration available status for book after return

## v0.7.3 19.12.2018
**Added:**
- Ability do extend hire books
- Ability to return books
- last modification field to hire entity
- sql triggers which updates dates

**Fix:**
- Login
- Adding new books and book items

## v0.7.2 15.12.2018
**Added:**
- Entity for hire books

## v0.7.1 27.11.2018
**Added:**
- Entity for files
- Field image for book entity with id to file
- download file by id

## v0.7.0 15.11.2018
**Added:**
- searching books by author/title/description containing typed text

## v0.6.2 15.11.2018
**Fix:**
- StackOverflowError throw by gson for adding new root book

**Added:**
- ability to add books item

## v0.6.1 12.11.2018

**Changed:**
- Customer login and password sent in body while login process
- structure of book entity
- adding root book

## v0.6.0 12.11.2018

**Added:**
- Ability to add new books by librarian

## v0.5.3 12.11.2018

**Added:**
- Entity for books
- Local repository for books

## v0.5.2 4.11.2018

**Added:**
- Ability to remove users accounts

**Changed:**
- Login only when account is not deleted

## v0.5.1 4.11.2018

**Added:**
- Dictionaries for user roles
- AccountType to customer model
- Account type in registratiion process

**Fixed:**
- registration validation (incorrect role type)

## v0.5.0 3.11.2018

**Added:**
- Model for Resources
- Dictionary items for Language
- Endpoint to get list of static resources, list of static resources in selected language, list of resource in every languages, single resource in selected language

## v0.4.0 31.10.2018

**Added:**
- Model for Dictionary and DictionaryItem
- Endpoint to get list of available dictionaries and collection of dictionaryItems from specific domain

**Changed:**
- Refactor model names

## v0.3.0 29.10.2018

**Added:**
- Registration 

**Changed:**
- Finish login process

## v0.2.2 28.10.2018

**Added:**
- Change log
- Gson library to serialize and deserialize json objects

## v0.2.1 25.10.2018

**Change:**
- Customer model improvement

**Added:**
- Address model

## v0.2.0 24.10.2018

Project configurations.

**Added:**
- Connection to database
- Customer model
- Login controller
- Customer DAO

## v0.1.0 24.10.2018

Initial commit.

**Added:**
- Readme
