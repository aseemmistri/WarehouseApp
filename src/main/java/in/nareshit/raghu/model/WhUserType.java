package in.nareshit.raghu.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="wh_user_type_tab")
public class WhUserType {
	
	@Id
	@GeneratedValue(generator = "whusertype")
	//@SequenceGenerator(name="whusertype",sequenceName = "whusertype_seq")
	@GenericGenerator(name="whusertype",strategy = "in.nareshit.raghu.WhUserTypeIdGenerator")
	@Column(name="wh_id_col")
	private String id;
	
	@Column(name="wh_type_col")
	private String userType;
	@Column(name="wh_code_col")
	private String userCode;
	@Column(name="wh_for_col")
	private String userFor;
	@Column(name="wh_email_col")
	private String userEmail;
	@Column(name="wh_contact_col")
	private String userContact;
	@Column(name="wh_id_type_col")
	private String userIdType;
	@Column(name="wh_if_other_col")
	private String ifOther;
	@Column(name="wh_id_number_col")
	private String idNumber;


}
