package ps.emperor.easy_water.entity;

/** This is just a simple class for holding data that is used to render our custom view */
public class CustomData {
    private int mBackgroundColor;
    private String mText;

    public int getmBackgroundColor() {
		return mBackgroundColor;
	}

	public void setmBackgroundColor(int mBackgroundColor) {
		this.mBackgroundColor = mBackgroundColor;
	}

	public String getmText() {
		return mText;
	}

	public void setmText(String mText) {
		this.mText = mText;
	}

	public CustomData() {
    }

    /**
     * @return the backgroundColor
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * @return the text
     */
    public String getText() {
        return mText;
    }
}
