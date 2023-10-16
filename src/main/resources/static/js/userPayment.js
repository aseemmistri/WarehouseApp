const paymentStart = () => {
console.log("payment stated....");
let amount = $("#payment_field").val();
console.log(amount);
if(amount == "" || amount == null){
    alert("amount is requert !");
}


$.ajax({
    url: 'createOrder',
    data: {amount:amount},
    success:function(response){
      //invoke where success
      console.log(response);
      if(response.status == 'created'){
        //open payment form
        let options = {
            "key": "rzp_test_0OEkArMp4kyUYn", // Enter the Key ID generated from the Dashboard
            "amount": response.amount, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
            "currency": "INR",
            "name": "ASEEM MISTRI", //your business name
            "description": "Bill Payment",
            "image": "https://www.canva.com/p/templates/EAFUjQ7id7A-black-gold-luxury-initial-jewelry-shop-logo/",
            "order_id":response.id, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
            "handler": function (response){
                console.log(response.razorpay_payment_id);
                console.log(response.razorpay_order_id);
                console.log(response.razorpay_signature);
                console.log("Payment Successfull...!");
                alert("Congrates ! Payment Successfull...!");

            },
            "prefill": { //We recommend using the prefill parameter to auto-fill customer's contact information, especially their phone number
                "name": "", //your customer's name
                "email": "", 
                "contact": ""  //Provide the customer's phone number for better conversion rates 
            },
            "notes": {
                "address": "ASEEM WHATEHOUSE SERVICES"
            },
            "theme": {
                "color": "#3399cc"
            }
        };
        let rzp1 = new Razorpay(options);
        rzp1.on('payment.failed', function (response){
                console.log(response.error.code);
                console.log(response.error.description);
                console.log(response.error.source);
                console.log(response.error.step);
                console.log(response.error.reason);
                console.log(response.error.metadata.order_id);
                console.log(response.error.metadata.payment_id);
                alert("OOps Payment faild");
        });
        rzp1.open();
      }
    },
    error:function(error){
        //invoke where error
        console.log(error);
        alert("something went worng");
    }
});


};