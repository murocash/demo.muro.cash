package cash.muro.demo.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Peak {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int elevation;
	@ManyToOne
	private Place county;
	@Column(nullable = false, precision = 9, scale = 7)
	private BigDecimal latitude;
	@Column(nullable = false, precision = 9, scale = 7)
	private BigDecimal longitude;
	@Column(nullable = false)
	private int distance;
	
	public String getCompleteCountyName() {
		return county.getName() + ", " + county.getContainer().getName();
	}
	
	public int getFeet() {
		return (int)Math.abs(elevation * 3.2808);
	}
}
