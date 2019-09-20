## Error Description

In the **src/resources** folder, there is a file called *application.properties*. In this file, I have wrote this line of code:

```
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:coms-309-ss-4.misc.iastate.edu}:3306/db_example
```
found on the spring.io guide to setup a [MySQL database](https://spring.io/guides/gs/accessing-data-mysql/). The problem is that when I try to run the 
`mvnw clean package` command, I get and error. (Attached in email sent to TA.) If I replace `coms-309-ss-4.misc.iastate.edu` with `localhost` Maven will compile the project, and
I am able to test it on my local PC.


This does not solve the issue though because when I copy the *.jar* file to the remote server and go to run it, it will fail every time. As stated in my email, I believe this
is because it says `localhost` in that line instead of `coms-309-ss-4.misc.iastate.edu`. I am at an impasse because I cannot compile the project with the server
name at that location, but I cannot get the project to run remotely if I do not have that server name listed.
