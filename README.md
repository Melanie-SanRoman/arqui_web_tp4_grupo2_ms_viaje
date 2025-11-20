# Arquitecturas Web - Microservicio -> `viaje_service`

## Descripcion

Microservicio encargado de gestionar los viajes de monopatines.
Permite crear, finalizar y consultar viajes, obteniendo datos externos sobre pausas y tarifas a través de otros microservicios.

## Arquitectura y responsabilidades

Este microservicio forma parte de un sistema distribuido compuesto por:

* `pausa_service` -> Gestiona pausas de un viaje.
* `tarifa_service` -> Calcula el costo total de un viaje según tarifa y kilómetros.

Microservicio externo:
* `monopatin_service` -> Desplaza la ubicacion del monopatin.

Responsabilidades de `viaje_service`:
* Registrar el inicio de un viaje.
* Finalizar un viaje calculando:
	* Kilometros recorridos.
	* Minutos de pausa solicitados a `pausa_service`.
	* Costo total usando `tarifa_service`.
* Exponer la informacion del viaje a otros sistemas.

## Entidad principal 

**Viaje**
| Campo       | Tipo      | Descripción                    |
| ----------- | --------  | ---------------------          |
| id          | Long      | Identificador único            |
| inicio      | LocalDate | Inicio del viaje               |
| fin         | LocalDate | Fin del viaje                  |
| monopatinId | Long      | ID del monopatín asociado      |
| kilometros  | Double    | Kilómetros recorridos          |
| costo       | Double    | Costo final calculado          |

## Comunicacion con otros microservicios

### _PausaClient_

Consume endpoints de `pausa_service`:

* GET `/api/pausas/viaje/{idViaje}/total-minutos`
  * Obtiene los minutos totales de pausa en formato `Double`.
* GET `/api/pausas/viaje/{id}/pausas`.
	* Devuelve la lista de pausas registradas para auditoria o calculos complementarios.

### _TarifaClient_

Consume `tarifa_service`:

* POST `/api/tarifas/calcular`
  * Envia: `CostoRequestDTO.java`
    
  ```json
  {
    "kilometros": 12.5,
    "minutosPausa": 3.5
  }
  ```
  
  * Recibe: `CostoResponseDTO.java`
  ```json
  {
  "costo": 123.0,
  "tipoTarifa": "BASICA"
  }
  ```

### _MonopatinClient_

Consume endpoints de `monopatin_service`:

* GET `api/monopatines/{id}`
  * Obtiene un DTO de Monopatin a traves de su id.
* PATCH `/api/monopatines/{id}/localizacion`
  * Desplaza la ubicacion del monopatin y lo retorna.   

## Logica de negocio

1. Inicio del viaje:
   Se registra inicio + monopatinId
   
3. Finalizacion del viaje:
   * Se calcula distancia recorrida.
   * Se consultan minutos de pausa al `pausa_service`.
   * Se envia kilometros + minutos de pausa a `tarifa_service`.
   * Se obtiene el costo inicial.
   * Se persiste y se retorna el DTO final.
  
## Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL
* RestTemplate

Pendientes de agregar: 
* Swagger
* Autenticacion JWT

## Ejecucion 

Requisistos previos:
* Java 21+
* Maven
* MySQL en ejecucion

Ajustar credenciales en `application.properties`

```
spring.datasource.url=jdbc:mysql://localhost:3306/viaje_db
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

## Diagrama del microservivio

*Agregar*

## Colaboradores

> * Langenheim, Geronimo V.
> * Lopez, Micaela N.
> * San Roman, Emanuel.
> * San Roman, Melanie.
