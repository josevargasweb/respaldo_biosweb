# README #

El proceso de desarrollo del BiosLib en su versión web contempla el uso de la herramienta bitbucket. 

Configuración general


Pasos para incorporar un nuevo usuario.

El uso de bitbucket está configurado de acuerdo con la siguiente estructura.
1.	Rama principal o maestra.
2.	Rama versión
3.	Ramas desarrolladores.

La rama principal es la creada con la estructura inicial del proyecto. (Para efectos de esta versión de documento no se explicará.)

La rama versión es la rama principal del release de BiosLIS que se está construyendo. Esta rama es administrada por BiosLIS (Administrador de versión).
Esta rama debe siempre compilar y pasar el conjunto de pruebas básicas de integración. (Plan por definir)

La rama de desarrollador es una rama cuya “propiedad” es del desarrollador para el cuál ésta se ha creado. Es responsabilidad del desarrollador mantener su rama local con su rama remota al menos diariamente. 

Proceso de incorporación de cambios desde una rama.

Una vez que un desarrollador desea liberar una versión de su trabajo debe seguir los pasos siguientes:

i.	Hacer un merge local con la rama versión.
ii.	Resolver los conflictos locales.
iii. Verificar que su rama siga operativa una vez mezclada con los conflictos resueltos.
iv.	Realizar un pull request para realizar un merge con la rama versión verificando que no haya conflictos.
v.	El proceso debe repetirse hasta que no haya conflictos con la rama versión en la realización del pull request.


### Como crear una versión local del repositorio y de mi rama de trabajo.###

i. Clonar repositorio

git clone https://enichollsv@bitbucket.org/enichollsv/bioslis_web.git

ii. Cambiar a rama de desarrollo asignada <nombre_rama_desarrollador>

git checkout -b <nombre_rama_desarrollador>

iii. Revisar ramas locales

git branch

iv. Apuntar local a remota

git branch --set-upstream-to=origin/<nombre_rama_desarrollador> <nombre_rama_desarrollador>




La rama versión es la rama principal del release de BiosLIS que se está construyendo. Esta rama es administrada por BiosLIS (Administrador de versión).
Esta rama debe siempre compilar y pasar el conjunto de pruebas básicas de integración. (Plan por definir)

La rama de desarrollador es una rama cuya “propiedad” es del desarrollador para el cuál ésta se ha creado. Es responsabilidad del desarrollador mantener su rama local con su rama remota al menos diariamente. 

Proceso de incorporación de cambios desde una rama.

Una vez que un desarrollador desea liberar una versión de su trabajo debe seguir los pasos siguientes:

i.	Hacer un merge local con la rama versión.
ii.	Resolver los conflictos locales.
iii. Verificar que su rama siga operativa una vez mezclada con los conflictos resueltos.
iv.	Realizar un pull request para realizar un merge con la rama versión verificando que no haya conflictos.
v.	El proceso debe repetirse hasta que no haya conflictos con la rama versión en la realización del pull request.


### Como crear una versión local del repositorio y de mi rama de trabajo.###

i. Clonar repositorio

git clone https://enichollsv@bitbucket.org/enichollsv/bioslis_web.git

ii. Cambiar a rama de desarrollo asignada <nombre_rama_desarrollador>

git checkout -b <nombre_rama_desarrollador>

iii. Revisar ramas locales

git branch

iv. Apuntar local a remota

git branch --set-upstream-to=origin/<nombre_rama_desarrollador> <nombre_rama_desarrollador>



## Ambiente
En esta sección se describe un metodo de desarrollo que levanta la aplicación en un contenedor Docker lo cual hace que levantar la aplicación sea rapido.

