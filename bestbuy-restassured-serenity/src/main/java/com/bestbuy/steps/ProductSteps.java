package com.bestbuy.steps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.models.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class ProductSteps extends ProductPojo{
    @Step("Creating product with name: {0}, type: {1}, price: {2}, upc: {3}, shipping: {4}, description: {5}, manufacturer: {6}, " +
            "model: {7}, image: {8}, url: {9}")

    public ValidatableResponse createProduct(String name, String type, double price, String upc, int shipping,
                                             String description, String manufacturer, String model, String image, String url) {

        ProductPojo productPojo = ProductPojo.getProductPojo
                (name, type, price, upc, shipping, description, manufacturer, model, url, image);


        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post(Path.PRODUCTS)
                .then().log().all().statusCode(201);
    }

    @Step("Getting the product information with productID: {0}")

    public HashMap<String, Object> getProductInfoById(int productID) {

        return SerenityRest.given().log().all()
                .when()
                .pathParam("productID", productID)
                .get(Path.PRODUCTS + EndPoints.GET_PRODUCT_BY_ID)
                .then().log().all()
                .statusCode(200)
                .extract().path("");
    }

    @Step("Updating the product information using PATCH")

    public ValidatableResponse updateProduct(int productID, String name, String type, double price, String upc, int shipping,
                                             String description, String manufacturer, String model, String image, String url) {

        ProductPojo productPojo = ProductPojo.getProductPojo
                (name, type, price, upc, shipping, description, manufacturer, model, url, image);


        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .patch(Path.PRODUCTS + EndPoints.UPDATE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);

    }

    @Step("Deleting product information by productID")

    public ValidatableResponse deleteProductById(int productID) {

        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .delete(Path.PRODUCTS + EndPoints.DELETE_PRODUCT_BY_ID)
                .then().log().all()
                .statusCode(200);
    }

    @Step("Verifying Deleted Product information with productID : {0}")
    public ValidatableResponse getProductById(int productID) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .get(Path.PRODUCTS + EndPoints.GET_PRODUCT_BY_ID)
                .then().log().all()
                .statusCode(404);
    }
}

