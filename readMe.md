# An Image Management Utility. This utility begins by presenting a login screen that looks like this
# SONAR and JENKINS Integrated

___
 
> User would enter an username and password for authentication. 
> Upon clicking the Login button, the submitted username / password should be verified (authenticated) against existing users information in the database.
> Upon successful authentication, the user would be presented with a screen as follows:
![User homepage](/snaps/Picture1.png)
> User should be allowed to edit (change the uploaded image’s attributes viz. name, image source) 
> and delete the image and total size of the uploaded images should be displayed at the bottom of the listing on the same page.

### Constraints

* Max size of a single file uploaded should be 1 MB
* Max size of all uploaded files should be 10 MB

### Key Points
* appropriate data model and object model 
* Hibernate Technology for Data Base connectivity
* MySQL Database used
* No SQL scripts used instead use Hibernate POJOs and HQL
* Tomcat version 8 or above and hibernate 5.x should be used
* Integrated with SONAR and Jenkins.




