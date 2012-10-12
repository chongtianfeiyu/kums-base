package com.kurui.kums.base.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.kurui.kums.base.Constant;
import com.kurui.kums.base.exception.AppException;

public class XmlUtil_jdom {

	protected static Log log = LogFactory.getLog(XmlUtil_jdom.class);

	// 用于快速查询的cache
	private Map lookupCache = new HashMap();

	public static void main(String[] args) {
		String xmlSource = "E:\\Noname2.xml";
		Document doc = initXmlSource(xmlSource);

		Element root = doc.getRootElement();

		try {
			List<Element> list = getAllElements(doc, "//tree/item/item");
			System.out.println("list:" + list.size());
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getAttributeValue("id"));
				Element tempRootItem = list.get(i);
				long tempRootAgentId = Constant.toLong(tempRootItem
						.getAttributeValue("id"));

				// System.out.println("tempRootAgentId:"+tempRootAgentId);
				if (tempRootAgentId == 654) {
					Element item = new Element("item");
					// item.setAttribute("text", agent.getName());
					item.setAttribute("id", "9999");
					item.setAttribute("open", "1");

					tempRootItem.addContent(item);
				}
			}
			saveToXmlFile(doc, "E:\\NonameTemp.xml");
		} catch (AppException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 返回所有满足XPath条件的节点元素集合。
	 */
	public static List<Element> getAllElements(Document doc, String xpath)
			throws AppException {
		List<Element> elements = null;
		try {
			List selectNodes = XPath.selectNodes(doc, xpath);
			elements = selectNodes;
		} catch (JDOMException e) {
			e.printStackTrace();
			System.out.println("获取节点元素集合发生异常！" + e.getMessage());
		}
		return elements;
	}

	/**
	 * 返回满足XPath条件的第一个节点元素。
	 */
	public Element getSingleElement(Document doc, String xpath)
			throws AppException {
		// 所有查询单个元素的方法都调用了这个方法，所以只在这里使用cache
		if (lookupCache.containsKey(xpath)) {
			return (Element) lookupCache.get(xpath);
		} else {
			Element element = null;
			try {
				element = (Element) XPath.selectSingleNode(doc, xpath);
			} catch (JDOMException e) {
				if (log.isErrorEnabled()) {
					log.error("获取节点元素发生异常！" + e.getMessage());
				}
				throw new AppException(e.getMessage());
			}
			lookupCache.put(xpath, element);
			return element;
		}
	}

	/**
	 * 返回满足XPath条件的第一个节点元素的内容，字符串格式
	 * 
	 * @return String。如果指定的元素不存在，返回null。
	 */
	public String getSingleElementText(Document doc, String xpath)
			throws AppException {
		Element element = getSingleElement(doc, xpath);
		if (element == null) {
			return null;
		} else {
			return element.getTextTrim();
		}
	}

	/**
	 * 返回满足XPath条件的第一个节点元素的指定属性。
	 */
	public Attribute getElementAttribute(Document doc, String xpath,
			String attrName) throws AppException {
		if (getSingleElement(doc, xpath) == null) {
			return null;
		} else {
			return getSingleElement(doc, xpath).getAttribute(attrName);
		}
	}

	/**
	 * 返回满足XPath条件的第一个节点元素的指定属性的内容值。
	 */
	public String getElementAttributeValue(Document doc, String xpath,
			String attrName) throws AppException {
		Attribute attr = getElementAttribute(doc, xpath, attrName);
		if (attr == null) {
			return null;
		} else {
			return attr.getValue().trim();
		}
	}

	/**
	 * 在指定的元素下面增加一个元素。
	 * 
	 * @param xpath
	 *            指定的元素
	 * @param elemName
	 *            增加元素的名称
	 * @param elemText
	 *            增加元素的内容
	 * @throws AppException
	 */
	public void addElement(Document doc, String xpath, String elemName,
			String elemText) throws AppException {
		Element parent = getSingleElement(doc, xpath);
		Element newElement = new Element(elemName);
		newElement.addContent(elemText);
		parent.addContent(newElement);
	}

	/**
	 * 使指定位置的元素从他的上级脱离。并且返回这个元素。如果没有上级，不作任何删除 的操作。
	 * 
	 * @param xpath
	 * @return 被修改的元素
	 * @throws AppException
	 */
	public Content removeElement(Document doc, String xpath)
			throws AppException {
		lookupCache.remove(xpath);
		Element element = getSingleElement(doc, xpath);
		if (element.isRootElement()) {
			return element;
		} else {
			return element.detach();
		}
	}

	/**
	 * 改变指定元素的文本内容。
	 * 
	 * @param xpath
	 *            指定元素
	 * @param elemText
	 *            需要设置的文本
	 * @throws AppException
	 *             如果指定的元素不存在
	 */
	public void setElementText(Document doc, String xpath, String elemText)
			throws AppException {
		Element element = getSingleElement(doc, xpath);
		if (element == null) {
			throw new AppException("指定的元素不存在！");
		} else {
			element.setText(elemText);
		}
	}

	/**
	 * 在指定路径的元素上增加一个属性。如果同名属性已经存在，重新设置这个属性的值。
	 */
	public void setAttribute(Document doc, String xpath, String attrName,
			String attrValue) throws AppException {
		Element element = getSingleElement(doc, xpath);
		try {
			element.setAttribute(attrName, attrValue);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("设置节点元素的属性发生异常！" + e.getMessage());
			}
			throw new AppException(e.getMessage());
		}
	}

	/**
	 * 删除指定元素的指定属性。
	 */
	public boolean removeAttribute(Document doc, String xpath, String attrName)
			throws AppException {
		Element element = getSingleElement(doc, xpath);
		if (element == null) {
			return false;
		} else {
			return element.removeAttribute(attrName);
		}
	}

	public static String getDocumentString(Document doc) {
		String result = "";
		Format format = getFormat();
		XMLOutputter XMLOut = new XMLOutputter(format);
		result = XMLOut.outputString(doc);
		return result;
	}

	public static Format getFormat() {
		org.jdom.output.Format format = org.jdom.output.Format
				.getPrettyFormat();
		format.setEncoding("UTF-8");
		format.setIndent("");
		format.setLineSeparator("");
		format.setTextMode(org.jdom.output.Format.TextMode.TRIM_FULL_WHITE);
		return format;
	}

	// 初始化 xml document 的 initXmlSource 方法
	public static Document initXmlSource(String xmlSource) {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(new File(xmlSource));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return doc;
	}

	// 保存 Document 对象到 XML 文件的 saveToXmlFile 方法
	public static void saveToXmlFile(Document doc, String xmlFilePath) {
		XMLOutputter outputter = new XMLOutputter();
		try {
			outputter.output(doc, new FileOutputStream(xmlFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * 把XML文档的内容输出到一个给定的流对象中。默认编码GB2312
	 * 
	 * @param stream
	 *            给定的输出流对象
	 * @throws AppException
	 */
	public void writeToStream(Document doc, OutputStream stream)
			throws AppException {
		writeToStream(doc, stream, "UTF-8");
	}

	/**
	 * 把XML文档的内容输出到一个给定的流对象中。
	 * 
	 * @param stream
	 *            给定的输出流对象
	 * @param encoding
	 *            指定字符编码。
	 * @throws AppException
	 */
	public void writeToStream(Document doc, OutputStream stream, String encoding)
			throws AppException {
		// 表现形式的常量
		final String INDENT = "  ";
		final boolean NEW_LINES = true;

		try {
//			XMLOutputter out = new XMLOutputter(INDENT, NEW_LINES, encoding);
			XMLOutputter out = new XMLOutputter();
			
			out.output(doc, stream);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("输出文件到指定的流对象发生异常！" + e.getMessage());
			}
			throw new AppException(e.getMessage());
		}
	}

}
