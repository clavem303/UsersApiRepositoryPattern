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

        String patchBody = """
                {
                    "name": "Bob Johnson",
                    "age": 46,
                    "email": "bob.johnson@johnson.com"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(patchBody)
                .when()
                .post("/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetUsers(){
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("", hasSize(3)) // Quantidade de registros na coleção.
                .body("[1].name", is("Alice Smith")) //Verifica campo da posição 2.
                .body("[1].email", is("alice@example.com"));
    }

    @Test
    public void testGetUserById() {

        testCreateUser();

        given()
                .pathParam("id", 1)   // ① Define o valor do path param
                .when()
                .get("/users/{id}")   // ② Usa o path param na URL
                .then()
                .statusCode(200)     // ③ Verifica que retorna 200 OK
                .body("name", is("Roy Updated"))                      // ④ Checa o campo name
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
                .body("", hasSize(3))   // ④ Garante que apenas um usuário foi retornado
                .body("[0].name", is("Roy Fielding"))                 // ⑤ Valida o nome
                .body("[0].email", is("roy.fielding@rest.com"));      // ⑥ Valida o e-mail
    }

    @Test
    public void testPatchUser() {
        // Primeiro, cria o usuário para ter algo para atualizar
        testCreateUser();

        // Define o JSON parcial para atualizar o nome
        String patchBody = """
        {
            "name": "Roy Updated"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(patchBody)
                .pathParam("id", 1) // Id do usuário criado
                .when()
                .patch("/users/{id}")
                .then()
                .statusCode(200)
                .body("name", is("Roy Updated"))
                .body("email", is("roy.fielding@rest.com")); // email permanece o mesmo
    }

    @Test
    public void testPatchUserNotFound() {
        String patchBody = """
        {
            "name": "Nonexistent User"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(patchBody)
                .pathParam("id", 9999) // Id que provavelmente não existe
                .when()
                .patch("/users/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDeleteUser() {
        // Deleta o usuário com id 3 (ajuste se necessário)
        given()
                .pathParam("id", 3)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(204); // No Content

        // Verifica que o usuário foi realmente deletado
        given()
                .pathParam("id", 3)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(500);
    }

    @Test
    public void testDeleteUserNotFound() {
        given()
                .pathParam("id", 9999)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(404);
    }


}
