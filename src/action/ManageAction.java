package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Kemu;
import model.Shijuan;
import model.Shijuanitem;
import model.Shiti;
import model.User;

import org.apache.struts2.ServletActionContext;

import util.Pager;

import com.opensymphony.xwork2.ActionSupport;

import dao.KemuDao;
import dao.ShijuanDao;
import dao.ShijuanitemDao;
import dao.ShitiDao;
import dao.UserDao;

public class ManageAction extends ActionSupport {

	private static final long serialVersionUID = -4304509122548259589L;

	private UserDao userDao;

	private String url = "./";

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	
	
	
//登入请求
	public String login() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String  role = request.getParameter("role");
		User user = userDao.selectBean(" where username = '" + username
				+ "' and password= '" + password + "' and role= "+role +" and userlock=0 ");
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			this.setUrl("index.jsp");
			return "redirect";
		} else {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");
			response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('用户名或者密码错误');window.location.href='login.jsp';</script>");
		}
		return null;
	}
//用户退出
	public String loginout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		this.setUrl("login.jsp");
		return SUCCESS;
	}
//跳转到修改密码页面
	public String changepwd() {
		this.setUrl("user/user.jsp");
		return SUCCESS;
	}
//修改密码操作
	public void changepwd2() throws IOException {
HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		User bean = userDao.selectBean(" where username= '"+u.getUsername()+"' and password= '"+password1+"' and userlock=0");
		if(bean!=null){
			bean.setPassword(password2);
			userDao.updateBean(bean);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('修改成功');</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('用户名或者密码错误');</script>");
		}
	}
	
	//老师用户列表
	public String userlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String truename = request.getParameter("truename");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (username != null && !"".equals(username)) {

			sb.append("username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}

		if (truename != null && !"".equals(truename)) {
			sb.append("truename like '%" + truename + "%'");
			sb.append(" and ");
			request.setAttribute("truename", truename);
		}
		
		
		sb.append("  role=0 and userlock=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist.action", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist.action");
		request.setAttribute("url2", "method!user");
		request.setAttribute("title", "老师用户管理");
		this.setUrl("user/userlist.jsp");
		return SUCCESS;

	}
//跳转到添加老师用户页面
	public String useradd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!useradd2.action");
		request.setAttribute("title", "老师用户添加");
		this.setUrl("user/useradd.jsp");
		return SUCCESS;
	}
//添加老师用户操作
	public void useradd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String truename = request.getParameter("truename");
		String lianxifangshi = request.getParameter("lianxifangshi");

		User bean = userDao.selectBean(" where username='"+username+"' and userlock=0 ");
		if(bean==null){
			bean = new User();
			bean.setCreatetime(new Date());
			bean.setPassword("111111");
			bean.setRole(0);
			bean.setTruename(truename);
			bean.setUsername(username);
			bean.setLianxifangshi(lianxifangshi);
			userDao.insertBean(bean);
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!userlist.action';</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，该用户名已经存在');window.location.href='method!userlist.action';</script>");
		}
	}
//跳转到更新老师用户页面
	public String userupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!userupdate2.action");
		request.setAttribute("title", "老师用户修改");
		this.setUrl("user/userupdate.jsp");
		return SUCCESS;
	}
//更新老师用户操作
	public void userupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String truename = request.getParameter("truename");
		String lianxifangshi = request.getParameter("lianxifangshi");

		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setLianxifangshi(lianxifangshi);

		bean.setTruename(truename);
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist.action';</script>");
	}
//删除老师用户操作
	public void userdelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setUserlock(1);
		userDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!userlist.action';</script>");
	}
	
	//跳转到查看老师用户页面
	public String userupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User bean = userDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "老师用户查看");
		this.setUrl("user/userupdate3.jsp");
		return SUCCESS;
	}
	
	private KemuDao kemuDao;

	public KemuDao getKemuDao() {
		return kemuDao;
	}

	public void setKemuDao(KemuDao kemuDao) {
		this.kemuDao = kemuDao;
	}
	
	//科目列表
	public String kemulist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (name != null && !"".equals(name)) {

			sb.append("name like '%" + name + "%'");
			sb.append(" and ");
			request.setAttribute("name", name);
		}

		
		
		
		sb.append("  kemulock=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = kemuDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", kemuDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!kemulist.action", "共有" + total + "条记录"));
		request.setAttribute("url", "method!kemulist.action");
		request.setAttribute("url2", "method!kemu");
		request.setAttribute("title", "科目管理");
		this.setUrl("kemu/kemulist.jsp");
		return SUCCESS;

	}
//跳转到添加科目页面
	public String kemuadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("url", "method!kemuadd2.action");
		request.setAttribute("title", "科目添加");
		this.setUrl("kemu/kemuadd.jsp");
		return SUCCESS;
	}
//添加科目操作
	public void kemuadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");


		Kemu bean = kemuDao.selectBean(" where name='"+name+"' and kemulock=0 ");
		if(bean==null){
			bean = new Kemu();
			bean.setName(name);
			kemuDao.insertBean(bean);
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作成功');window.location.href='method!kemulist.action';</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，该科目名已经存在');window.location.href='method!kemulist.action';</script>");
		}
	}

//删除科目操作
	public void kemudelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Kemu bean = kemuDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setKemulock(1);
		kemuDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!kemulist.action';</script>");
	}
	
	//跳转到查看科目页面
	public String kemuupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Kemu bean = kemuDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "科目查看");
		this.setUrl("kemu/kemuupdate3.jsp");
		return SUCCESS;
	}
	
	
	private ShitiDao shitiDao;

	public ShitiDao getShitiDao() {
		return shitiDao;
	}

	public void setShitiDao(ShitiDao shitiDao) {
		this.shitiDao = shitiDao;
	}
	
	
	//选择题列表
	public String shitilist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String wenti = request.getParameter("wenti");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		if (wenti != null && !"".equals(wenti)) {
			sb.append("wenti like '%" + wenti + "%'");
			sb.append(" and ");
			request.setAttribute("wenti", wenti);
		}
		
		
		sb.append("  leixing='选择题' and shitilock=0  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shitiDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shitiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shitilist.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shitilist.action");
		request.setAttribute("url2", "method!shiti");
		request.setAttribute("title", "选择题管理");
		this.setUrl("shiti/shitilist.jsp");
		return SUCCESS;

	}
