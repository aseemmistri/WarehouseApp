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
@Table(name="purchase_order_tab")
public class PurchaseOrder {
	
	@Id
	@Column(name="po_id_col")
	@GeneratedValue(generator = "purchaseorder")
	@GenericGenerator(name="purchaseorder",strategy = "in.nareshit.raghu.PurchaseOrderIdGenerator")
	private String id;
	
	@Column(name="po_code_col")
	private String orderCode;
	
	@Column(name="po_ref_num_col")
	private String refNumber;
	
	@Column(name="po_qty_chk_col")
	private String qltyCheck;
	
	@Column(name="po_status_col")
	private String status;
	
	@Column(name="po_desc_col")
	private String description;
	
	//Integration
	@ManyToOne
	@JoinColumn(name="ship_id_fk_col")
	private ShipmentType shipmentType; //HAS-A
	
	@ManyToOne
	@JoinColumn(name="wh_user_ven_id_fk_col")
	private WhUserType vendor;

}
