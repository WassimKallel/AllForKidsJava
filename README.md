# AllForKidsJava

This is a project of a platform built using [DopsieORM](https://github.com/WassimKallel/DopsieORM)

## Getting started

After cloning the project, you should specify the database credentials in the projet main entry 

```java 
    public static void main(String[] args) {
        System.setProperty("host", "host");
        System.setProperty("port", "port");
        System.setProperty("database", "from_scratch");
        System.setProperty("user", "sql_user_username");
        System.setProperty("password", "sql_user_password");
        System.setProperty("uploads_folder", "absolute path for storage directory");
        launch(args);
    }
```

The database schema can be found in the repository ```from_scratch.sql```.


When using allForKids you can log in as admin using username ```wassim``` and password ```1``` .