(se recomienda evaluar usar [Spring Boot](https://spring.io/projects/spring-boot) en el futuro pues ayudaría a mejorar este método)

### Requerimientos

Para comenzar a desarrollar usando este metodo se requiere lo siguiente:

1. Java 8
2. Maven
3. [Docker](https://docs.docker.com/engine/install)
4. Terminal con Bash Shell (para Windows 10 se recomienda usar [Windows Subsystem for Linux](https://docs.microsoft.com/en-us/windows/wsl/install-win10))

### Uso

Para compilar, generar el contenedor Docker y levantarlo se debe ejecutar el siguiente script:

```
$ ./run-development.sh
```

Este script hace lo siguiente:

1. Compila el archivo WAR
2. Genera un contenedor docker usando Tomcat 7 con el archivo WAR
3. Levanta el contenedor docker
4. Abre un navegador con (o escribe escribe en consola) la URL para acceder a la aplicación
5. Abre una consola dentro del contenedor donde se puede acceder a los logs y todo el ambiente de Tomcat
6. Al salir de la consola del contenedor (con el comando `exit`) el script baja el contendor

## Agregar una vista y controlador

Para agregar una vista y controlador se deben crear un archivo llamado `NombreController.java` en la ruta `src/java/com/grupobios/bioslis/controller/` que correspodne al controlador y otro llamado `Nombre.jsp` en la ruta `src/webapp/WEB-INF/jsp/` que corresponde a la vista. Para ambos archivos `Nombre` debe ser reemplazado por el nombre de la vista/controlador.

Además de lo anterior se deben agregar las siguientes lineas en el archivo `src/webapp/WEB-INF/dispatcher-servlet.xml`:

```xml
    <bean id="urlMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                ...
                <prop key="/Nombre">nombre</prop> <!--agregar esta linea 1-->
                ...
            </props>
        </property>
    </bean>
```

```xml

    <bean id="multipartResolver"
          class="org.springframework.web.multipart.commons.CommonsMultipartResolver" />
    ...
    <bean name="microbiologia" class="com.grupobios.bioslis.microbiologia.controller.MicrobiologiaController"/> <!--agregar esta linea 2-->
    ...
    <mvc:resources mapping="/resources/**" location="/resources/" />
    <mvc:annotation-driven />

```

En el ejempo anterior see sigue con el ejemplo donde el controlador se llama `Nombre`.

A continuación se muestra un ejemplo del archivo para un controlador donde el controlador se llama `Microbiologia`:

```java
package com.grupobios.bioslis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MicrobiologiaController {
 
    ModelAndView mav = new ModelAndView();

    @RequestMapping("/Microbiologia")
    public ModelAndView pageLoad() {
        mav.addObject("someVariable", "Vista de microbiología");
        mav.setViewName("Microbiologia");
        return mav;
    }
    
}
````

y su respectiva vista sería:

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="styles.jsp"/>
        <title>Microbiologia</title>
    </head>
    <body id="kt_body" class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
        <jsp:include page="SideBar.jsp"/>

        <div class="container mt-5">
            ${someVariable}
        </div>
        
        <jsp:include page="scripts.jsp"/>
    </body>
</html>
```

en el ejemplo de la vista anterior es importante tener el cuenta las siguientes lineas pues sirven para modularizar las vistas pues corresponden a elementos compratidos a través de la aplicación:

1. `<jsp:include page="styles.jsp"/>`: Agrega los estilos de la aplicación a la vista respectiva.
2. `<jsp:include page="SideBar.jsp"/>`: Agrega el sidebar que es compartido por toda la aplicación.
3. `<jsp:include page="scripts.jsp"/>`: Agrega los scripts Javascript que son comunes a la aplicación.

Estos archivos JSP base se encuentran en la misma ruta de los otros archivos JSP. Es recomendable en caso de tener vistas que puedan ser reutilizadas generar los archvios JSP y usarlos de esta manera,

ii. Cambiar a rama de desarrollo asignada <nombre_rama_desarrollador>

git checkout -b <nombre_rama_desarrollador>

iii. Revisar ramas locales

git branch

iv. Apuntar local a remota

git branch --set-upstream-to=origin/<nombre_rama_desarrollador> <nombre_rama_desarrollador>









































## Ambiente
En esta sección se describe un metodo de desarrollo que levanta la aplicación en un contenedor Docker lo cual hace que levantar la aplicación sea rapido.

(se recomienda evaluar usar [Spring Boot](https://spring.io/projects/spring-boot) en el futuro pues ayudaría a mejorar este método)

### Requerimientos

Para comenzar a desarrollar usando este metodo se requiere lo siguiente:

1. Java 8
2. Maven
3. [Docker](https://docs.docker.com/engine/install)
4. Terminal con Bash Shell (para Windows 10 se recomienda usar [Windows Subsystem for Linux](https://docs.microsoft.com/en-us/windows/wsl/install-win10))

### Uso

Para compilar, generar el contenedor Docker y levantarlo se debe ejecutar el siguiente script:

```
$ ./run-development.sh
```

Este script hace lo siguiente:

1. Compila el archivo WAR
2. Genera un contenedor docker usando Tomcat 7 con el archivo WAR
3. Levanta el contenedor docker
4. Abre un navegador con (o escribe escribe en consola) la URL para acceder a la aplicación
5. Abre una consola dentro del contenedor donde se puede acceder a los logs y todo el ambiente de Tomcat
6. Al salir de la consola del contenedor (con el comando `exit`) el script baja el contendor

## Agregar una vista y controlador

Para agregar una vista y controlador se deben crear un archivo llamado `NombreController.java` en la ruta `src/java/com/grupobios/bioslis/controller/` que correspodne al controlador y otro llamado `Nombre.jsp` en la ruta `src/webapp/WEB-INF/jsp/` que corresponde a la vista. Para ambos archivos `Nombre` debe ser reemplazado por el nombre de la vista/controlador.

Además de lo anterior se deben agregar las siguientes lineas en el archivo `src/webapp/WEB-INF/dispatcher-servlet.xml`:

```xml
    <bean id="urlMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                ...
                <prop key="/Nombre">nombre</prop> <!--agregar esta linea 1-->
                ...
            </props>
        </property>
    </bean>
```

```xml

    <bean id="multipartResolver"
          class="org.springframework.web.multipart.commons.CommonsMultipartResolver" />
    ...
    <bean name="microbiologia" class="com.grupobios.bioslis.microbiologia.controller.MicrobiologiaController"/> <!--agregar esta linea 2-->
    ...
    <mvc:resources mapping="/resources/**" location="/resources/" />
    <mvc:annotation-driven />

```

En el ejempo anterior see sigue con el ejemplo donde el controlador se llama `Nombre`.

A continuación se muestra un ejemplo del archivo para un controlador donde el controlador se llama `Microbiologia`:

```java
package com.grupobios.bioslis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MicrobiologiaController {
 
    ModelAndView mav = new ModelAndView();

    @RequestMapping("/Microbiologia")
    public ModelAndView pageLoad() {
        mav.addObject("someVariable", "Vista de microbiología");
        mav.setViewName("Microbiologia");
        return mav;
    }
    
}
````

y su respectiva vista sería:

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="styles.jsp"/>
        <title>Microbiologia</title>
    </head>
    <body id="kt_body" class="header-fixed header-mobile-fixed subheader-enabled subheader-fixed aside-enabled aside-fixed aside-minimize-hoverable aside-minimize">
        <jsp:include page="SideBar.jsp"/>

        <div class="container mt-5">
            ${someVariable}
        </div>
        
        <jsp:include page="scripts.jsp"/>
    </body>
</html>
```

en el ejemplo de la vista anterior es importante tener el cuenta las siguientes lineas pues sirven para modularizar las vistas pues corresponden a elementos compratidos a través de la aplicación:

1. `<jsp:include page="styles.jsp"/>`: Agrega los estilos de la aplicación a la vista respectiva.
2. `<jsp:include page="SideBar.jsp"/>`: Agrega el sidebar que es compartido por toda la aplicación.
3. `<jsp:include page="scripts.jsp"/>`: Agrega los scripts Javascript que son comunes a la aplicación.

Estos archivos JSP base se encuentran en la misma ruta de los otros archivos JSP. Es recomendable en caso de tener vistas que puedan ser reutilizadas generar los archvios JSP y usarlos de esta manera,