//跳转到添加选择题页面
	public String shitiadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		request.setAttribute("url", "method!shitiadd2.action");
		request.setAttribute("title", "选择题添加");
		this.setUrl("shiti/shitiadd.jsp");
		return SUCCESS;
	}
//添加选择题操作
	public void shitiadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");
		
		
		if(kemu==null||"".equals(kemu)){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，科目不能为空');window.location.href='method!shitilist.action';</script>");
			return;
		}

		Shiti bean = new Shiti();
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setCreatetime(new Date());
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setLeixing("选择题");
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);
		shitiDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist.action';</script>");
		
	}
//跳转到更新选择题页面
	public String shitiupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!shitiupdate2.action");
		request.setAttribute("title", "选择题修改");
		this.setUrl("shiti/shitiupdate.jsp");
		return SUCCESS;
	}
//更新选择题操作
	public void shitiupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");

		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");

		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);
	
		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist.action';</script>");
	}
//删除选择题操作
	public void shitidelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setShitilock(1);
		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist.action';</script>");
	}
	
	//跳转到查看选择题页面
	public String shitiupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "选择题查看");
		this.setUrl("shiti/shitiupdate3.jsp");
		return SUCCESS;
	}
	
	
	//判断题列表
	public String shitilist10() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String wenti = request.getParameter("wenti");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		if (wenti != null && !"".equals(wenti)) {
			sb.append("wenti like '%" + wenti + "%'");
			sb.append(" and ");
			request.setAttribute("wenti", wenti);
		}
		
		
		sb.append("  shitilock=0 and leixing='判断题' order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shitiDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shitiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shitilist10.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shitilist10.action");
		request.setAttribute("url2", "method!shiti");
		request.setAttribute("title", "判断题管理");
		this.setUrl("shiti/shitilist10.jsp");
		return SUCCESS;

	}
//跳转到添加判断题页面
	public String shitiadd10() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		request.setAttribute("url", "method!shitiadd102.action");
		request.setAttribute("title", "判断题添加");
		this.setUrl("shiti/shitiadd10.jsp");
		return SUCCESS;
	}
//添加判断题操作
	public void shitiadd102() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");
		
		if(kemu==null||"".equals(kemu)){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，科目不能为空');window.location.href='method!shitilist10.action';</script>");
			return;
		}

		Shiti bean = new Shiti();
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setCreatetime(new Date());
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setLeixing("判断题");
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);
		shitiDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist10.action';</script>");
		
	}
//跳转到更新判断题页面
	public String shitiupdate10() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!shitiupdate102.action");
		request.setAttribute("title", "判断题修改");
		this.setUrl("shiti/shitiupdate10.jsp");
		return SUCCESS;
	}
//更新判断题操作
	public void shitiupdate102() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");

		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");

		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);

		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist10.action';</script>");
	}
//删除判断题操作
	public void shitidelete10() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setShitilock(1);
		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist10.action';</script>");
	}
	
	//跳转到查看判断题页面
	public String shitiupdate103() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "判断题查看");
		this.setUrl("shiti/shitiupdate103.jsp");
		return SUCCESS;
	}
	
	
	//填空题列表
	public String shitilist100() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String wenti = request.getParameter("wenti");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		if (wenti != null && !"".equals(wenti)) {
			sb.append("wenti like '%" + wenti + "%'");
			sb.append(" and ");
			request.setAttribute("wenti", wenti);
		}
		
		
		sb.append("  shitilock=0 and  leixing='填空题' order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shitiDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shitiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shitilist100.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shitilist100.action");
		request.setAttribute("url2", "method!shiti");
		request.setAttribute("title", "填空题管理");
		this.setUrl("shiti/shitilist100.jsp");
		return SUCCESS;

	}
//跳转到添加填空题页面
	public String shitiadd100() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		request.setAttribute("url", "method!shitiadd1002.action");
		request.setAttribute("title", "填空题添加");
		this.setUrl("shiti/shitiadd100.jsp");
		return SUCCESS;
	}
//添加填空题操作
	public void shitiadd1002() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");
		
		if(kemu==null||"".equals(kemu)){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，科目不能为空');window.location.href='method!shitilist100.action';</script>");
			return;
		}

		Shiti bean = new Shiti();
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setCreatetime(new Date());
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setLeixing("填空题");
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);
		shitiDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist100.action';</script>");
		
	}
//跳转到更新填空题页面
	public String shitiupdate100() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!shitiupdate1002.action");
		request.setAttribute("title", "填空题修改");
		this.setUrl("shiti/shitiupdate100.jsp");
		return SUCCESS;
	}
//更新填空题操作
	public void shitiupdate1002() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");

		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");

		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);

		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist100.action';</script>");
	}
//删除填空题操作
	public void shitidelete100() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setShitilock(1);
		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist100.action';</script>");
	}
	
	//跳转到查看填空题页面
	public String shitiupdate1003() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "填空题查看");
		this.setUrl("shiti/shitiupdate1003.jsp");
		return SUCCESS;
	}
	
	
	//设计题列表
	public String shitilist1000() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String wenti = request.getParameter("wenti");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		if (wenti != null && !"".equals(wenti)) {
			sb.append("wenti like '%" + wenti + "%'");
			sb.append(" and ");
			request.setAttribute("wenti", wenti);
		}
		
		
		sb.append("  shitilock=0 and leixing='设计题' order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shitiDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shitiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shitilist1000.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shitilist1000.action");
		request.setAttribute("url2", "method!shiti");
		request.setAttribute("title", "设计题管理");
		this.setUrl("shiti/shitilist1000.jsp");
		return SUCCESS;

	}
//跳转到添加设计题页面
	public String shitiadd1000() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		request.setAttribute("url", "method!shitiadd10002.action");
		request.setAttribute("title", "设计题添加");
		this.setUrl("shiti/shitiadd1000.jsp");
		return SUCCESS;
	}
