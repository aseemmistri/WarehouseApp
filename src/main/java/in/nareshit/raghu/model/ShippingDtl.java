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
@Table(name="shipping_dtl_tab")
public class ShippingDtl {
	
	@Id
	@GeneratedValue(generator = "shippingdtl")
	@GenericGenerator(name="shippingdtl",strategy = "in.nareshit.raghu.ShippingDtlIdGenerator")
	@Column(name="ship_id_col")
	private String id;
	
	@Column(name="ship_code_col")
	private String itemCode;
	@Column(name="ship_cost_col")
	private Double baseCost;
	@Column(name="ship_qty_col")
	private Integer qty;
	@Column(name="ship_gst_col")
	private Integer gstSlob;
	@Column(name="ship_val_col")
	private Double itemValue;
	
	@Column(name="ship_status_col")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="ship_id_fk_col")
	private Shipping shipping;//HAS-A

}
