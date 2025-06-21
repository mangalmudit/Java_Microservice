package com.assignmentdemo.payment_gateway_service.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assignmentdemo.payment_gateway_service.dto.Payment;
import com.assignmentdemo.payment_gateway_service.dto.PaymentResponse;

@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {
    
	@Value("${card.number.length}")
	private int cardNumberLength;

	@Value("${cvv.length}")
	private int cvvLength;
	/**
	 * Processes the payment by verifying the input details and, if valid, updating the payment status.
	 * If any input details are invalid, the method returns a failure response with an appropriate error message.
	 * If the payment details are valid, the method generates a transaction ID and returns a success message along with the payment details.
	 *
	 * <p>This method works by first invoking the {@link #verifyUserInputDetails(Payment)} method to validate the payment
	 * information. If any validation fails, it returns a {@link ResponseEntity} containing a failed payment response
	 * with an HTTP status code of {@code 406 NOT_ACCEPTABLE}. If all validations pass, it proceeds to set the transaction
	 * details and returns a {@link ResponseEntity} containing a successful payment response with an HTTP status code of 
	 * {@code 202 ACCEPTED}.</p>
	 *
	 * @param payment The payment object containing details such as card number, CVV, recipient name, amount, etc.
	 *                This object will be validated before further processing.
	 *                <ul>
	 *                    <li>cardNumber (String): The card number to be validated.</li>
	 *                    <li>cvv (String): The CVV number to be validated.</li>
	 *                    <li>receiverAccountNumber (Integer): The receiver's account number to be validated.</li>
	 *                    <li>recepientName (String): The recipient's name to be validated.</li>
	 *                    <li>amount (Integer): The payment amount to be validated.</li>
	 *                    <li>accountNumnber (Integer): The account number to be validated.</li>
	 *                </ul>
	 * 
	 * @return A {@link ResponseEntity} containing the {@link PaymentResponse} object. The response can be:
	 *         <ul>
	 *             <li>{@code 406 NOT_ACCEPTABLE}: Returned when validation fails, indicating that the request is invalid 
	 *                 due to missing or incorrect information.</li>
	 *             <li>{@code 202 ACCEPTED}: Returned when the payment details are valid, indicating that the payment has 
	 *                 been successfully processed.</li>
	 *         </ul>
	 */
	public PaymentResponse processPayment(Payment payment) {
	    // Verify user input details and extract the PaymentResponse from the ResponseEntity
	    PaymentResponse paymentResponse = verifyUserInputDetails(payment);
	    
	    // If the verification fails (payment status is "failed"), return the response directly
	    if ("failed".equals(paymentResponse.getPaymentStatus())) {
	    	paymentResponse.setTransactionId("Transaction Id Not Generated");
	    	return paymentResponse ;
	    }
	    
	    // If verification passed, proceed with payment processing
	    // Set transaction details (assuming success)
	    paymentResponse.setTransactionId(generateTransactionId()); // Generate or fetch a transaction ID
	    paymentResponse.setPaymentStatus("Success");
	    paymentResponse.setMessage("Amount " + payment.getAmount() + " successfully transferred to " + 
	                               payment.getRecepientName() + "'s account on " + 
	                               LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    
	    // Return the response with an HTTP status code of 202 (ACCEPTED)
	    return paymentResponse;
	}

	// Helper method to generate a transaction ID (this can be modified based on your actual implementation)
	private String generateTransactionId() {
	    return "TXN" + System.currentTimeMillis(); // Simple example generating a transaction ID based on the current timestamp
	}

	/**
	 * Verifies the input details of the payment object to ensure all required fields are provided
	 * and valid according to predefined rules. The method checks the following:
	 * <ul>
	 *     <li>Card number: Must not be null and must have exactly 16 digits.</li>
	 *     <li>CVV: Must not be null and must have exactly 3 digits.</li>
	 *     <li>Receiver account number: Must not be null.</li>
	 *     <li>Recipient name: Must not be null or empty.</li>
	 *     <li>Amount and account number: Must not be null.</li>
	 * </ul>
	 * If any of these fields fail validation, a response with an appropriate message is returned
	 * with an HTTP status code of 406 (NOT_ACCEPTABLE). If all the validations pass, a success message
	 * is returned with an HTTP status code of 202 (ACCEPTED).
	 *
	 * @param payment The Payment object containing the payment details to be verified.
	 *                This object must have the following fields:
	 *                <ul>
	 *                    <li>cardNumber (String): The card number to be validated.</li>
	 *                    <li>cvv (String): The CVV number to be validated.</li>
	 *                    <li>receiverAccountNumber (Integer): The receiver's account number to be validated.</li>
	 *                    <li>recepientName (String): The recipient's name to be validated.</li>
	 *                    <li>amount (Integer): The payment amount to be validated.</li>
	 *                    <li>accountNumnber (Integer): The account number to be validated.</li>
	 *                </ul>
	 *
	 * @return A ResponseEntity containing the PaymentResponse object with an appropriate
	 *         status message and HTTP status code. The response can be:
	 *         <ul>
	 *             <li>406 (NOT_ACCEPTABLE) if validation fails with a message indicating what is wrong.</li>
	 *             <li>202 (ACCEPTED) if all the details are valid and the payment can proceed.</li>
	 *         </ul>
	 */
	private PaymentResponse verifyUserInputDetails(Payment payment) {
	    PaymentResponse paymentResponse = new PaymentResponse();
	    
	    
	    // Validate card number
	    if (isNullOrEmpty(payment.getCardNumber()) || payment.getCardNumber().length() != cardNumberLength) {
	        paymentResponse.setMessage("Please Provide valid card number");
	        paymentResponse.setPaymentStatus("failed");
	        return paymentResponse;
	    }

	    // Validate CVV
	    if (isNullOrEmpty(payment.getCvv()) || payment.getCvv().length() != cvvLength) {
	        paymentResponse.setMessage("Please Provide valid CVV number");
	        paymentResponse.setPaymentStatus("failed");
	        return paymentResponse;
	    }

	    // Validate receiver account number
	    if (payment.getReceiverAccountNumber() == null) {
	        paymentResponse.setMessage("Please Provide recipient account number");
	        paymentResponse.setPaymentStatus("failed");
	        return paymentResponse;
	    }

	    // Validate recipient name
	    if (isNullOrEmpty(payment.getRecepientName())) {
	        paymentResponse.setMessage("Please Provide recipient name");
	        paymentResponse.setPaymentStatus("failed");
	        return paymentResponse;
	    }

	    // Validate amount and account number (additional validation logic can be added if needed)
	    if (payment.getAmount() == null) {
	        paymentResponse.setMessage("Please Provide all the details");
	        paymentResponse.setPaymentStatus("failed");
	        return paymentResponse;
	    }
	    
	    // If all validations pass
	    return paymentResponse;
	}

	// Helper method to check if a string is null or empty
	private boolean isNullOrEmpty(String value) {
	    return value == null || value.trim().isEmpty();
	}

}
