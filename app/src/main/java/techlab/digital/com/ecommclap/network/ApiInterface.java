package techlab.digital.com.ecommclap.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import techlab.digital.com.ecommclap.model.CityLocationModel.CityLocationResponse;
import techlab.digital.com.ecommclap.model.CreateTicketModel.CreateTicketReq;
import techlab.digital.com.ecommclap.model.GetTotalPayable;
import techlab.digital.com.ecommclap.model.RemoveScheduleOrder;
import techlab.digital.com.ecommclap.model.ScheduleOrderResponse.ScheduleOrdrResp;
import techlab.digital.com.ecommclap.model.UpdateSchuledOrderQuantityModel;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.NewAddTocart;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.newAddToCartResponse;
import techlab.digital.com.ecommclap.model.complaints_model.ListComplaint;
import techlab.digital.com.ecommclap.model.complaints_model.get_complain_replies.GetChatHistory;
import techlab.digital.com.ecommclap.model.couponModel.CouponDetailsResponse;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponse;
import techlab.digital.com.ecommclap.model.fetchSubProducts.ProductListingsModeResponsetTwo;
import techlab.digital.com.ecommclap.model.fetch_category.Category;
import techlab.digital.com.ecommclap.model.walletDeduction.WalletDeductedResponse;
import techlab.digital.com.ecommclap.model.auth.LoginReq;
import techlab.digital.com.ecommclap.model.auth.LoginResponse;
import techlab.digital.com.ecommclap.model.auth.RegistrationReq;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartReq;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartResponse;
import techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel.AddToCartWithVariationReq;
import techlab.digital.com.ecommclap.model.cartTotal.CartTotalResponse;
import techlab.digital.com.ecommclap.model.categories.subCategories.FetchSubCategory;
import techlab.digital.com.ecommclap.model.imageSlider.ImageSliderResponse;
import techlab.digital.com.ecommclap.model.orderPlaced.OrderResponse;
import techlab.digital.com.ecommclap.model.registrationModel.RegisterUserResponse;
import techlab.digital.com.ecommclap.model.update_order.UpdateCartPayment;
import techlab.digital.com.ecommclap.model.userDetails.UserDetailsResponse;

public interface ApiInterface {

    @GET("/ecom-urbanclap/wp-json/wp/v2/media?slug=151,4,8")
    Call<List<ImageSliderResponse>> fetchImageSlider();

    @POST("/ecom-urbanclap/wp-json/wc/v2/user_login")
    Call<LoginResponse> getToken(@Body LoginReq loginReq);

    @POST("/ecom-urbanclap/wp-json/wc/v2/cart/clear")
    Call<Object> cartClear(@Header("Authorization") String token);


    @DELETE("/ecom-urbanclap/wp-json/wc/v2/cart/cart-item")
    Call<ResponseBody> deleteItems(@Header("Authorization") String token,@Query("cart_item_key") String cart_item_key);

  //  @GET("/ecom-urbanclap/wp-json/wc/v2/cart")
     @GET("/ecom-urbanclap/wp-json/wc/v2/cart")
    Call<JsonObject>  getCartItems(@Header("Authorization") String token, @Query("thumb") Boolean bool);



    @GET("/ecom-urbanclap/wp-json/wc/v2/products/categories")
    Call<List<FetchSubCategory>> getSubcategories(@Query("parent") int id);


  /*  @GET("/ecom-urbanclap/wp-json/wc/v1/products")
    Call<List<ProductListingsModeResponse>> getProducts(@Query("filter[product_cat]") String categoryName);
*/


    @GET("/ecom-urbanclap/wp-json/wc/v1/products")
    Call<List<ProductListingsModeResponse>> getProducts(@Query("location") String location,@Query("product_category") String categoryName);


     @GET("/ecom-urbanclap/wp-json/wc/v2/cart/totals")
     Call<CartTotalResponse> getCartTotal(@Header("Authorization")String token, @Query("id") int id);

    @Headers("Content-Type: application/json")
    @POST("/ecom-urbanclap/wp-json/wc/v2/cart/add")
    Call<newAddToCartResponse> addToCart(@Header("Authorization")String token,@Body AddToCartReq addCartModelsReq);

