/**
 *
 */
package com.servicelive.domain.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.AbstractLookupDomain;
import com.servicelive.domain.LookupDomain;

/**
 * @author hoza
 *
 */
@LookupDomain
@Entity
@Table (name = "lu_languages_spoken")
public class LookupLanguage extends AbstractLookupDomain {
		/**
	 * 
	 */
	private static final long serialVersionUID = -1603179811063148882L;
		@Id
		private Integer id;
		
		@Column (name = "type")
		private String type;
		
		
		@Column ( name = "descr")
		private String description;
		/**
		 * @return the id
		 */
		@Override
		public Integer getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		/**
		 * @return the description
		 */
		@Override
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

}
