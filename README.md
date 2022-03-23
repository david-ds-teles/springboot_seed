# Author

**author**: David S Teles

**email**: david.ds.teles@gmail.com

Hello, my name is David Teles. I'm a software engineer. You can find me on [Linkedin](https://www.linkedin.com/in/david-teles/?locale=en_US). 

Feel free to send me a message.

# About

This is a starter point for springboot projects. It has basic examples and configurations that you maybe want in your projects.

You'll find here:

* basic project structure
* example of layers communication using dependecy injection.
* interceptors for exceptions and logging
* example of client consumer of api using webClient.
* connection with mysql to illustrate how entities works with open-session-in-view off.
* i18n configurations as well as messages bundles to both spring and hibernate validation changing dynamically.
* multiple profiles to separate environment.
* prettier.

## Prettier

This project has a maven plugin **prettier-maven-plugin** to help keep things neat. 

to run prettier check in this project you can:
```
mvn prettier:check
```

to run prettier write in this project you can:
```
mvn write
```

## Running the application with different profile

You just need to replace -Plocal with your profile declared on pom.xml <profiles> tag.

```
mvn spring-boot:run -Pdev
```