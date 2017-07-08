package cn.mldn.util.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public abstract class AbstractAction extends ActionSupport {
	// 定义分页有关的处理操作，分页的参数是简写参数
	private Integer cp = 1;
	private Integer ls = 5;
	private String col;
	private String kw;

	public void setCp(Integer cp) {
		this.cp = cp;
	}

	public void setLs(Integer ls) {
		this.ls = ls;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public String getKw() {
		if (this.kw == null) {
			return "";
		}
		return kw;
	}

	public Integer getCp() {
		return cp;
	}

	public Integer getLs() {
		return ls;
	}

	public String getCol() {
		if (this.col == null || "".equals(this.col)) { // 没有设置查询列
			return this.getDefaultColumn();
		}
		return this.col;
	}

	/**
	 * 用来取得默认的分页显示列
	 * 
	 * @return
	 */
	public abstract String getDefaultColumn();

	/**
	 * 设置有可能进行模糊查询字段
	 * 
	 * @return
	 */
	public abstract String getColumnData();

	/**
	 * 处理分页传递到组件的操作属性
	 * 
	 * @param allRecorders
	 *            当前所在的页面
	 * @param urlKey
	 *            要进行分页处理的URL，通过Pages.properties读取
	 * @param paramName
	 *            参数名称
	 * @param paramValue
	 *            参数内容
	 */
	public void handleSplit(Object allRecorders, String urlKey,
			String paramName, String paramValue) {
		this.getRequest().setAttribute("currentPage", this.getCp());
		this.getRequest().setAttribute("lineSize", this.getLs());
		this.getRequest().setAttribute("column", this.getCol());
		this.getRequest().setAttribute("keyWord", this.getKw());
		this.getRequest().setAttribute("url", this.getUrl(urlKey));
		this.getRequest().setAttribute("allRecorders", allRecorders);
		this.getRequest().setAttribute("columnData", this.getColumnData());
		this.getRequest().setAttribute("paramName", paramName);
		this.getRequest().setAttribute("paramValue", paramValue);
	}

	/**
	 * 负责读取要通过forward.jsp页面进行跳转的路径，读取Pages.properties
	 * 
	 * @param key
	 *            路径的key的信息
	 * @return 返回Pages.properties文件中指定key的内容
	 */
	public String getUrl(String key) {
		return super.getText(key);
	}

	/**
	 * 负责信息读取的操作
	 * 
	 * @param key
	 *            Messages.properties里面定义的key信息
	 * @return 返回Messages.properties文件中的指定key对应的内容
	 */
	public String getMsg(String key) {
		return super.getText(key, new String[] { this.getTypeName() });
	}

	/**
	 * 每一个Action的业务操作方法执行完毕之后需要传递给forward.jsp页面的信息 所有的信息都需要通过资源文件进行读取
	 * 
	 * @param msgKey
	 *            Mesasges.properties里面的key的名字
	 * @param urlKey
	 *            Pages.properties里面的key的名字
	 */
	public void setMsgAndUrl(String msgKey, String urlKey) {
		this.getRequest().setAttribute("msg", this.getMsg(msgKey));
		this.getRequest().setAttribute("url", this.getUrl(urlKey));
	}

	/**
	 * 取得操作的类型信息，主要用于更新的提示数据，例如：如果是公告，则返回公告字符串，如果是文档则返回文档字符串
	 * 
	 * @return
	 */
	public abstract String getTypeName();

	/**
	 * 取得HttpServletRequest对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpServletResponse对象
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 取得HttpSession接口对象
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return this.getRequest().getSession();
	}

	/**
	 * 取得ServletContext接口对象
	 * 
	 * @return
	 */
	public ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * 信息的输出操作
	 * 
	 * @param val
	 *            要输出的内容
	 */
	public void print(Object val) { // 输出数据的方法
		try {
			this.getResponse().getWriter().print(val);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printJSON(Object val) { // 输出数据的方法
		try {
			JSONObject obj = new JSONObject();
			obj.put("data", val);
			this.getResponse().getWriter().print(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据文件的类型创建文件的名字
	 * 
	 * @param contentType
	 * @return
	 */
	public String createSingleFileName(String contentType) {
		String fileExt = null;
		if ("image/bmp".equalsIgnoreCase(contentType)) {
			fileExt = "bmp";
		} else if ("image/jpg".equalsIgnoreCase(contentType)) {
			fileExt = "jpg";
		} else if ("image/jpeg".equalsIgnoreCase(contentType)) {
			fileExt = "jpg";
		} else if ("image/gif".equalsIgnoreCase(contentType)) {
			fileExt = "gif";
		} else if ("image/png".equalsIgnoreCase(contentType)) {
			fileExt = "png";
		} else if ("application/msword".equalsIgnoreCase(contentType)) {
			fileExt = "doc";
		} else if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document"
				.equalsIgnoreCase(contentType)) {
			fileExt = "docx";
		} else if ("text/plain".equalsIgnoreCase(contentType)) {
			fileExt = "txt";
		} else if ("application/vnd.ms-excel".equalsIgnoreCase(contentType)) {
			fileExt = "xls";
		} else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
				.equalsIgnoreCase(contentType)) {
			fileExt = "xlsx";
		} else if ("application/x-rar-compressed".equalsIgnoreCase(contentType)) {
			fileExt = "rar";
		} else if ("application/zip".equalsIgnoreCase(contentType)) {
			fileExt = "zip";
		}
		return UUID.randomUUID().toString() + "." + fileExt;
	}

	/**
	 * 实现文件的保存操作
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param file
	 *            要保存的文件信息
	 * @return 保存成功返回true，否则返回false
	 */
	public boolean saveSingleFile(String filePath, File file) {
		File saveFile = new File(filePath);
		if (!saveFile.getParentFile().exists()) { // 父路径不存在
			saveFile.getParentFile().mkdirs(); // 创建目录
		}
		boolean flag = false;
		OutputStream output = null;
		InputStream input = null;
		try {
			output = new FileOutputStream(saveFile);
			input = new FileInputStream(file);
			byte data[] = new byte[1024];
			int len = 0;
			while ((len = input.read(data)) != -1) {
				output.write(data, 0, len);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public boolean deleteFile(String filePath) {
		File dfile = new File(filePath);
		if (dfile.exists()) {
			return dfile.delete();
		}
		return false;
	}

	public void deleteFileBatch(String filePath, Set<String> fileNames) {
		Iterator<String> iter = fileNames.iterator();
		while (iter.hasNext()) {
			this.deleteFile(filePath + iter.next());
		}
	}

	public String formatDate(Date date) {
		if (date == null) {
			return "" ;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
}
