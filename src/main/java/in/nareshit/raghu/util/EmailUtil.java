package in.nareshit.raghu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
	
	@Autowired
	private JavaMailSender sender;
	
	public boolean sendEmail(
			String to,
			String subject,
			String text,
			String[] cc,
			String[] bcc,
			MultipartFile file)
	{
		boolean flag = false;
		
		try {
			//1. Create Empty Message (MIME = Multipurpose Internet Mail Extension )
			MimeMessage message=sender.createMimeMessage();
			//2. Use Helper class object
			MimeMessageHelper helper=new MimeMessageHelper(message,file!=null);
			//set message details
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			
			if(cc!=null)
			     helper.setCc(cc);
			if(bcc!=null)
			     helper.setCc(bcc);
			
			if(file!=null) {
				helper.addAttachment(file.getOriginalFilename(), file);
			}
			
			//3. send mail
			sender.send(message);
			flag =true;
		}catch(Exception e) {
			e.printStackTrace();
			flag =false;
		}
		
		return flag;
	}
	
	public boolean sendEmail(
			String to,
			String subject,
			String text)
	{
		return sendEmail(to,subject,text,null,null,null);
	}

}
