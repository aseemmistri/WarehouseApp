package in.nareshit.raghu.model;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="order_method_tab")
public class OrderMethod {
	@Id
	@GeneratedValue(generator = "ordermethod")
	//@SequenceGenerator(name="ordermethod",sequenceName = "ordermethod_seq")
	@GenericGenerator(name="ordermethod",strategy = "in.nareshit.raghu.OrderMethodIdGenerator")
	@Column(name="ord_id_col")
	private String id;
	
	@Column(name="ord_mode_col")
	private String orderMode;
	@Column(name="ord_code_col")
	private String orderCode;
	@Column(name="ord_type_col")
	private String orderType;
	
	@ElementCollection
	@CollectionTable(name="ord_acpt_tab",
	joinColumns = @JoinColumn(name="ord_id_col") )
	@Column(name="ord_acpt_col")
	private List<String> orderAcpt;
	
	@Column(name="ord_description_col")
	private String description;
	
	

}
