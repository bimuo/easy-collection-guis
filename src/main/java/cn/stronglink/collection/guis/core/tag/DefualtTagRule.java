package cn.stronglink.collection.guis.core.tag;

/**
 * 判断标签非全0的规
 * @author 25969
 *
 */
public class DefualtTagRule implements ITagRule {

	@Override
	public boolean isValid(String code) {
	//	String pattern = "(!0+$)";
	//	return Pattern.matches(pattern, code.substring(code.length()-8));
		if("00000000".equals(code.substring(code.length()-8))) {
			return false;
		}
		return true;
	}

}