    @Headers("Content-Type: application/json")
    @POST("/ecom-urbanclap/wp-json/wc/v2/cart/add")
    Call<newAddToCartResponse> addToCart(@Header("Authorization")String token, @Body AddToCartWithVariationReq addCartModelsReq);

   /*
   @Multipart
    @POST("/ecom-urbanclap/wp-json/wc/v2/cart/add")
    Call<newAddToCartResponse> addToCart(@Header("Authorization")String token, @Part("product_id") String productid, @Part("quantity") String quantity);
*/


    @POST("/ecom-urbanclap/wp-json/wc/v2/user_signup")
     Call<Object> submitUserDetails(@Body RegistrationReq regReq);


    @Headers({
            "Content-Type: application/json; charset=utf-8",

    })
     @POST("/ecom-urbanclap/wp-json/wc/v3/orders")
     Call<OrderResponse> createOrders(@Header("Authorization")String token, @Body RequestBody regReq);

    @POST("/ecom-urbanclap/wp-json/wc/v2/create_order")
    Call<OrderResponse> createScheduleOrders(@Header("Authorization")String token, @Body RequestBody regReq);


    @POST("/ecom-urbanclap/wp-json/wc/v3/orders")
    Call<JsonArray> userNumberConfirmation(@Query ("phone") String phone);

   /* @GET("/ecom-urbanclap/wp-json/wc/v3/orders?type=normal")
    Call<JsonArray> fetchUserHistory(@Header("Authorization")String token,@Query ("customer_id") int customer);*/
   @GET("/ecom-urbanclap/wp-json/wc/v3/orders?type=normal&customer_id")
   Call<JsonArray> fetchUserHistory(@Header("Authorization")String token,@Query("customer_id") int customer_id);

    @GET("/ecom-urbanclap/wp-json/wc/v2/get_user")
    Call<UserDetailsResponse> fetchUserDetails(@Query ("customer_id") int customer_id,@Header("Authorization")String token);

   @PUT("/ecom-urbanclap/wp-json/wc/v2/customers/{id}")
   Call<ResponseBody> updateUserDetails(@Header("Authorization")String token,@Body UserDetailsResponse userDetailsResponse,@Path("id") int id);


    @PUT("/ecom-urbanclap/wp-json/wc/v3/orders/{order_id}")
    Call<ResponseBody> updatePlacedOrder(@Header("Authorization")String token,@Body UpdateCartPayment updateCartPayment,@Path("order_id") int order_id);



    @GET("/ecom-urbanclap/wp-json/wp/v2/current_balance/{id}")
    Call<Object> getWalletBalance(@Header("Authorization") String token,@Path("id") int id);



     @POST("/ecom-urbanclap/wp-json/wc/v2/user_reward")
    Call<GetTotalPayable> getPayableAmountFromServer(@Header("Authorization")String token, @Query("id") int id, @Query("total_amount") String total_amount);



    @GET("/ecom-urbanclap/wp-json/wp/v2/wallet/{id}")
    Call<JsonArray> getWalletHistories(@Header("Authorization") String token,@Path("id") int id);

    @POST("/ecom-urbanclap/wp-json/wp/v2/wallet/{id}")
    Call<ResponseBody> rechargeWallet(@Header("Authorization")String token,@Path("id") int id,@Query("amount") int amount,@Query("details") String details,@Query("type") String type);


    @POST("/ecom-urbanclap/wp-json/wp/v2/wallet/{id}")
    Call<WalletDeductedResponse> walletbalanceDeduct(@Header("Authorization") String token, @Path("id") int id,@Query("type") String type,@Query("amount") String amount,@Query("details") String details);

     @POST("/ecom-urbanclap/wp-json/wc/v2/user_point")
     Call<Object> getUserReward(@Header("Authorization")String token, @Query("id") int id);

    /*fetching my offer.....*/

    @GET("/ecom-urbanclap/wp-json/wc/v2/coupons")
    Call<List<CouponDetailsResponse>> fetchAllCoupons();

    @Headers("Content-Type: application/json")
    @POST("/ecom-urbanclap/wp-json/wc/v2/user_coupon")
    Call<List<CouponDetailsResponse>> fetchUserCoupons(@Header("Authorization") String token);


    @GET("/ecom-urbanclap/wp-json/wc/v2/categories")
    Call<List<Category>> getCategories(@Query("parent") int id,@Query("per_page") int per_page);

