package cn.mldn.util.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

@SuppressWarnings("serial")
@ParentPackage("root")
@Namespace("/")
@Action("CheckCode")
public class CheckCodeAction extends AbstractAction {
	private String code;

	public void setCode(String code) {
		this.code = code;
	}

	public void check() {
		String rand = (String) super.getSession().getAttribute("rand");
		super.print(rand.equalsIgnoreCase(this.code));
	}

	@Override
	public String getTypeName() {
		return null;
	}

	@Override
	public String getDefaultColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnData() {
		// TODO Auto-generated method stub
		return null;
	}
}
