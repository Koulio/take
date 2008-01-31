package nz.ac.massey.take.takeep.editor;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class DesignManager {

	private RGB defaultColor = new RGB(0, 0, 0);
	private int defaultStyle = SWT.NORMAL;

	private HashMap<String, RGB> colors = new HashMap<String, RGB>();
	private HashMap<String, Integer> styles = new HashMap<String, Integer>();

	public void addStyle(String group, int style) {
		this.styles.put(group, style);
	}

	public void addColor(String group, RGB color) {
		this.colors.put(group, color);
	}

	public Color getColor(String group) {
		RGB rgb = this.colors.get(group);
		if (rgb == null) {
			return new Color(Display.getCurrent(), this.defaultColor);
		}
		return new Color(Display.getCurrent(), rgb);
	}

	public int getStyle(String group) {
		Integer style = this.styles.get(group);
		if (style == null) {
			return this.defaultStyle;
		}
		return style;
	}
}