    /*create new ticket.......*/
    @Headers("Content-Type: application/json")
    @POST("/ecom-urbanclap/wp-json/wc/v2/add_ticket")
    Call<Object> createTickets(@Header("Authorization")String token,@Body CreateTicketReq createTicketReq);

    /*to update quantity....*/
    @Headers("Content-Type: application/json")
    @POST("/ecom-urbanclap/wp-json/wc/v2/cart/cart-item")
    Call<ResponseBody> updateQuantity(@Header("Authorization") String token,@Query("cart_item_key") String cart_item_key,@Query("quantity")String quantity);

    @GET("/ecom-urbanclap/wp-json/wpas-api/v1/tickets")
    Call<List<ListComplaint>> fetch_complaits_List(@Header("Authorization") String token);


    @GET("/ecom-urbanclap/wp-json/wpas-api/v1/tickets/{id}/replies")
    Call<List<GetChatHistory>> get_chat_history(@Path("id") String id, @Header("Authorization") String token);

    @POST("/ecom-urbanclap/wp-json/wpas-api/v1/tickets/{ticket_id}/replies")
    Call<Object> add_Reply(@Header("Authorization")String token,@Path("ticket_id") String id,@Query("content") String content,@Query("author") String author);


    @GET("/ecom-urbanclap/wp-json/wc/v3/orders?type=schedule")
    Call<JsonArray> fetchScheduledProductsparents(@Header("Authorization")String token,@Query ("customer_id") int customer);


    @GET("/ecom-urbanclap/wp-json/wc/v3/orders?type=child")
    Call<JsonArray> fetchChildScheduledProducts(@Header("Authorization")String token,@Query ("customer_id") int customer,@Query ("parent_id") String parent_id,@Query ("delivery_date") String delivery_date);


    @GET("/ecom-urbanclap/wp-json/wc/v3/customers/{id}")
    Call<Object> update_user_password(@Header("Authorization")String token, @Path("id") int id, @Query ("password") String password);


    @GET("/ecom-urbanclap/wp-json/wc/v1/products?filter%5Bproduct_cat%5D=scheduling")
    Call<List<ProductListingsModeResponsetTwo>> get_schedulable_products(@Header("Authorization")String token);


    /*crete shedule order*/

    @POST("/ecom-urbanclap/wp-json/wc/v2/create_order")
    Call<ScheduleOrdrResp> createScheduleOrder(@Header("Authorization")String token, @Body RequestBody regReq);

    @Headers("Content-Type: application/json")
    @POST("/ecom-urbanclap/wp-json/wc/v2/update_order")
    Call<ResponseBody> updateScheduleQuantity(@Header("Authorization") String token, @Body UpdateSchuledOrderQuantityModel updateSchuledOrderQuantityModel);


    /*cancel schedule order*/
    @PUT("/ecom-urbanclap/wp-json/wc/v3/orders/{id}")
    Call<ResponseBody> cancelScheduleOrder(@Header("Authorization")String token, @Body RemoveScheduleOrder removeScheduleOrder, @Path("id") int id);

    /*its deduct the user reward point*/
    @POST("/ecom-urbanclap/wp-json/wc/v2/user_point_detect")
    Call<ResponseBody> userRewardPointDeduct(@Header("Authorization")String token, @Query ("id") int customer_id,@Query ("total_amount") String total_amount);


  /*  http://techlabdigital.co.in/ecom-urbanclap/wp-json/wc/v1/products?filter%5Bproduct_cat%5D=Vegetarian

    http://techlabdigital.co.in/ecom-urbanclap/wp-json/wc/v2/categories_schedule?parent=0&per_page=50 (edi

*/

    @GET("/ecom-urbanclap/wp-json/wc/v2/categories_schedule")
    Call<List<FetchSubCategory>> getSchedulable_subCategory(@Query("parent") int id);

    @GET("/ecom-urbanclap/wp-json/wc/v1/products")
    Call<List<ProductListingsModeResponsetTwo>> get_product_for_schedule(@Query("filter[product_cat]") String categoryName);

// to fetch city location list.......
//http://techlabdigital.co.in/ecom-urbanclap/wp-json/wc/v3/products/attributes/1/terms
    @GET("/ecom-urbanclap/wp-json/wc/v3/products/attributes/1/terms")
    Call<List<CityLocationResponse>> fetchCityLocation();

}
