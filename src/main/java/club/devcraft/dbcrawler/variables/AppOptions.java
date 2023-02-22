package club.devcraft.dbcrawler.variables;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AppOptions {
	private String DbHost = "localhost";
	private int DbPort = 3306;
	private String DbUser = "mysql";
	private String DbPassword = "mysql";
	private String DbName = "DLE";
	private String OutputPath = "";
	private boolean exportApi = false;
	private boolean createApiFile = false;
	private boolean createApiFileOnly = false;
	private String apiFileName = "DLE-API";
	private boolean autoSave = false;
	private boolean createSubfolders = false;
	private String apiFilePath = "";

	public String getDbHost() {
		return DbHost;
	}

	public void setDbHost(String dbHost) {
		DbHost = dbHost;
	}

	public int getDbPort() {
		return DbPort;
	}

	public void setDbPort(int dbPort) {
		DbPort = dbPort;
	}

	public String getDbUser() {
		return DbUser;
	}

	public void setDbUser(String dbUser) {
		DbUser = dbUser;
	}

	public String getDbPassword() {
		return DbPassword;
	}

	public void setDbPassword(String dbPassword) {
		DbPassword = dbPassword;
	}

	public String getDbName() {
		return DbName;
	}

	public void setDbName(String dbName) {
		DbName = dbName;
	}

	public String getOutputPath() {
		return OutputPath;
	}

	public void setOutputPath(String outputPath) {
		OutputPath = outputPath;
	}

	public boolean isExportApi() {
		return exportApi;
	}

	public void setExportApi(boolean exportApi) {
		this.exportApi = exportApi;
	}

	public boolean isCreateApiFile() {
		return createApiFile;
	}

	public void setCreateApiFile(boolean createApiFile) {
		this.createApiFile = createApiFile;
	}

	public boolean isCreateApiFileOnly() {
		return createApiFileOnly;
	}

	public void setCreateApiFileOnly(boolean createApiFileOnly) {
		this.createApiFileOnly = createApiFileOnly;
	}

	public String getApiFileName() {
		return apiFileName;
	}

	public void setApiFileName(String apiFileName) {
		this.apiFileName = apiFileName;
	}

	public boolean isAutoSave() {
		return autoSave;
	}

	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
	}

	public boolean isCreateSubfolders() {
		return createSubfolders;
	}

	public void setCreateSubfolders(boolean createSubfolders) {
		this.createSubfolders = createSubfolders;
	}

	public String getApiFilePath() {
		return apiFilePath;
	}

	public void setApiFilePath(String apiFilePath) {
		this.apiFilePath = apiFilePath;
	}
}
