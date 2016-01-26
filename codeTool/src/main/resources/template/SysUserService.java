/**
 * <p>Author		:	cielo</p>
 * <p>Date 			: 	${now}</p>
 */
package ${packageName}.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lezic.app.sys.user.entity.SysUser;
import com.lezic.core.orm.service.impl.BaseServiceImpl;

/**
 * @author cielo
 *
 */
@Service
@Transactional
public class ${entityName}Service extends BaseServiceImpl<${entityName}> {
}
