package th.ac.chandra.eduqa.web.tag.function;
import javax.servlet.jsp.tagext.TagSupport;

public class TagUtils extends TagSupport{
	/**
	 * @author Wirun Pengsri
	 */
	private static final long serialVersionUID = -6008169370673340407L;

	public static String nl2br(String string) {
        return (string != null) ? string.replace("\n", "<br/>") : null;
    }
}
