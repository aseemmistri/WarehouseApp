package in.nareshit.raghu.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import in.nareshit.raghu.model.User;
import in.nareshit.raghu.service.IUserService;
import in.nareshit.raghu.util.EmailUtil;
import in.nareshit.raghu.util.UserUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService service;
	
	
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private UserUtil userUtil;
	
	@GetMapping("/showLogin")
	public String showLogin() {
		return "UserLogin";
	}
	
	
	//1. show user reg page
	@GetMapping("/register")
	public String showReg() {
		return "UserRegister";
	}
	
	//2. add user
	@PostMapping("/save")
	public String saveUser(
			@ModelAttribute User user,
			Model model) 
	{
		String pwd =user.getPwd();
		String otp = userUtil.genOtp();
		user.setOtp(otp);
		
		String id=service.saveUser(user);
		model.addAttribute("message","USER "+user.getName()+",("+id+") SAVED");
		if(id!=null) {
			String text =
					"Hellow User, you username :"+user.getName()
					+ ", Password : " + pwd
					+ ", Password : " + otp
					+ ", Roles :" + user.getRoles() 
					+ ", Status :" + (user.isActive()?"ACTIVE":"IN ACTIVE");
				
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					emailUtil.sendEmail(user.getEmail(),"WELCOME TO USER!!", text);
					
				}
			}).start();
		    
		}
		return "UserRegister";
	}
	
	//3. view all user
	@GetMapping("/all")
	public String viewAll(Model model) 
	{
		List<User> list = service.getAllUsers();
		model.addAttribute("list", list);
		return "UserData";
	}
	
	//activate user
	@GetMapping("/activate")
	public String activateUser(@RequestParam String uid) 
	{
		service.modifyStatus(uid,true);
		User user = service.getOneUser(uid);
		String text =
				"Hello User (" + user.getName()
				+ "), Your Modified Status : ACTIVE";
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				emailUtil.sendEmail(user.getEmail(),"Status Update Message", text);
				
			}
		}).start();
		return "redirect:all";
	}
	
	//inactivate user
	@GetMapping("/inactivate")
	public String inActivateUser(@RequestParam String uid) 
	{
		service.modifyStatus(uid,false);
		User user = service.getOneUser(uid);
		String text =
				"Hello User (" + user.getName()
				+ "), Your Modified Status :IN-ACTIVE";
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				emailUtil.sendEmail(user.getEmail(),"Status Update Message", text);
				
			}
		}).start();
		return "redirect:all";
	}
	
	@GetMapping("/setup")
	public String loginSetup(Principal p,HttpSession ses) 
	{
		Optional<User> opt = service.findByEmail(p.getName());
		ses.setAttribute("userOb", opt.get());
	
		return "redirect:../uom/all";
	}
	
	@GetMapping("/profile")
	public String showProfile(HttpSession ses,Model model) {
		User user = (User) ses.getAttribute("userOb");
		model.addAttribute("user", user);
		return "UserProfile";
	}
	
	@GetMapping("/modifyPwd")
	public String modifyPwd() 
	{
		return "UserPwd";
	}
	
	@PostMapping("/pwdUpdate")
	public String updatePwd(
			@RequestParam String pwd1,
			HttpSession ses) 
	{
		User user = (User) ses.getAttribute("userOb");
		service.updatePwd(user.getId(), pwd1);
		String text = "Hello "+user.getName()+". Your New Password is : "+pwd1;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				emailUtil.sendEmail(user.getEmail(),"Password Modified", text);
				
			}
		}).start();
		return "redirect:profile";
	}
	
	@GetMapping("/showForgotPwd")
	public String showForgotPwd() 
	{
		return "UserForgotPwd";
	}
	
	@PostMapping("/newPwdGen")
	public String newPwdGen(
			@RequestParam String uemail,
			Model model) 
	{
		String message = null;
		
		Optional<User> opt = service.findByEmail(uemail);
		if(opt.isPresent()) {
		  User user = opt.get();
		  //generate new pwd
		  String pwd =	userUtil.genPwd();
		  //update to database
		  service.updatePwd(user.getId(), pwd);
		  
		  String text = "Hello "+user.getName()
		  +". Your Password is Updated. Your New Password is : "+pwd;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					emailUtil.sendEmail(user.getEmail(),"Password Generated", text);
					
				}
			}).start();
			
			message= "New password is sent to your email!";
			
		}else {
			message= uemail+" not exist";
		}
		
		model.addAttribute("message", message);
		
		return "UserForgotPwd";
	}
	
	@GetMapping("/showactivateUserotp")
	public String showactivateUserotp() {
		return "UserOtpPage";
	}
	
	@PostMapping("/activatebyOtp")
	public String activatebyOtp(
			@RequestParam String uemail,
			@RequestParam String otp,
			@RequestParam String opr,
			Model model) 
	{
		String message = null;
		Optional<User> opt = service.findByEmail(uemail);
		if(opt.isEmpty()) {
			message = "Invalid Email Id Provided";
		}else {
			User user =opt.get();
			if(opr.equalsIgnoreCase("ACTIVATE")) {
				if(user.getOtp().equals(otp)) {
					service.modifyStatus(user.getId(), true);
					message = user.getName()+" You Are Activated Please Login Now!";
				}else {
					message = "Invalid OTP Provided";
				}
			}else if(opr.equalsIgnoreCase("RESEND")) {
				//generate otp
				String otpNew = userUtil.genOtp();
				//update inDB
				service.updateNewOtpById(user.getId(),otpNew);
				//also send email
				String text = "Hello "+user.getName()
				  +". Your New OTP is : "+otpNew;
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							emailUtil.sendEmail(user.getEmail(),"NEW OTP", text);
							
						}
					}).start();
				//otp send , plese check it
				message = "OTP SENT TO EMAIL PLEASE CHECK IT";
			}
			
		}
		model.addAttribute("message", message);
		return "UserOtpPage";
	}
	
	@GetMapping("/showPaymentPage")
	public String showPaymentPage() {
		return "UserPayment";
	}
	/*
	@PostMapping("/createOrder")
	public String createOrder(@RequestParam Double amount,
			Model model) throws RazorpayException
	{
	 RazorpayClient client = new RazorpayClient("rzp_test_0OEkArMp4kyUYn", "S4S8uv4O56YNcl89s2FdWxE4");
	 
	 JSONObject ob=new JSONObject();
	 ob.put("amount", amount*100);
	 ob.put("currency", "INR");
	 ob.put("receipt", "txn_235425");
	 
	 //creating order
	 Order order = client.orders.create(ob);
	   model.addAttribute("orderId", order);
	   return "GeneratePayment";
	}
	*/
	@GetMapping("/createOrder")
	public @ResponseBody String createOrder(@RequestParam double amount) throws RazorpayException 
	{
		RazorpayClient client = new RazorpayClient("rzp_test_0OEkArMp4kyUYn", "S4S8uv4O56YNcl89s2FdWxE4");
		JSONObject ob=new JSONObject();
		ob.put("amount", amount*100);
		ob.put("currency", "INR");
		ob.put("receipt", "txn_235425");
		
		//creating order
		Order order = client.orders.create(ob);
		String orderId = order.get("id");
		return orderId;
	}
	
	
}
