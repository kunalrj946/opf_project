package com.alethe.opf.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Kunal Kumar
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "user_mst")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_id; // | integer | | not null | nextval('user_mst_user_id_seq'::regclass)

	@Column(name = "login_id", length = 200, unique = true)
	@JsonProperty("loginid")
	private String loginid; // | character varying(200) | | not null |

	@Column(name = "user_fname", length = 200, nullable = false)
	@JsonProperty("user_fname")
	private String user_fname; // | character varying(200) | | not null |

	@Column(name = "user_lname", length = 200, nullable = false)
	@JsonProperty("user_lname")
	private String user_lname; // | character varying(200) | | not null |

	@Column(name = "user_email", length = 200, nullable = false)
	@JsonProperty("user_email")
	private String user_email; // | character varying(200) | | not null |

	@Column(name = "user_contact", length = 200, nullable = false)
	@JsonProperty("user_contact")
	private String user_contact; // | character varying(200) | | not null |

	@Column(name = "user_password", length = 200, nullable = false)
	@JsonProperty("user_password")
	private String user_password; // | character varying(200) | | not null |

	@Column(name = "user_type", length = 3, nullable = false)
	@JsonProperty("user_type")
	private String user_type; // | character varying(3) | | not null |

	@Column(name = "user_role", length = 3, nullable = false)
	@JsonProperty("user_role")
	private String user_role;

	@Column(name = "user_parent", nullable = true)
	@JsonProperty("user_parent")
	private Integer user_parent; // | integer | | |

	@Column(name = "created_on", nullable = true, columnDefinition = "timestamp without time zone default now()")
	@JsonProperty("created_on")
	private Timestamp created_on; // | timestamp without time zone | | | now()

	@Column(name = "created_by", nullable = false, columnDefinition = "integer default 1")
	@JsonProperty("created_by")
	private Integer created_by; // | integer | | not null | 1

	@Column(name = "modified_on", nullable = true)
	@JsonProperty("modified_on")
	private Timestamp modified_on; // | timestamp without time zone | | |

	@Column(name = "modified_by", nullable = true)
	@JsonProperty("modified_by")
	private Integer modified_by; // | integer | | |

	@Column(name = "is_deleted", columnDefinition = "integer default 0", nullable = false)
	@JsonProperty("is_deleted")
	private Integer is_deleted; // | integer | | not null | 0

	private Integer is_active;

}
