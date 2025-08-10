# Users API - Repository Pattern #

## Project structure ##
* models/User.java
  * *Database table representation.*
* DTOs/UserDTO.java
  * *Decoupling the internal representation (User) from the external representation (JSON).*
* repositories/UserRepository.java
  * *Isolation and centralization of all data access logic.*
* services/UserService.java
  * *Intermediary between the API layer and the data access layer.*
* resources/UserResource.java
  * *Exposing business logic via HTTP endpoints.*
  
## Extensions ##
* quarkus-hibernate-orm-panache
* quarkus-jdbc-h2
* quarkus-rest-jackson
* lombok

## Application Properties ##
* quarkus.datasource.db-kind=h2
  * *Database type.*
* quarkus.datasource.jdbc.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
  * *JDBC URL with in-memory database (does not persist across restarts).*
* quarkus.datasource.username=sa
* quarkus.datasource.password=
  * *Username and password.*
* quarkus.hibernate-orm.schema-management.strategy=drop-and-create
  * *Schema generation: drop and create.*
* quarkus.hibernate-orm.log.sql=true
  * *(optional) Show SQLs in the log.*

## Apprenticeship Program ##
* Resource Annotations: 
  * @Path
    * @Consumes
    * @Produces
  * @GET
    * @PathParam
    * @QueryParam
  * @POST
  * @PATCH
  * @DELETE
* Communication between Services:
  * Synchrone
    *  extension => rest-client-jackson
      * @RegisterRestClient
      * @RestClient
  * Asynchronous