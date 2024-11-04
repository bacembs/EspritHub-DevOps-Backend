package tn.esprit.esprithub.controllers;
import com.google.gson.Gson;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import tn.esprit.esprithub.entities.CheckoutPayment;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping(value = "/api")
//@CrossOrigin(origins = "*")
public class StripeController {

    // create a Gson object
    private static Gson gson = new Gson();

    @PostMapping("/payment")
    /**
     * Payment with Stripe checkout page
     *
     * @throws StripeException
     */
    public String paymentWithCheckoutPage(@RequestBody CheckoutPayment payment) throws StripeException {
        // We initilize stripe object with the api key
        init();
        // We create a  stripe session parameters
        SessionCreateParams params = SessionCreateParams.builder()
                // We will use the credit card payment method
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl(payment.getSuccessUrl())
                .setCancelUrl(
                        payment.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(payment.getQuantity())
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount())
                                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                                        .builder().setName(payment.getName()).build())
                                                .build())
                                .build())
                .build();
        // create a stripe session
        Session session = Session.create(params);

        if (session != null) {
            // Print success message
            System.out.println("Payment session created successfully. Session ID: " + session.getId());
        } else {
            // Print error message
            System.out.println("Failed to create payment session.");
        }

        Map<String, String> responseData = new HashMap<>();
        // We get the sessionId and we putted inside the response data you can get more info from the session object
        responseData.put("id", session.getId());
        // We can return only the sessionId as a String
        return gson.toJson(responseData);
    }

    private static void init() {

        Stripe.apiKey = "sk_test_51Oz6fDRqZpxfJakawVJ9naLxVgbpu5ZXmq52JupRG5OlSVQFvp1pJnRgAZUNsU51JYZ0A2mvZn4RcFNVtHJY2UPJ00TIouEuI4";
    }
}