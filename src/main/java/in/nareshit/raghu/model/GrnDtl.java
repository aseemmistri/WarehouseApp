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
//c# create model class
@Data
@Entity
@Table(name="grn_dtl_tab")
public class GrnDtl {
	
	@Id
	@GeneratedValue(generator = "grndtl")
	@GenericGenerator(name="grndtl",strategy = "in.nareshit.raghu.GrnDtlIdGenerator")
	@Column(name="grn_id_col")
	private String id;
	
	@Column(name="grn_code_col")
	private String itemCode;
	@Column(name="grn_cost_col")
	private Double baseCost;
	@Column(name="grn_qty_col")
	private Integer qty;
	@Column(name="grn_val_col")
	private Double itemVal;
	
	@Column(name="grn_status_col")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="grn_id_fk_col")
	private Grn grn;//HAS-A

}
