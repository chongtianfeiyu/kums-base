package com.kurui.kums.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

import com.kurui.kums.base.Inform;

public class BaseAction extends DispatchAction {

	public BaseAction() {
	}

	public ActionForward execute(ActionMapping actionmapping,
			ActionForm actionform, HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) throws Exception {
		return super.execute(actionmapping, actionform, httpservletrequest,
				httpservletresponse);
	}

	public ActionForward forwardInformPage(Inform inf, ActionMapping mapping,
			HttpServletRequest request) {
		if (inf == null) {
			inf = new Inform();
		}
		if (inf.getMessage() == null) {
			inf.setMessage("未定义提示信息");
		}
		inf.setBack(true);
		request.setAttribute("inf", inf);
		String forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward forwardInformPageNoBack(Inform inf,
			ActionMapping mapping, HttpServletRequest request) {
		if (inf == null) {
			inf = new Inform();
		}
		if (inf.getMessage() == null) {
			inf.setMessage("未定义提示信息");
		}

		request.setAttribute("inf", inf);
		String forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public Inform forwardErrorInfo(String forwardPage, Inform inf) {
		String message = MessageStore.getContent(forwardPage);
		if (message != null) {
			inf.setMessage(message);
		} else {
			return null;
		}
		return inf;
	}
}