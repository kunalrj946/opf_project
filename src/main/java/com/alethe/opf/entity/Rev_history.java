package com.alethe.opf.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rev_history {

	private String so_no; // | character varying(20) | | not null |
	private Integer so_rev; // | integer | | not null | 1
	private Integer so_id; // | integer | | not null | nextval('sale_order_so_id_seq'::regclass)
	private String so_type = "SO";
	private Integer tam; // | character varying(2) | | not null | 'SO'::character varying
	private Integer company_id; // | integer | | not null |
	
	private String company_address;
	private String company_gstn;
	
	private Timestamp so_date; // | timestamp without time zone | | | now()
	private Timestamp revision_add_on;// | timestamp without time zone | | |
	private Integer revision_add_by;// | integer |
	private String so_reference; // | character varying(200) | | not null |
	private String po_no; // | character varying(300) | | |
	private Timestamp po_date; // | timestamp without time zone | | |
	private String so_category_id; // | character varying(3) | | not null |
	private String business_unit_id; // | character varying(3) | | not null |
	private Integer am; // | integer | | not null |
	private Integer customer_id; // | integer | | not null |
	private String customer_name; // | character varying(500) | | not null |
	private String customer_billing_name; // EXTRA ADDING
	private String customer_billing_address; // | character varying(1500) | | not null |
	private String customer_dispatch_address; // | character varying(1500) | | not null |
	private String customer_gstn; // | character varying(20) | | not null |
	private String customer_segement; // | character varying(3) | | not null |
	private String business_nature_id; // | character varying(3) | | not null |
	private String primary_contact_name; // | character varying(100) | | not null |
	private String primary_contact_phone; // | character varying(100) | | not null |
	private String primary_contact_email; // | character varying(100) | | not null |
	private String secondary_contact_name; // | character varying(100) | | |
	private String secondary_contact_phone; // | character varying(100) | | |
	private String secondary_contact_email; // | character varying(100) | | |
	private Double total_purchase_amount; // | double precision | | |
	private Double total_sale_amount; // | double precision | | |
	private Double margin_amount; // | double precision | | |
	private Double margin_per; // | double precision | | |
	private Long po_attach1_id; // | bigint | | |
	private Long po_attach2_id; // | bigint | | |
	private Long so_attach1_id; // | bigint | | |
	private Long so_attach2_id; // | bigint | | |
	private String delivery_instruction; // | character varying(100) | | |
	private Double other_expenses; // | double precision | | |

	private String payment_term; // | text | | |

	private String am_remark; // | text | | |

	private Timestamp am_approved_on; // | timestamp without time zone | | |

	private String tam_remark; // | text | | |

	private Timestamp tam_approved_on; // | timestamp without time zone | | |

	private String zm_remark; // | text | | |

	private Timestamp zm_approved_on; // | timestamp without time zone | | |

	private String cfo_remark; // | text | | |

	private Timestamp cfo_approved_on; // | timestamp without time zone | | |
	private Integer so_status; // | integer | | |
	private Timestamp created_on; // | timestamp without time zone | | | now()
	private Integer   created_by; // | integer | | | 0
	private Timestamp modified_on; // | timestamp without time zone | | |
	private Integer modified_by; // | integer | | |
	private Integer is_deleted; // | integer | | |
	private Integer rev_flag;

}
