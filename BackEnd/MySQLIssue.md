# Update - 09/20/2019

This error has been resolved as of **10:51 a.m. September 20th**. The problem was I did not have the remote server timezone in the *application.properties* file. After
figuring this out, that file now looks like:
```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://coms-309-ss-4.misc.iastate.edu:3306/db_example?serverTimezone=CST
spring.datasource.username=team_ss4
spring.datasource.password=309!F19ss4
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```
Now the server and database seem to be running on **coms-309-ss-4.misc.iastate.edu**. Hopefully, this error is completely resolved, but I doubt it :)
___
#### Error Description

In the **src/resources** folder, there is a file called *application.properties*. In this file, I have wrote this line of code:

```
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:coms-309-ss-4.misc.iastate.edu}:3306/db_example
```
found on the spring.io guide to setup a [MySQL database](https://spring.io/guides/gs/accessing-data-mysql/). The problem is that when I try to run the 
`mvnw clean package` command, I get an error. (Attached in email sent to TA.) If I replace `coms-309-ss-4.misc.iastate.edu` with `localhost` Maven will compile the project, and
I am able to test it on my local PC.


This does not solve the issue though because when I copy the *.jar* file to the remote server and go to run it, it will fail every time. As stated in my email, I believe this
is because it says `localhost` in that line instead of `coms-309-ss-4.misc.iastate.edu`. I am at an impasse because I cannot compile the project with the server
name at that location, but I cannot get the project to run remotely if I do not have that server name listed.
