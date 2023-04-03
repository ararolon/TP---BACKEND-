# ELECTIVA - BACKEND
# Trabajo Práctico: Primer Parcial
### Prof.: Ing. Gustavo Sosa Cataldo
### ----- INTEGRANTES -----
### - Aramy Rolón
### - Alisha Rotela
### - Jennifer Staple

Este Trabajo involucra la implementación del Backend de un sistema informático que será especificado para cada grupo. El proyecto desarrollado se validará con peticiones GET, POST, PUT y DELETE que deberán
elaborar y facilitar para el día de la revisión de manera a validar el funcionamiento. Como complemento también pueden tener preparadas consultas (select) SQL como apoyo a las peticiones GET para verificar el estado de los
datos.
### Enunciado 1: Sistema de fidelización de clientes
Se requiere la implementación de un sistema de fidelización de clientes que pueda hacer
seguimiento de los puntos otorgados por operaciones de pago.
Los módulos a desarrollar son los siguientes:
#### 1) Administración de datos del cliente (POST,GET,PUT, DELETE)
Este módulo contempla la administración de datos del cliente, los cuales serán los que acumulen puntos de fidelidad con sus operaciones.
Los datos de clientes a almacenar serán los siguientes: nombre, apellido, número de documento, tipo de documento, nacionalidad, email, teléfono, fecha de nacimiento.
Estructura: id autogenerado, nombre, apellido, número de documento, tipo de documento, nacionalidad, email, teléfono, fecha de nacimiento.
#### 2) Administración de conceptos de uso de puntos (POST,GET,PUT, DELETE)
Este módulo contempla la administración de los diferentes conceptos que especifican a qué fueron destinados los puntos utilizados, con su respectiva cantidad de puntos requerida. Por
ejemplo: vale de premio, vale de descuento, vale de consumición, etc.
Estructura: id autogenerado, descripción de concepto, puntos requeridos.
#### 3) Administración de reglas de asignación de puntos (POST,GET,PUT, DELETE)
Este módulo permite definir las reglas que rigen la cantidad de puntos a asignar a un cliente en base al rango de valor de consumo:
-Ejemplo:
* 0 a 199.999Gs.: 1 punto cada 50.000
* 200.000Gs. a 499.999Gs. 1 punto cada 30.000
* 500.000Gs. para arriba: 1 punto cada 20.000
Observación: los rangos serán opcionales, es decir, se puede tener una sola regla que asigne 1 punto cada X Gs. sin importar en qué rango cae el monto de la operación.
Estructura: id autogenerado, limite inferior, límite superior, monto de equivalencia de 1 punto
#### 4) Parametrización de vencimientos de puntos (POST,GET,PUT, DELETE)
Este módulo permite definir el tiempo de validez de los puntajes asignados a los clientes. Una vez alcanzado el tiempo determinado, los puntos son descontados de la bolsa por
vencimiento.
Estructura: id autogenerado, fecha de inicio de validez, fecha fin de validez, días de duración del puntaje.
#### 5) Bolsa de puntos
Estructura: id autogenerado, identificador del cliente, fecha de asignación de puntaje, fecha de caducidad de puntaje, puntaje asignado, puntaje utilizado, saldo de puntos, monto de la
operación
#### 6) Uso de puntos
Debe utilizarse en un esquema FIFO (primero se utilizan las bolsas más antiguas). Tiene un detalle debido a que para satisfacer una petición de puntos se puede utilizar más de una bolsa.
Estructura:
- Cabecera: id autogenerado, identificador del cliente, puntaje utilizado, fecha, concepto de uso de punto
- Detalle: id autogenerado, identificador de la cabecera, puntaje utilizado, identificador de la bolsa de puntos utilizada
#### 7) Consultas (GET)
Este módulo contempla la consulta para el desarrollo de reportes. Las consultas a proveer son:
- uso de puntos por: concepto de uso, fecha de uso, cliente
- bolsa de puntos por: cliente, rango de puntos
- clientes con puntos a vencer en x días
- consulta de clientes por: nombre (aproximación), apellido (aproximación), cumpleaños
#### 8) Servicios
- carga de puntos (POST): se recibe el identificador de cliente y el monto de la operación, y se asigna los puntos (genera datos con la estructura del punto 5)
- utilizar puntos (POST): se recibe el identificador del cliente y el identificador del concepto de uso y se descuenta dicho puntaje al cliente registrando el uso de puntos
(genera datos con la estructura del punto 6 y actualiza la del punto 5) o además debe enviar un correo electrónico al cliente como comprobante
- consultar cuantos puntos equivale a un monto X (GET): es un servicio informativo que devuelve la cantidad de puntos equivalente al monto proporcionado
como parámetro utilizando la configuración del punto 3
#### 9) Proceso planificado cada x horas
Proceso que pueda planificarse que corra cada X horas y actualice el estado de las bolsas con puntos vencidos.
