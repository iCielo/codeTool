<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/form.jsp"%>
<title>${title} - 修改</title>
<script type="text/javascript">
	$(function() {
		$("#form").validator({
			fields : {
				#foreach( $item in $fields )
					#if( ${item.rules} != '' )
						${item.name} : "${item.rules}",
					#end
				#end				
			}
		});
		$('#form').on('valid.form', function(e) {
			Common.ajax({
				url : "${CP}/${module}/${entityName}.do?method=updEntity",
				data : $(this).serialize(),
				success : function(data) {
					Common.closeDialog();
				}
			})
		});
	});
</script>
</head>
<body>
	<section class="panel">
		<div class="panel-body">
			<form class="form-horizontal tasi-form" method="post" id="form">
				#set($entity="{entity")
				#foreach( $item in $fields )
					<div class="form-group">
						<label class="col-sm-3 col-xs-3 control-label">${item.label} #if( ${item.nullable} == false )<span class="form-must">*</span> #end</label>
						<div class="col-sm-6 col-xs-6">
							<input class="form-control" placeholder="${item.placeholder}" type="text" id="${item.name}" name="${item.name}"  value="<c:out value="${mark}${entity}.$item.name}"/>">
						</div>
					</div> 
				#end
				<div class="form-group">
					<div class="col-sm-offset-3 col-xs-offset-3">
						<button class="btn btn-primary" type="submit"><i class="glyphicon glyphicon-ok"></i>&nbsp;确定</button>
						<button class="btn btn-primary" type="button" onclick="Common.closeDialog();"><i class="glyphicon glyphicon-remove"></i>&nbsp;关闭</button>
					</div>
				</div>
			</form>
		</div>
	</section>
</body>
</html>