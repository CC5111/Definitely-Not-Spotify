# play-slick-rest
The Play Slick Rest is a very simple json rest api showing one way of using Play Framework 2.5 with [slick 3](https://github.com/slick/slick) library for database access.


It supports the following features:

* Generic Data Access Objects, create a DAOS with crud for an entity with just one line using DI
* Models as case classes and slick models, independent from database driver and profile

The project was thought to be used as an activator template.

#Running

The database pre-configured is an h2, so you just have to:


        $ sbt run

#Testing

To run all tests (routes and persistence tests):


        $ sbt test

#Using

	curl --request POST localhost:9000/supplier -H "Content-type: application/json" --data "{\"name\" : \"sup1\",\"desc\" : \"low prices\"}"

	curl localhost:9000/supplier/1

#TODO

Tests

#Credits

To make this template, I just mixed the play scala template with play slick.

