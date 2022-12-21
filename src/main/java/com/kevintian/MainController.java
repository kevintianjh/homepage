package com.kevintian;

import java.io.BufferedInputStream; 
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping; 

class Form {
	int howMany;
	String[] labels;
	boolean lowerCaseOption;
	boolean upperCaseOption;
	boolean numberOption;
	boolean specialCharOption;
	String filePw;
}

@Controller
public class MainController {

	@Autowired
	ServletContext servletContext;

	@RequestMapping("/")
	public String m1() {
		return "redirect:static/index.html";
	}

	@RequestMapping("/pwapp")
	public String m2(HttpServletRequest req, HttpServletResponse rsp) throws Exception {

		String howManyStr = req.getParameter("form_howmany");

		if (howManyStr == null) {
			req.setAttribute("howMany", 1);
		} else if (Pattern.matches("^[1-9]$", howManyStr)) {
			int howMany = Integer.parseInt(req.getParameter("form_howmany"));
			req.setAttribute("howMany", howMany);
		} else {
			req.setAttribute("howMany", 1);
		}

		if (req.getParameter("submit") != null) {
			ArrayList<String> errors = new ArrayList<>();
			Form form = new Form();
			validateInputs(req, errors, form);

			if (errors.size() == 0) { 

				String path = this.servletContext.getRealPath("/") + "tmp\\" + req.getSession().getId();
				ZipFileGenerator zipGenerator = new ZipFileGenerator(path);
				File zipFile = zipGenerator.generate(form.labels, form.lowerCaseOption, form.upperCaseOption, form.numberOption, form.specialCharOption, form.filePw);

				rsp.setHeader("Content-Disposition", "attachment; filename=\"" + zipFile.getName() + "\"");
				rsp.setContentLength((int) zipFile.length());
				rsp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				rsp.setHeader("Pragma", "no-cache");
				rsp.setHeader("Expires", "0");

				InputStream inputStream = new BufferedInputStream(new FileInputStream(zipFile));
				FileCopyUtils.copy(inputStream, rsp.getOutputStream());

				rsp.getOutputStream().flush();
				inputStream.close();
				
				zipGenerator.cleanUp();

				return null;
			} else {
				req.setAttribute("errors", errors);
			}
		}

		return "pwapp";
	}

	public void validateInputs(HttpServletRequest req, ArrayList<String> errors, Form form) {

		String howManyStr = req.getParameter("form_howmany");
		int howMany = 0;

		if (howManyStr == null) {
			errors.add("Don't meddle with the form!");
			return;
		} else if (Pattern.matches("^[1-9]$", howManyStr)) {
			howMany = Integer.parseInt(req.getParameter("form_howmany"));
			form.howMany = howMany;
		} else {
			errors.add("Don't meddle with the form!");
			return;
		}

		String[] labels = req.getParameterValues("form_pwlabel");
		form.labels = labels;

		if (labels == null) {
			errors.add("Don't meddle with the form!");
			return;
		} else if (labels.length != howMany) {
			errors.add("Don't meddle with the form!");
			return;
		} else {
			for (String label : labels) {
				if (!label.equals("")) {
					label = label.trim();

					if (label.length() == 0 || label.length() > 20) {
						errors.add("Label cannot be just space or more than 20 chars");
						break;
					}
				}
			}
		}

		form.lowerCaseOption = req.getParameter("form_lowercase") != null;
		form.upperCaseOption = req.getParameter("form_uppercase") != null;
		form.numberOption = req.getParameter("form_number") != null;
		form.specialCharOption = req.getParameter("form_specialchar") != null;

		if (form.lowerCaseOption == false && form.upperCaseOption == false && form.numberOption == false
				&& form.specialCharOption == false) {
			errors.add("Please select at least one checkbox option");
		}

		String filePw = req.getParameter("form_filepw");
		form.filePw = filePw;

		if (filePw == null) {
			errors.add("Don't meddle with the form!");
			return;
		} else {
			if (filePw.length() != 0) {
				if (filePw.length() < 6 || filePw.length() > 20) {
					errors.add("Password must be at least 6 chars and max 20 chars");
				}
			}
		}
	}
}