package petstore;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIBOidTable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    //3.1 - atributos
    String uri = "https://petstore.swagger.io/v2/pet";

    //3.2  - Metodos e funções


    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir- Create - Post
    @Test(priority = 1) // identifica o metodo ou função como um teste para o testeNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("DB/pet1json");

        // Sintaxe Gherkin
        // dado - quando - então (given- when - then)

        given()
                .contentType("application/json") // comum em API rest assured
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Athena"))
                .body("status", is("available"))
                .body("category.name", is("Dog"))
                .body("tags.name", contains("vacinated"))
                .body("tags.id", contains(2021))
        ;
    }

    @Test(priority = 2)
    public void consultarPet(){
        String petId = "103031198989";

        given()
                .contentType("aplication/json")
                .log().all()

        .when()
                .get(uri + "/" + petId)

        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Athena"))
                .body("status", is("available"))
                .body("category.name", is("Dog"))
                .body("tags.name", contains("vacinated"))

        ;

}

@Test (priority = 3)
public void AlterarPet() throws IOException {

      String jsonBody = lerJson("DB/pet2json");

      given()
              .contentType("application/json")
              .log().all()
              .body(jsonBody)
      .when()
              .put(uri)
      .then()
              .log().all()
              .statusCode(200)
              .body("name",is("Athena"))
              .body("status", is ("Sold"))
      ;

}

@Test(priority = 4)
    public void excluirPet() throws IOException {

    String petId = "103031198989";


    given()
            .contentType("application/json")
            .log().all()
    .when()
            .delete(uri + "/" + petId)
    .then()
            .log().all()
            .statusCode(200)
            .body("code", is (200))
            .body("type", is ("unknown"))
            .body("message", is (petId))

    ;
}



}
