# Proyecto IS2

## Descripción General

El objetivo de este proyecto es construir una aplicación de gestión de la
información con una arquitectura de 3 capas: Presentación, Negocio e
Integración.


## Restricciones

- Es obligatorio realizar el proyecto con los patrones arquitectónicos y de
    diseño explicados en clase correctamente aplicados.
- El lenguaje para caracterizar el diseño de la aplicación debe ser *UML 2.x*.
    y la herramienta para el modelado *Modelio 5.*
- La aplicación debe ser de *escritorio* (no web).
- El lenguaje de implementación debe ser *Java* y el entorno de programación
    *Eclipse.*
- La persistencia de los datos debe hacerse en formato *JSON.*
- El código debe incluir pruebas *JUnit.*
- Es recomendable usar un sistema de control de versiones para
    desarrolladores. Si lo usáis utilizad *Git y GitHub.*


## Iniciando el proyecto

- Elegir tipo proyecto de gestión que facture (o que genere un ticket).
    Ejemplo: Gestión Pastelería.
- Modelo del dominio con diagrama de clases.
- Subsistemas identificados (todos los proyectos tienen que tener el
    subsistema Facturas).
- Para los subsistemas que se indiquen en clase, su D.C.U. y cada C.U.
    especificarlo con diagrama de actividades (en Modelio 5.4).
- A lo largo del curso se pedirán más diagramas ya correspondientes a
    la asignatura IS2: diagramas de clase, secuencia...


## Jerarquía de paquetes

Los subsistemas se subdividen y se organizan en *3 capas/paquetes: integración, negocio y presentación.*
