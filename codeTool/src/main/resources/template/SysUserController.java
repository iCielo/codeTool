/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	2016 下午3:56:51</p>
 */
package com.lezic.app.sys.user.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lezic.app.sys.user.entity.${entityName};
import com.lezic.app.sys.user.service.${entityName}Service;
import com.lezic.core.lang.ParamMap;
import com.lezic.core.orm.Page;
import com.lezic.core.util.UtilData;
import com.lezic.core.web.action.BaseController;
import com.lezic.core.web.constant.Status;

/**
 * @author cielo
 *
 */
@Controller
@RequestMapping("/sys/${entityName}.do")
public class ${entityName}Controller extends BaseController {

	private Logger logger = LogManager.getLogger();

	@Autowired
	private ${entityName}Service ${entityName}Service;

	/**
	 * 列表页面
	 * 
	 * @param model
	 * @return
	 * @author cielo
	 */
	@RequestMapping(params = "method=listPage", method = RequestMethod.GET)
	public String listPage(Model model) {
		return "/sys/user/listPage";
	}

	/**
	 * 新增页面
	 */
	@RequestMapping(params = "method=addPage", method = RequestMethod.GET)
	public String addPage(Model model) {
		model.addAttribute("test", "test");
		return "/sys/user/addPage";
	}

	/**
	 * 修改页面
	 */
	@RequestMapping(params = "method=updPage", method = RequestMethod.GET)
	public String updPage(Model model) {
		String id = this.getParam("id");
		if (UtilData.isNotNull(id)) {
			model.addAttribute("entity", ${entityName}Service.getH(id));
		}
		return "/sys/user/updPage";
	}

	/**
	 * 列表页面
	 * 
	 * @param model
	 * @return
	 * @author cielo
	 * @throws IOException
	 */
	@RequestMapping(params = "method=loadData", method = RequestMethod.GET)
	public void loadData() throws IOException {
		Page<${entityName}> page = new Page<${entityName}>();
		page.setOffset(UtilData.integerOfString(this.getParam("offset"), 0));
		page.setPageSize(UtilData.integerOfString(this.getParam("limit"), 10));
		String hql = "from ${entityName}";
		ParamMap params = new ParamMap();
		${entityName}Service.pageH(page, hql, params);
		this.outBootstrapTable(page);
	}

	/**
	 * 新增
	 * 
	 * @throws IOException
	 */
	@RequestMapping(params = "method=addEntity")
	public void addEntity(@ModelAttribute ${entityName} entity) throws IOException {
		if (entity != null) {
			entity.setId(UUID.randomUUID().toString());
		}
		${entityName}Service.saveH(entity);
		this.outMsg(Status.SUCCESS, null);
	}

	/**
	 * 修改
	 * 
	 * @throws IOException
	 */
	@RequestMapping(params = "method=updEntity")
	public void updEntity(@ModelAttribute ${entityName} entity) throws IOException {
		if (entity != null) {

			// ${entityName} item = ${entityName}Service.getH(entity.getId());
			// item.setAccount(entity.getAccount());
			// item.setName(entity.getName());
			// item.setSex(entity.getSex());
			// item

			${entityName}Service.updH(entity);
		}
		this.outMsg(Status.SUCCESS, null);
	}

	/**
	 * 删除
	 * 
	 * @throws IOException
	 */
	@RequestMapping(params = "method=delEntity")
	public void delEntity() throws IOException {
		String[] ids = UtilData.split(this.getParam("ids"), ",");
		${entityName}Service.batchDelH(${entityName}.class, ids);
		this.outMsg(Status.SUCCESS, null);
	}

	/**
	 * 判断名称是否重复
	 * 
	 * @throws IOException
	 * @author cielo
	 */
	@RequestMapping(params = "method=isRepeat")
	public void isRepeat() throws IOException {
		String id = this.getParam("id");
		String account = this.getParam("account");
		String hql = "from ${entityName} where  (id != ? or ? is null) and account = ?";
		boolean isRepeat = ${entityName}Service.isRepeat(hql, id, id, account);

		Map<String, String> ret = new HashMap<String, String>();
		if (isRepeat) {
			ret.put("error", "对不起，已存在该账号！");
		} else {
			ret.put("ok", "该账号可用！");
		}
		this.write(ret);
	}

}
