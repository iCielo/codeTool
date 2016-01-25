/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	2016 下午2:41:21</p>
 */
package com.lezic.core.util;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author cielo
 *
 */
public class UtilVelocity {
	
	/**
	 * 根据模板生成代码字符串
	 * 
	 * @param template
	 * @param params
	 * @return
	 * @author cielo
	 */
	public static String process(String template, Map<String, Object> params) {
		try {
			Velocity.init();
			/* 初始化运行时引擎， 默认初始化 */
			VelocityContext context = new VelocityContext();
			Set<String> keys = params.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				context.put(key, params.get(key));
			}
			StringWriter result = new StringWriter();
			Velocity.evaluate(context, result, "codeTool", template);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
