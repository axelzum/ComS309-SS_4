# How to Access Remote Server and Open MySQL on it

## Remote Server

First off, our remote server address is: **coms-309-ss-4.misc.iastate.edu**
- We all have access to it and we will need to login at least once.

To login to the remote server, you either need to use use a Linux/Unix terminal or use PuTTY. I do not know how to use the latter, so I cannot help there.
I am pretty sure there are tutorials online though.
- If you are like me and use a Windows computer but don't want to use PuTTY, install the [Git Bash terminal](https://gitforwindows.org/). This terminal is very nice and even
comes with a basic GUI for Git if you want it.

Now, the command you need to enter into the terminal is:
```
ssh "your_netid_no_quotes"@coms-309-ss-4.misc.iastate.edu
```
When you hit enter, it might take a really long time but that is okay, just let it think. It will soon ask you for your password. This password is just your
netid password you use for other ISU related things.

After entering that, you should be on the remote server! If you type `pwd` you can see the directory you are in. I am pretty sure it will create your own directory for you.
For example, my directory is **/home/wpknox**.

I have a couple different **.jar** files in my directory that you can run if you want. You will need to navigate to them and then type:
```
java -jar ".jar file name no quotes"
```
You will see whole bunch of stuff go on and then it will stop. This means that the Spring Boot application is now running. To quit, simply press *Ctrl+C* at the same time.

I don't actually recommend doing this in the main terminal though because then you can't do anything in the terminal. Instead, make a new screen by typing `screen` and then
running the `java -jar` command. Then, after the server is up and running, you can leave this screen by typing: *Ctrl+A* then, while still holding *Ctrl*, press *D*. This will
exit the current screen and return you to the main terminal without shutting off the server!

If you want to go back to the screen, type `screen -r` and it will take you back. You can then shutdown the server if you want.

If you want to exit out of the *ssh* session, type `exit` and press enter.

## MySQL
To access the MySQL server you need to know the UserName and password of the user I created. These are:
- username: **team_ss4**
- password: **309!F19ss4**
- I also changed the root password to: **coms309ss4**

To using these info, type into the terminal:
```
mysql -u team_ss4 -p
```
You will then be asked for the password and then just enter it. After you log in, you can view the databases you have access to by typing `show databases;`

**NOTE: All mysql commands end in a semicolon.**

The database that I have created for Demo2 is called "db_example"

If you want to quit out of mysql, simply type either `quit;` or `exit;`
