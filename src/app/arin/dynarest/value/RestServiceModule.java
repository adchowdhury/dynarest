package app.arin.dynarest.value;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_rservice_module")
public class RestServiceModule implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "module_id", nullable = false)
	private Integer moduleId;
	
	@Column(name = "module_name")
	private String moduleName;
	
	@Column(name = "module_description")
	private String moduleDescription;
	
	@Column(name = "module_version")
	private Float moduleVersion;
	
	@Column(name = "parent_module")
	private String parentModule;
	
	@Column(name = "module_url")
	private String moduleUrl;
	
	/*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "module_id", referencedColumnName = "module_id")
	private List<RestServiceMethod> restServiceMethods;*/
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="module_id")
	@MapKey(name="methodUrl")
	private Map<String,RestServiceMethod> restMethodMap;
	
	@Column(name = "api_class")
	private String apiClass;

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public Float getModuleVersion() {
		return moduleVersion;
	}

	public void setModuleVersion(Float moduleVersion) {
		this.moduleVersion = moduleVersion;
	}

	public String getParentModule() {
		return parentModule;
	}

	public void setParentModule(String parentModule) {
		this.parentModule = parentModule;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public String getApiClass() {
		return apiClass;
	}

	public void setApiClass(String apiClass) {
		this.apiClass = apiClass;
	}

	public Map<String, RestServiceMethod> getRestMethodMap() {
		return restMethodMap;
	}

	public void setRestMethodMap(Map<String, RestServiceMethod> restMethodMap) {
		this.restMethodMap = restMethodMap;
	}

	/*public List<RestServiceMethod> getRestServiceMethods() {
		return restServiceMethods;
	}

	public void setRestServiceMethods(List<RestServiceMethod> restServiceMethods) {
		this.restServiceMethods = restServiceMethods;
	}*/
	
	
}