//添加设计题操作
	public void shitiadd10002() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");
		
		if(kemu==null||"".equals(kemu)){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，科目不能为空');window.location.href='method!shitilist1000.action';</script>");
			return;
		}

		Shiti bean = new Shiti();
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setCreatetime(new Date());
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setLeixing("设计题");
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);
		shitiDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist1000.action';</script>");
		
	}
//跳转到更新设计题页面
	public String shitiupdate1000() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!shitiupdate10002.action");
		request.setAttribute("title", "设计题修改");
		this.setUrl("shiti/shitiupdate1000.jsp");
		return SUCCESS;
	}
//更新设计题操作
	public void shitiupdate10002() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");

		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");

		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);

		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist1000.action';</script>");
	}
//删除设计题操作
	public void shitidelete1000() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setShitilock(1);
		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist1000.action';</script>");
	}
	
	//跳转到查看设计题页面
	public String shitiupdate10003() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "设计题查看");
		this.setUrl("shiti/shitiupdate10003.jsp");
		return SUCCESS;
	}
	
	
	
	
	//计算题列表
	public String shitilist10000() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String wenti = request.getParameter("wenti");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		if (wenti != null && !"".equals(wenti)) {
			sb.append("wenti like '%" + wenti + "%'");
			sb.append(" and ");
			request.setAttribute("wenti", wenti);
		}
		
		
		sb.append("  shitilock=0 and leixing='计算题' order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shitiDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shitiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shitilist10000.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shitilist10000.action");
		request.setAttribute("url2", "method!shiti");
		request.setAttribute("title", "计算题管理");
		this.setUrl("shiti/shitilist10000.jsp");
		return SUCCESS;

	}
//跳转到添加计算题页面
	public String shitiadd10000() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		request.setAttribute("url", "method!shitiadd100002.action");
		request.setAttribute("title", "计算题添加");
		this.setUrl("shiti/shitiadd10000.jsp");
		return SUCCESS;
	}
//添加计算题操作
	public void shitiadd100002() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");
		
		if(kemu==null||"".equals(kemu)){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response
					.getWriter()
					.print(
							"<script language=javascript>alert('操作失败，科目不能为空');window.location.href='method!shitilist10000.action';</script>");
			return;
		}

		Shiti bean = new Shiti();
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setCreatetime(new Date());
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setLeixing("计算题");
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);
		shitiDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist10000.action';</script>");
		
	}
//跳转到更新计算题页面
	public String shitiupdate10000() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!shitiupdate100002.action");
		request.setAttribute("title", "计算题修改");
		this.setUrl("shiti/shitiupdate10000.jsp");
		return SUCCESS;
	}
//更新计算题操作
	public void shitiupdate100002() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		String daan = request.getParameter("daan");
		String kemu = request.getParameter("kemu");

		String nandu = request.getParameter("nandu");
		String shizhidian = request.getParameter("shizhidian");
		String wenti = request.getParameter("wenti");

		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setA(a);
		bean.setB(b);
		bean.setC(c);
		bean.setD(d);
		bean.setDaan(daan);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		bean.setNandu(nandu);
		bean.setShizhidian(shizhidian);
		bean.setWenti(wenti);

		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist10000.action';</script>");
	}
//删除计算题操作
	public void shitidelete10000() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setShitilock(1);
		shitiDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shitilist10000.action';</script>");
	}
	
	//跳转到查看计算题页面
	public String shitiupdate100003() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shiti bean = shitiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "计算题查看");
		this.setUrl("shiti/shitiupdate100003.jsp");
		return SUCCESS;
	}
	

	
	
	//题库查询
	public String shitilist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");
		String wenti = request.getParameter("wenti");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		if (wenti != null && !"".equals(wenti)) {
			sb.append("wenti like '%" + wenti + "%'");
			sb.append(" and ");
			request.setAttribute("wenti", wenti);
		}
		
		
		sb.append("  shitilock=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shitiDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shitiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shitilist2.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shitilist2.action");
		request.setAttribute("url2", "method!shiti");
		request.setAttribute("title", "题库查询");
		this.setUrl("shiti/shitilist2.jsp");
		return SUCCESS;

	}
	
	private ShijuanDao shijuanDao;

	public ShijuanDao getShijuanDao() {
		return shijuanDao;
	}

	public void setShijuanDao(ShijuanDao shijuanDao) {
		this.shijuanDao = shijuanDao;
	}
	
	//试卷列表
	public String shijuanlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String juanming = request.getParameter("juanming");
		String nandu = request.getParameter("nandu");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (juanming != null && !"".equals(juanming)) {
			sb.append("juanming like '%" + juanming + "%'");
			sb.append(" and ");
			request.setAttribute("juanming", juanming);
		}
		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		sb.append("  user.id="+user.getId()+"  and shijuanlock=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shijuanDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shijuanDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shijuanlist.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shijuanlist.action");
		request.setAttribute("url2", "method!shijuan");
		request.setAttribute("title", "试卷管理");
		this.setUrl("shijuan/shijuanlist.jsp");
		return SUCCESS;

	}
//跳转到添加试卷页面
	public String shijuanadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		request.setAttribute("url", "method!shijuanadd2.action");
		request.setAttribute("title", "试卷添加");
		this.setUrl("shijuan/shijuanadd.jsp");
		return SUCCESS;
	}
//添加试卷操作
	public void shijuanadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String juanming = request.getParameter("juanming");

		String kaoshishijian = request.getParameter("kaoshishijian");
		String kemu = request.getParameter("kemu");
		String nandu = request.getParameter("nandu");


		Shijuan bean = new Shijuan();
		bean.setNandu(nandu);
		bean.setCreatetime(new Date());
		bean.setJuanming(juanming);
		bean.setKaoshishijian(kaoshishijian);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");

		bean.setUser(user);
		bean.setZujuan("未完成组卷");

		shijuanDao.insertBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shijuanlist.action';</script>");
		
	}
