package com.bestbuy.crudsuite;

import com.bestbuy.steps.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ProductCRUDTestWithSteps extends TestBase {

    static String name = "Duracell" + TestUtils.getRandomString();
    static String type = "HardGood" + TestUtils.getRandomValue();
    static Double price = 5.75;
    static String upc =  TestUtils.getRandomValue();
    static int shipping = 0;
    static String description = "Compatible with select electronic devices; AAA size; DURALOCK Power Preserve technology; 4-pack";
    static String manufacturer = "Duracell";
    static String model = "MN2400B4Z";
    static String url = "http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";
    static int productID;

    @Steps
    ProductSteps productSteps;


    @Title("Create a new Product")
    @Test
    public void test01_createProduct() {

        ValidatableResponse response = productSteps.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image).statusCode(201);
        productID = response.log().all().extract().path("id");
    }

    @Title("Verify if the product was added to application")
    @Test
    public void test02_verifyNewProduct() {
        HashMap<String, Object> productMap = productSteps.getProductInfoById(productID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test03_updateNewProduct() {
        ValidatableResponse response = productSteps.updateProduct(productID, name, type, price, upc, shipping, description, manufacturer, model, url, image).statusCode(200);
        productID = response.log().all().extract().path("id");

        HashMap<String, Object> productMap = productSteps.getProductInfoById(productID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Delete the product and verify if the product has been deleted")
    @Test
    public void test04_deleteProduct() {
        productSteps.deleteProductById(productID).statusCode(200);
        productSteps.getProductById(productID).statusCode(404);
    }

}
