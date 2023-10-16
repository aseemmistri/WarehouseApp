$(document).ready(function(){
		 //hide error section
		 $("#shipmentModeError").hide();
		 $("#shipmentCodeError").hide();
		 $("#enableShipmentError").hide();
		 $("#shipmentGradeError").hide();
		 $("#descriptionError").hide();
		 //define error variable
		 var shipmentModeError = false ;
		 var shipmentCodeError = false ;
		 var enableShipmentError = false ;
		 var shipmentGradeError = false ;
		 var descriptionError = false ;
		 //define validate function
		 function validate_shipmentMode(){
			var val = $("#shipmentMode").val();
			if(val==''){
				 $("#shipmentModeError").show();
				  $("#shipmentModeError").html("Select <b> Shipment Mode</b>");
				   $("#shipmentModeError").css("color","red");
				 shipmentModeError = false;
			} else{
				 $("#shipmentModeError").hide();
				 shipmentModeError = true;
			}
			return shipmentModeError;
		 }	
		 
		 function validate_shipmentCode(){
			 var val = $("#shipmentCode").val();
			 var exp = /^[A-Z\-\s]{4,12}$/;
			 if(val==''){
				 $("#shipmentCodeError").show();
				 $("#shipmentCodeError").html("Code <b>Can not be Empty</b>");
				 $("#shipmentCodeError").css("color","red");
				 shipmentCodeError = false;
			 }else if(!exp.test(val)){
				 $("#shipmentCodeError").show();
				 $("#shipmentCodeError").html("Code <b> must be A-Z 4-12 chars only</b>");
				 $("#shipmentCodeError").css("color","red");
				 shipmentCodeError = false;
			 }else{
				 //ajax call start
				 var id="";
					if($("#id").val() !==undefined){
						id = $("#id").val();
					}
					$.ajax({
					url : 'validate',
					data: {'code':val,'id':id}	,
					success:function(resText){
						if(resText==''){//no error
							$("#shipmentCodeError").hide();
					        uomModelError = true;
						}else{
							$("#shipmentCodeError").show();
					        $("#shipmentCodeError").html(resText);
					        $("#shipmentCodeError").css("color","red");
					        shipmentCodeError = false;
						}
					}
					});
					//ajax call end
			 }
			 return shipmentCodeError;
		 }
		 function validate_enableShipment(){
			 var len = $("[name='enableShipment']:checked").length;
			 if(len==0){
				 $("#enableShipmentError").show();
				 $("#enableShipmentError").html("Choose <b> Enable Shipment </b>");
				 $("#enableShipmentError").css("color","red");
				 enableShipmentError = false ;
			 }else{
				$("#enableShipmentError").hide(); 
				enableShipmentError = true;
			 }
			 return enableShipmentError;
		 }
		 function validate_shipmentGrade(){
			 var len = $("[name='shipmentGrade']:checked").length;
			 if(len==0){
				 $("#shipmentGradeError").show();
				 $("#shipmentGradeError").html("Select <b>Shipment Grade</b>");
				 $("#shipmentGradeError").css("color","red");
				 shipmentGradeError = false;
			 }else{
				 $("#shipmentGradeError").hide();
				 shipmentGradeError = true;
			 }
			 return shipmentGradeError;
		 }
		 function validate_description(){
			 var val = $("#description").val();
			 var exp = /^[a-zA-Z0-9\-\_\.\,\s]{10,200}$/;
			 if(val==''){
				 $("#descriptionError").show();
				 $("#descriptionError").html("Description <b>Can not be Empty</b>");
				 $("#descriptionError").css("color","red");
				 descriptionError = false;
			 }else if(!exp.test(val)){
				 $("#descriptionError").show();
				 $("#descriptionError").html("Description <b>must be 10-200 chars only!");
				 $("#descriptionError").css("color","red");
				 descriptionError = false;
				 
			 }else{
				 $("#descriptionError").hide();
				 descriptionError = ture;
			 }
			 return descriptionError;
		 }
		 //link with action
		 $("#shipmentMode").change(function(){
			validate_shipmentMode(); 
		 });
		 $("#shipmentCode").keyup(function(){
			 $("#shipmentCode").val($("#shipmentCode").val().toUpperCase());
			 validate_shipmentCode();
		 });
		 $("[name='enableShipment']").click(function(){
			 validate_enableShipment();
		 });
		 $("[name='shipmentGrade']").click(function(){
			 validate_shipmentGrade();
		 });
		 $("#description").keyup(function(){
			validate_description(); 
		 });
		 //onsubmit
		 $("#stRegForm").submit(function(){
			validate_shipmentCode();
			validate_shipmentMode();
			validate_enableShipment();
			validate_shipmentGrade();
			validate_description();
			if(enableShipmentError && shipmentCodeError 
			&& shipmentModeError && shipmentGradeError && descriptionError)
			return true;
			else return false;
			 
		 });
		});