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
@Table(name="po_dtl_tab")
public class PurchaseDtl {
	
	@Id	
	@GeneratedValue(generator = "purchaseorder")
	@GenericGenerator(name="purchaseorder",strategy = "in.nareshit.raghu.PurchaseDtlIdGenerator")
	@Column(name="po_dtl_id_col")
	private String id;
	
	@Column(name="po_dtl_qty_col")
	private Integer qty;
	
	//HAS-A /*....1
	//PurchaseDtl ---<>Part
	@ManyToOne
	@JoinColumn(name="pat_id_fk_col")
	private Part part; //HAS-A
	
	
	//HAS-A /*....1
		//PurchaseDtl ---<>Part
		@ManyToOne
		@JoinColumn(name="order_id_fk_col")
		private PurchaseOrder order; //HAS-A
	
	
}