//跳转到更新试卷页面
	public String shijuanupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		Shijuan bean = shijuanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!shijuanupdate2.action");
		request.setAttribute("title", "试卷修改");
		this.setUrl("shijuan/shijuanupdate.jsp");
		return SUCCESS;
	}
//更新试卷操作
	public void shijuanupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String juanming = request.getParameter("juanming");

		String kaoshishijian = request.getParameter("kaoshishijian");
		String nandu = request.getParameter("nandu");
		String kemu = request.getParameter("kemu");

		Shijuan bean = shijuanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setNandu(nandu);
		bean.setJuanming(juanming);
		bean.setKaoshishijian(kaoshishijian);
		bean.setKemu(kemuDao.selectBean(" where id= "+kemu));

		shijuanDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shijuanlist.action';</script>");
	}
//删除试卷操作
	public void shijuandelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shijuan bean = shijuanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		bean.setShijuanlock(1);
		shijuanDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shijuanlist.action';</script>");
	}
	
	//跳转到查看试卷页面
	public String shijuanupdate3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shijuan bean = shijuanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "试卷查看");
		this.setUrl("shijuan/shijuanupdate3.jsp");
		return SUCCESS;
	}
	
	private ShijuanitemDao  shijuanitemDao;

	public ShijuanitemDao getShijuanitemDao() {
		return shijuanitemDao;
	}

	public void setShijuanitemDao(ShijuanitemDao shijuanitemDao) {
		this.shijuanitemDao = shijuanitemDao;
	}
	
	
	//试卷列表
	public String shijuanlist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String juanming = request.getParameter("juanming");
		String nandu = request.getParameter("nandu");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (juanming != null && !"".equals(juanming)) {
			sb.append("juanming like '%" + juanming + "%'");
			sb.append(" and ");
			request.setAttribute("juanming", juanming);
		}
		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		sb.append("  user.id="+user.getId()+" and zujuan='未完成组卷' and shijuanlock=0  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shijuanDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shijuanDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shijuanlist2.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shijuanlist2.action");
		request.setAttribute("url2", "method!shijuan");
		request.setAttribute("title", "试卷管理");
		this.setUrl("shijuan/shijuanlist2.jsp");
		return SUCCESS;

	}
	
	//完成组卷操作
	public void shijuandelete2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shijuan bean = shijuanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		bean.setZujuan("完成组卷");
		shijuanDao.updateBean(bean);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
				.getWriter()
				.print(
						"<script language=javascript>alert('操作成功');window.location.href='method!shijuanlist4.action';</script>");
	}
	
	
	//跳转到开始组卷
	public String shijuanupdate5() {
		HttpServletRequest request = ServletActionContext.getRequest();

		Shijuan bean = shijuanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!shijuanupdate6.action");
		request.setAttribute("title", "定制组卷规则");
		this.setUrl("shijuan/shijuanupdate5.jsp");
		return SUCCESS;
	}
	
	
	//num随机的数量
	private static List<Shiti> suiji(List<Shiti> list,int num){
		Collections.shuffle(list);
		List<Shiti> list2 = new ArrayList<Shiti>();
		if(list.size()<=num){
			num = list.size();
		}
		for(int i=0;i<num;i++){
			list2.add(list.get(i));
		}
		return list2;
	}
	
	//自动组卷操作
	@SuppressWarnings("static-access")
	public void shijuanupdate6() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		Shijuan bean = shijuanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		
		
		List<Shijuanitem> sjlist = shijuanitemDao.selectBeanList(0, 9999, " where shijuan.id= "+bean.getId());
		for(Shijuanitem sji:sjlist){
			sji.setShijuanitemlock(1);
			shijuanitemDao.updateBean(sji);
		}
		
		
		
		String xzt1 = request.getParameter("xzt1");
		String xzt2 = request.getParameter("xzt2");
		String xzt3 = request.getParameter("xzt3");
		String xzt4 = request.getParameter("xzt4");
		
		String pdt1 = request.getParameter("pdt1");
		String pdt2 = request.getParameter("pdt2");
		String pdt3 = request.getParameter("pdt3");
		String pdt4 = request.getParameter("pdt4");
		
		String tkt1 = request.getParameter("tkt1");
		String tkt2 = request.getParameter("tkt2");
		String tkt3 = request.getParameter("tkt3");
		String tkt4 = request.getParameter("tkt4");
		
		String wdt1 = request.getParameter("wdt1");
		String wdt2 = request.getParameter("wdt2");
		String wdt3 = request.getParameter("wdt3");
		String wdt4 = request.getParameter("wdt4");
		
		String jst1 = request.getParameter("jst1");
		String jst2 = request.getParameter("jst2");
		String jst3 = request.getParameter("jst3");
		String jst4 = request.getParameter("jst4");
		
		int cxzt1 = Integer.parseInt(xzt1);
		int cxzt2 = Integer.parseInt(xzt2);
		int cxzt3 = Integer.parseInt(xzt3);
		
		int cpdt1 = Integer.parseInt(pdt1);
		int cpdt2 = Integer.parseInt(pdt2);
		int cpdt3 = Integer.parseInt(pdt3);
		
		int ctkt1 = Integer.parseInt(tkt1);
		int ctkt2 = Integer.parseInt(tkt2);
		int ctkt3 = Integer.parseInt(tkt3);
		
		int cwdt1 = Integer.parseInt(wdt1);
		int cwdt2 = Integer.parseInt(wdt2);
		int cwdt3 = Integer.parseInt(wdt3);
		
		int cjst1 = Integer.parseInt(jst1);
		int cjst2 = Integer.parseInt(jst2);
		int cjst3 = Integer.parseInt(jst3);
		
		
		
		
		
		int ccxzt1 = shitiDao.selectBeanCount(" where shitilock=0 and  leixing='选择题'  and nandu='简单' and kemu.id= "+bean.getKemu().getId());
		int ccxzt2 = shitiDao.selectBeanCount(" where shitilock=0 and  leixing='选择题'  and nandu='中等' and kemu.id= "+bean.getKemu().getId());
		int ccxzt3 = shitiDao.selectBeanCount(" where shitilock=0 and  leixing='选择题'  and nandu='复杂' and kemu.id= "+bean.getKemu().getId());
		
		int ccpdt1 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='判断题' and nandu='简单' and kemu.id= "+bean.getKemu().getId());
		int ccpdt2 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='判断题' and nandu='中等' and kemu.id= "+bean.getKemu().getId());
		int ccpdt3 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='判断题' and nandu='复杂' and kemu.id= "+bean.getKemu().getId());
		
		int cctkt1 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='填空题' and nandu='简单'  and kemu.id= "+bean.getKemu().getId());
		int cctkt2 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='填空题' and nandu='中等'  and kemu.id= "+bean.getKemu().getId());
		int cctkt3 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='填空题' and nandu='复杂'  and kemu.id= "+bean.getKemu().getId());
		
		int ccwdt1 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='设计题' and nandu='简单' and kemu.id= "+bean.getKemu().getId());
		int ccwdt2 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='设计题' and nandu='中等' and kemu.id= "+bean.getKemu().getId());
		int ccwdt3 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='设计题' and nandu='复杂' and kemu.id= "+bean.getKemu().getId());
		
		int ccjst1 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='计算题' and nandu='简单' and kemu.id= "+bean.getKemu().getId());
		int ccjst2 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='计算题' and nandu='中等' and kemu.id= "+bean.getKemu().getId());
		int ccjst3 = shitiDao.selectBeanCount(" where shitilock=0 and leixing='计算题' and nandu='复杂' and kemu.id= "+bean.getKemu().getId());
		
		
		//判断组卷的题目数量和题库的题目数量比较，不够则提示组卷失败
		
		if(cxzt1>ccxzt1){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为简单的选择题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		if(cxzt2>ccxzt2){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为中等的选择题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		if(cxzt3>ccxzt3){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为复杂的选择题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		
		
		if(cpdt1>ccpdt1){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为简单的判断题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		if(cpdt2>ccpdt2){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为中等的判断题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		if(cpdt3>ccpdt3){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为复杂的判断题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		
		if(ctkt1>cctkt1){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为简单的填空数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		if(ctkt2>cctkt2){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为中等的填空数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		if(ctkt3>cctkt3){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为复杂的填空数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		
		if(cwdt1>ccwdt1){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为简单的设计题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		if(cwdt2>ccwdt2){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为中等的设计题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		if(cwdt3>ccwdt3){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为复杂的设计题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		if(cjst1>ccjst1){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为简单的计算题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		if(cjst2>ccjst2){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为中等的计算题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		if(cjst3>ccjst3){
			response
			.getWriter()
			.print(
					"<script language=javascript>alert('题库中科目为"+bean.getKemu().getName()+"，难度为复杂的计算题数量不够，组卷失败');" +
							"window.location.href='method!shijuanupdate5.action?id="+bean.getId()+"';</script>");
			return;
		}
		
		List<Shiti> list = new ArrayList<Shiti>();
		
		List<Shiti> xzt1list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='选择题'  and nandu='简单' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> xzt2list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='选择题'  and nandu='中等' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> xzt3list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='选择题'  and nandu='复杂' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> pdt1list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='判断题'  and nandu='简单' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> pdt2list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='判断题'  and nandu='中等' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> pdt3list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='判断题'  and nandu='复杂' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> tkt1list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='填空题'  and nandu='简单' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> tkt2list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='填空题'  and nandu='中等' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> tkt3list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='填空题'  and nandu='复杂' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> wdt1list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='设计题'  and nandu='简单' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> wdt2list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='设计题'  and nandu='中等' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> wdt3list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='设计题'  and nandu='复杂' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> jst1list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='计算题'  and nandu='简单' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> jst2list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='计算题'  and nandu='中等' and kemu.id= "+bean.getKemu().getId());
		List<Shiti> jst3list = shitiDao.selectBeanList(0, 9999, " where shitilock=0 and leixing='计算题'  and nandu='复杂' and kemu.id= "+bean.getKemu().getId());
		
		

		List<Shiti> xztlist1 =  this.suiji(xzt1list, cxzt1);
		
		for(Shiti s:xztlist1){
			list.add(s);
		}
		
		

		List<Shiti> xztlist2 =  this.suiji(xzt2list, cxzt2);
		
		for(Shiti s:xztlist2){
			list.add(s);
		}
		
		

		List<Shiti> xztlist3 =  this.suiji(xzt3list, cxzt3);
		
		for(Shiti s:xztlist3){
			list.add(s);
		}
		
		List<Shiti> pdtlist1 =  this.suiji(pdt1list, cpdt1);
		
		for(Shiti s:pdtlist1){
			list.add(s);
		}
		List<Shiti> pdtlist2 =  this.suiji(pdt2list, cpdt2);
		
		for(Shiti s:pdtlist2){
			list.add(s);
		}
		List<Shiti> pdtlist3 =  this.suiji(pdt3list, cpdt3);
		
		for(Shiti s:pdtlist3){
			list.add(s);
		}
		
		List<Shiti> tktlist1 =  this.suiji(tkt1list, ctkt1);
		
		for(Shiti s:tktlist1){
			list.add(s);
		}
		
		List<Shiti> tktlist2 =  this.suiji(tkt2list, ctkt2);
		
		for(Shiti s:tktlist2){
			list.add(s);
		}
		
		List<Shiti> tktlist3 =  this.suiji(tkt3list, ctkt3);
		
		for(Shiti s:tktlist3){
			list.add(s);
		}
		
		List<Shiti> wdtlist1 =  this.suiji(wdt1list, cwdt1);
		
		for(Shiti s:wdtlist1){
			list.add(s);
		}
		
		List<Shiti> wdtlist2 =  this.suiji(wdt2list, cwdt2);
		
		for(Shiti s:wdtlist2){
			list.add(s);
		}
		
		List<Shiti> wdtlist3 =  this.suiji(wdt3list, cwdt3);
		
		for(Shiti s:wdtlist3){
			list.add(s);
		}
		
        List<Shiti> jstlist1 =  this.suiji(jst1list, cjst1);
		
		for(Shiti s:jstlist1){
			list.add(s);
		}
		
		List<Shiti> jstlist2 =  this.suiji(jst2list, cjst2);
		
		for(Shiti s:jstlist2){
			list.add(s);
		}
		
		List<Shiti> jstlist3 =  this.suiji(jst3list, cjst3);
		
		for(Shiti s:jstlist3){
			list.add(s);
		}

		bean.setZongfen(0);
		
		for(Shiti st:list){
			Shijuanitem si = new Shijuanitem();
			if("选择题".equals(st.getLeixing())){
				si.setFenzhi(Double.parseDouble(xzt4));
			}else if("判断题".equals(st.getLeixing())){
				si.setFenzhi(Double.parseDouble(pdt4));
			}else if("填空题".equals(st.getLeixing())){
				si.setFenzhi(Double.parseDouble(tkt4));
			}else if("设计题".equals(st.getLeixing())){
				si.setFenzhi(Double.parseDouble(wdt4));
			}else if("计算题".equals(st.getLeixing())){
				si.setFenzhi(Double.parseDouble(jst4));
			}
			si.setShijuan(bean);
			si.setShiti(st);
			shijuanitemDao.insertBean(si);
			bean.setZongfen(util.Arith.add(si.getFenzhi(), bean.getZongfen()));
		}
		shijuanDao.updateBean(bean);
		
		
		response
		.getWriter()
		.print(
				"<script language=javascript>alert('完成自动组卷');" +
						"window.location.href='method!shijuanlist2.action';</script>");
		
		
		
	}
	
	
	//试卷详情列表
	public String shijuanitemlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");

		request.setAttribute("bean", shijuanDao.selectBean(" where id= "+id));
		
		List<Shijuanitem> list1 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and shiti.leixing='选择题' and shijuan.id= "+id);
		List<Shijuanitem> list2 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and shiti.leixing='判断题' and shijuan.id= "+id);
		List<Shijuanitem> list3 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and shiti.leixing='填空题' and shijuan.id= "+id);
		List<Shijuanitem> list4 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and shiti.leixing='设计题' and shijuan.id= "+id);
		List<Shijuanitem> list5 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and shiti.leixing='计算题' and shijuan.id= "+id);

		request.setAttribute("list1",list1 );
		request.setAttribute("list2",list2 );
		request.setAttribute("list3",list3 );
		request.setAttribute("list4",list4 );
		request.setAttribute("list5",list5 );
		double  fenzhi1 = 0;
		for(Shijuanitem  si:list1){
			fenzhi1 = fenzhi1+si.getFenzhi();
		}
		double  fenzhi2 = 0;
		for(Shijuanitem  si:list2){
			fenzhi2 = fenzhi2+si.getFenzhi();
		}
		double  fenzhi3 = 0;
		for(Shijuanitem  si:list3){
			fenzhi3 = fenzhi3+si.getFenzhi();
		}
		double  fenzhi4 = 0;
		for(Shijuanitem  si:list4){
			fenzhi4 = fenzhi4+si.getFenzhi();
		}
		double  fenzhi5 = 0;
		for(Shijuanitem  si:list5){
			fenzhi5 = fenzhi5+si.getFenzhi();
		}
		request.setAttribute("fenzhi1",fenzhi1 );
		request.setAttribute("fenzhi2",fenzhi2 );
		request.setAttribute("fenzhi3",fenzhi3 );
		request.setAttribute("fenzhi4",fenzhi4 );
		request.setAttribute("fenzhi5",fenzhi5 );
		

		this.setUrl("shijuanitem/shijuanitemlist.jsp");
		return SUCCESS;

	}
	
	
	//试卷列表
	public String shijuanlist3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String juanming = request.getParameter("juanming");
		String nandu = request.getParameter("nandu");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (juanming != null && !"".equals(juanming)) {
			sb.append("juanming like '%" + juanming + "%'");
			sb.append(" and ");
			request.setAttribute("juanming", juanming);
		}
		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		sb.append("  user.id="+user.getId()+" and zujuan='未完成组卷' and shijuanlock=0  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shijuanDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shijuanDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shijuanlist3.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shijuanlist3.action");
		request.setAttribute("url2", "method!shijuan");
		request.setAttribute("title", "试卷管理");
		this.setUrl("shijuan/shijuanlist3.jsp");
		return SUCCESS;

	}
	
	//试卷题目列表
	public String shijuanitemlist2() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String wenti = request.getParameter("wenti");
		String nandu = request.getParameter("nandu");
		String leixing = request.getParameter("leixing");
		String shijuanid = request.getParameter("shijuanid");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (shijuanid != null && !"".equals(shijuanid)) {

			sb.append("shijuan.id= "+shijuanid);
			sb.append(" and ");
			request.setAttribute("shijuanid", shijuanid);
		}
		
		if (wenti != null && !"".equals(wenti)) {

			sb.append(" shiti.wenti like '%" + wenti + "%'");
			sb.append(" and ");
			request.setAttribute("wenti", wenti);
		}
		
		if (nandu != null && !"".equals(nandu)) {

			sb.append(" shiti.nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		if (leixing != null && !"".equals(leixing)) {

			sb.append(" shiti.leixing like '%" + leixing + "%'");
			sb.append(" and ");
			request.setAttribute("leixing", leixing);
		}

		
		
		
		sb.append("  shijuanitemlock=0  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shijuanitemDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shijuanitemDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shijuanitemlist2.action?shijuanid="+shijuanid, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shijuanitemlist2.action");
		request.setAttribute("url2", "method!shijuanitem");
		request.setAttribute("title", "试卷题目管理");
		this.setUrl("shijuanitem/shijuanitemlist2.jsp");
		return SUCCESS;

	}
	
	
	//跳转到添加题目的页面
	public String shijuanitemadd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("shijuanid", request.getParameter("shijuanid"));
		request.setAttribute("bean", shijuanDao.selectBean(" where id= "+request.getParameter("shijuanid")));
		this.setUrl("shijuanitem/shijuanitemadd.jsp");
		return SUCCESS;
	}
	
	//筛选试题操作
	public String shanxuan() throws Exception{
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String kemuid = java.net.URLDecoder.decode(request.getParameter("kemuid"), "utf-8");
    	String leixingid = java.net.URLDecoder.decode(request.getParameter("leixingid"), "utf-8");
    	String zhishidianid = java.net.URLDecoder.decode(request.getParameter("zhishidianid"), "utf-8");
    	String nanduid = java.net.URLDecoder.decode(request.getParameter("nanduid"), "utf-8");
    	StringBuffer sb = new StringBuffer();
		sb.append(" where kemu.id="+kemuid +" and ");
		
		if(leixingid!=null&&!"".equals(leixingid)){
			sb.append(" leixing like '%"+leixingid+"%'");
			sb.append(" and ");
		}
		
		if(zhishidianid!=null&&!"".equals(zhishidianid)){
			sb.append(" shizhidian like '%"+zhishidianid+"%'");
			sb.append(" and ");
		}
		
		if(nanduid!=null&&!"".equals(nanduid)){
			sb.append(" nandu like '%"+nanduid+"%'");
			sb.append(" and ");
		}

		sb.append(" shitilock=0 order by leixing");
		String where = sb.toString();
    	
    	HttpServletResponse response = ServletActionContext.getResponse();
    	response.setContentType("text/xml");
    	response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	    response.setCharacterEncoding("UTF-8");
    	List<Shiti> list = shitiDao.selectBeanList(0, 9999, where);

        String xml = "<table border='1' ><tr><th width='120'>选择</th><th width='120'>分值</th><th width='120'>科目</th><th width='120'>难度</th>" +
        		"<th width='120'>题型</th><th width='120'>问题</th>"+
        	
        	"<th width='120'>答案</th><th width='120'>知识点</th>" +
        	"<th width='120'>选项A</th><th width='120'>选项B</th><th width='120'>选项C</th><th width='120'>选项D</th>"+
        	"</tr>";
        for(int i=0;i<list.size();i++){
        	String a ="";
        	if(list.get(i).getA()!=null)
        		a = list.get(i).getA();
        	String b ="";
        	if(list.get(i).getB()!=null)
        		b = list.get(i).getB();
        	String c ="";
        	if(list.get(i).getC()!=null)
        		c = list.get(i).getC();
        	String d ="";
        	if(list.get(i).getD()!=null)
        		d = list.get(i).getD();
        	
        	xml+="<tr>";
        	xml+="<td><input  name='ids' type='checkbox'  value='"+list.get(i).getId()+"' /></td>";
        	xml+="<td><input  name='fenzhi"+i+"' type='text'  size='3' /></td>";
        	xml+="<td>"+list.get(i).getKemu().getName()+"</td>";
        	xml+="<td>"+list.get(i).getNandu()+"</td>";
        	xml+="<td>"+list.get(i).getLeixing()+"</td>";
        	xml+="<td>"+list.get(i).getWenti()+"</td>";
        
        	xml+="<td>"+list.get(i).getDaan()+"</td>";
        	xml+="<td>"+list.get(i).getShizhidian()+"</td>";
        	xml+="<td>"+a+"</td>";
        	xml+="<td>"+b+"</td>";
        	xml+="<td>"+c+"</td>";
        	xml+="<td>"+d+"</td>";
        	xml+="</tr>";
        	
        }
        xml+="<tr>";
        xml+="<td><input  name='a' type='hidden'  value='"+list.size()+"' /></td>";
        xml+="</tr>";
        response.getWriter().write(xml);
        return null;
    }
	
	//添加试卷题目操作
	public void shijuanitemadd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String shijuanid = request.getParameter("shijuanid");
		Shijuan shijuan = shijuanDao.selectBean(" where id= "+shijuanid);
		String[] ids = request.getParameterValues("ids");
		String a = request.getParameter("a");
		List<String> fenzhi = new ArrayList<String>();
		for(int i=0;i<Integer.parseInt(a);i++){
			String fz = request.getParameter("fenzhi"+i);
			if(fz!=null&&!"".equals(fz))
				fenzhi.add(fz);
		}
		int c1 = 0;
		int c2 = 0;
		for(int i = 0;i<ids.length;i++){
			Shijuanitem bean = shijuanitemDao.selectBean(" where shijuanitemlock=0 and  shiti.id= "+ids[i] +" and shijuan.id= "+shijuan.getId());
			if(bean==null){
				bean = new Shijuanitem();
				bean.setShijuan(shijuan);
				bean.setFenzhi(Double.parseDouble(fenzhi.get(i)));
				bean.setShiti(shitiDao.selectBean(" where id= "+ids[i]));
		
				shijuanitemDao.insertBean(bean);
				shijuan.setZongfen(shijuan.getZongfen()+Double.parseDouble(fenzhi.get(i)));
				shijuanDao.updateBean(shijuan);
				c1++;
			}else{
				c2++;
			}
			
		}
		
	
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
		.getWriter()
		.print(
				"<script language=javascript>alert('操作成功,成功添加"+c1+"道题目，重复"+c2+"道');" +
						"window.location.href='method!shijuanitemlist2.action?shijuanid="+shijuanid+"';</script>");
	}
	
	//跳转到更新试题分值的页面
	public String shijuanitemupdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Shijuanitem bean = shijuanitemDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("shijuanid", request.getParameter("shijuanid"));
		this.setUrl("shijuanitem/shijuanitemupdate.jsp");
		return SUCCESS;
	}
//更新试题分值的操作
	public void shijuanitemupdate2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String shijuanid = request.getParameter("shijuanid");
		String fenzhi = request.getParameter("fenzhi");
		Shijuanitem bean = shijuanitemDao.selectBean(" where id= "
				+ request.getParameter("id"));
		Shijuan shijuan = shijuanDao.selectBean(" where id= "+shijuanid);
		shijuan.setZongfen(shijuan.getZongfen()-bean.getFenzhi()+Double.parseDouble(fenzhi));
		shijuanDao.updateBean(shijuan);
		bean.setFenzhi(Double.parseDouble(fenzhi));
		shijuanitemDao.updateBean(bean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
		.getWriter()
		.print(
				"<script language=javascript>alert('操作成功');" +
						"window.location.href='method!shijuanitemlist2.action?shijuanid="+shijuanid+"';</script>");
	}
//删除试题的操作
	public void shijuanitemdelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String shijuanid = request.getParameter("shijuanid");
		Shijuanitem bean = shijuanitemDao.selectBean(" where id= "
				+ request.getParameter("id"));
		Shijuan shijuan = shijuanDao.selectBean(" where id= "+shijuanid);
		shijuan.setZongfen(shijuan.getZongfen()-bean.getFenzhi());
		shijuanDao.updateBean(shijuan);
		bean.setShijuanitemlock(1);
		
		shijuanitemDao.updateBean(bean);
		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response
		.getWriter()
		.print(
				"<script language=javascript>alert('操作成功');" +
						"window.location.href='method!shijuanitemlist2.action?shijuanid="+shijuanid+"';</script>");
	}
	
	
	//试卷列表
	public String shijuanlist4() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String juanming = request.getParameter("juanming");
		String nandu = request.getParameter("nandu");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (juanming != null && !"".equals(juanming)) {
			sb.append("juanming like '%" + juanming + "%'");
			sb.append(" and ");
			request.setAttribute("juanming", juanming);
		}
		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		sb.append("  user.id="+user.getId()+" and zujuan='未完成组卷' and shijuanlock=0 order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shijuanDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shijuanDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shijuanlist4.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shijuanlist4.action");
		request.setAttribute("url2", "method!shijuan");
		request.setAttribute("title", "试卷管理");
		this.setUrl("shijuan/shijuanlist4.jsp");
		return SUCCESS;

	}
	
	//试卷打印列表
	public String shijuanlist5() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String juanming = request.getParameter("juanming");
		String nandu = request.getParameter("nandu");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (juanming != null && !"".equals(juanming)) {
			sb.append("juanming like '%" + juanming + "%'");
			sb.append(" and ");
			request.setAttribute("juanming", juanming);
		}
		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		sb.append("  user.id="+user.getId()+" and zujuan='完成组卷' and shijuanlock=0  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shijuanDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shijuanDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shijuanlist5.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shijuanlist5.action");
		request.setAttribute("url2", "method!shijuan");
		request.setAttribute("title", "试卷打印管理");
		this.setUrl("shijuan/shijuanlist5.jsp");
		return SUCCESS;

	}
	
	
	//打印试卷
	public String shijuanitemlist3() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");

		request.setAttribute("bean", shijuanDao.selectBean(" where id= "+id));
		
		List<Shijuanitem> list1 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and  shiti.leixing='选择题' and shijuan.id= "+id);
		List<Shijuanitem> list2 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and shiti.leixing='判断题' and shijuan.id= "+id);
		List<Shijuanitem> list3 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and shiti.leixing='填空题' and shijuan.id= "+id);
		List<Shijuanitem> list4 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and shiti.leixing='设计题' and shijuan.id= "+id);
		List<Shijuanitem> list5 = shijuanitemDao.selectBeanList(0, 9999, " where shijuanitemlock=0 and shiti.leixing='计算题' and shijuan.id= "+id);

		request.setAttribute("list1",list1 );
		request.setAttribute("list2",list2 );
		request.setAttribute("list3",list3 );
		request.setAttribute("list4",list4 );
		request.setAttribute("list5",list5 );
		double  fenzhi1 = 0;
		for(Shijuanitem  si:list1){
			fenzhi1 = fenzhi1+si.getFenzhi();
		}
		double  fenzhi2 = 0;
		for(Shijuanitem  si:list2){
			fenzhi2 = fenzhi2+si.getFenzhi();
		}
		double  fenzhi3 = 0;
		for(Shijuanitem  si:list3){
			fenzhi3 = fenzhi3+si.getFenzhi();
		}
		double  fenzhi4 = 0;
		for(Shijuanitem  si:list4){
			fenzhi4 = fenzhi4+si.getFenzhi();
		}
		double  fenzhi5 = 0;
		for(Shijuanitem  si:list5){
			fenzhi5 = fenzhi5+si.getFenzhi();
		}
		request.setAttribute("fenzhi1",fenzhi1 );
		request.setAttribute("fenzhi2",fenzhi2 );
		request.setAttribute("fenzhi3",fenzhi3 );
		request.setAttribute("fenzhi4",fenzhi4 );
		request.setAttribute("fenzhi5",fenzhi5 );
		

		this.setUrl("shijuanitem/shijuanitemlist3.jsp");
		return SUCCESS;

	}
	
	
	//试卷查询列表
	public String shijuanlist6() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Kemu> kemulist = kemuDao.selectBeanList(0, 9999, " where kemulock=0 " );
		request.setAttribute("kemulist", kemulist);
		
		String kemu = request.getParameter("kemu");
		String juanming = request.getParameter("juanming");
		String nandu = request.getParameter("nandu");

		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (kemu != null && !"".equals(kemu)) {

			sb.append("kemu.id= "+kemu+" ");
			sb.append(" and ");
			request.setAttribute("kemu", kemu);
		}

		if (juanming != null && !"".equals(juanming)) {
			sb.append("juanming like '%" + juanming + "%'");
			sb.append(" and ");
			request.setAttribute("juanming", juanming);
		}
		if (nandu != null && !"".equals(nandu)) {
			sb.append("nandu like '%" + nandu + "%'");
			sb.append(" and ");
			request.setAttribute("nandu", nandu);
		}
		
		
		
		
		sb.append("  shijuanlock=0  and zujuan='完成组卷'  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 6;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = shijuanDao.selectBeanCount(where.replaceAll(" order by id desc ", ""));
		request.setAttribute("list", shijuanDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!shijuanlist6.action?kemu="+kemu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!shijuanlist6.action");
		request.setAttribute("url2", "method!shijuan");
		request.setAttribute("title", "试卷查询管理");
		this.setUrl("shijuan/shijuanlist6.jsp");
		return SUCCESS;

	}
	

	
}
