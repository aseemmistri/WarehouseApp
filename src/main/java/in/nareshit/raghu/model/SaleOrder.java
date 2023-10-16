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
@Table(name="sale_order_tab")
public class SaleOrder {

	@Id
	@GeneratedValue(generator = "saleorder")
	@GenericGenerator(name="saleorder",strategy = "in.nareshit.raghu.SaleOrderIdGenerator")
	@Column(name="so_id_col")
	private String id;
	
	@Column(name="so_code_col")
	private String orderCode;
	
	@Column(name="so_ref_num_col")
	private String refNumber;
	
	@Column(name="so_stock_mode_col")
	private String stockMode;
	
	@Column(name="so_stock_source_col")
	private String stockSource;
	
	@Column(name="so_status_col")
	private String status;
	
	@Column(name="so_desc_col")
	private String description;
	
	//Integration
			@ManyToOne
			@JoinColumn(name="ship_id_fk_col")
			private ShipmentType shipmentType; //HAS-A
			
			@ManyToOne
			@JoinColumn(name="wh_user_ven_id_fk_col")
			private WhUserType customer;
}
