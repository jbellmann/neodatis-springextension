# NeoDatis Spring-Extension
## What is NeoDatis Spring-Extension?

* It is an unofficial [Spring-Extension](http://www.springsource.org/extensions) for the NeoDatis ObjectDB.
* It supports you using NeoDatis ObjectDB in your Spring-Apps in a well known way with XXXTemplate and declarative transaction-support `@Transactional`
* It adds an parameterized QueryType you can extend from to define the contract for native queries

## How to build

This project uses Maven to build all artifacts.

Check out the source with:

`git clone https://github.com/jbellmann/neodatis-springextension.git`

then `mvn install` will do the build.

## Example-Application

This project comes with a very small example application that uses

* latest version of Spring (3.1.0.RELEASE)
* annotation based configuration (no xml)
* [Thymeleaf 2](http://www.thymeleaf.org/) as view technology


For trying out go to 'se-neodatis-example' folder

`cd se-neodatis-example`

then use `mvn t7:run`.

A Tomcat 7 server is started and you should point your browser to [http://localhost:9090/neodatis-example/](http://localhost:9090/neodatis-example/).
