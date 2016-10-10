package ps.emperor.easy_water.application;

/**
 * app 模块中item
 * @author purplesea
 *
 */
public class AppBeen {
	
	private int pic;
	private String content;
	public AppBeen() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppBeen(int pic, String content) {
		super();
		this.pic = pic;
		this.content = content;
	}
	public int getPic() {
		return pic;
	}
	public void setPic(int pic) {
		this.pic = pic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "AppBeen [pic=" + pic + ", content=" + content + "]";
	}
	
	

}
