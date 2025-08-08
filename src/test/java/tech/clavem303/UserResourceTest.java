package tech.clavem303;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserResourceTest {

    @Test
    public void testCreateUser() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                          {
                              "name": "Roy Fielding",
                              "email": "roy.fielding@rest.com"
                           }
                        """)
                .when()
                .post("/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetUsers(){

        testCreateUser();

        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("", hasSize(2))
                .body("[0].name", is("Roy Fielding"))
                .body("[0].email", is("roy.fielding@rest.com"));
    }

    @Test
    public void testGetUserById() {

        testCreateUser();

        given()
                .pathParam("id", 3)   // ① Define o valor do path param
                .when()
                .get("/users/{id}")   // ② Usa o path param na URL
                .then()
                .statusCode(200)     // ③ Verifica que retorna 200 OK
                .body("name", is("Roy Fielding"))                      // ④ Checa o campo name
                .body("email", is("roy.fielding@rest.com"));           // ⑤ Checa o campo email
    }

    @Test
    public void testGetUserByEmail() {

        testCreateUser();

        given()
                .queryParam("email", "roy.fielding@rest.com")  // ① Passa o e-mail como parâmetro de consulta
                .when()
                .get("/users")  // ② Realiza a requisição GET para /users?email=...
                .then()
                .statusCode(200)        // ③ Verifica que retorna 200 OK
                .body("", hasSize(1))   // ④ Garante que apenas um usuário foi retornado
                .body("[0].name", is("Roy Fielding"))                 // ⑤ Valida o nome
                .body("[0].email", is("roy.fielding@rest.com"));      // ⑥ Valida o e-mail
    }

}
