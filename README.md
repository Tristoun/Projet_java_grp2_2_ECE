# Projet_java_grp2_2_ECE

## Run the project with VsCode

### 1 - Create .vscode folder

launch.json
```json
{
    "version": "0.2.0",
    "configurations": [
      {
        "type": "java",
        "name": "Run JavaFX App",
        "request": "launch",
        "mainClass": "Main",
        "vmArgs": "--module-path lib --add-modules javafx.controls,javafx.fxml"
      }
    ]
  }
```

settings.json
```json
{
    "java.project.sourcePaths": [
      "src"
    ],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
      "lib/**/*.jar"
    ]
  }
```
### 2- Create lib folder

* Download MYSQL Connector https://dev.mysql.com/downloads/connector/j/
* Download JavaFX https://gluonhq.com/products/javafx/
* Put all the .jar in the lib folder

### 3- Create Database

* Go to PhpMyAdmin and create the database with the sql File **info_doctolib.sql**

### 4- Install Java extensions