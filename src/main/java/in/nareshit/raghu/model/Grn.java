package in.nareshit.raghu.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="grn_tab")
public class Grn {

	@Id
	@Column(name="grn_id_col")
	@GeneratedValue(generator = "grn")
	@GenericGenerator(name="grn",strategy = "in.nareshit.raghu.GrnIdGenerator")
	private String id;
	
	@Column(name="grn_code_col")
	private String code;
	
	@Column(name="grn_type_col")
	private String type;
	
	@Column(name="grn_desc_col")
	private String description;
	
	//1...1=*...1 + unique
	@ManyToOne
	@JoinColumn(name="po_order_id_fk",unique=true)
	private PurchaseOrder po; //HAS_A
}
