$(document).ready(function () {
			//1. hide error section
			$("#uomTypeError").hide();
			$("#uomModelError").hide();
			$("#descriptionError").hide();

			//2. error variable
			var uomTypeError = false;
			var uomModelError = false;
			var descriptionError = false;

			//3. validate fuction
			function validate_uomType() {
				var val = $("#uomType").val();
				if (val == '') {
					$("#uomTypeError").show();
					$("#uomTypeError").html("SELECT <b>UOM TYPE</b>");
					$("#uomTypeError").css("color", "red");
					uomTypeError = false;
				} else {
					$("#uomTypeError").hide();
					uomTypeError = true;
				}
				return uomTypeError;
			}

			function validate_uomModel() {
				var val = $("#uomModel").val();
				var exp = /^[A-Z\-\s]{4,25}$/;
				if (val == '') {
					$("#uomModelError").show();
					$("#uomModelError").html("Model <b>can not be empty</b>");
					$("#uomModelError").css("color", "red");
					uomModelError = false;
				} else if (!exp.test(val)) {
					$("#uomModelError").show();
					$("#uomModelError").html("Model <b>is not valid</b>");
					$("#uomModelError").css("color", "red");
					uomModelError = false;
				} else {

					//ajax call start
					var id="";
					if($("#id").val() !==undefined){
						id = $("#id").val();
					}
					$.ajax({
						url: 'validate',
						data: {'model': val,'id':id},
						success: function (resText) {
							if (resText == '') {//no error
								$("#uomModelError").hide();
								uomModelError = true;
							} else {
								$("#uomModelError").show();
								$("#uomModelError").html(resText);
								$("#uomModelError").css("color", "red");
								uomModelError = false;
							}
						}
					});
					//ajax call end
				}
				return uomModelError;
			}

			function validate_descripiton() {
				var val = $("#description").val();
				var exp = /^[A-Za-z0-9\-\,\.\s]{10,100}/;
				if (val == '') {
					$("#descriptionError").show();
					$("#descriptionError").html("Description <b>can not be empty</b>");
					$("#descriptionError").css("color", "red");
					descriptionError = false;
				} else if (!exp.test(val)) {
					$("#descriptionError").show();
					$("#descriptionError").html("Description <b>must be 10-100 chars only</b>");
					$("#descriptionError").css("color", "red");
					descriptionError = false;
				} else {
					$("#descriptionError").hide();
					descriptionError = true;
				}
				return descriptionError;
			}

			//4. link input with action
			$("#uomType").change(function () {
				validate_uomType();
			});

			$("#uomModel").keyup(function () {
				validate_uomModel();
			});

			$("#description").keyup(function () {
				validate_descripiton();
			});
			//5. submit button
			$("#uomRegForm").submit(function () {
				//call all validate functions
				validate_uomType();
				validate_uomModel();
				validate_descripiton();
				//check all error variables
				//if all true then submit form return true
				if (uomModelError && uomTypeError && descriptionError)
					return true;
				//else false
				 else return false;
			});
		});