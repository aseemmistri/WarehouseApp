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
@Table(name="part_tab")
public class Part {
	@Id
	@GeneratedValue(generator = "part")
	@GenericGenerator(name="part",strategy = "in.nareshit.raghu.PartIdGenerator")
	@Column(name="prt_id_col")
	private String id;
	
	@Column(name="prt_code_col")
	private String partCode;
	@Column(name="prt_len_col")
	private Double partLen;
	
	@Column(name="prt_wid_col")
	private Double partWid;
	@Column(name="prt_hght_col")
	private Double partHght;
	
	@Column(name="prt_cost_col")
	private Double partCost;
	@Column(name="prt_gst_col")
	private Integer gstSlob;
	@Column(name="prt_curr_col")
	private String partCurrency;
	
	//Association Mappings
	@ManyToOne
	@JoinColumn(name="uom_id_fk_col")
	private Uom uom; //HAS-A
	
	@ManyToOne
	@JoinColumn(name="om_id_fk_col")
	private OrderMethod orderMethod;//HAS-A
		
	
}
