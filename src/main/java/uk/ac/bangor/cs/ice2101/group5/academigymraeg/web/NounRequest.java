package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class NounRequest 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_post")
	@SequenceGenerator(
		    name = "seq_post",
		    initialValue = 46,
		    allocationSize = 1
		)
	@Column(name = "primary_key")
	private int primaryKey;
	@Column(nullable = false, length = 255)
	private String englishNoun;
	@Column(nullable = false, length = 255)
	private String welshNoun;
	@Column(nullable = false)
	private String nounGenders;
	@Column(length = 255)
	private String nounDescription;
	
	
	public int getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getEnglishNoun() {
		return englishNoun;
	}
	public void setEnglishNoun(String englishNoun) {
		this.englishNoun = englishNoun;
	}
	public String getWelshNoun() {
		return welshNoun;
	}
	public void setWelshNoun(String welshNoun) {
		this.welshNoun = welshNoun;
	}
	
	public String getNounGenders() {
		return nounGenders;
	}
	public void setNounGenders(String nounGenders) {
		this.nounGenders = nounGenders;
	}
	
	

	public String getNounDescription() {
		return nounDescription;
	}
	public void setNounDescription(String nounDescription) {
		this.nounDescription = nounDescription;
	}



	public enum NounGenders {
		MALE, FEMALE
	}

}
