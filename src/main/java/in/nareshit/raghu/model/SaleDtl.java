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
@Table(name="so_dtl_tab")
public class SaleDtl {

	@Id	
	@GeneratedValue(generator = "saleorder")
	@GenericGenerator(name="saleorder",strategy = "in.nareshit.raghu.SaleDtlIdGenerator")
	@Column(name="so_dtl_id_col")
	private String id;
	
	@Column(name="so_dtl_qty_col")
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
			private SaleOrder order; //HAS-A
}
