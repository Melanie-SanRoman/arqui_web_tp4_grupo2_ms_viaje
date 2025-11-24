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
* Registrar el inicio de un viaje a traves del id de un monopatin.
* Finalizar un viaje calculando:
	* Kilometros recorridos.
	* Minutos de pausa solicitados a `pausa_service`.
	* Costo total usando `tarifa_service`.
* Exponer la informacion del viaje a otros sistemas.

## Entidad principal 

**Viaje**
| Campo       | Tipo        | Descripción                       |
| ----------- | ----------- | --------------------------------- |
| id          | Long        | Identificador único               |
| inicio      | LocalDate   | Inicio del viaje                  |
| fin         | LocalDate   | Fin del viaje                     |
| monopatinId | Long        | ID del monopatín asociado         |
| kilometros  | Double      | Kilómetros recorridos             |
| costo       | Double      | Costo final calculado             |
| pausas      | List<Long>  | IDs de pausas asociadas al viaje  |

## Endpoints disponibles

* POST `/api/viajes/iniciar/{monopatinId}`
	* Inicia un viaje para el monopatin indicado.
 	* Devuelve: `ViajeResponseDTO.java`
```
{
	"inicio": fecha_actual,
	"fin": null,
	"monopatinId": monopatinId,
	"costo": 0.0,
	"kilometros": 0.0
}
```

* PUT `/api/viajes/{id}/finalizar`
	* Finaliza un viaje, obteniendo:
 		* Distancia recorrida (`MonopatinClient` + `DistanciaService`)
   		* Minutos de pausa (`PausaClient`)
     	* Costo final calculado (`TarifaClient`)
   * Devuelve: `ViajeResponseDTO.java` completo. 

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
   * Se recibe monopatinId.
   * Se obtiene el monopatín mediante `MonopatinClient`.
   * Se crea la entidad Viaje y se persiste.
   * Se retorna el `ViajeResponseDTO.java`.
   
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

## Diagramas del microservicio
Endpoint y dependencias internas

``` mermaid
flowchart LR
%% Estilos globales
%% ----------------------------------------
classDef service fill:#e8f1ff,stroke:#3a6ea5,stroke-width:2px,color:#0b2545,rx:10,ry:10;
classDef db fill:#fff4d6,stroke:#b68b00,stroke-width:2px,color:#4e3b00,rx:10,ry:10;
classDef op fill:#e2ffe9,stroke:#41a35a,stroke-width:2px,color:#1a4e26,rx:10,ry:10;
    subgraph Viaje_Service ["viaje_service"]
        A1[POST /viajes/iniciar]:::service -->|Crea viaje| A2[(Viaje)]:::db
        A3[GET /viajes/id]:::service -->|Consulta| A2
        A4[GET /viajes/id/finalizar]:::service -->|Finaliza| A2

        A2 -->|Calcular distancia| D[DistanciaService]:::service
        A2 -->|Obtener monopatín| M[monopatin_service]:::service
        A2 -->|Consultar pausas| P[pausa_service]:::service
        A2 -->|Calcular costo| T[tarifa_service]:::service
    end
```

## Colaboradores

> * Langenheim, Geronimo V.
> * Lopez, Micaela N.
> * San Roman, Emanuel.
> * San Roman, Melanie.
