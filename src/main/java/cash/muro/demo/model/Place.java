package cash.muro.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Place implements Serializable{
	@Id
	private String code;
	
	@Column(nullable = false)
	private String Name;	
	
	@ManyToOne
	private Place Container;
	
}
