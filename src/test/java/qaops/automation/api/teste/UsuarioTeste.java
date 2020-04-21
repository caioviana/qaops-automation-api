package qaops.automation.api.teste;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import qaops.automation.api.dominio.Usuario;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class UsuarioTeste {

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://reqres.in";
        basePath = "/api";
    }


    @Test
    public void testListaPaginaDeUsuarioEspecifica() {
        given().
            params("page","2").
        when().
            get("/users").
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testSuccessfullyCreateaUser() {
        Usuario usuario = new Usuario("rafael","eng test", "email@gmail.com");

        given().
            contentType(ContentType.JSON).
            body(usuario).
        when().
            post("/user").
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("rafael"));
    }

    @Test
    public void testListaUsuario() {
        given().
            pathParam("user", "2").
        when().
            get("/users/{user}").
        then().
            statusCode(HttpStatus.SC_OK).
            body("data.email", containsString("@reqres.in"));
    }
}
