package club.devcraft.dbcrawler.variables;

public class PossibleData {
	private String name;
	private String type;
	private boolean required = false;
	private boolean post = false;
	private int length = 0;

	public PossibleData(String name, String type, boolean required, boolean post, int length) {
		setName(name);
		setName(type);
		setRequired(required);
		setPost(post);
		setLength(length);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isPost() {
		return post;
	}

	public void setPost(boolean post) {
		this.post = post;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
    public String toString() {

		return "array(\n" +
				String.format("\t'name'\t=>\t'%s',\n", getName()) +
				String.format("\t'type'\t=>\t'%s',\n", getType()) +
				String.format("\t'required'\t=>\t%s,\n", isRequired()) +
				String.format("\t'post'\t=>\t%s,\n", isPost()) +
				String.format("\t'length'\t=>\t%d\n", getLength()) +
				"\t),\n";
    }
}
