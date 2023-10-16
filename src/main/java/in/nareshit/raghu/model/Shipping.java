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
@Table(name="shipping_tab")
public class Shipping {
	
	@Id
	@GeneratedValue(generator = "shipping")
	@GenericGenerator(name="shipping", strategy = "in.nareshit.raghu.ShippingIdGenerator")
	@Column(name="ship_id_col")
	private String id;
	
	@Column(name="ship_code_col")
	private String code;
	@Column(name="ship_ref_num_col")
	private String shipRefNum;
	@Column(name="cour_ref_num_col")
	private String courRefNum;
	@Column(name="ship_con_dtl_col")
	private String conDetails;
	@Column(name="ship_desc_col")
	private String description;
	@Column(name="bill_to_addr_col")
	private String billToAddr;
	@Column(name="ship_to_addr_col")
	private String shipToAddr;
	
	//1...1=*....1+unique
	@ManyToOne
	@JoinColumn(name="so_order_id_fk",unique = true)
	private SaleOrder so;//HAS-A
	

}
